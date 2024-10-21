package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * An UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label appointmentType;
    @FXML
    private Label id;
    @FXML
    private Label appointmentId;
    @FXML
    private Label personId;
    @FXML
    private Label dateTime;
    @FXML
    private Label personName;
    @FXML
    private Label sickness;
    @FXML
    private Label medicine;


    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm a");

        id.setText(displayedIndex + ". ");
        appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        personId.setText(String.valueOf(appointment.getPersonID()));
        appointmentType.setText(appointment.getAppointmentType().value);
        dateTime.setText(appointment.getAppointmentDateTime().format(formatter));
        //TODO: don't talk to strangers
        personName.setText(appointment.getPerson().getName().fullName);
        sickness.setText(appointment.getSickness().value);
        medicine.setText(appointment.getMedicine().value);
    }
}
