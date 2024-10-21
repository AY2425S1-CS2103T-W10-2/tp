package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SICKNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+");

        if (splitArgs.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        String entityType = splitArgs[0];
        String indexString = splitArgs[1];
        Index index = ParserUtil.parseIndex(indexString);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_STATUS, PREFIX_TAG, PREFIX_PERSON_ID, PREFIX_DATETIME,
                        PREFIX_APPOINTMENT_TYPE, PREFIX_SICKNESS, PREFIX_MEDICINE);

        switch (entityType) {
        case PERSON_ENTITY_STRING:
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

            EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            }
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
            }
            if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
                editPersonDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
            }
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

            if (!editPersonDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditPersonCommand(index, editPersonDescriptor);

        case APPOINTMENT_ENTITY_STRING:
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_ID, PREFIX_DATETIME, PREFIX_APPOINTMENT_TYPE,
                    PREFIX_MEDICINE, PREFIX_SICKNESS);

            EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();

            if (argMultimap.getValue(PREFIX_PERSON_ID).isPresent()) {
                editAppointmentDescriptor.setPersonId(
                        ParserUtil.parsePersonId(argMultimap.getValue(PREFIX_PERSON_ID).get()));
            }
            if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
                editAppointmentDescriptor.setAppointmentDateTime(
                        ParserUtil.parseAppointmentDateTime(argMultimap.getValue(PREFIX_DATETIME).get()));
            }
            if (argMultimap.getValue(PREFIX_APPOINTMENT_TYPE).isPresent()) {
                editAppointmentDescriptor.setAppointmentType(
                        ParserUtil.parseAppointmentType(argMultimap.getValue(PREFIX_APPOINTMENT_TYPE).get()));
            }
            if (argMultimap.getValue(PREFIX_MEDICINE).isPresent()) {
                editAppointmentDescriptor.setMedicine(
                        ParserUtil.parseMedicine(argMultimap.getValue(PREFIX_MEDICINE).get()));
            }
            if (argMultimap.getValue(PREFIX_SICKNESS).isPresent()) {
                editAppointmentDescriptor.setSickness(
                        ParserUtil.parseSickness(argMultimap.getValue(PREFIX_SICKNESS).get()));
            }

            if (!editAppointmentDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditAppointmentCommand(index, editAppointmentDescriptor);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
