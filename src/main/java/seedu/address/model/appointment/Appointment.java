package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    // Identity fields
    private final AppointmentType appointmentType;
    private final LocalDateTime appointmentDateTime;
    private final int personId;

    // Data fields
    private final Sickness sickness;
    private final Medicine medicine;

    /**
     * Every field must be present and not null.
     */
    public Appointment(
            AppointmentType appointmentType,
            LocalDateTime appointmentDateTime,
            int personId,
            Sickness sickness,
            Medicine medicine) {

        requireAllNonNull(appointmentType, personId, sickness, medicine);
        this.appointmentType = appointmentType;
        this.appointmentDateTime = appointmentDateTime;
        this.personId = personId;
        this.sickness = sickness;
        this.medicine = medicine;
    }

    /**
     * Creates an appointment object using appointmentDescriptor.
     */
    public Appointment(AppointmentDescriptor appointmentDescriptor) {
        this.appointmentType = appointmentDescriptor.getAppointmentType();
        this.appointmentDateTime = appointmentDescriptor.getAppointmentDateTime();
        this.personId = appointmentDescriptor.getPersonId();
        this.sickness = appointmentDescriptor.getSickness();
        this.medicine = appointmentDescriptor.getMedicine();
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public int getPersonId() {
        return personId;
    }

    public Sickness getSickness() {
        return sickness;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    /**
     * Returns true if both appointments have the same name.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getAppointmentType().equals(getAppointmentType())
                && otherAppointment.getPersonId() == getPersonId()
                && otherAppointment.getAppointmentDateTime() == getAppointmentDateTime();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return appointmentType.equals(otherAppointment.appointmentType)
                && appointmentDateTime == otherAppointment.appointmentDateTime
                && personId == otherAppointment.personId
                && medicine.equals(otherAppointment.medicine)
                && sickness.equals(otherAppointment.sickness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentType, appointmentDateTime, personId, medicine, sickness);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointmentType", appointmentType)
                .add("appointmentDateTime", appointmentDateTime)
                .add("personId", personId)
                .add("medicine", medicine)
                .add("sickness", sickness)
                .toString();
    }

}
