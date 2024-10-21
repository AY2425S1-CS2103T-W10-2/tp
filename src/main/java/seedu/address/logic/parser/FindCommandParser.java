package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand<?>> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @param  args user input
     * @return findCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String entityType = getEntity(trimmedArgs);
        String[] keywords = getArgs(trimmedArgs);

        switch (entityType) {
        case PERSON_ENTITY_STRING:
            return new FindPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        case APPOINTMENT_ENTITY_STRING:
            return new FindAppointmentCommandParser().parse(trimmedArgs);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private String getEntity(String args) {
        String[] nameKeywords = args.split("\\s+");
        String entityType = nameKeywords[0];
        return entityType;
    }

    private String[] getArgs(String args) throws ParseException {
        String[] nameKeywords = args.split("\\s+");
        String entityType = nameKeywords[0];

        nameKeywords = Arrays.copyOfRange(nameKeywords, 1, nameKeywords.length);
        if (nameKeywords.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return nameKeywords;
    }

    private String getArgString(String args) {
        return args.substring(args.indexOf(' ') + 1);
    }
}
