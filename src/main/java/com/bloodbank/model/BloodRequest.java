package com.bloodbank.model;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "blood_requests")
public class BloodRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "hospital_id")
    private int hospitalId;
    @Column(name = "blood_group")
    private String bloodGroup;
    private int units;
    private String urgency;
    private String status;
    @Column(name = "requested_at", insertable = false, updatable = false)
    private LocalDateTime requestedAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getHospitalId() { return hospitalId; }
    public void setHospitalId(int h) { this.hospitalId = h; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String g) { this.bloodGroup = g; }
    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }
    public String getUrgency() { return urgency; }
    public void setUrgency(String u) { this.urgency = u; }
    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
}