# ECSE 321 - Introduction to Software Engineering Project Group 14

## Project Overview

Welcome to the Sport Center Registration Application! Our goal is to develop a web application to assist a local sports center in managing its operations effectively. This application will enable the center's owner and instructors to organize various types of classes. Additionally, customers will be able to browse available classes, register for sessions, and manage their bookings through their accounts. Stay tuned as we work to deliver a solution that simplifies the management of sports center activities for all stakeholders involved.


Please check our [Wiki](https://github.com/McGill-ECSE321-Winter2024/project-group-14/wiki) for more details!


## Deploying our App
Our frontend was developed using Vue.js and was deployed to the port: 8087 with the host: 127.0.0.1. Our backend was developed according to what was outlined previously in deliverable 2 and was deployed to the port: 8080 with the host: 127.0.0.1. To the run backend please use the command “./gradlew bootrun” in the terminal.
Before running the frontend, please ensure you have node.js and npm installed (https://nodejs.org/en/learn/getting-started/how-to-install-nodejs). 

To run the app locally, follow these steps:
1. Open the `application.properties` file located at `sportCenterRegistration/src/main/resources/` and change the value of `spring.datasource.password` to your own database password.
2. Open a terminal at directory `sportCenterRegistration/` and run `./gradlew bootRun`
3. Open a new terminal at directory `sportCenterRegistration-frontend/` and run `npm install` to download dependencies. Please also ensure that you have Axios added as a dependency, (you can add it as a dependency by inputting the following command in the terminal “npm install --save axios@1.5”).
4. At current directory, run `npm run dev` and see our frontend app at [http://127.0.0.1/#/](http://127.0.0.1:8087/#/)

## Features
Our application offers distinct operating pages for Owners, Instructors, and Customers, providing a personalized experience based on your role. Upon logging in with your account credentials, you'll be seamlessly directed to the corresponding section of the app.
### Owner Features:
Owners have comprehensive control over the management of the platform. Key functionalities include:
- Managing instructors and customers, including creating new instructor/customer accounts, deleting instructor/customer accounts.
- Approving new sport classes proposed by instructors.
- Overseeing and managing sessions.
- Handling registrations.
(For demonstration purposes, simply use "admin" as the username and a random password when logging in as an Owner.)
### Instructor Features:
Instructors enjoy a streamlined interface focused on their teaching responsibilities. Key functionalities include:
- Viewing all available sport classes suitable for teaching.
- Applying to create new sport classes.
- Viewing and managing the sessions they conduct.
(For demonstration purposes, use "instructor" as the username and a random password when logging in as an Instructor.)
### Customer Features:
Customers have access to essential tools for browsing and registering for sessions. Key functionalities include:
- Browsing a list of instructors, sport classes, and sessions.
- Registering for sessions they wish to attend.
- Viewing a list of all registered sessions.
(You can sign up for a customer account at the homepage)


## Our Team
### <br> Hammad Muhammad
A U1 Software Engineering student with a passion for software engineering. Muhammad has 2 years of experience with Java and Python.
### <br> Motta Vivek
A Software Engineering student with a passion for coding, Vivek has many years of experience with Java.
### <br> Marji David
A Jordanian U1 software engineering student who is very passionate about coding and he has around 4 years of experience with Java.
### <br> Yue Ming Xuan <br>
Hi, I am a second-year software engineering student. I have a decent knowledge of Python, Java, C, and HTML. I am interested in exploring in depth the myth behind machine learning and AI algorithms. I will be able to contribute a lot to the domain model design, the software design and the documentation of the project.
### <br> Huang Stephen
U2 Computer engineering student. Has 2 years experience with Java.
### <br> Luo Loridy
An exchange student from the University of Hong Kong with experience in Python, Java, JavaScript

## Meet the team

| Team Member | Team Role | Github Profile |
| -- | -- | -- |
| Hammad Muhammad | Project Manager | [hammad0860](https://github.com/hammad0860) |
| Motta Vivek | Documentation Engineer | [VMotta1](https://github.com/VMotta1) |
| Marji David | Software Engineer | [DavidMarji](https://github.com/DavidMarji) |
| Yue Ming Xuan | Software Engineer | [ming-x-yue](https://github.com/ming-x-yue) |
| Huang Stephen | Test Engineer | [stephh12](https://github.com/stephh12) |
| Luo Loridy | Test Engineer | [loridy](https://github.com/loridy) |



## Testing
We have implemented a comprehensive testing suite to examine the reliability of our system
Our tests cover:
1. ***Object Creation***: We validate that objects can be successfully created and retrieved from the database for each class in our system.
2. ***Attribute Validation***: We verify that the attributes of each object are correctly stored and retrieved from the database.
3. ***Reference Integrity***: We ensure that relationships between objects (such as foreign key constraints) are properly maintained in the database.

To run the tests locally, follow these steps:
- Open the `application.properties` file located at `sportCenterRegistration/src/main/resources/` and change the value of `spring.datasource.password` to your own database password.
- Open a terminal in the `sportCenterRegistration/` directory and run `./gradlew test`


## Overview Table

| Team Member | Sprint 1 Efforts (hours)  | Sprint 2 Efforts (hours) |Sprint 3 Efforts (hours)| Total Effort (hours) |
| -- | -- | --  | -- | -- |
| Hammad Muhammad | 35 | 60| 80 | 175 |
| Motta Vivek | 40 | 30 | 40 | 110 |
| Marji David | 30 | 60 | 65 | 155 |
| Yue Ming Xuan | 35 | 60 | 75 | 170 |
| Huang Stephen | 40 | 60 | 80 | 180 |
| Luo Loridy | 40 | 70 | 75 | 185 |

# Deliverable 1
## Sprint 1 effort table

| Team Member | Contributions  | Effort (Hours) |
| -- | -- | -- | 
| Hammad Muhammad | Functional Requirements, Non-Functional Requirements, Use Case Specifications, Persistence Layer Testing, Domain Model | 35 |
| Motta Vivek | Functional Requirements, Non-Functional Requirements, Project Report, Domain Model, Use Case Specifications | 40 |
| Marji David | Functional Requirements, Non-Functional Requirements, Project Report, Domain Model, Meeting Minutes, Use Case Specifications | 30 | 
| Yue Ming Xuan | Functional Requirements, Non-Functional Requirements, Domain Model, Use Case Specifications | 35 | 
| Huang Stephen | Functional Requirements, Non-Functional Requirements, Domain Model, Use Case Specifications, Use Case Diagrams | 40 |
| Luo Loridy | Persistence Layer, Domain Model, Functional Requirements, Non-Functional Requirements, Use Case Specifications | 40 |

## Sprint 1 Project report
The project report for this deliverable can be found [here](https://github.com/McGill-ECSE321-Winter2024/project-group-14/wiki/Sprint-Report-1)

# Deliverable 2
## Sprint 2 effort table

| Team Member | Contributions  | Effort (Hours) |
| -- | -- | -- | 
| Hammad Muhammad | Wrote service logic for instructor owner and customer, Wrote the service tests instructor owner and customer, wrote the controller methods for instructor owner and customer, wrote the integration tests for instructor owner and customer | 60 |
| Motta Vivek | Wrote the SAQ report and sprint 2 report | 30 |
| Marji David | Wrote service logic for Registration, Wrote the service tests Registration, wrote the controller methods Registration, wrote the integration tests for Session Registration and Shift | 60 | 
| Yue Ming Xuan | Wrote service logic Shift, Wrote the service tests Shift, wrote the controller methods Shift | 60 | 
| Huang Stephen | Wrote service logic Session, Wrote the service tests Session, wrote the controller methods Session | 60 |
| Luo Loridy | Wrote service logic SportClass, Wrote the service tests, wrote the controller methods, wrote the integration tests  SportClass | 70 |

## Sprint 2 Project report
The project report for this deliverable can be found [here](https://github.com/McGill-ECSE321-Winter2024/project-group-14/wiki/Sprint-Report-2)

# Deliverable 3
## Sprint 3 effort table
| Team Member | Contributions  | Effort (Hours) |
| -- | -- | -- | 
| Hammad Muhammad | Wrote create, delete and approve sport class | 80 |
| Motta Vivek | Worked on presentation and did the sprint report | 40 |
| Marji David | Worked on presentation and wrote create registration and sessions | 65 | 
| Yue Ming Xuan | Wrote homepage for all accounts(sidebars) and all features for OwnerApp (user management, approve new classes, sessions and registration) | 85 | 
| Huang Stephen | Created the Figma UI, wrote login and signup,  wrote home page for instructor and owner  | 80 |
| Luo Loridy | Assisted other members in troubleshooting code, Fixed the backend, wrote login and signup, wrote home page for owner, customer and instructor, Logout | 85 |

## Sprint 3 Project report
The project report for this deliverable can be found [here](https://github.com/McGill-ECSE321-Winter2024/project-group-14/wiki/Sprint-Report-3)

