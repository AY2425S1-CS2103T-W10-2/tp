package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.person.Person;

/**
 * Wraps all data at the appointment-book level
 * Duplicates are not allowed (by .isSameAppointment comparison)
 */
public class AppointmentBook implements ReadOnlyAppointmentBook {

    private int nextAppointmentId;
    private final UniqueAppointmentList appointments;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        appointments = new UniqueAppointmentList();
    }

    public AppointmentBook() {
        nextAppointmentId = 0;
    }

    /**
     * Creates an AppointmentBook using the Appointments in the {@code toBeCopied}
     */
    public AppointmentBook(ReadOnlyAppointmentBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public int getNextAppointmentId() {
        return nextAppointmentId;
    }

    public void setNextAppointmentId(int appointmentId) {
        this.nextAppointmentId = appointmentId;
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    /**
     * Resets the existing data of this {@code AppointmentBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAppointmentBook newData) {
        requireNonNull(newData);

        setNextAppointmentId(newData.getNextAppointmentId());
        setAppointments(newData.getAppointmentList());
    }

    //// appointment-level operations

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the appointment book.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.containsAppointment(appointment);
    }

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the appointment book.
     */
    public boolean hasAppointment(AppointmentDescriptor appointmentDescriptor) {
        requireNonNull(appointmentDescriptor);
        return appointments.containsAppointment(appointmentDescriptor);
    }

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the appointment book,
     * and is assigned to the same person.
     */
    public boolean hasAppointment(AppointmentDescriptor appointmentDescriptor, Person person) {
        requireAllNonNull(appointmentDescriptor, person);
        return appointments.containsAppointment(appointmentDescriptor, person);
    }


    /**
     * Adds an appointment to the appointment book.
     * The appointment must not already exist in the appointment book.
     */
    public void addAppointment(Appointment appointment) {
        appointments.addAppointment(appointment);
        ++nextAppointmentId;
    }

    /**
     * Adds an appointment to the appointment book.
     * The appointment must not already exist in the appointment book.
     */
    public Appointment addAppointment(Person person, AppointmentDescriptor appointmentDescriptor) {
        requireAllNonNull(person, appointmentDescriptor);
        Appointment appointment = new Appointment(nextAppointmentId, person, appointmentDescriptor);
        appointments.addAppointment(appointment);
        ++nextAppointmentId;
        return appointment;
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the appointment book.
     * The appointment identity of {@code editedAppointment} must not be the same as another
     * existing appointment in the appointment book.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);
        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Removes {@code key} from this {@code AppointmentBook}.
     * {@code key} must exist in the appointment book.
     */
    public void removeAppointment(Appointment key) {
        appointments.removeAppointment(key);
    }
    public void removeAppointmentsForPerson(Person person) {
        appointments.removeAppointmentsForPerson(person);
    }

    //// util methods
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointments", appointments)
                .toString();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentBook otherAppointmentBook)) {
            return false;
        }

        return appointments.equals(otherAppointmentBook.appointments);
    }

    @Override
    public int hashCode() {
        return appointments.hashCode();
    }
}
