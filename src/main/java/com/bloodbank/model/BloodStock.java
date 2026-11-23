package com.bloodbank.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "blood_stock")
public class BloodStock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "donor_id")
    private int donorId;
    @Column(name = "blood_group")
    private String bloodGroup;
    private int units;
    @Column(name = "collected_on")
    private LocalDate collectedOn;
    @Column(name = "expires_on")
    private LocalDate expiresOn;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDonorId() { return donorId; }
    public void setDonorId(int donorId) { this.donorId = donorId; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }
    public LocalDate getCollectedOn() { return collectedOn; }
    public void setCollectedOn(LocalDate d) { this.collectedOn = d; }
    public LocalDate getExpiresOn() { return expiresOn; }
    public void setExpiresOn(LocalDate d) { this.expiresOn = d; }
}