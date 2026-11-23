package com.bloodbank.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "donors")
public class Donor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "blood_group")
    private String bloodGroup;
    private LocalDate dob;
    private String phone;
    private String city;
    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDate d) { this.lastDonationDate = d; }
}