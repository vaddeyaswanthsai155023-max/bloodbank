CREATE DATABASE IF NOT EXISTS bloodbank;
USE bloodbank;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role ENUM('ADMIN','DONOR','HOSPITAL') NOT NULL
);

CREATE TABLE donors (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  blood_group ENUM('A+','A-','B+','B-','AB+','AB-','O+','O-') NOT NULL,
  dob DATE NOT NULL,
  phone VARCHAR(15) NOT NULL,
  city VARCHAR(50),
  last_donation_date DATE,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE blood_stock (
  id INT AUTO_INCREMENT PRIMARY KEY,
  donor_id INT NOT NULL,
  blood_group VARCHAR(3) NOT NULL,
  units INT NOT NULL,
  collected_on DATE NOT NULL,
  expires_on DATE NOT NULL,
  FOREIGN KEY (donor_id) REFERENCES donors(id)
);

CREATE TABLE blood_requests (
  id INT AUTO_INCREMENT PRIMARY KEY,
  hospital_id INT NOT NULL,
  blood_group VARCHAR(3) NOT NULL,
  units INT NOT NULL,
  urgency ENUM('NORMAL','URGENT') DEFAULT 'NORMAL',
  status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (hospital_id) REFERENCES users(id)
);