# hotelManagementAppSpringBoot
Demo Project for Spring Boot, Spring Mvc, Spring Security, Validation, JPA, Hibernate, MySql, Thymeleaf

It is a basic hotel management application with three types of user roles (admin, manager, and employee).
Thymeleaf is used as a template engine.

What the roles can do:
-Admin role can list rooms, add new rooms, update existing ones, and delete if the room hasn't any reservations.
-Manager role can list rooms, list room reservations, make new future-dated reservations, and cancel existing ones.
-Employee role can only list available rooms, search available rooms with the current date as reservation check-in date and check-out date, and make new reservations with check-in date starting from the current date.


To make it run:
1-Run sql-scripts on MySQL Workbench
2-Run the app on your IDE
3-Go to http://localhost:7070/ on your browser
