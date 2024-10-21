package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book.
 */
public class ListAppointmentCommand extends ListCommand {
    public static final String MESSAGE_SUCCESS = "Listed all appointments!";

    /**
     * Lists all appointments in the appointment book to the user.*
     * @param model {@code Model} which the ListAppointmentCommand should operate on.
     * */
    @Override
    protected void listEntity(Model model) {
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    /**
     * Returns the message to be displayed after listing all appointments.
     *
     * @return the message to be displayed after listing all appointments.
    */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
