package seedu.address.model.appointment;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Appointments}'s {@code Name} of their {@code Person} matches any of the keywords given.
 */
public class AppointmentPersonContainsNamePredicate implements Predicate<Appointment> {
    private final List<String> keywords;

    public AppointmentPersonContainsNamePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Appointment appointment) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                appointment.getPerson().getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentPersonContainsNamePredicate)) {
            return false;
        }

        AppointmentPersonContainsNamePredicate otherPredicate = (AppointmentPersonContainsNamePredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
