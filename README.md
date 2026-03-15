# Smart Restaurant Reservation System

A full-stack restaurant reservation application with smart table recommendation and interactive table layout.

The system allows users to search for available tables based on date, time, party size and preferences.

# Features

## Table layout visualization
Interactive visual layout of restaurant tables that shows:
- available tables
- occupied tables
- recommended table for the selected request

## Smart table recommendation
The system recommends the best table based on:
- party size
- table capacity
- user preferences
- table availability

## Reservation conflict detection
- The system prevents double booking of tables.
- A table is considered unavailable when reservation time intervals overlap.
- Each reservation has a default duration of **2 hours**.

## User preferences
Users can specify seating preferences such as:
- window seat
- quiet area
- near kids area
- accessible table

These preferences influence the recommendation score.

## Reservation simulation
To make the system realistic, reservations are randomly generated for **today and the next 14 days**, creating realistic table availability scenarios.

## Unit tests
Core backend logic is covered by automated unit tests:
- reservation conflict detection
- recommendation scoring logic

## Docker support
The application can be started using Docker Compose, which launches both:
- backend (Spring Boot)
- frontend (React)

# Tech Stack

Backend
- Java
- Spring Boot
- Maven

Frontend
- React
- TypeScript
- Vite

DevOps
- Docker
- Docker Compose

Testing
- JUnit

# How to run the project

## Option 1 — Run locally

Backend: 
  ./mvnw spring-boot:run

Frontend:
  cd frontend
  npm install
  npm run dev

Application will be available at:
  http://localhost:5173

## Option 2 — Run with Docker

docker compose up --build

Application will be available at:
  http://localhost:5173

# Architecture

Frontend communicates with backend using REST API.

Main components:

- TableService – searches for available tables
- ReservationService – handles reservation creation and conflict detection
- RecommendationService – calculates table recommendation score

# Recommendation Logic

Tables are scored based on:

• exact capacity match  
• extra seat penalty  
• user preferences (window, quiet, accessible)  
• conflict between quiet preference and kids area  

The system recommends the highest scoring table.

# Time Spent 

Development time: 68 hours


