package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SICKNESS;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to the appointment book.
 */
public class AddAppointmentCommand extends AddCommand {
    public static final String COMMAND_WORD = "add appt ";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an appointment to the appointment book. \n"
            + "Parameters: "
            + PREFIX_APPOINTMENT_TYPE + "AppointmentType "
            + PREFIX_DATETIME + "Date "
            + PREFIX_PERSON_ID + "PersonId "
            + PREFIX_SICKNESS + "Sickness "
            + PREFIX_MEDICINE + "Medicine \n"
            + "Example: " + COMMAND_WORD
            + PREFIX_APPOINTMENT_TYPE + "Check up "
            + PREFIX_DATETIME + "2024-10-16 12:30:30 "
            + PREFIX_PERSON_ID + "1 "
            + PREFIX_SICKNESS + "Common Cold "
            + PREFIX_MEDICINE + "Paracetamol";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "This appointment already exists in the appointment book";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "This person ID does not belong to anyone in the address book";

    private final AppointmentDescriptor toAdd;
    private final int personId;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(AppointmentDescriptor appointmentDescriptor, int personId) {
        requireAllNonNull(appointmentDescriptor, personId);
        this.toAdd = appointmentDescriptor;
        this.personId = personId;
    }

    /*
     * Checks if the entity being added to model already exists.
     */
    @Override
    protected boolean alreadyExists(Model model) {
        return model.hasAppointment(toAdd);
    };

    /*
     * Adds the entity to the model.
     */
    @Override
    protected void addEntity(Model model) throws CommandException {
        Optional<Person> personOptional = model.findPerson(personId);
        if (!personOptional.isPresent()) {
            throw new CommandException(getPersonIdDoesNotExistMessage());
        }
        model.addAppointment(personOptional.get(), toAdd);
    };

    /*
     * Returns success message to display upon adding entity.
     */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    };

    /*
     * Returns the message to display when there is a duplicate.
     */
    @Override
    protected String getDuplicateEntityMessage() {
        return MESSAGE_DUPLICATE_APPOINTMENT;
    };

    /*
     * Returns the message to display when the person ID does not exist.
     */
    protected String getPersonIdDoesNotExistMessage() {
        return MESSAGE_PERSON_NOT_FOUND;
    };

    /**
     * Formats the entity for displaying in the success message.
     */
    @Override
    protected String formatEntity() {
        return Messages.formatAppointment(toAdd);
    };

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (alreadyExists(model)) {
            throw new CommandException(getDuplicateEntityMessage());
        }

        addEntity(model);
        return new CommandResult(String.format(getSuccessMessage(), formatEntity()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand otherAddAppointmentCommand)) {
            return false;
        }

        return toAdd.equals(otherAddAppointmentCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
