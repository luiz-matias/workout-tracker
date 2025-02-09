<div id="top"></div>

<br />
<div align="center">
  <!-- <img src="https://i.imgur.com/42m4MaA.png" alt="Project Logo" height="150"> -->
  <h1 align="center">Workout Tracker - Backend</h1>
  <p align="center">
    A service that tracks your gym workouts along with your friends to provide insights about your routine and competition with your closed ones.
  </p>
</div>

# Project Features

The application will have the following features:

## Authentication

### Login

As a user, I want to be able to log in to the application to access my account safely.

### Registration

As a user, I want to register my account into the application so that I can have access to the platform.

### Password Recovery

As a user, I want to be able to recover my password in case I missed it so that I can recover my access to the platform.

## Account Management

### Edit Profile

As a user, I want to edit my account information to keep my profile updated.

### Manage photo

As a user, I want to manage my profile photo so other users can see me.

### Change Password

As a user, I want to change my password to protect my account whenever necessary.

### Delete Account

As a user, I want to delete my account to remove all my sensitive data from the platform.

## Creating and Managing Workouts

### List of Workout Routines

As a user, I want to list all workout routines I created.

### Create Workout Routines

As a user, I want to create workout routines according to my existing workout plans.

### Edit Workout Routines

As a user, I want to edit my workout routines.

### Delete Workout Routines

As a user, I want to delete my workout routines.

## Workout Logs

### List Workout Logs

As a user, I want to list all my workout logs.

### Create Workout Log

As a user, I want to log a workout into my profile.

### Edit Workout Log

As a user, I want to edit a workout log.

## Admin Management

### Manage users

As an admin, I want to manage the users of my platform.

### Manage Workout Exercises

As an admin, I want to manage all the exercises available to insert into a workout routine.

___

# Main Tools

## [Spring Boot](https://spring.io/projects/spring-boot)

<div align="center">
  <img src="https://i.imgur.com/eB4qk7Z.png" alt="Spring Boot" height="150">
</div>

<br />

Spring Boot is a framework extension from Spring, allowing us to develop backend services without too much traction to deal with Java Servlets.

# Roadmap

## Basic Business Rules

- [ ] Project Setup
    - [X] Readme About the Project
    - [X] List of epics and stories
    - [X] Environment Preparation
- [ ] Authentication
    - [X] Login
    - [ ] Email activation
    - [X] Registration
    - [ ] 2FA through TOTP
    - [ ] Password Recovery
- [ ] Account Management
    - [ ] Edit Profile
    - [ ] Manage Photo
    - [ ] Change Password
    - [ ] Delete Account
- [ ] Workout Routines
    - [ ] List Workout Routines
    - [ ] Create Workout Routines
    - [ ] Edit Workout Routines
    - [ ] Delete Workout Routines
- [ ] Workout Logs
    - [ ] List Workout Logs
    - [ ] Create Workout Logs
    - [ ] Edit Workout Logs
    - [ ] Delete Workout Logs
- [ ] Admin Management
    - [ ] Manage Users
      - [ ] Lock users
      - [ ] Ban users
    - [ ] Manage Moderators
    - [ ] Manage Available Exercises

## Tech Features Wishlist

- [X] Security
    - [X] Authentication via JWT
    - [ ] OAuth2
    - [X] Strong password requirements
    - [X] Password encryption
    - [ ] Role-based routes
- [ ] Email services
    - [ ] Account activation
    - [ ] Password change
    - [ ] Password recovery
    - [ ] Password changed confirmation
- [ ] Listing basics
    - [ ] Pagination
    - [ ] Sorting
- [ ] CI/CD
    - [ ] PR pipeline
        - [ ] Run lint
        - [ ] Run tests
        - [ ] Code coverage analysis
    - [ ] Staging pipeline
        - [ ] Automated deployment in staging environment
        - [ ] Clone production data everyday
    - [ ] Production pipeline
        - [ ] Automated deployment in production environment
        - [ ] Automated database backups everyday
- [ ] Database
    - [ ] Migrations
    - [X] Pre-populate data for dev environments
- [ ] Analytics
    - [ ] Logging
    - [ ] Analytics Dashboard
    - [ ] Error Dashboard
- [ ] Storage
    - [ ] User profile pictures
    - [ ] Thumbnails

# License

Distributed under the MIT License. See `LICENSE` for more information.

# Contact

Luiz Matias - [LinkedIn](https://www.linkedin.com/in/luizmatiasdev/) - contact@luizmatias.com
