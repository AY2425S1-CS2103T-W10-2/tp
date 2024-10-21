package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE_P;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;


public class AppointmentTest {
    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment3;
    private Person person1;
    private Person person2;
    private LocalDateTime dateTime1;
    private LocalDateTime dateTime2;

    @BeforeEach
    void setUp() {
        dateTime1 = LocalDateTime.of(2024, 1, 1, 10, 0);
        dateTime2 = LocalDateTime.of(2024, 1, 1, 11, 0);

        person1 = ALICE_P;
        person2 = BOB;

        appointment1 = new Appointment(new AppointmentType("Checkup"), dateTime1,
            person1, new Sickness("Cold"), new Medicine("Aspirin"));
        appointment2 = new Appointment(new AppointmentType("Checkup"), dateTime1,
            person1, new Sickness("Cold"), new Medicine("Aspirin"));
        appointment3 = new Appointment(new AppointmentType("Followup"), dateTime2,
            person2, new Sickness("Flu"), new Medicine("Tamiflu"));
    }

    @Test
    public void getAppointmentType_multipleAppointments_returnsCorrectAppointmentTypes() {
        assertEquals(new AppointmentType("Checkup"), appointment1.getAppointmentType());
        assertEquals(new AppointmentType("Checkup"), appointment2.getAppointmentType());
        assertEquals(new AppointmentType("Followup"), appointment3.getAppointmentType());
    }

    @Test
    public void getPerson_multipleAppointments_returnsCorrectPersons() {
        assertEquals(person1, appointment1.getPerson());
        assertEquals(person1, appointment2.getPerson());
        assertEquals(person2, appointment3.getPerson());
    }

    @Test
    public void execute_testGetSickness_success() {
        assertEquals(new Sickness("Cold"), appointment1.getSickness());
        assertEquals(new Sickness("Cold"), appointment2.getSickness());
        assertEquals(new Sickness("Flu"), appointment3.getSickness());
    }

    @Test
    public void getAppointmentDateTime_multipleAppointments_returnsCorrectDateTime() {
        assertEquals(dateTime1, appointment1.getAppointmentDateTime());
        assertEquals(dateTime1, appointment2.getAppointmentDateTime());
        assertEquals(dateTime2, appointment3.getAppointmentDateTime());
    }

    @Test
    public void getMedicine_multipleAppointments_returnsCorrectMedicine() {
        assertEquals(new Medicine("Aspirin"), appointment1.getMedicine());
        assertEquals(new Medicine("Aspirin"), appointment2.getMedicine());
        assertEquals(new Medicine("Tamiflu"), appointment3.getMedicine());
    }

    @Test
    public void isSameAppointment_compareIdenticalAndDifferentAppointments_returnsCorrectComparison() {
        assertTrue(appointment1.isSameAppointment(appointment2));
        assertTrue(appointment1.isSameAppointment(appointment1));
        assertFalse(appointment1.isSameAppointment(appointment3));
    }

    @Test
    public void equals_compareIdenticalAndDifferentAppointments_returnsCorrectEquality() {
        assertEquals(appointment1, appointment1);
        assertEquals(appointment1, appointment2);
        assertNotEquals(appointment1, appointment3);
    }

    @Test
    public void hashCode_compareIdenticalAndDifferentAppointments_returnsConsistentHashes() {
        assertEquals(appointment1.hashCode(), appointment2.hashCode());
        assertEquals(appointment2.hashCode(), appointment2.hashCode());
        assertNotEquals(appointment1.hashCode(), appointment3.hashCode());
    }

    @Test
    public void toString_validAppointments_returnsFormattedString() {
        String appointment1String = "seedu.address.model.appointment.Appointment"
            + "{appointmentId=0,"
            + " person=seedu.address.model.person.Person{personId=0, name=Alice Pauline, phone=94351253,"
            + " email=alice@example.com, address=123, Jurong West Ave 6, #08-111, status=Recovered,"
            + " tags=[[friends]]}," + " appointmentType=Checkup, appointmentDateTime=2024-01-01T10:00,"
            + " medicine=Aspirin, sickness=Cold}";

        assertEquals(appointment1String, appointment1.toString());
    }
}
