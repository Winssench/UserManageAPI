# Offer technical test API

A Springboot API that exposes two services:

- One that allows to register a user
- one that displays the details of a registered user

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

## Prerequisites

Here is what we need for this project

```
Java 1.8
```

```
Maven 3.0+
```

```
an IDE Eclipse is recommended
```

- Note : in this project it includes many dependencies, luckily, it is all handeled by Maven for us.

#

## Steps to Setup

- A step by step series of examples that tell you how to get a development env running
  download Eclipse

**1. Clone the application**

```bash
git clone https://github.com/Winssench/UserManageAPI.git
```

**2. Import the project to Eclipse**

**2. Embedded Database**

We are using here H2 in memory database
which make us dont need any configuration about that

for more information about the configuration

- open `src/main/resources/application.properties`

**4. Run the app using maven**

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>

The database Console will run at <http://localhost:8080/h2-console/l>

The reference documentation includes detailed installation instructions as well as a comprehensive getting started guide.

<https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/getting-started.html#getting-started.first-application>

#

## Explore Rest APIs

The app defines following CRUD APIs.

- **1. Auth**

| Method | Url                  | Decription | Sample Valid Request Body |
| ------ | -------------------- | ---------- | ------------------------- |
| POST   | /api/v1/registration | Sign up    | [JSON](#signup)           |
| POST   | /login               | Log in     | [form-data](#signin)      |
| GET    | /logout              | Log out    | --                        |

### Notes:

- for the authentification a basic auth is used, the password is in encrypted in the DB.
- since we are using in memory database it is empty at the begining so we have to Sign Up first in order to create user
- the json in the SIgnup must respect the form like for the Email, All the fields are required Except the phone field as well as the gender they are Optional

- Only Adult French are allowed to create an account otherwise it will display a message :

## Sample Valid JSON Request Bodys

##### <a id="signup">Sign Up -> /api/v1/registration</a>

```json
{
  "firstName": "Pierre",
  "lastName": "DUPONT",
  "gender": "MALE",
  "email": "nilal.ahed@gmail.com",
  "password": "password",
  "country": "France",
  "birthday": "2000-11-12",
  "phoneNumber": "0727389432"
}
```

- for the login it the values are in form data

* **1. Users**

| Method | Url                          | Decription | Params                       |
| ------ | ---------------------------- | ---------- | ---------------------------- |
| GET    | /api/v1/userDetail?email=... | userDetail | Key=email, Value=ActualEmail |

### Notes:

- the following route is protected using Spring Security (only Logged in users are allowed)
- it displays the detail of a registered User by Email

#

## IDE

- [Eclipse](https://www.eclipse.org/downloads/) - as IDE

## Authors

- **Omar CHICHAOUI** - _Software Engineering _

## Acknowledgments

- Thanks to everyone who works to develop Spring [SpringBoot](https://spring.io/) .
- Template used Billie Thompson -
