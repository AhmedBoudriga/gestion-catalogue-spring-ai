# Gestion Catalogue : agent IA (Spring Boot, Spring AI, Angular)

## Prérequis (Windows)

Java 21, PostgreSQL, Node.js 20.19+ (ou 22.12+), Git, et une clé API Mistral gratuite (https://console.mistral.ai, menu API Keys).

Installer Angular CLI une seule fois :

```powershell
npm install -g @angular/cli
```

## 1. Cloner le projet

```powershell
git clone https://github.com/AhmedBoudriga/gestion-catalogue-spring-ai.git
cd gestion-catalogue-spring-ai
```

## 2. Configurer `GestionCatalogue\src\main\resources\application.properties`

Ouvre ce fichier et remplace les valeurs suivantes :

```properties
# Colle ta clé Mistral à la place de ${MISTRAL_API_KEY}
spring.ai.mistralai.api-key=TA_CLE_MISTRAL

# Ton mot de passe PostgreSQL à la place de ${DB_PASSWORD}
spring.datasource.password=TON_MOT_DE_PASSE
```

Regarde aussi la ligne `spring.datasource.url` : elle indique le **port** et le **nom de la base** que l'application attend, par exemple :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/test
```

Ici : port `5433`, base `test`. Utilise ces valeurs à l'étape suivante. Si ton PostgreSQL tourne sur un autre port (5432 par défaut), modifie le port dans cette ligne.

## 3. Créer la base de données

Crée une base portant **exactement le nom indiqué dans `spring.datasource.url`** (ici `test`).

Avec le terminal :

```powershell
psql -U postgres -p 5433 -c "CREATE DATABASE test;"
```

Ou avec pgAdmin : clic droit sur Databases, Create, Database, puis le nom. Les tables sont créées automatiquement au premier lancement.

## 4. Lancer le backend (port 8080)

```powershell
cd GestionCatalogue
.\mvnw.cmd spring-boot:run
```

Laisse ce terminal ouvert. Vérification : http://localhost:8080/swagger-ui/index.html

## 5. Lancer le frontend (port 4200)

Dans un **second** terminal :

```powershell
cd assistant-boutique
npm install
npm start
```

Ouvre http://localhost:4200

## Dépannage

- **npm : l'exécution de scripts est désactivée** : lance `Set-ExecutionPolicy -Scope CurrentUser RemoteSigned` dans PowerShell.
- **Erreur 401 ou 429** : clé Mistral absente ou invalide, ou limite du plan gratuit atteinte.
- **Erreur de connexion à la base** : vérifie que PostgreSQL tourne, que la base existe, et que le port et le mot de passe correspondent à `application.properties`.
- **404 dans le chat Angular** : relance avec `npm start` (et non `ng serve`).
