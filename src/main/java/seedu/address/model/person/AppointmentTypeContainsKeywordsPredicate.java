package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;

/**
 * Tests that a {@code Appointment}'s {@code Appointment} matches any of the keywords given.
 */
public class AppointmentTypeContainsKeywordsPredicate implements Predicate<Appointment> {
    private final List<String> keywords;

    public AppointmentTypeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Appointment appt) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        appt.getAppointmentType().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentTypeContainsKeywordsPredicate)) {
            return false;
        }

        AppointmentTypeContainsKeywordsPredicate otherAppointmentTypeContainsKeywordsPredicate =
                (AppointmentTypeContainsKeywordsPredicate) other;
        return keywords.equals(otherAppointmentTypeContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
