package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;

/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {

    private final EditAppointmentDescriptor descriptor;

    public EditAppointmentDescriptorBuilder() {
        descriptor = new EditAppointmentDescriptor();
    }

    public EditAppointmentDescriptorBuilder(EditAppointmentDescriptor descriptor) {
        this.descriptor = new EditAppointmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details.
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppointmentDescriptor();
        descriptor.setAppointmentType(appointment.getAppointmentType());
        descriptor.setAppointmentDateTime(appointment.getAppointmentDateTime());
        descriptor.setSickness(appointment.getSickness());
        descriptor.setMedicine(appointment.getMedicine());
        descriptor.setPersonId(appointment.getPersonId());
    }

    /**
     * Sets the {@code AppointmentType} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withAppointmentType(String appointmentType) {
        descriptor.setAppointmentType(new AppointmentType(appointmentType));
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withAppointmentDateTime(LocalDateTime appointmentDateTime) {
        descriptor.setAppointmentDateTime(appointmentDateTime);
        return this;
    }

    /**
     * Sets the {@code Sickness} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withSickness(String sickness) {
        descriptor.setSickness(new Sickness(sickness));
        return this;
    }

    /**
     * Sets the {@code Medicine} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withMedicine(String medicine) {
        descriptor.setMedicine(new Medicine(medicine));
        return this;
    }

    /**
     * Sets the {@code PersonId} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withPersonId(Integer personId) {
        descriptor.setPersonId(personId);
        return this;
    }

    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
