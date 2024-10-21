package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents an Appointment in the appointment book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    // Identity fields
    private final int appointmentId;
    private final Person person;

    // Data fields
    private final AppointmentDescriptor appointmentDescriptor;

    /**
     * Every field must be present and not null.
     */
    public Appointment(
            AppointmentType appointmentType,
            LocalDateTime appointmentDateTime,
            Person person,
            Sickness sickness,
            Medicine medicine) {

        requireAllNonNull(appointmentType, appointmentDateTime, person, sickness, medicine);
        this.appointmentDescriptor = new AppointmentDescriptor(
                appointmentType,
                appointmentDateTime,
                sickness,
                medicine);
        this.person = person;

        // Increment the static counter and assign a unique ID to the appointment
        this.appointmentId = 0;
    }

    /**
     * Creates an appointment object using appointmentDescriptor.
     */
    public Appointment(
            AppointmentType appointmentType,
            LocalDateTime appointmentDateTime,
            Person person,
            Sickness sickness,
            Medicine medicine,
            int appointmentId) {
        this.appointmentDescriptor = new AppointmentDescriptor(
                appointmentType,
                appointmentDateTime,
                sickness,
                medicine);
        this.person = person;

        // Increment the static counter and assign a unique ID to the appointment
        this.appointmentId = appointmentId;
    }

    /**
     * Creates an appointment object using person and appointmentDescriptor.
     */
    public Appointment(int appointmentId, Person person, AppointmentDescriptor appointmentDescriptor) {
        requireAllNonNull(appointmentId, appointmentDescriptor, person);
        this.appointmentId = appointmentId;
        this.person = person;
        this.appointmentDescriptor = appointmentDescriptor;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public AppointmentDescriptor getAppointmentDescriptor() {
        return appointmentDescriptor;
    }

    public Person getPerson() {
        return person;
    }

    public int getPersonId() {
        return person.getPersonId();
    }

    public AppointmentType getAppointmentType() {
        return appointmentDescriptor.getAppointmentType();
    }

    public Sickness getSickness() {
        return appointmentDescriptor.getSickness();
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDescriptor.getAppointmentDateTime();
    }

    public Medicine getMedicine() {
        return appointmentDescriptor.getMedicine();
    }

    /**
     * Returns true if both appointments have the same person, appointmentDateTime, appointmentType.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        return appointmentDescriptor.isSameAppointment(otherAppointment.appointmentDescriptor)
                && person.isSamePerson(otherAppointment.person);
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two appointments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment otherAppointment)) {
            return false;
        }

        return appointmentDescriptor.equals(otherAppointment.appointmentDescriptor)
                && person == otherAppointment.person;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, person, appointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointmentId", appointmentId)
                .add("person", person)
                .add("appointmentType", getAppointmentType())
                .add("appointmentDateTime", getAppointmentDateTime())
                .add("medicine", getMedicine())
                .add("sickness", getSickness())
                .toString();
    }

}
