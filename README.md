# Personal Finance Manager

## Overview
This is a personal finance manager system that helps users track their transaction, categories, and savings goals. Database used is in-memeory h2 database.

## Features
- User registration and login
- Add, view, update, and delete financial transactions
- custom categories
- Set and track savings goals

## Setup
1. Clone the repository
2. Navigate to the project directory
3. Run the application: `./mvnw spring-boot:run`
4. Access the application at `http://localhost:6969`

## Hitting The End Points
1. Initially, user needs to hit `http://localhost:6969/api/users/register` and `http://localhost:6969/api/users/login`. To register user needs to provide username, password, totalAmount, name, email.
2. After registering, user need to login by giving username and password in request body: x-www-form-urlencoded format
3. Then start with creating new transactions `http://localhost:6969/api/transactions`. similarly with saving goals, custom category. Hit the APIs on postman.
4. Provide user_id, with other requirements as per model in the request.
5. Unit Tests have also been written. You can check the working of function thorugh those unit tests , without hitting any end-points. 
6. I will be still improving this. Feel free to conatact me.
