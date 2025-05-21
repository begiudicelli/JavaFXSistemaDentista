package org.example.javafxsistemadentista.services;

import org.example.javafxsistemadentista.entities.Dentist;
import org.example.javafxsistemadentista.entities.Employee;
import org.example.javafxsistemadentista.entities.Treatments;

import java.util.List;

public class AppointmentService {

    public List<Treatments> getAllTreatments() {
        return List.of(
                new Treatments(1, "Consulta simples", 100),
                new Treatments(2, "Limpeza", 200)
        );
    }

    public List<Dentist> getAllDentists() {
        return List.of(
                new Dentist(1, "Rodrigo", "12013014099", "Cirurgiao", "3184847575", "rodrigodentista@gmail.com"),
                new Dentist(2, "Leandro", "19018017011", "Aparelho dentario", "3195951515", "leandrodentista@gmail.com"));
    }

    public List<Employee> getAllEmployees() {
        return List.of(
                new Employee(1, "Maria", "98076054010", "3114141212", "maria123@gmail.com"),
                new Employee(2, "Juliana", "17623456712", "3125254848", "juliana456@gmail.com")
        );
    }
}
