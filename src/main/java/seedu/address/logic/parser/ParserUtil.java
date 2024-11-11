package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String PERSON_ENTITY_STRING = "person";
    public static final String APPOINTMENT_ENTITY_STRING = "appt";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(Index.MESSAGE_CONSTRAINTS);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String status} into an {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String personId} into a {@code int personId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code personId} is invalid.
     */
    public static int parsePersonId(String personId) throws ParseException {
        requireNonNull(personId);
        String trimmerPersonId = personId.trim();
        int parsedPersonId = Integer.parseInt(trimmerPersonId);
        if (parsedPersonId < 0) {
            throw new ParseException("personId needs to be a non-negative integer");
        }
        return parsedPersonId;
    };

    /**
     * Parses a {@code String appointmentDateTime} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code appointmentDateTime} is invalid.
     */
    public static LocalDateTime parseAppointmentDateTime(String appointmentDateTime) throws ParseException {
        requireNonNull(appointmentDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(appointmentDateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date-time format. "
                    + "Expected format: yyyy-MM-dd HH:mm for example 2024-04-24 13:00 is 24th March 2024, 1:00 PM", e);
        }
    }

    /**
     * Parses a {@code String appointmentType} into a {@code AppointmentType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code appointmentType} is invalid.
     */
    public static AppointmentType parseAppointmentType(String appointmentType) throws ParseException {
        requireNonNull(appointmentType);
        String trimmedAppointmentType = appointmentType.trim();
        if (!AppointmentType.isValidAppointmentType(trimmedAppointmentType)) {
            throw new ParseException(AppointmentType.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentType(trimmedAppointmentType);
    }

    /**
     * Parses a {@code String sickness} into a {@code Sickness}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sickness} is invalid.
     */
    public static Sickness parseSickness(String sickness) throws ParseException {
        requireNonNull(sickness);
        String trimmedSickness = sickness.trim();
        if (!Sickness.isValidSickness(sickness)) {
            throw new ParseException(Sickness.MESSAGE_CONSTRAINTS);
        }
        return new Sickness(trimmedSickness);
    }

    /**
     * Parses a {@code String medicine} into a {@code Medicine}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medicine} is invalid.
     */
    public static Medicine parseMedicine(String medicine) throws ParseException {
        requireNonNull(medicine);
        String trimmedMedicine = medicine.trim();
        if (!Medicine.isValidMedicine(medicine)) {
            throw new ParseException(Medicine.MESSAGE_CONSTRAINTS);
        }
        return new Medicine(trimmedMedicine);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseAppointmentDate(String date) throws ParseException {
        requireNonNull(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format. Expected format: YYYY-MM-DD", e);
        }
    }
}
