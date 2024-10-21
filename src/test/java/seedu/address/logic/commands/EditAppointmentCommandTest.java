package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SICKNESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAppointmentAtIndex;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditAppointmentCommand.
 */
public class EditAppointmentCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalAppointmentBook(), new UserPrefs());

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAppointment = Index.fromOneBased(model.getFilteredAppointmentList().size());
        Appointment lastAppointment = model.getFilteredAppointmentList().get(indexLastAppointment.getZeroBased());

        AppointmentBuilder appointmentInList = new AppointmentBuilder(lastAppointment);
        Appointment editedAppointment = appointmentInList.withAppointmentType(VALID_APPOINTMENT_TYPE_BOB)
            .withDateTime(VALID_APPOINTMENT_DATE_TIME_BOB)
            .withMedicine(VALID_MEDICINE_BOB).withSickness(VALID_SICKNESS_BOB).build();

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
            .withAppointmentType(VALID_APPOINTMENT_TYPE_BOB).withAppointmentDateTime(VALID_APPOINTMENT_DATE_TIME_BOB)
            .withMedicine(VALID_MEDICINE_BOB).withSickness(VALID_SICKNESS_BOB).build();
        EditCommand editCommand = new EditAppointmentCommand(indexLastAppointment, descriptor);

        String expectedMessage = String.format(
            EditCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS, Messages.formatAppointment(editedAppointment));

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
            new AppointmentBook(model.getAppointmentBook()), new UserPrefs());
        expectedModel.setAppointment(lastAppointment, editedAppointment);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, new EditAppointmentDescriptor());
        Appointment editedAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());

        String expectedMessage = String.format(
            EditCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS, Messages.formatAppointment(editedAppointment));

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
            new AppointmentBook(model.getAppointmentBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Appointment appointmentInFilteredList = model
                .getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment editedAppointment = new AppointmentBuilder(appointmentInFilteredList)
            .withAppointmentType(VALID_APPOINTMENT_TYPE_BOB).build();
        EditCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT,
            new EditAppointmentDescriptorBuilder().withAppointmentType(VALID_APPOINTMENT_TYPE_BOB).build());

        String expectedMessage = String.format(
            EditCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS, Messages.formatAppointment(editedAppointment));

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
            new AppointmentBook(model.getAppointmentBook()), new UserPrefs());
        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of appointment book
     */
    @Test
    public void execute_invalidAppointmentIndexFilteredList_failure() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentBook().getAppointmentList().size());

        EditCommand editCommand = new EditAppointmentCommand(outOfBoundIndex,
            new EditAppointmentDescriptorBuilder().withAppointmentType(VALID_APPOINTMENT_TYPE_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, DESC_AMY_APPOINTMENT);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptor(DESC_AMY_APPOINTMENT);
        EditCommand commandWithSameValues = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ListAppointmentCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditAppointmentCommand(INDEX_SECOND_APPOINTMENT, DESC_AMY_APPOINTMENT));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, DESC_BOB_APPOINTMENT));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        EditAppointmentCommand editAppointmentCommand =
                new EditAppointmentCommand(index, editAppointmentDescriptor);
        String expected = EditAppointmentCommand.class.getCanonicalName()
                + "{index=" + index + ", editAppointmentDescriptor="
                + editAppointmentDescriptor + "}";
        assertEquals(expected, editAppointmentCommand.toString());
    }

}
