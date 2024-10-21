package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book.
 */
public class ListPersonCommand extends ListCommand {
    public static final String MESSAGE_SUCCESS = "Listed all persons!";

    /**
     * Lists all persons in the address book to the user.*
     * @param model {@code Model} which the ListPersonCommand should operate on.
     * */
    @Override
    protected void listEntity(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Returns the message to be displayed after listing all persons.
     *
     * @return the message to be displayed after listing all persons.
    */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
