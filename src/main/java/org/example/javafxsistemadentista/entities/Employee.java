package org.example.javafxsistemadentista.entities;

public class Employee {
    private int id;
    private String name;
    private String cpf;
    private String role;
    private String phone;
    private String email;

    public Employee(){

    }

    public Employee(int id, String name, String cpf, String role, String phone, String email) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
