package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SICKNESS;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;
import seedu.address.model.person.Person;


/**
 * Edits the details of an existing appointment in the appointment book.
 */
public class EditAppointmentCommand extends EditCommand {
    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "This appointment already exists in the appointment book.";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "This person ID does not belong to anyone in the address book";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING
            + ": Edits the details of an appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_PERSON_ID + "PERSON_ID] "
            + "[" + PREFIX_APPOINTMENT_TYPE + "APPOINTMENT_TYPE] "
            + "[" + PREFIX_DATETIME + "DATE_TIME] "
            + "[" + PREFIX_SICKNESS + "SICKNESS] "
            + "[" + PREFIX_MEDICINE + "MEDICINE] \n"
            + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " 1 "
            + PREFIX_APPOINTMENT_TYPE + "Health Checkup "
            + PREFIX_MEDICINE + "Panadol";

    /**
     * Creates an EditPersonCommand to add the specified {@code Person}
     * @param index The index of the appointment to edit.
     * @param editAppointmentDescriptor Details to edit the appointment with.
     */
    public EditAppointmentCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        super(index, editAppointmentDescriptor);
    }

    /**
     * Returns the message to display when the person ID does not exist.
     * @return A string indicating that the person ID does not exist.
     */
    protected String getPersonIdDoesNotExistMessage() {
        return MESSAGE_PERSON_NOT_FOUND;
    };

    /**
     * Checks if the given entity exists within the model.
     *
     * @param model The model to check against.
     * @param entity The entity to verify its existence.
     * @return true if the model contains the specified entity, false otherwise.
     * @throws CommandException if there is an error during the existence check.
     */
    @Override
    protected boolean hasEntity(Model model, Object entity) throws CommandException {
        return model.hasAppointment((Appointment) entity);
    }

    /**
     * Checks if the edited entity is not the same as the entity to edit.
     *
     * @param model The model that contains the entities.
     * @param editedEntity The entity after the edit.
     * @param entityToEdit The original entity to be edited.
     * @return true if the edited entity is different from the entity to edit, false otherwise.
     * @throws CommandException if there is an error during the comparison.
     */
    @Override
    protected boolean isSameEntity(Model model, Object editedEntity, Object entityToEdit)
            throws CommandException {
        Appointment entityToEditCasted = (Appointment) entityToEdit;
        return !(entityToEditCasted.isSameAppointment((Appointment) editedEntity));
    }

    /**
     * Retrieves the list of filtered appointments from the model.
     *
     * @param model The model containing the filtered list.
     * @return A list of filtered appointments.
     */
    @Override
    protected List<Appointment> getFilteredList(Model model) {
        return model.getFilteredAppointmentList();
    }

    @Override
    protected void editEntity(
            Model model,
            Object editedAppointment,
            Object appointmentToEdit) throws CommandException {
        model.setAppointment((Appointment) appointmentToEdit, (Appointment) editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    @Override
    protected Object createEditedEntity(Model model, Object appointmentToEdit,
            EditEntityDescriptor editAppointmentDescriptor) throws CommandException {
        assert appointmentToEdit != null;
        EditAppointmentDescriptor editAppointmentDescriptorCasted =
                (EditAppointmentDescriptor) editAppointmentDescriptor;
        Appointment appointmentToEditCasted = (Appointment) appointmentToEdit;

        int appointmentId = appointmentToEditCasted.getAppointmentId();
        Optional<Integer> personId = editAppointmentDescriptorCasted.getPersonId();
        Person updatedPerson = appointmentToEditCasted.getPerson();
        if (personId.isPresent()) {
            Optional<Person> person = model.findPerson(personId.get());
            if (person.isEmpty()) {
                throw new CommandException(getPersonIdDoesNotExistMessage());
            } else {
                updatedPerson = person.get();
            }
        }

        AppointmentType updatedAppointmentType =
                editAppointmentDescriptorCasted.getAppointmentType()
                        .orElse(appointmentToEditCasted.getAppointmentType());
        LocalDateTime updatedAppointmentDateTime =
                editAppointmentDescriptorCasted.getAppointmentDateTime()
                        .orElse(appointmentToEditCasted.getAppointmentDateTime());
        Medicine updatedMedicine = editAppointmentDescriptorCasted.getMedicine()
                .orElse(appointmentToEditCasted.getMedicine());
        Sickness updatedSickness = editAppointmentDescriptorCasted.getSickness()
                .orElse(appointmentToEditCasted.getSickness());

        return new Appointment(
                updatedAppointmentType,
                updatedAppointmentDateTime,
                updatedPerson,
                updatedSickness,
                updatedMedicine,
                appointmentId
        );
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_EDIT_APPOINTMENT_SUCCESS;
    }

    @Override
    protected String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_APPOINTMENT;
    }

    @Override
    protected String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX;
    }

    @Override
    protected String formatEntity(Object entity) {
        assert entity instanceof Appointment;
        return Messages.formatAppointment((Appointment) entity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("editAppointmentDescriptor", editEntityDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor extends EditCommand.EditEntityDescriptor {
        private AppointmentType appointmentType;
        private LocalDateTime appointmentDateTime;
        private Sickness sickness;
        private Medicine medicine;
        private Integer personId;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            super(toCopy);
            setAppointmentType(toCopy.appointmentType);
            setAppointmentDateTime(toCopy.appointmentDateTime);
            setSickness(toCopy.sickness);
            setMedicine(toCopy.medicine);
            setPersonId(toCopy.personId);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    appointmentType,
                    appointmentDateTime,
                    sickness,
                    medicine,
                    personId);
        }

        public void setAppointmentType(AppointmentType appointmentType) {
            this.appointmentType = appointmentType;
        }

        public Optional<AppointmentType> getAppointmentType() {
            return Optional.ofNullable(appointmentType);
        }

        public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
            this.appointmentDateTime = appointmentDateTime;
        }

        public Optional<LocalDateTime> getAppointmentDateTime() {
            return Optional.ofNullable(appointmentDateTime);
        }

        public void setSickness(Sickness sickness) {
            this.sickness = sickness;
        }

        public Optional<Sickness> getSickness() {
            return Optional.ofNullable(sickness);
        }

        public void setMedicine(Medicine medicine) {
            this.medicine = medicine;
        }

        public Optional<Medicine> getMedicine() {
            return Optional.ofNullable(medicine);
        }

        public void setPersonId(int personId) {
            this.personId = personId;
        }

        public Optional<Integer> getPersonId() {
            return Optional.ofNullable(personId);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor otherEditAppointmentDescriptor)) {
                return false;
            }

            return Objects.equals(appointmentType, otherEditAppointmentDescriptor.appointmentType)
                    && Objects.equals(appointmentDateTime, otherEditAppointmentDescriptor.appointmentDateTime)
                    && Objects.equals(medicine, otherEditAppointmentDescriptor.medicine)
                    && Objects.equals(sickness, otherEditAppointmentDescriptor.sickness)
                    && Objects.equals(personId, otherEditAppointmentDescriptor.personId);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("appointmentType", appointmentType)
                    .add("appointmentDateTime", appointmentDateTime)
                    .add("medicine", medicine)
                    .add("sickness", sickness)
                    .add("personId", personId)
                    .toString();
        }
    }
}
