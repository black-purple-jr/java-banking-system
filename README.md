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

* JBCrypt: ...
* MySQL Connector for Java: ...

## Quick Start

### Prerequisites

* Git
* JDK 25.0.2 (or higher)

### Installation

#### 1. Clone the repo locally

```bash
git clone https://github.com/black-purple-jr/java-banking-system
```

#### 2. Compile and execute

##### Windows

```bash
javac -cp ".;lib\*" *.java
```

```bash
java -cp ".;lib\*" Main
```

###### Mac / Linux

```bash
javac -cp ".:lib/*" *.java
```

```bash
java -cp ".:lib/*" Main
```