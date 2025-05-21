package org.example.javafxsistemadentista.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Appointment {
    private int id;
    private Patient patient;
    private Dentist dentist;
    private Employee employee;
    private LocalDateTime dateTime;
    private int duration;
    private AppointmentStatus status;
    private String notes;
    private LocalDate createdAt;
    private List<Treatments> treatmentsList;
    private double totalPrice;

    public Appointment() {
    }

    public Appointment(int id, Patient patient, Dentist dentist, Employee employee, LocalDateTime dateTime, int duration, AppointmentStatus status, String notes, LocalDate createdAt) {
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
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

    public List<Treatments> getTreatmentsList() {
        return treatmentsList;
    }

    public void setTreatmentsList(List<Treatments> treatmentsList) {
        this.treatmentsList = treatmentsList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
