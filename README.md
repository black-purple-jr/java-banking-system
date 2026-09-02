# Banking System

## Overview

A simple banking system built with Java and uses a MySQL database to store customers data including personal info, password, and balance. The code is far from being clean because it's my first Java project, but I'll try to optimize it later.

## Features

* Secure Login / sign up system with password hashing.
* Seeing balance.
* Deposit / Withdraw money.

## Technology Used

* JDK
* Java

## Packages

* **jBCrypt**: used for secure password hashing (bcrypt algorithm)
* **MySQL Connector/J**: JDBC driver for connecting to MySQL

## Quick Start

### Prerequisites

* Git
* JDK 25.0.2 (or higher)

### Installation

#### 1. Clone the repo locally

```bash
git clone https://github.com/black-purple-jr/java-banking-system
```

#### 2. Set up the database

Start MySQL (e.g. via XAMPP), then create the database and table:

```sql
CREATE DATABASE bank;
USE bank;

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    balance DOUBLE NOT NULL DEFAULT 0
);
```

#### 3. Add dependencies

Download and place these jars into the `lib/` folder:
* [jBCrypt 0.4](https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar)
* [MySQL Connector/J 9.3.0](https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.3.0/mysql-connector-j-9.3.0.jar)

#### 4. Compile and execute

  ##### Windows

```bash
javac -cp ".;lib\*" *.java
```

```bash
java -cp ".;lib\*" Main
```

  ##### Mac / Linux

```bash
javac -cp ".:lib/*" *.java
```

```bash
java -cp ".:lib/*" Main
```

## Author

* Abdellah DAKIR ALLAH - a.k.a [black-purple-jr](https://github.com/black-purple-jr) on Github and some other platforms.