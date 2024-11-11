package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.Person;


/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 * An appointment is considered unique by comparing using {@code Appointment#isSameAppointment(Appointment)}.
 * As such, adding and updating of appointments uses Appointment#isSameAppointment(Appointment) for equality so as
 * to ensure that the appointment being added or updated isunique in terms of identity in the UniqueAppointmentList.
 * However, the removal of an appointment uses Appointment#equals(Object) so as to ensure that the appointment with
 * exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#isSameAppointment(Appointment)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean containsAppointment(Appointment appointmentToCheck) {
        requireNonNull(appointmentToCheck);
        return internalList.stream().anyMatch(appointmentToCheck::isSameAppointment);
    }

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean containsAppointment(AppointmentDescriptor appointmentDescriptorToCheck) {
        requireNonNull(appointmentDescriptorToCheck);
        return internalList.stream().anyMatch(appointmentDescriptorToCheck::isSameAppointment);
    }

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean containsAppointment(AppointmentDescriptor appointmentDescriptorToCheck, Person personToCheck) {
        requireAllNonNull(appointmentDescriptorToCheck, personToCheck);
        return internalList.stream()
            .filter(appointmentDescriptorToCheck::isSameAppointment)
            .anyMatch(appointment -> appointment.getPerson().equals(personToCheck));
    }

    /**
     * Adds an appointment to the list.
     * The appointment must not already exist in the list.
     */
    public void addAppointment(Appointment appointmentToAdd) {
        requireNonNull(appointmentToAdd);
        if (containsAppointment(appointmentToAdd)) {
            throw new DuplicateAppointmentException();
        }
        internalList.add(appointmentToAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the list.
     * The appointment identity of {@code editedAppointment} must not be the same as another
     * existing appointment in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.isSameAppointment(editedAppointment) && containsAppointment(editedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, editedAppointment);
    }

    /**
     * Removes the equivalent appointment from the list.
     * The appointment must exist in the list.
     */
    public void removeAppointment(Appointment appointmentToRemove) {
        requireNonNull(appointmentToRemove);
        if (!internalList.remove(appointmentToRemove)) {
            throw new AppointmentNotFoundException();
        }
    }

    public void setAppointments(UniqueAppointmentList appointmentToReplace) {
        requireNonNull(appointmentToReplace);
        internalList.setAll(appointmentToReplace.internalList);
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        if (!appointmentsAreUnique(appointments)) {
            throw new DuplicateAppointmentException();
        }

        internalList.setAll(appointments);
    }

    /**
     * Removes the equivalent appointment from the list.
     * The appointment must exist in the list.
     */
    public void removeAppointmentsForPerson(Person toRemove) {
        requireNonNull(toRemove);
        internalList.removeIf(appointment -> appointment.getPersonId() == toRemove.getPersonId());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueAppointmentList otherUniqueAppointmentList)) {
            return false;
        }

        return internalList.equals(otherUniqueAppointmentList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code appointments} contains only unique appointments.
     */
    private boolean appointmentsAreUnique(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = i + 1; j < appointments.size(); j++) {
                if (appointments.get(i).isSameAppointment(appointments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
