package org.example.javafxsistemadentista.entities;

import java.util.List;

public class PatientProfile {
    private Integer id;
    private Patient patient;;
    private String notes;

    private List<Appointments> appointmentsList;
    private List<Exam> exams;

    public PatientProfile() {
    }

    public PatientProfile(Integer id, Patient patient, String notes) {
        this.id = id;
        this.patient = patient;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

}
