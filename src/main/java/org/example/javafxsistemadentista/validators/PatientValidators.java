package org.example.javafxsistemadentista.validators;

import org.example.javafxsistemadentista.entities.Patient;

import java.util.regex.Pattern;

public class PatientValidators {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    public void validatePatient(Patient patient) {
        if (patient.getName() == null || patient.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do paciente é obrigatório.");
        }
        if (patient.getCpf() == null || patient.getCpf().isBlank() || patient.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos.");
        }
        if (patient.getPhone() == null || patient.getPhone().isBlank()) {
            throw new IllegalArgumentException("O telefone do paciente é obrigatório.");
        }
        if(patient.getAddress() == null || patient.getAddress().isBlank()){
            throw new IllegalArgumentException("O endereco do paciente e obrigatorio.");
        }
        if(!EMAIL_PATTERN.matcher(patient.getEmail()).matches()){
            throw  new IllegalArgumentException("Email informado e invalido.");
        }
    }
}
