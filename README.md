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

# Challenges

During the development of the project several technical challenges were encountered.

### Recommendation algorithm design

One of the most challenging parts was designing the **table recommendation algorithm**.  
The system needed to recommend the best table based on several factors at the same time:

- party size
- table capacity
- user preferences (window, quiet, accessible, near kids area)
- minimizing unused seats
- avoiding conflicts between preferences (for example quiet table near kids area)

The algorithm had to balance these factors using a scoring system so that the recommended table would be the most suitable option for the guest.

### Reservation conflict detection

Another important challenge was implementing **reservation overlap detection**.  
The system needs to ensure that two reservations cannot be made for the same table at overlapping times.

This required careful implementation of time interval logic to correctly detect conflicts between existing reservations and new booking requests.

### Reservation simulation

To make the application more realistic, random reservations are generated for **today and the next 14 days**.  
Designing this simulation required creating a balance between available and occupied tables so that the system behaves like a real restaurant environment.

### Docker configuration

Initially there were several issues configuring **Docker and Docker Compose** for the project.  
The main challenge was correctly setting up both containers (backend and frontend) so that:

- the backend runs on port 8080
- the frontend runs on port 5173
- the services can communicate properly

After resolving configuration issues, the application can now be started with a single command using Docker.

### Frontend development with React

Frontend development was also challenging because **this was my first experience working with React**.  
Implementing the interactive table layout, state management, and communication with the backend API required learning several React concepts such as:

- React components
- hooks
- state management
- API calls from the frontend

Despite the initial learning curve, React made it possible to build an interactive and responsive user interface.

# Use of AI

AI tools (ChatGPT) were used during development primarily to assist with the **frontend implementation using React**.

Since this was my first experience working with React, AI was used to:
- understand React component structure
- generate UI component
- assist with state management and API integration
- help structure the table layout visualization
