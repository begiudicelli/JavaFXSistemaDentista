package org.example.javafxsistemadentista.entities;

import java.util.List;

public class Exam {
    private int id;
    private Patient patient;
    private String urlExam;

    public Exam(int id, Patient patient, String urlExams) {
        this.id = id;
        this.patient = patient;
        this.urlExam = urlExams;
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

    public String getUrlExam() {
        return urlExam;
    }

    public void setUrlExam(String urlExam) {
        this.urlExam = urlExam;
    }
}
