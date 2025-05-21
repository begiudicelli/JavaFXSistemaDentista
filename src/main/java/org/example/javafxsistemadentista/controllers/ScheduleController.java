
package org.example.javafxsistemadentista.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.example.javafxsistemadentista.entities.Appointment;
import org.example.javafxsistemadentista.entities.AppointmentStatus;
import org.example.javafxsistemadentista.entities.Dentist;
import org.example.javafxsistemadentista.services.AppointmentService;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;



public class ScheduleController implements Initializable {
    private final int DAYS_IN_A_WEEK = 7;

    @FXML
    public Label monthYearLabel;

    @FXML
    public Label currentDateLabel;

    @FXML
    public ComboBox<Dentist> dentistComboBox;

    @FXML
    public GridPane scheduleGrid;

    // Current date used for schedule navigation
    private LocalDate currentDate = LocalDate.now();

    // Data structure to store appointment status information
    private Map<LocalTime, Map<LocalDate, AppointmentStatus>> scheduleData = new HashMap<>();

    // Services
    AppointmentService appointmentService = new AppointmentService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupDentistComboBox();
        initializeScheduleData();
        buildScheduleView();
        updateDateLabels();
        updateDayHeaders();
    }

    private void setupDentistComboBox() {
        try {
            List<Dentist> dentists = appointmentService.getAllDentists();
            dentistComboBox.getItems().setAll(dentists);
            dentistComboBox.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldVal, newVal) -> refreshSchedule());

            // Select first dentist by default if available
            if (!dentists.isEmpty()) {
                dentistComboBox.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            System.err.println("Error setting up dentist combo box: " + e.getMessage());
        }
    }

    private void initializeScheduleData() {
        scheduleData.clear();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(18, 0);

        // Initialize all time slots as available
        for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(30)) {
            scheduleData.put(time, new HashMap<>());

            for (int i = 0; i < 7; i++) {
                LocalDate date = getStartOfWeek().plusDays(i);
                scheduleData.get(time).put(date, AppointmentStatus.AVAILABLE);
            }
        }

        // Load existing appointments and mark as booked
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsForWeek();
            for (Appointment appointment : appointments) {
                LocalTime time = appointment.getDateTime().toLocalTime();
                LocalDate date = appointment.getDateTime().toLocalDate();

                if (scheduleData.containsKey(time) && scheduleData.get(time).containsKey(date)) {
                    scheduleData.get(time).put(date, AppointmentStatus.BOOKED);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }

        // Mark past time slots as unavailable
        markPastSlotsAsUnavailable();
    }

    private void markPastSlotsAsUnavailable() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        for (LocalTime time : scheduleData.keySet()) {
            for (LocalDate date : scheduleData.get(time).keySet()) {
                // Mark past dates as unavailable
                if (date.isBefore(today)) {
                    scheduleData.get(time).put(date, AppointmentStatus.UNAVAILABLE);
                }
                // Mark past time slots for today as unavailable
                else if (date.equals(today) && time.isBefore(now)) {
                    scheduleData.get(time).put(date, AppointmentStatus.UNAVAILABLE);
                }
            }
        }
    }

    private LocalDate getStartOfWeek() {
        // Get Monday of the current week
        return currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
    }

    private void buildScheduleView() {
        scheduleGrid.getChildren().clear();

        // Build time slots (no headers since they're in FXML)
        int row = 0;

        List<LocalTime> sortedTimes = scheduleData.keySet().stream()
                .sorted()
                .toList();

        for (LocalTime time : sortedTimes) {
            // Add time label in first column
            Label timeLabel = new Label(formatTime(time));
            timeLabel.getStyleClass().add("time-label");
            scheduleGrid.add(timeLabel, 0, row);

            // Add time slots for each day of the week
            for (int day = 0; day < DAYS_IN_A_WEEK; day++) {
                LocalDate date = getStartOfWeek().plusDays(day);
                AppointmentStatus status = scheduleData.get(time).get(date);

                Pane slot = createTimeSlot(time, date, status);
                scheduleGrid.add(slot, day + 1, row);
            }
            row++;
        }
    }

    private String formatTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    private Pane createTimeSlot(LocalTime time, LocalDate date, AppointmentStatus status) {
        StackPane slot = new StackPane();
        slot.getStyleClass().add("time-slot");

        // Add status-specific styling and text
        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("slot-text");

        switch (status) {
            case AVAILABLE:
                slot.getStyleClass().add("available-slot");
                statusLabel.setText("Available");
                break;
            case BOOKED:
                slot.getStyleClass().add("booked-slot");
                statusLabel.setText("Booked");
                // You could add patient name here if available
                break;
            case UNAVAILABLE:
                slot.getStyleClass().add("unavailable-slot");
                statusLabel.setText("N/A");
                break;
        }

        slot.getChildren().add(statusLabel);
        slot.setOnMouseClicked(e -> handleSlotClick(time, date, status, slot));

        // Add tooltip for better UX
        addTooltipToSlot(slot, time, date, status);

        return slot;
    }

    private void addTooltipToSlot(Pane slot, LocalTime time, LocalDate date, AppointmentStatus status) {
        // You can implement tooltip functionality here
        // For now, we'll just set a simple user data
        slot.setUserData("Time: " + formatTime(time) + ", Date: " + date + ", Status: " + status);
    }

    /**
     * Handle clicks on schedule time slots.
     */
    private void handleSlotClick(LocalTime time, LocalDate date, AppointmentStatus status, Pane slot) {
        switch (status) {
            case AVAILABLE:
                openAppointmentDialog(time, date);
                break;
            case BOOKED:
                showAppointmentDetails(time, date);
                break;
            case UNAVAILABLE:
                System.out.println("This time slot is not available");
                break;
        }
    }

    /**
     * Open a dialog to create a new appointment.
     */
    private void openAppointmentDialog(LocalTime time, LocalDate date) {
        System.out.println("Opening appointment dialog for " + date + " at " + formatTime(time));

        // Get selected dentist
        Dentist selectedDentist = dentistComboBox.getSelectionModel().getSelectedItem();
        if (selectedDentist == null) {
            System.out.println("Please select a dentist first");
            return;
        }

        // TODO: Implement dialog to create appointment
        // This would typically open a new window/dialog with appointment details form
    }

    /**
     * Show details for an existing appointment.
     */
    private void showAppointmentDetails(LocalTime time, LocalDate date) {
        System.out.println("Showing appointment details for " + date + " at " + formatTime(time));

        // TODO: Implement dialog to show appointment details
        // This would typically show patient info, dentist, procedure, etc.
    }

    /**
     * Navigate to the previous week.
     */
    @FXML
    public void previousWeek() {
        currentDate = currentDate.minusWeeks(1);
        updateDateLabels();
        updateDayHeaders();
        refreshSchedule();
    }

    /**
     * Navigate to the next week.
     */
    @FXML
    public void nextWeek() {
        currentDate = currentDate.plusWeeks(1);
        updateDateLabels();
        updateDayHeaders();
        refreshSchedule();
    }

    /**
     * Return to the current week.
     */
    @FXML
    public void currentWeek() {
        currentDate = LocalDate.now();
        updateDateLabels();
        updateDayHeaders();
        refreshSchedule();
    }

    /**
     * Handle button click to create a new appointment without selecting a time slot first.
     */
    @FXML
    public void createNewAppointment() {
        System.out.println("Creating new appointment");

        // Check if dentist is selected
        Dentist selectedDentist = dentistComboBox.getSelectionModel().getSelectedItem();
        if (selectedDentist == null) {
            System.out.println("Please select a dentist first");
            return;
        }

        // TODO: Implement dialog to create appointment
        // This would open a dialog where user can select date/time and enter patient details
    }

    /**
     * Update the month/year and current date labels.
     */
    private void updateDateLabels() {
        // Update month/year label
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthYearLabel.setText(getStartOfWeek().format(monthYearFormatter));

        // Update current date label
        if (currentDateLabel != null) {
            LocalDate today = LocalDate.now();
            DateTimeFormatter currentDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
            currentDateLabel.setText(today.format(currentDateFormatter));
        }
    }

    /**
     * Update day headers to show correct dates for the current week
     */
    private void updateDayHeaders() {
        // This method could be used to update day headers dynamically
        // if you want to show dates in the headers
        // For now, the static headers in FXML are sufficient
    }

    /**
     * Refresh the schedule view with updated data.
     */
    private void refreshSchedule() {
        initializeScheduleData();
        buildScheduleView();
    }

    /**
     * Get formatted week range for display
     */
    private String getWeekRangeText() {
        LocalDate startOfWeek = getStartOfWeek();
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");

        if (startOfWeek.getMonth() == endOfWeek.getMonth()) {
            return startOfWeek.getDayOfMonth() + " - " + endOfWeek.format(formatter) + ", " + startOfWeek.getYear();
        } else {
            return startOfWeek.format(formatter) + " - " + endOfWeek.format(formatter) + ", " + startOfWeek.getYear();
        }
    }

    /**
     * Check if a time slot conflicts with existing appointments
     */
    private boolean hasConflict(LocalTime time, LocalDate date, Dentist dentist) {
        // TODO: Implement conflict checking logic
        // This would check if the dentist already has an appointment at this time
        return false;
    }

    /**
     * Get available time slots for a specific date and dentist
     */
    public List<LocalTime> getAvailableSlots(LocalDate date, Dentist dentist) {
        List<LocalTime> availableSlots = new ArrayList<>();

        if (scheduleData.isEmpty()) {
            return availableSlots;
        }

        for (LocalTime time : scheduleData.keySet()) {
            if (scheduleData.get(time).containsKey(date)) {
                AppointmentStatus status = scheduleData.get(time).get(date);
                if (status == AppointmentStatus.AVAILABLE) {
                    availableSlots.add(time);
                }
            }
        }

        return availableSlots.stream().sorted().toList();
    }

    /**
     * Book a time slot
     */
    public boolean bookTimeSlot(LocalTime time, LocalDate date) {
        if (scheduleData.containsKey(time) && scheduleData.get(time).containsKey(date)) {
            AppointmentStatus currentStatus = scheduleData.get(time).get(date);
            if (currentStatus == AppointmentStatus.AVAILABLE) {
                scheduleData.get(time).put(date, AppointmentStatus.BOOKED);
                refreshSchedule();
                return true;
            }
        }
        return false;
    }

    /**
     * Cancel a booked time slot
     */
    public boolean cancelTimeSlot(LocalTime time, LocalDate date) {
        if (scheduleData.containsKey(time) && scheduleData.get(time).containsKey(date)) {
            AppointmentStatus currentStatus = scheduleData.get(time).get(date);
            if (currentStatus == AppointmentStatus.BOOKED) {
                scheduleData.get(time).put(date, AppointmentStatus.AVAILABLE);
                refreshSchedule();
                return true;
            }
        }
        return false;
    }
}