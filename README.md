# Personal Finance Manager

## Overview
This is a personal finance manager system that helps users track their transaction, categories, and savings goals. 

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
2. After registering, user need to login by giving

## Testing
Run the unit tests using the following command:
