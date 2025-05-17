package org.example.javafxsistemadentista.entities;

import java.time.LocalDate;

public class Appointments {
    private int id;
    private Patient patient;
    private Dentist dentist;
    private Employee employee;
    private LocalDate dateTime;
    private int duration;
    private String status;
    private String notes;
    private LocalDate createdAt;
    private double totalPrice;

    public Appointments(){

    }

    public Appointments(int id, Patient patient, Dentist dentist, Employee employee, LocalDate dateTime, int duration, String status, String notes, LocalDate createdAt) {
        this.id = id;
        this.patient = patient;
        this.dentist = dentist;
        this.employee = employee;
        this.dateTime = dateTime;
        this.duration = duration;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
