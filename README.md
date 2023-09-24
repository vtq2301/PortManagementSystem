# Port Management System

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Menu Structure](#menu-structure)
- [Sample Data](#sample-data)
- [Documentation](#documentation)
- [Contributing](#contributing)

## Introduction

The Port Management System is a Java-based command-line application that allows users to manage and simulate various aspects of a port, including containers, vehicles, ports, and trips. The system is designed to provide port administrators and managers with tools to efficiently manage their resources and make decisions related to port operations.

Youtube link: https://youtu.be/pMvAq7POoNA

## Features

The Port Management System offers the following key features:

- Management of containers, vehicles, ports, and trips.
- CRUD (Create, Read, Update, Delete) operations for entities.
- Simulation of container loading, unloading, and movement between vehicles and ports.
- Fuel management for vehicles.
- User authentication and role-based access (admin and port manager).
- Statistical calculations and reporting for fuel usage, container weight, ship lists, trip lists, and more.
- Sample data generation for testing purposes.

## Getting Started

### Prerequisites

Before running the Port Management System, ensure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or higher.
- A Java IDE (such as IntelliJ IDEA or Eclipse) or a text editor for code editing.

### Installation

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/vtq2301/PortManagementSystem.git
   ```

2. Open the project in your Java IDE or text editor.

3. Build and run the application.

## Usage

To start using the Port Management System, follow these steps:

1. Launch the application.

2. Log in with your username and password.

3. Explore the menu options to perform various tasks related to port management, data manipulation, and statistics.

## Menu Structure

The application's menu structure is organized as follows:

- Main Menu
  - Log in
  - Exit

- Log in
  - Admin
  - Port Manager
  - Return

- Admin Menu (For Admin)
  - Ports
  - Vehicles
  - Containers
  - Port Managers
  - Trips
  - View stats
  - Calculate fuel used in a day
  - Log out

- Port Manager Menu (For Port Manager)
  - Vehicles
  - Containers
  - View stats
  - Log out

## Sample Data

The system provides sample data for testing purposes. You can use this data to simulate port operations and test the application's functionality.

Test cases used for demo video:

- Admin:
  - Admin username: AdminTest
  - Admin password: RMIT@2023
- Port:
  - PortID: 128
  - PortName: HEllo
  - Port latitude: 90.0
  - Port Longitude: 90.0
  - Port storing capacity: 200000
  - Port current capacity: 20.0
  - Landing ability: true
More sample are given in our sample folder.
## Documentation

For detailed documentation and code comments, refer to the source code files in the project.

## Contributing

Contributions to the Port Management System project are welcome. Feel free to open issues, suggest enhancements, or submit pull requests.

