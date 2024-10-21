package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SICKNESS_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditAppointmentDescriptor descriptorWithSameValues = new EditAppointmentDescriptor(DESC_AMY_APPOINTMENT);
        assertEquals(DESC_AMY_APPOINTMENT, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_AMY_APPOINTMENT, DESC_AMY_APPOINTMENT);

        // null -> returns false
        assertNotEquals(null, DESC_AMY_APPOINTMENT);

        // different types -> returns false
        assertNotEquals(5, DESC_AMY_APPOINTMENT);

        // different values -> returns false
        assertNotEquals(DESC_AMY_APPOINTMENT, DESC_BOB_APPOINTMENT);

        // different appointment type -> returns false
        EditAppointmentDescriptor editedAmy = new EditAppointmentDescriptorBuilder(DESC_AMY_APPOINTMENT)
                .withAppointmentType(VALID_APPOINTMENT_TYPE_BOB)
                .build();
        assertNotEquals(DESC_AMY_APPOINTMENT, editedAmy);

        // different appointment date/time -> returns false
        editedAmy = new EditAppointmentDescriptorBuilder(DESC_AMY_APPOINTMENT)
                .withAppointmentDateTime(VALID_APPOINTMENT_DATE_TIME_BOB)
                .build();
        assertNotEquals(DESC_AMY_APPOINTMENT, editedAmy);

        // different sickness -> returns false
        editedAmy = new EditAppointmentDescriptorBuilder(DESC_AMY_APPOINTMENT)
                .withSickness(VALID_SICKNESS_BOB)
                .build();
        assertNotEquals(DESC_AMY_APPOINTMENT, editedAmy);

        // different medicine -> returns false
        editedAmy = new EditAppointmentDescriptorBuilder(DESC_AMY_APPOINTMENT)
                .withMedicine(VALID_MEDICINE_BOB)
                .build();
        assertNotEquals(DESC_AMY_APPOINTMENT, editedAmy);

        // different personId -> returns false
        editedAmy = new EditAppointmentDescriptorBuilder(DESC_AMY_APPOINTMENT)
                .withPersonId(1)
                .build();
        assertNotEquals(DESC_AMY_APPOINTMENT, editedAmy);
    }

    @Test
    public void toStringMethod() {
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        String expected = EditAppointmentDescriptor.class.getCanonicalName() + "{appointmentType="
            + editAppointmentDescriptor.getAppointmentType().orElse(null) + ", appointmentDateTime="
            + editAppointmentDescriptor.getSickness().orElse(null) + ", medicine="
            + editAppointmentDescriptor.getAppointmentDateTime().orElse(null) + ", sickness="
            + editAppointmentDescriptor.getMedicine().orElse(null) + ", personId="
            + editAppointmentDescriptor.getPersonId().orElse(null) + "}";
        assertEquals(expected, editAppointmentDescriptor.toString());
    }
}
