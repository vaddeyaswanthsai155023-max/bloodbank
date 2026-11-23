# Blood Bank Management System

A Java web application where a blood bank manages donors and blood stock, hospitals request blood, and admins approve or reject requests with stock updating automatically.

## Problem statement
Blood banks and hospitals often track donors, stock and requests in registers or spreadsheets. This makes it slow to find a matching blood group in an emergency, and expired units can go unnoticed. This project provides a web application that manages donors, stock with expiry dates, and hospital requests.

## Features
- Registration, login, logout and session timeout (15 minutes), with passwords hashed using BCrypt
- Role-based access (Admin, Donor, Hospital) enforced by a servlet filter
- **Donor:** create and update a profile (blood group, date of birth, phone, city); age 18+ rule
- **Admin:** record donations (stock expires after 35 days), 90-day gap rule between donations, view available stock by blood group, approve or reject requests
- **Hospital:** submit blood requests and track status (Pending, Approved, Rejected)
- Business rules: approval fails with `InsufficientStockException` if stock is too low; expired units are excluded
- Validation: email format, 10-digit phone, password length, units range
- Custom error page

## Tech stack
Java 17, Servlets, JSP, JSTL and EL, Hibernate 5 (ORM), MySQL, Maven, Apache Tomcat 9, jBCrypt

## Database
Tables: `users`, `donors`, `blood_stock`, `blood_requests`. The script is in `sql/schema.sql`.

## Setup
1. Install JDK 17, Apache Tomcat 9 and MySQL (XAMPP works).
2. Run `sql/schema.sql` in MySQL (phpMyAdmin > SQL tab).
3. Check the database settings in `src/main/resources/hibernate.cfg.xml` (default: user `root`, empty password, port 3306).
4. Open the project in NetBeans, run **Clean and Build**, then **Run** on Tomcat 9.
5. Open `http://localhost:8080/bloodbank/register`.
6. To create an admin, register a user, then in phpMyAdmin set that user's `role` to `ADMIN`.

## Demo accounts
| Role | Email | Password |
|---|---|---|
| Admin | admin@example.com | Admin@1234 |
| Donor | test@example.com | Test@1234 |
| Hospital | hospital@example.com | Hospital@1234 |

## Screenshots
Add images in a `screenshots/` folder and link them here: login, donor profile, admin stock, approvals with the insufficient-stock message, error page.

## Author
[Vadde Yaswanth Sai], [92600119333], [Marwadi University]