package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MedicineTest {
    @Test
    public void isValidMedicine_validMedicineNames_returnsTrue() {
        assertTrue(Medicine.isValidMedicine("Aspirin"));
        assertTrue(Medicine.isValidMedicine("Tamiflu"));
        assertTrue(Medicine.isValidMedicine("Paracetamol"));
        assertTrue(Medicine.isValidMedicine("Neoprofen"));
    }

    @Test
    public void isValidMedicine_invalidMedicineNames_returnsFalse() {
        assertFalse(Medicine.isValidMedicine(""));
        assertFalse(Medicine.isValidMedicine(" "));
        assertFalse(Medicine.isValidMedicine(" 1"));
        assertFalse(Medicine.isValidMedicine("1"));
        assertFalse(Medicine.isValidMedicine("!"));
    }

    @Test
    public void constructor_validMedicineName_createsMedicine() {
        Medicine medicine = new Medicine("Aspirin");
        assertEquals("Aspirin", medicine.value);
    }

    @Test
    public void constructor_invalidMedicineName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Medicine(""));
        assertThrows(IllegalArgumentException.class, () -> new Medicine(" "));
        assertThrows(IllegalArgumentException.class, () -> new Medicine("!"));
        assertThrows(IllegalArgumentException.class, () -> new Medicine(" 1"));
        assertThrows(IllegalArgumentException.class, () -> new Medicine("1"));
    }

    @Test
    public void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sickness(null));
    }

    @Test
    public void toString_validMedicine_returnsFormattedString() {
        Medicine medicine = new Medicine("Aspirin");
        assertEquals("Aspirin", medicine.toString());
        assertNotEquals("aspirin", medicine.toString());
    }

    @Test
    public void equals_compareIdenticalAndDifferentMedicines_returnsCorrectEquality() {
        Medicine medicine1 = new Medicine("Aspirin");
        Medicine medicine2 = new Medicine("Aspirin");
        Medicine medicine3 = new Medicine("Tamiflu");

        assertEquals(medicine1, medicine2);
        assertNotEquals(medicine1, medicine3);
        assertNotEquals(null, medicine1);
        assertNotEquals("Aspirin", medicine1);
        assertEquals(medicine1, medicine1);
    }

    @Test
    public void hashCode_compareIdenticalAndDifferentMedicines_returnsConsistentHashes() {
        Medicine medicine1 = new Medicine("Aspirin");
        Medicine medicine2 = new Medicine("Aspirin");
        Medicine medicine3 = new Medicine("Tamiflu");

        assertEquals(medicine1.hashCode(), medicine2.hashCode());
        assertNotEquals(medicine1.hashCode(), medicine3.hashCode());
    }
}
