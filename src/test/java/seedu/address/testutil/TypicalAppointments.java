package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SICKNESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SICKNESS_BOB;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AppointmentBook;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    // Set default dates and times for the appointments
    public static final LocalDateTime DEFAULT_DATE_TIME_1 = LocalDateTime.of(2024, 10, 15, 10, 30);
    public static final LocalDateTime DEFAULT_DATE_TIME_2 = LocalDateTime.of(2024, 11, 10, 14, 0);
    public static final LocalDateTime DEFAULT_DATE_TIME_3 = LocalDateTime.of(2024, 12, 5, 9, 0);
    public static final LocalDateTime DEFAULT_DATE_TIME_4 = LocalDateTime.of(2024, 10, 20, 11, 15);
    public static final LocalDateTime DEFAULT_DATE_TIME_5 = LocalDateTime.of(2024, 11, 25, 16, 0);
    public static final LocalDateTime DEFAULT_DATE_TIME_6 = LocalDateTime.of(2024, 12, 1, 8, 30);
    public static final LocalDateTime DEFAULT_DATE_TIME_7 = LocalDateTime.of(2024, 10, 30, 15, 45);

    public static final Appointment APPOINTMENT_1 = new AppointmentBuilder()
        .withAppointmentType("Health Checkup").withPerson(BOB)
        .withSickness("Flu")
        .withMedicine("Panadol")
        .withDateTime(DEFAULT_DATE_TIME_1)
        .build();

    public static final Appointment APPOINTMENT_2 = new AppointmentBuilder()
        .withAppointmentType("Dental Checkup").withPerson(AMY)
        .withSickness("Toothache")
        .withMedicine("Ibuprofen")
        .withDateTime(DEFAULT_DATE_TIME_2)
        .build();

    public static final Appointment APPOINTMENT_3 = new AppointmentBuilder()
        .withAppointmentType("Eye Exam")
        .withPerson(BOB)
        .withSickness("Eye Strain")
        .withMedicine("Eye Drops")
        .withDateTime(DEFAULT_DATE_TIME_3)
        .build();

    public static final Appointment APPOINTMENT_4 = new AppointmentBuilder()
        .withAppointmentType("Physical Therapy").withPerson(AMY)
        .withSickness("Back Pain")
        .withMedicine("Paracetamol")
        .withDateTime(DEFAULT_DATE_TIME_4)
        .build();

    public static final Appointment APPOINTMENT_5 = new AppointmentBuilder()
        .withAppointmentType("Vaccination").withPerson(BOB)
        .withSickness("None")
        .withMedicine("Vaccine")
        .withDateTime(DEFAULT_DATE_TIME_5)
        .build();

    public static final Appointment APPOINTMENT_6 = new AppointmentBuilder()
        .withAppointmentType("Routine Checkup").withPerson(AMY)
        .withSickness("None")
        .withMedicine("None")
        .withDateTime(DEFAULT_DATE_TIME_6)
        .build();

    public static final Appointment APPOINTMENT_7 = new AppointmentBuilder()
        .withAppointmentType("Consultation").withPerson(BOB)
        .withSickness("Headache")
        .withMedicine("Aspirin")
        .withDateTime(DEFAULT_DATE_TIME_7)
        .build();

    // Manually added
    public static final Appointment APPOINTMENT_8 = new AppointmentBuilder()
        .withAppointmentType("Emergency Room").withPerson(AMY)
        .withSickness("Injury")
        .withMedicine("Morphine")
        .withDateTime(DEFAULT_DATE_TIME_4)
        .build();

    public static final Appointment APPOINTMENT_9 = new AppointmentBuilder()
        .withAppointmentType("Surgery").withPerson(BOB)
        .withSickness("Pre-Surgery")
        .withMedicine("Pre-Meds")
        .withDateTime(DEFAULT_DATE_TIME_5)
        .build();

    // Manually added - Appointment's details found in {@code CommandTestUtil}
    public static final Appointment APPOINTMENT_AMY = new AppointmentBuilder()
        .withAppointmentType(VALID_APPOINTMENT_TYPE_AMY)
        .withSickness(VALID_SICKNESS_AMY)
        .withMedicine(VALID_MEDICINE_AMY)
        .withDateTime(DEFAULT_DATE_TIME_6)
        .build();
    public static final Appointment APPOINTMENT_BOB = new AppointmentBuilder()
        .withAppointmentType(VALID_APPOINTMENT_TYPE_BOB)
        .withSickness(VALID_SICKNESS_BOB)
        .withMedicine(VALID_MEDICINE_BOB)
        .withDateTime(DEFAULT_DATE_TIME_7)
        .build();

    public static final String KEYWORD_MATCHING_CHECKUP = "Checkup"; // A keyword that matches Checkup

    private TypicalAppointments() {
    } // prevents instantiation

    /**
     * Returns an {@code AppointmentBook} with all the typical appointments.
     */
    public static AppointmentBook getTypicalAppointmentBook() {
        AppointmentBook ab = new AppointmentBook();
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(
            APPOINTMENT_1,
            APPOINTMENT_2,
            APPOINTMENT_3,
            APPOINTMENT_4,
            APPOINTMENT_5,
            APPOINTMENT_6,
            APPOINTMENT_7));
    }
}
