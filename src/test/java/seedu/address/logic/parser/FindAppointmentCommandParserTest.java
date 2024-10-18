package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindAppointmentCommand;

public class FindAppointmentCommandParserTest {

    @Test
    public void parse_noPrefix_failure() {
        FindAppointmentCommandParser parser = new FindAppointmentCommandParser();;
        assertParseFailure(parser, " appt Alex",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_unsupportedPrefix_failure() {
        FindAppointmentCommandParser parser = new FindAppointmentCommandParser();;
        assertParseFailure(parser, " appt p/81505050",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_success() {
        FindAppointmentCommandParser parser = new FindAppointmentCommandParser();;
        assertDoesNotThrow(() -> parser.parse(" appt n/Alex"));
    }


}
