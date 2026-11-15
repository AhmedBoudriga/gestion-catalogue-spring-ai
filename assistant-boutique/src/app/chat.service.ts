import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private http = inject(HttpClient);

  ask(question: string): Observable<string> {
    return this.http.get('/agent', {
      params: { message: question },
      responseType: 'text', // le backend renvoie du texte, pas du JSON
    });
  }
}
