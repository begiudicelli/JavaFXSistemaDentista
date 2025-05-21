package org.example.javafxsistemadentista.services;

import org.example.javafxsistemadentista.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AppointmentService {

    public List<Appointment> getAppointmentsForWeek() {
        Patient patient = new Patient(
                "ronaldo",
                "13051928",
                "3175754848",
                "teste@gmail.com",
                "rua das timbiras",
                LocalDate.now(),
                "teste dos pacientes ne"
        );

        Dentist dentist = new Dentist(
                1,
                "Rodrigo",
                "12013014099",
                "Cirurgiao",
                "3184847575",
                "rodrigodentista@gmail.com"
        );

        Employee employee = new Employee(
                1,
                "Maria",
                "98076054010",
                "3114141212",
                "maria123@gmail.com"
        );

        // Data da consulta: amanhã às 10:00
        LocalDateTime appointmentDateTime = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        LocalDate createdAt = LocalDate.now();

        Appointment appointment = new Appointment(
                1,
                patient,
                dentist,
                employee,
                appointmentDateTime,
                30,
                AppointmentStatus.BOOKED,
                "Teste",
                createdAt
        );

        List<Treatments> treatments = List.of(
                new Treatments(1, "Consulta simples", 100),
                new Treatments(2, "Limpeza", 200)
        );

        appointment.setTreatmentsList(treatments);
        appointment.setTotalPrice(100 + 200);

        return List.of(appointment);
    }


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
