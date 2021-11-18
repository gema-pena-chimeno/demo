# Demo data

Contains the entities od the common database:

## Tables

customer: contains the customer managed by Customer App.
user-info: contains the user managed by User App and used to access to Customer and User App (using Spring Security)

## Roles

Defined in UserRole enum: ADMIN and USER.

ADMIN users can manage add, update and remove users through the User App.
USER and ADMIN users can manage add, update and remove customers through the Customer App.