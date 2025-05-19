package org.example.javafxsistemadentista.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller for the dental appointment schedule view.
 */
public class ScheduleController implements Initializable {
    @FXML
    public Label monthYearLabel;
    @FXML
    public ComboBox<Dentist> dentistComboBox;
    @FXML
    public GridPane scheduleGrid;

    // Current date used for schedule navigation
    private LocalDate currentDate = LocalDate.now();
    // Data structure to store appointment status information
    private Map<LocalTime, Map<LocalDate, AppointmentStatus>> scheduleData = new HashMap<>();

    /**
     * Initializes the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupDentistComboBox();
        initializeScheduleData();
        buildScheduleView();
        updateMonthYearLabel();
    }

    private void setupDentistComboBox() {
        // Populate with dentists from your service
        // dentistComboBox.getItems().addAll(new DentistService().getAllDentists());

        // Temporary code until you implement DentistService
        // Remove this and uncomment the above line when ready
        dentistComboBox.getItems().add(new Dentist(1, "Dr. Silva"));
        dentistComboBox.getItems().add(new Dentist(2, "Dr. Oliveira"));

        dentistComboBox.getSelectionModel().selectFirst();
        dentistComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> refreshSchedule());
    }

    /**
     * Initialize the schedule data structure with default availability.
     */
    private void initializeScheduleData() {
        // Clear existing data
        scheduleData.clear();

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(18, 0);

        for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(30)) {
            scheduleData.put(time, new HashMap<>());

            for (int i = 0; i < 7; i++) { // Next 7 days
                LocalDate date = currentDate.plusDays(i);
                scheduleData.get(time).put(date, AppointmentStatus.AVAILABLE);
            }
        }

        // Here you would normally load real appointment data from your database
        // For example:
        // List<Appointment> appointments = appointmentService.getAppointmentsForWeek(currentDate, selectedDentist);
        // for (Appointment appointment : appointments) {
        //     scheduleData.get(appointment.getTime()).put(appointment.getDate(), AppointmentStatus.BOOKED);
        // }
    }

    /**
     * Build the schedule view based on the current data.
     */
    private void buildScheduleView() {
        scheduleGrid.getChildren().clear();

        // Header row with dates
        for (int i = 0; i < 7; i++) {
            LocalDate date = currentDate.plusDays(i);
            Label dateLabel = new Label(date.getDayOfWeek().toString() + "\n" + date.getDayOfMonth());
            dateLabel.getStyleClass().add("date-header");
            scheduleGrid.add(dateLabel, i+1, 0);
        }

        // Time slots
        int row = 1;
        for (LocalTime time : scheduleData.keySet().stream().sorted().toList()) {
            Label timeLabel = new Label(time.toString());
            timeLabel.getStyleClass().add("time-label");
            scheduleGrid.add(timeLabel, 0, row);

            for (int day = 0; day < 7; day++) {
                LocalDate date = currentDate.plusDays(day);
                AppointmentStatus status = scheduleData.get(time).get(date);

                Pane slot = createTimeSlot(time, date, status);
                scheduleGrid.add(slot, day+1, row);
            }
            row++;
        }
    }

    /**
     * Create a visual representation of a time slot with the appropriate styling.
     */
    private Pane createTimeSlot(LocalTime time, LocalDate date, AppointmentStatus status) {
        StackPane slot = new StackPane();
        slot.getStyleClass().add("time-slot");

        switch (status) {
            case AVAILABLE:
                slot.getStyleClass().add("available-slot");
                break;
            case BOOKED:
                slot.getStyleClass().add("booked-slot");
                break;
            case UNAVAILABLE:
                slot.getStyleClass().add("unavailable-slot");
                break;
        }

        slot.setOnMouseClicked(e -> handleSlotClick(time, date, status, slot));
        return slot;
    }

    /**
     * Handle clicks on schedule time slots.
     */
    private void handleSlotClick(LocalTime time, LocalDate date,
                                 AppointmentStatus status, Pane slot) {
        if (status == AppointmentStatus.AVAILABLE) {
            // Open appointment dialog
            openAppointmentDialog(time, date);
        } else if (status == AppointmentStatus.BOOKED) {
            // Show appointment details or allow rescheduling
            showAppointmentDetails(time, date);
        }
    }

    /**
     * Open a dialog to create a new appointment.
     */
    private void openAppointmentDialog(LocalTime time, LocalDate date) {
        System.out.println("Opening appointment dialog for " + date + " at " + time);
        // TODO: Implement dialog to create appointment
    }

    /**
     * Show details for an existing appointment.
     */
    private void showAppointmentDetails(LocalTime time, LocalDate date) {
        System.out.println("Showing appointment details for " + date + " at " + time);
        // TODO: Implement dialog to show appointment details
    }

    /**
     * Navigate to the previous week.
     */
    @FXML
    public void previousWeek() {
        currentDate = currentDate.minusWeeks(1);
        updateMonthYearLabel();
        refreshSchedule();
    }

    /**
     * Navigate to the next week.
     */
    @FXML
    public void nextWeek() {
        currentDate = currentDate.plusWeeks(1);
        updateMonthYearLabel();
        refreshSchedule();
    }

    /**
     * Return to the current week.
     */
    @FXML
    public void currentWeek() {
        currentDate = LocalDate.now();
        updateMonthYearLabel();
        refreshSchedule();
    }

    /**
     * Handle button click to create a new appointment without selecting a time slot first.
     */
    @FXML
    public void createNewAppointment() {
        System.out.println("Creating new appointment");
        // TODO: Implement dialog to create appointment
    }

    /**
     * Update the month/year label based on current date.
     */
    private void updateMonthYearLabel() {
        monthYearLabel.setText(currentDate.getMonth().toString() + " " + currentDate.getYear());
    }

    /**
     * Refresh the schedule view with updated data.
     */
    private void refreshSchedule() {
        initializeScheduleData();
        buildScheduleView();
    }

    /**
     * Simple class to represent a dentist.
     * You should replace this with your actual Dentist class.
     */
    public static class Dentist {
        private int id;
        private String name;

        public Dentist(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Enum to represent the status of an appointment slot.
     */
    public enum AppointmentStatus {
        AVAILABLE, BOOKED, UNAVAILABLE
    }
}