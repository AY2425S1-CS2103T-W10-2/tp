package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Finds a subset of appointments from the list of appointments,
 * given that they match the predicate given
 */
public class FindAppointmentCommand extends FindCommand<Appointment> {

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + " appt : Finds all appointments matching the following parameters.\n"
        + "To specify name, use the parameter n/[NAME] where [NAME] is a list of names, non-case-sensitive,"
        + "separated by spaces.\n"
        + "Parameters: n/[NAME]\n"
        + "Example: " + COMMAND_WORD + " " + ParserUtil.APPOINTMENT_ENTITY_STRING + " n/alice bob";

    /**
     * Creates the command
     * @param predicate predicate to filter appointments
     */
    public FindAppointmentCommand(Predicate<Appointment> predicate) {
        super(predicate);
    }

    /**
     * Finds all persons with names matching the predicate in the address book to the user.
     * @param model {@code Model} which the FindAppointment command should operate on.
     * */
    @Override
    protected void findEntity(Model model) {
        model.updateFilteredAppointmentList(this.predicate);
    }

    /**
     * Returns the message to be displayed after finding the appointment(s).
     *
     * @return the message to be displayed after finding all appointment(s).
     */
    @Override
    public String getSuccessMessage(Model model) {
        return String.format(Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, model.getFilteredAppointmentList().size());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindAppointmentCommand)) {
            return false;
        }
        FindAppointmentCommand otherFindAppointmentCommand = (FindAppointmentCommand) other;
        return predicate.equals(otherFindAppointmentCommand.predicate);
    }
}
