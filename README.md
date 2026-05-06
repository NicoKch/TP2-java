```
# FoodLink API

API REST Spring Boot pour la gestion des commandes du réseau de restaurants FoodLink.

## Stack technique

- Java 21 / Spring Boot 4.0.6
- Spring Security + JWT (OAuth2 Resource Server, HS256)
- Spring Data JPA + H2 (base de données in-memory)
- Lombok

## Lancer l'application

```bash
mvn spring-boot:run
```

L'application démarre sur le port `9005`.

## Authentification

Les routes employé sont protégées par un Bearer token JWT.

**Récupérer un token :**
POST /api/login
Content-Type: application/json
{
"username": "employe",
"password": "password"
}

Utiliser le token retourné dans le header des requêtes protégées :
Authorization: Bearer <token>

## Routes

### Client (public)

| Méthode | Route              | Description                      |
|---------|--------------------|----------------------------------|
| GET     | `/api/menu`        | Consulter la carte du restaurant |
| POST    | `/api/orders`      | Passer une commande              |
| GET     | `/api/orders/{id}` | Consulter une commande par id    |

**Exemple body pour passer commande** (menuItemId: quantité) :

```json
{
  "1": 2,
  "3": 1
}
```

### Employé (authentifié)

| Méthode | Route                                                | Description                        |
|---------|------------------------------------------------------|------------------------------------|
| GET     | `/api/employee/orders/waiting`                       | Consulter les commandes en attente |
| PATCH   | `/api/employee/orders/{orderId}/assign/{employeeId}` | S'affecter une commande            |
| PATCH   | `/api/employee/orders/{orderId}/status`              | Changer le statut d'une commande   |

**Exemple body pour changer le statut :**

```json
{
  "status": "READY"
}
```

**Statuts disponibles :** `WAITING` → `IN_PREPARATION` → `READY` → `SERVED`

## Données de test

Au démarrage, l'application insère automatiquement :

- 1 compte employé : `employe` / `password`
- 2 employés : Alice, Bob
- 3 éléments de carte : Burger (12.50€), Frites (4.00€), Coca (2.50€)

## Console H2

Accessible sur `http://localhost:9005/h2-console`

- JDBC URL : `jdbc:h2:mem:testdb`
- Username : `SA`
- Password : *(vide)*

## Lancer les tests

```bash
mvn test
```