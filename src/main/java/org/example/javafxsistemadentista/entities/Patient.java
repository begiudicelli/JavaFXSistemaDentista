package org.example.javafxsistemadentista.entities;

import java.time.LocalDate;
import java.util.List;

public class Patient {
    private Integer id;
    private String name;
    private String cpf;
    private String phone;
    private String email;
    private String address;
    private LocalDate birthDate;
    private String notes;

    private List<Appointment> appointmentList;
    private List<Exam> examList;

    public Patient() {}

    public Patient(String name, String cpf, String phone, String email, String address, LocalDate birthDate, String notes) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Appointment> getAppointmentsList() {
        return appointmentList;
    }

    public void setAppointmentsList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}