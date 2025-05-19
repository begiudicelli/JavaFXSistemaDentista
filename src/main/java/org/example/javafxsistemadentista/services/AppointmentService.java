package org.example.javafxsistemadentista.services;

import java.util.List;

public class AppointmentService {



    public List<String> getAllTreatments() {
        return List.of("Limpeza", "Canal", "Restauração", "Extração");
    }

    public List<String> getAllDoctors() {
        return List.of("Dr. João Silva", "Dra. Maria Oliveira", "Dra. Ana Costa");
    }

    public List<String> getAllEmployees() {
        return List.of("Dr. João Silva", "Dra. Maria Oliveira", "Dra. Ana Costa");
    }
}
