# Bibli-API — Gestion d’une bibliothèque en REST API
## Réalisé par Marin Vandelet et Yanis Buhot

Une API REST développée avec **Spring Boot**, **JPA** et **MySQL (via XAMPP)** permettant de gérer :

- Les **auteurs**
- Les **livres**
- Les **statistiques** liées à la bibliothèque
- La **validation**
- La **gestion des erreurs**
- La **sécurisation par API Key**

---

## Description du projet

Bibli-API est un service back-end permettant de gérer une bibliothèque.
Il offre toutes les opérations CRUD sur les auteurs et les livres ainsi que plusieurs fonctionnalités avancées :

- Filtre API-Key obligatoire : `X-API-KEY: 12345`
- Validation automatique des entrées
- Gestion globale des erreurs
- Statistiques :
  - nombre total d’auteurs
  - nombre total de livres
  - répartition des livres par catégorie
- Recherche des livres avec filtres (titre, catégorie, auteur, années)

---

## Technologies utilisées

| Outil | Description |
|-------|-------------|
| **Java 17** | Langage utilisé |
| **Spring Boot 4** | Framework principal |
| **Spring Data JPA** | Accès aux données |
| **MySQL 8 (XAMPP)** | Base de données |
| **Postman** | Tests API |
| **Maven** | Gestion du build |

---

## Installation & lancement du projet

### 1) Prérequis

- XAMPP installé (MySQL activé)
- Java 17
- Maven
- IntelliJ ou autre IDE

### 2) Créer la base de données

```sql
CREATE DATABASE bibliotheque;
```

### 3) Vérifier la configuration MySQL (`application.properties`)

```
spring.datasource.url=jdbc:mysql://localhost:3306/bibliotheque?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
app.api-key=12345
server.port=8080
```

### 4) Lancer l’application

Via IntelliJ :  
**Run BibliApiApplication**

Ou via terminal :

```
mvn spring-boot:run
```

⚠️ Si une erreur de port apparaît, il suffit de changer dans **application.properties** et donc de changer le port dans les tests Postman.


---

# Test de l’API avec Postman

⚠️ Toutes les requêtes doivent contenir ce header :

```
X-API-KEY : 12345
```

---

# 1. Les Auteurs

---

## Créer un auteur

```
POST http://localhost:8080/authors
```

Body :

```
{
  "firstName": "Victor",
  "lastName": "Hugo",
  "birthYear": 1802
}
```

Réponse :

```
{
  "id": 1,
  "firstName": "Victor",
  "lastName": "Hugo",
  "birthYear": 1802
}
```

---

# 2. Les livres

---

## Créer un livre

```
POST http://localhost:8080/books
```

Body :

```
{
  "title": "Les Misérables",
  "isbn": "ISBN-123",
  "year": 1862,
  "category": "NOVEL",
  "authorId": 1
}
```

---

# 3. Tests des statistiques

---

## Nombre total de livres

```
GET http://localhost:8080/stats/books
```

---

## Nombre total d’auteurs

```
GET http://localhost:8080/stats/authors
```

---

## Nombre de livres par catégorie

```
GET http://localhost:8080/stats/books/by-category
```

---

# Autres Tests pour voir si l'API fonctionne

```
DELETE http://localhost:8080/books/1
GET http://localhost:8080/books/1
GET http://localhost:8080/books?category=ESSAY
GET http://localhost:8080/books?title=les
GET http://localhost:8080/books?fromYear=1800&toYear=1950
```

