<div id="top"></div>

<br />
<div align="center">
  <!-- <img src="https://i.imgur.com/42m4MaA.png" alt="Project Logo" height="150"> -->
  <h1 align="center">Workout Tracker - Backend</h1>
  <p align="center">
    A service that tracks your gym workouts along with your friends to provide insights about your routine and competition with your closed ones.
  </p>
</div>

# Roadmap

## Basic Business Rules (Functional Requirements)

- [X] Project Setup
    - [X] Readme About the Project
    - [X] List of epics and stories
    - [X] Environment Preparation
- [ ] Authentication
    - [X] Login
    - [X] Email activation
    - [X] Registration
    - [ ] 2FA through TOTP
    - [X] Password Recovery
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
- [ ] Group Management
    - [X] Create Group
    - [X] Create Invite
    - [ ] Edit Group
    - [ ] Delete Group
    - [ ] Quit from Group
- [ ] Report System
    - [ ] Report User
    - [ ] Report Post
- [ ] Admin Management
    - [ ] Manage Users
      - [ ] Lock users temporarily
      - [ ] Ban users
    - [ ] Manage Reports
        - [ ] List of Reports
        - [ ] Accept Reports
        - [ ] Decline Reports
    - [ ] Manage Available Exercises
        - [ ] Create Exercises
        - [ ] Edit Existing Exercises
        - [ ] "Delete" Exercises

## Tech Features (Non-functional Requirements)

- [X] Security
    - [X] Authentication via JWT
    - [ ] OAuth2
    - [X] Strong password requirements
    - [X] Password encryption
    - [ ] Role-based routes
- [ ] Email services
    - [X] Account activation
    - [ ] Password change
    - [X] Password recovery
    - [X] Password changed confirmation
- [X] Listing basics
    - [X] Pagination
    - [X] Sorting
- [ ] CI/CD
    - [ ] PR pipeline
        - [X] Run lint
        - [X] Run tests
        - [ ] Code coverage analysis
    - [ ] Staging pipeline
        - [ ] Automated deployment in staging environment (any push on staging branch)
    - [ ] Production pipeline
        - [ ] Automated deployment in production environment (any push on main branch)
        - [ ] Automated database backups everyday
- [ ] Database
    - [ ] Migrations
    - [X] Pre-populate data for dev environments (database seeder)
- [ ] Analytics
    - [ ] Logging
    - [ ] Analytics Dashboard
    - [ ] Error and Health Check Dashboard
    - [ ] Automated alerts
- [ ] Storage (CDN)
    - [ ] User profile pictures
    - [ ] Thumbnails
    - [ ] Workout images
     
___

# Main Tools

## [Spring Boot](https://spring.io/projects/spring-boot)

<div align="center">
  <img src="https://i.imgur.com/eB4qk7Z.png" alt="Spring Boot" height="150">
</div>

<br />

Spring Boot is a framework extension from Spring, allowing us to develop backend services without too much traction to deal with Java Servlets.

___

# Detailed Project Features

The application will have the following features:

## Authentication

### Login

As an user, I want to be able to log in to the application to access my account safely.

### Registration

As an user, I want to register my account into the application so that I can have access to the platform.

### Password Recovery

As an user, I want to be able to recover my password in case I missed it so that I can recover my access to the platform.

### Email Verification

As an user, I want to be able to verify my email so I can provide a trustful resource to recover my access to my account.

## Account Management

### Edit Profile

As an user, I want to edit my account information to keep my profile updated.

### Manage photo

As an user, I want to manage my profile photo so other users can see me.

### Change Password

As an user, I want to change my password to protect my account whenever necessary.

### Delete Account

As an user, I want to delete my account to remove all my sensitive data from the platform.

## Creating and Managing Workouts

### List of Workout Routines

As an user, I want to list all workout routines I created.

### Create Workout Routines

As an user, I want to create workout routines according to my existing workout plans.

### Edit Workout Routines

As an user, I want to edit my workout routines.

### Delete Workout Routines

As an user, I want to delete my workout routines.

## Workout Logs

### List Workout Logs

As an user, I want to list all my workout logs.

### Create Workout Log

As an user, I want to log a workout into my profile.

### Edit Workout Log

As an user, I want to edit a workout log.

### Share Workout Log into Groups

As an user, I want to share my workout log into all groups I'm part of.

## Group Management

### Create Group

As an user, I want to create a workout group so I can interact with my closed ones when doing workouts.

### Create Invites

As an user, I want to create group invites so other people can join my group.

### Accept Invites

As an user, I want to join a group through an invite link so I can join groups of my interest.

### Edit Group

As an user, I want to edit the group information so I can keep it up to date to all members.

### Delete Group

As an user, I want to delete the group so I can remove all the group information data from the platform.

### Quit from Group

As an user, I want to quit a group so I won't be part of it anymore.

## Report System

### Report User

As an user, I want to report another user to admins so I can inform my bad experience to the administrators.

### Report Post

As an user, I want to report a workout post so I can inform inappropriate posts into the platform for the administrators.

## Admin Management

### Manage users

As an admin, I want to manage the users public data and accesses of the platform.

### Manage Reports

As an admin, I want to manage reports from users so I can take proper actions based on the report.

### Manage Workout Exercises

As an admin, I want to manage all the exercises available to insert into a workout routine.

___

# License

Distributed under the MIT License. See `LICENSE` for more information.

# Contact

Luiz Matias - [LinkedIn](https://www.linkedin.com/in/luizmatiasdev/) - contact@luizmatias.com
