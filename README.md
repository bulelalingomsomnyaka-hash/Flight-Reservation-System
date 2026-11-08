#  Flight-Reservation-System

A desktop Flight Reservation System built in Java (NetBeans) with MySQL database.
This Application allows users to log in, search for flights, book tickets, and generate boarding pass tickets for FlySafair flights.

## What does this application do?

**Main Features:**
-Login System: Secure login with fixed username & password(connected to mySQL)
-Flight Booking: Booking flight by selecting Flight type, From, To, Date & seat
-Passenger Details: passenger Name
-Button selection: selecting check seats button (checks availability of seats in flight), booking button(booking flight), view booking button(view your ticket(s))
-Ticket Generation: After booking, a Ticket / Boarding pass pops up with all flight details(name, from, to, flight type, gate, time, date, & seat)
-Database storage: All bookings are saved in MySQL database for record keeping
-View Bookings: User can view all bookings stored in database

**Technology Used**
-Language: Java(Swing / JFrame)
-IDE: Apache NetBeans
-Database: MySQL
-Database Connector: MySQL Connector
-UI Design: Java Swing GUI Builder

## HOW TO RUN THIS APPLICATION##

## 1.Requirements
-Install Java JDK 8 if you don't have it.
-Install Apache NetBeans IDE
- MySQL Connector/J jar file

### 2. Setup Database
1. Open **XAMPP** and click **Start** next to **MySQL** and **Apache**
2. Go to browser and open: `http://localhost/phpmyadmin`
3. Create a new database named: `flysafair_db`
4. Create a table named: `bookings` with columns:
   `id, name, flight, from_city, to_city, date, time, gate, seat, booking_ref`
5. Import the `flysafair_db.sql` file if provided in this repo

### 3. Setup Project in NetBeans
1. Download / Cl### 3. Setup Project in NetBeans
1. Download / Clone this repository
2. Open NetBeans > File > Open Project > Select this folder
3. Right-click **Libraries** > Add JAR > Select `mysql-connector-j.jar`
4. Right-click Project > **Clean and Build**

### 4. Run
1. Right-click `LoginForm.java` > **Run File**
2. Login with:
3. Usernames (Bulela, Asemahle, Mary or Kwakhanya) and password is the same that is: 1234
4. Then book the ticket


