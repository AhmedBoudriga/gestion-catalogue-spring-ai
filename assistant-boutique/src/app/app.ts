import { Component, ElementRef, inject, signal, viewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { marked } from 'marked';
import { ChatService } from './chat.service';

interface Message {
  role: 'user' | 'bot';
  text: string;
  html?: string; // réponse de l'agent convertie du Markdown en HTML
}

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private chat = inject(ChatService);
  private zone = viewChild<ElementRef<HTMLElement>>('zone');

  messages = signal<Message[]>([
    { role: 'bot', text: 'Bonjour ! Posez-moi vos questions sur le catalogue.' },
  ]);
  loading = signal(false);
  seconds = signal(0);
  input = '';
  private timer?: ReturnType<typeof setInterval>;

  ask(question: string): void {
    question = question.trim();
    if (!question || this.loading()) return;

    this.input = '';
    this.messages.update((m) => [...m, { role: 'user', text: question }]);
    this.startWait();

    this.chat.ask(question).subscribe({
      next: (res) => this.reply(res),
      error: (err) =>
        this.reply(
          err.status === 0 ? "Impossible de joindre l'agent." : 'Erreur HTTP ' + err.status,
          false,
        ),
    });
  }

  // Pré-remplit le champ sans envoyer (pour modifier le texte avant)
  fill(text: string): void {
    this.input = text;
  }

  private reply(text: string, markdown = true): void {
    this.stopWait();
    const html = markdown ? (marked.parse(text, { async: false }) as string) : undefined;
    this.messages.update((m) => [...m, { role: 'bot', text, html }]);
    this.scrollDown();
  }

  private startWait(): void {
    this.seconds.set(0);
    this.loading.set(true);
    this.timer = setInterval(() => this.seconds.update((s) => s + 1), 1000);
    this.scrollDown();
  }

  private stopWait(): void {
    clearInterval(this.timer);
    this.loading.set(false);
  }

  private scrollDown(): void {
    setTimeout(() => {
      const el = this.zone()?.nativeElement;
      if (el) el.scrollTop = el.scrollHeight;
    });
  }
}
