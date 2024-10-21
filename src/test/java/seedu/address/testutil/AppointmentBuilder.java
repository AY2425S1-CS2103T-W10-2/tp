package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final String DEFAULT_APPOINTMENT_TYPE = "Health Checkup";
    public static final LocalDateTime DEFAULT_APPOINTMENT_DATE_TIME =
        LocalDateTime.of(2024, 10, 15, 10, 30);
    public static final Person DEFAULT_PERSON = BOB;
    public static final String DEFAULT_MEDICINE = "Panadol";
    public static final String DEFAULT_SICKNESS = "Flu";

    private AppointmentType appointmentType;
    private LocalDateTime appointmentDateTime;
    private Person person;
    private Medicine medicine;
    private Sickness sickness;

    /**
     * Creates a {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        appointmentType = new AppointmentType(DEFAULT_APPOINTMENT_TYPE);
        appointmentDateTime = DEFAULT_APPOINTMENT_DATE_TIME;
        person = DEFAULT_PERSON;
        medicine = new Medicine(DEFAULT_MEDICINE);
        sickness = new Sickness(DEFAULT_SICKNESS);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        appointmentType = appointmentToCopy.getAppointmentType();
        appointmentDateTime = appointmentToCopy.getAppointmentDateTime();
        person = appointmentToCopy.getPerson();
        medicine = appointmentToCopy.getMedicine();
        sickness = appointmentToCopy.getSickness();
    }

    /**
     * Sets the {@code AppointmentType} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withAppointmentType(String appointmentType) {
        this.appointmentType = new AppointmentType(appointmentType);
        return this;
    }

    /**
     * Sets the {@code person} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Sets the {@code Medicine} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withMedicine(String medicine) {
        this.medicine = new Medicine(medicine);
        return this;
    }

    /**
     * Sets the {@code appointmentDateTime} of the {@code LocalDateTime} that we are building.
     */
    public AppointmentBuilder withDateTime(LocalDateTime dateTime) {
        this.appointmentDateTime = dateTime;
        return this;
    }

    /**
     * Sets the {@code Sickness} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withSickness(String sickness) {
        this.sickness = new Sickness(sickness);
        return this;
    }

    /**
     * Builds the {@code Appointment} object.
     */
    public Appointment build() {
        return new Appointment(appointmentType, appointmentDateTime, person, sickness, medicine);
    }

}
