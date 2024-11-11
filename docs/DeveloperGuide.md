---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---
<br>

![](images/doctrack.png)
# DocTrack Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Acknowledgements**

- Initial project template (code and documentation):
    - [AB3](https://github.com/se-edu/addressbook-level3) - Adapted the overall structure and some core functionalities of AB3 for DocTrack.
- Third-party libraries:
    - [JUnit](https://junit.org/junit5/) - Used for testing.
    - [JavaFX](https://openjfx.io/) - Used for the graphical user interface.
    - [Jackson](https://github.com/FasterXML/jackson) - Used for JSON parsing and serialization.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Design**

### Architecture

The ***Architecture Diagram*** below explains the high-level design of the DocTrack application.

<puml src="diagrams/ArchitectureDiagram.puml" width="280"></puml>

<br><br>

Given below is a quick overview of main components and how they interact with each other.

<br>

#### Main components of the architecture

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W10-2/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W10-2/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

<br>

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of DocTrack.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of DocTrack in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

<br>

#### How the architecture components interact with each other

The *Sequence Diagram* below shows how the components interact with each other, for the scenario where the user enters the command `delete  person 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574"></puml>

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the 
  corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class _(reason: to prevent outside component's being coupled to the implementation of a component)_, as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300"></puml>

<br><br>

The sections below give more details of each component.

<br>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W10-2/tp/tree/master/src/main/java/seedu/address/ui)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"></puml>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `AppointmentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` or `Appointment` object residing in the `Model`.

<br>

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W10-2/tp/tree/master/src/main/java/seedu/address/logic)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"></puml>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command"></puml>

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"></puml>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<br>

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W10-2/tp/tree/master/src/main/java/seedu/address/model)

<puml src="diagrams/ModelClassDiagram.puml" width="750"></puml>

The `Model` component,

* In regard to `Person` objects:
  * stores the details of a person in a `PersonDescriptor` object
  * stores the `PersonDescriptor` object with a `personId` in the `Person` class.
  * stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
  * stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* In regard to `Appointment` objects:
  * stores the details of an appointment in an `AppointmentDescriptor` object
  * stores the `AppointmentDescriptor` object with a `Person` and `appointmentId` in the `Appointment` class.
  * stores the address book data i.e., all `Appointment` objects (which are contained in a `UniqueAppointmentList` object).
  * stores the currently 'selected' `Appointment` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Appointment>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list (the 
`UniqueTagList`) in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects. Similarly, the `Appointment` objects are shown as such too.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="750"></puml>

</box>

<br>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W10-2/tp/tree/master/src/main/java/seedu/address/storage)

<puml src="diagrams/StorageClassDiagram.puml" width="650"></puml>

* The `Storage` component can save patient data, appointment data, and user preference data in JSON format, and read them back into corresponding objects.
* The `Storage` interface inherits from `AddressBookStorage`, `AppointmentBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* Patient data: 
  * Data is saved in `JsonAddressBookStorage` which inherits from interface `AddressBookStorage`.
  * Data is saved as `JsonSerializableAddressBook` which consists of `JsonAdaptedPerson` and 
    `JsonAdaptedTag` which embodies the actual data of the individual patient and their data
* Appointment data:
  * Data is saved in `JsonAppointmentBookStorage` which inherits from interface `AppointmentBookStorage`.
  * Data is saved as `JsonSerializableAppointmentBook` which consists of `JsonAdaptedAppointment` which 
    embodies the actual data of appointments and appointment details
* User Preference data:
    * Data is saved in `UserPrefsStorage` interface and saved as `JsonUserPrefsStorage`
* The `Storage` component depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

<br>

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Flow**

The activity diagram shows the general sequence of steps when a user interacts with DocTrack.

<puml src="diagrams/OverallFlowActivityDiagram.puml" width="700"></puml>

1. The user types a command in the `CommandBox`.
2. The `AddressBookParser` parses the command.
3. If the command is a known command and is in a valid format, a parser creates the corresponding 
   `Command` object. Else, an error is displayed. 
4. The `Command` object is executed.
5. The `UI` displays the result of the command execution to the user.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Implementation of entity command features**

Entity commands include `add`, `delete`, `find`, `clear`, `edit`, and `list` commands. 
* Hence, `xyzCommand` can be `addCommand`, `deleteCommand`, `findCommand`, `clearCommand`, `editCommand`, or `listCommand`.

The sequence diagram shows how an entity command is executed:

<puml src="diagrams/EntityCommandSequenceDiagram.puml" alt="EntityCommandSequenceDiagram"></puml>

<box type="info" seamless>

**Note:** There are two entities, `Person` and `Appointment`.
- The entity referred in `FindEntityCommand` refers to `FindPersonCommand` and `FindAppointmentCommand`.
- Similarly, the entity referred in `AddEntityCommand` refers to `AddPersonCommand` and `AddAppointmentCommand`.
- This applies for the other commands as well.
</box>

**Step 1**. The user types an `xyz` command input in the `CommandBox`, followed by the type of entity, 
`person` or `appt`. This is followed by appropriate arguments and prefixes.


**Step 2**. The input is passed to the `LogicManager`. `LogicManager` then calls the 
`AddressBookParser::parseCommand` method to parse the input.


**Step 3**. The `AddressBookParser` parses the input and creates an `xyzCommandParser` object, which is 
returned to the `AddressBookParser`.


**Step 4**. The `AddressBookParser` calls the 
`xyzCommandParser::parse` method to parse the arguments. 


**Step 5**. The `xyzCommandParser` creates an `xyzCommand` object, which is returned to the `LogicManager`.


**Step 6**. The `LogicManager` calls the `xyzCommand : execute` method, which creates a `CommandResult` 
Object.


**Step 7**. The `CommandResult` object is returned to the `LogicManager`.

<br>

### General Design Considerations

**Aspect: Whether to implement entity commands as separate commands or through an abstract base command**
- **Alternative 1 (Current choice):** Implement an abstract EntityCommand class that specific entity 
  commands (e.g. `AddPersonCommand`, `AddAppointmentCommand`, `DeletePersonCommand`,`DeleteAppointmentCommand`) inherit from.
  - **Pros**: Allows for reuse of code logic between entity commands.
  - **Cons**: Requires additional parsing logic since entity in command must be distinguished (person or appointment), which can add complexity.
- **Alternative 2:** Implement each entity command as entirely separate classes.
  - **Pros**: Creates a separate command, so the implementations of each command are separated and less coupled.
  - **Cons**: Results in significant code duplication.


<br>

**Aspect: What constitutes duplicate person that should not be added or edited into the AddressBook**
- **Alternative 1 (Current choice):** Duplicate person is one that has the same name (case-insensitive) 
  and same phone number as an existing person.
  - **Pros**: Name and phone numbers are identifiers that are commonly recognized by users.
  - **Cons**: Extra logic required to determine equality of different people.
- **Alternative 2:** A duplicate person is defined more loosely or strictly (e.g. by name only).
  - **Pros**: Less logic required to determine equality of different people.
  - **Cons**: Different people could have the same name, but cannot be added into the AddressBook.

<br>

**Aspect: What constitutes duplicate appointment that should not be added or edited into the AppointmentBook**
- **Alternative 1 (Current choice):** Duplicate appointment is one that has same person, date and time, 
  and appointment type as an existing appointment.
  - **Pros**: Provides a rule to avoid scheduling conflicts of same person.
  - **Cons**: Extra logic required to determine equality of different appointments.
- **Alternative 2:** Define duplicates with only the date and time
  - **Pros**: Less logic required to determine equality of different appointments.
  - **Cons**: Different appointments with different persons could have the same date time, but cannot be added 
    into the AppointmentBook.

<br>

### Command-Specific Design Considerations

#### Add Appointment feature

**Aspect: Whether appointment fields should be optional**
- **Alternative 1 (Current choice):** `Sickness` and `Medicine` fields are optional.
  - **Pros**: This allows users to create an appointment without specifying all fields initially, which is 
    more realistic and practical as some details may not be available at the time of creation.
- **Alternative 2**: Make all fields mandatory.
  - **Pros**: Ensures complete data at the time of appointment creation, which may simplify data handling and reduce the need for future edits.
  - **Cons**: Can be inconvenient for users who do not have all details available immediately, possibly leading to frustration or delays in creating appointments.

<br>

**Aspect: What input should be valid for fields `Sickness` and `Medicine`**
- **Alternative 1 (Current choice):** Require input with at least one alphabet.
  - **Pros**: Ensures these fields contain meaningful data, reducing the likelihood of accidental or erroneous inputs.
- **Alternative 2:** Allow any value as valid input.
  - **Cons**: Increases the risk of erroneous inputs, such as empty fields, accidental symbols, or 
    irrelevant characters, potentially reducing data quality.

<br>

#### Delete / Clear Person feature

**Aspect: Whether `delete person` and `clear person` should remove appointments too**

- **Alternative 1 (Current choice):** Deleting person and clearing the person list also removes appointments with the `personId` of the deleted person(s).
  - **Pros**: This prevents the case where appointments are linked to personIds that are non-existent.
- **Alternative 2:** Deleting person and clearing the person list will not remove any appointments with the `personId` of that person.
  - **Cons**: This assumes the user would delete the appointments linked to the deleted person's `personId`. However, the user might forget to do so. 
  
<br>

#### Edit Person / Appointment feature

**Aspect: What value to use for indicating entity**

- **Alternative 1 (Current choice):** Use the index of the person / appointment in the list.
  - **Pros**: Enables efficient retrieval directly from the list, simplifying implementation.
  - **Cons**: Entity indexes may shift after deletions, which could lead to unintended edits if the user is not aware of changes in ordering.
- **Alternative 2:** Use a unique ID for each entity.
  - **Pros**: IDs remain consistent regardless of list modifications, ensuring stable reference to the entity.
  - **Cons**: Implementing ID-based retrieval requires additional logic and may be slower, especially for larger lists.

<br>

**Aspect: Whether to check if the `personId` associated with edited appointment corresponds to an existing person in the AddressBook**
- **Alternative 1 (Current choice):** When executing the `edit appt` command, check if the new `personId` 
  exists in the AddressBook.
  - **Pros:** Ensures data integrity by preventing appointments from being linked to non-existent persons.
  - **Cons:** Requires additional validation logic during the edit process.
- **Alternative 2:** Execution of the `edit appt` command does not check if the new `personId` exists in the AddressBook.
  - **Pros:** Simplifies the edit process by removing the need for additional validation.
  - **Cons:** Increases the risk of data inconsistency, as appointments may be linked to non-existent persons.

<br>

#### Find Person / Appointment feature

**Aspect: How to implement the `find person` and `find appt` commands that allow finding by multiple criteria**
- **Alternative 1 (Current choice)**: Create one find command that supports filtering by multiple criteria (name, date) using prefixes.
  - **Pros**: Fast and easy to find by date and name.
  - **Cons**: Confusing syntax from user's perspective.
- **Alternative 2**: Create different find commands, find by date, find by name etc.
  - **Pros**: Much easy in terms of user experience.
  - **Cons**: More repeated code for each command.

<br>

**Aspect: How to combine multiple prefixes when executing the `find` commands**
- **Alternative 1 (Current choice)**: Prefixes should be combined using an AND condition.
  - **Pros**: Ensures more specific search results, as all conditions must be met.
  - **Cons**: May be too restrictive.
- **Alternative 2**: Prefixes should be combined using an OR condition
  - **Pros**: Allows for more flexible and broader search results, as any one of the conditions can yield matches.
  - **Cons**: May return too many results.

<br>

**Aspect: Whether to implement case sensitivity in matching for search terms**
- **Alternative 1 (Current choice)**: Implement case-insensitive matching for search terms.
    - **Pros**: Enhances user experience by allowing searches to ignore case differences.
    - **Cons**: Slightly more processing required to normalise the case during searches.
- **Alternative 2**: Implement case-sensitive matching for search terms.
    - **Pros**: Potentially faster searches, as no additional case normalization is required.
    - **Cons**: Reduces user-friendliness.
<br>

<box type="tip" theme="success" seamless>

**Tip:**
To add a new predicate, navigate the corresponding entity folder in the `model` package. There, you can create a new class that implements `Predicate<Entity>`. Ensure that this method has a test method which defines the specific condition for a predicate.

</box>

---

<br>

## Implementation of general command features
General commands include the `exit` and `help` commands.

The sequence diagram shows how a general command (`ExitCommand` or `HelpCommand`) is executed:
<puml src="diagrams/GeneralCommandsSequenceDiagram.puml" width="600"></puml>

**Step 1.** The user types an `xyz` command (`exit` or `help`) in the `CommandBox`, which is then passed to the `LogicManager`.

**Step 2.** The `LogicManager` calls the `AddressBookParser::parseCommand` method to parse the `xyz` command.

**Step 3.** The `AddressBookParser` creates an `XYZCommand` object, which is returned to the 
`LogicManager`. The `XYZCommand` object can be an `ExitCommand` or a `HelpCommand`.

**Step 4.** The `LogicManager` calls the `XYZCommand::execute` method, which creates a new `CommandResult` 
object.

**Step 5.** The `CommandResult` object is returned to the `LogicManager`.

<br>

### Exit feature
When a user types an `exit` command, the DocTrack application will exit.
  
<br>

### Help feature
When a user types a `help` command, the DocTrack application will display a `HelpWindow`.

#### Design considerations

**Aspect: How to display help information:**

* **Alternative 1 (current choice):** Display help information in a new window.
  * **Pros**: Keeps the main application window uncluttered.
  * **Cons**: Requires managing an additional window.
* **Alternative 2:** Display help information in a modal dialog.
  * **Pros**: Simpler to implement.
  * **Cons**: Can clutter the main application window and interrupt the user's workflow.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Appendix: Miscellaneous Design Considerations**

### Data storage and files

**Aspect: Save patient and appointment data in**
<br>
* **Alternative 1 (current choice):** two different files, patient data in `data/addressbook.json` and appointment data in `data/appointmentbook.json`.
    * **Pros**: More organised file management, with a quicker read and write times for each file.
    * **Cons**: Higher chance of inconsistencies between patient and appointment data.
* **Alternative 2:** one single file named `data/addressbook.json`
    * **Pros**: Simplicity and convenience of one file for all information.
    * **Cons**: Slower read and write times for file, especially if the user is only accessing one of patient or appointment data.

<box type="warning" seamless>

**Note:**
For `Appointment`, the fields `Sickness` and `Medicine` are optional. Hence, if `Sickness` or `Medicine` 
is not specified, it would be represented as `"null"`, in the `appointmentbook.json` file.
</box>

<br>

**Aspect: When the data is updated in the `.json` file**
<br>
* **Alternative 1 (current choice):** Automatically save all changes after any command that changes the data. 
    * **Pros**: Simplifies the process for the user, without needing to save manually.
    * **Cons**: May be slow if there are many changes to save.
* **Alternative 2:** Prompt the user to save changes before exiting.
    * **Pros**: Gives the user more control over the saving process.
    * **Cons**: May be annoying for users who do not want an additional step to save changes.

<br>

### Parsing

**Aspect: How to parse the commands**
<br>
Context: The commands (other than the general) have the command format: `COMMAND ENTITY_TYPE ENTITY_ARGS`
<br>
* **Alternative 1 (current choice):** Parse `ENTITY_TYPE` and `ENTITY_ARGS` separately.
  * **Pros**: Easier to parse and debug, as the parsing is separated into different portions. 
  * **Cons**: More verbose, and less centralised.
* **Alternative 2**: Parse them together.
    * **Pros**: Everything is parsed together, centralising the parsing logic.
    * **Cons**: Harder to parse and debug.

<br>

**Aspect: Command format (with or without prefixes)**
<br>
* **Alternative 1 (current choice):** Use prefixes.
  * **Pros**: Easier to identify markers for each parameter, and prefixes allow for free positioning of arguments.
  * **Cons**: Less intuitive for the user at start, and need to create the prefixes.
* **Alternative 2:** Use no prefixes, parse the arguments based on position.
  * **Pros**: More intuitive at start, no need to create prefixes.
  * **Cons**: Harder to identify markers - may result in issues with arguments that have spaces in between, no free positioning, and might be harder to implement variable amount of arguments.

<br>

**Aspect: The use of `ArgumentMultimap` across different entities**
* **Alternative 1 (current choice):** Use the same `ArgumentMultimap` for all entities.
  * **Pros**: Prefixes are shared universally, making it more consistent across entities. There is also less code duplication.
  * **Cons**: Might be more cluttered, as all the prefixes are together, and inability to use same prefixes for different arguments across entities.
* **Alternative 2**: Use different `ArgumentMultimap` for each entity.
  * **Pros**: Less cluttered, only prefixes for that entity will be addressed. Prefixes used in different entities can also be used for different arguments.
  * **Cons**: More code duplication, as there are shared prefixes. Might be harder to track shared prefixes, causing confusion to users.

<br>

### User Interface

**Aspect: How to show appointment and person lists**
<br>
Context: There are two entity types (appointment and person) being managed in DocTrack.
<br>
* **Alternative 1 (current choice):** Show lists as two separate panels side by side.
  * **Pros**: Easier to see all information at once, and easier to cross-reference when doing `add appt` 
    or `edit appt` commands, which may need information about `personId`.
  * **Cons**: More verbose and could result in information overload.
* **Alternative 2**: Show only one list at a time, but toggle between the two using a `list appt` or `list person` command.
  * **Pros**: Information is simpler to digest.
  * **Cons**: More overhead of handling switching between lists, and difficult to cross-reference when typing certain commands.

<br>

**Aspect: Color Scheme**
<br>
* **Alternative 1 (current choice):** Create new red, white and gray color scheme.
    * **Pros**: Creates brand identity, makes the GUI more appealing to the target audience.
    * **Cons**: Constant oversight needed to maintain color scheme in future feature enhancements.
* **Alternative 2:** Use the original AB3 gray color scheme
    * **Pros**: No extra needed to maintain color scheme. 
    * **Cons**: Colors are not appealing and not professional, they might not suit the target audience.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Appendix: Requirements**

### Product scope

**Target user profile**:
* General Practitioners (GPs) at small clinics

**Value proposition**: 
- Time spent looking through paper medical documents should be spent in other life-saving activities. 
- Our product, DocTrack, resolves this issue by creating fast access to patient contact details as well as their relevant appointment details, allowing GPs to contact and monitor their patients easily.

<br>

### User stories

Priorities: High `* * *` (must have), Medium `* *` (nice to have) , Low `*` (unlikely to have)

| Priority | As a …​              | I want to …​                                       | So that I can…​                                                      |
|----------|----------------------|----------------------------------------------------|----------------------------------------------------------------------|
| `* * *`  | doctor               | add appointments                                   | find them in the future for reference                                |
| `* * *`  | doctor               | schedule a new patient appointment                 | ensure that the patient is properly booked for consultation          |
| `* * *`  | doctor               | remove an appointment that is no longer needed     | free up time slots for other patients and avoid scheduling conflicts |
| `* * *`  | doctor               | view all upcoming appointments for better planning | organize my day effectively and ensure no appointments are missed    |
| `* * *`  | administrative staff | manage patient contact information                 | easily communicate with patients                                     |
| `* * *`  | administrative staff | update patient details                             | maintain accurate records                                            |
| `* * *`  | administrative staff | get details on a specific patient's appointments   | keep track of the patient                                            |
| `* * *`  | administrative staff | store all patients information                     | retrieve them in the future                                          |
| `* * *`  | nurse                | track appointments                                 | get ready to serve patients                                          |
| `* *`    | doctor               | access appointment history                         | understand patient visit patterns                                    |
| `* *`    | doctor               | categorize patients by conditions or treatments    | easily track patient groups                                          |
| `* *`    | doctor               | find free slots in the appointments                | find gaps for appointments or holidays                               |
| `* *`    | administrative staff | get details on appointments for the day            | keep track of the day's appointments                                 |
| `* *`    | doctor               | shift appointments to a different time             | change appointments based on holidays, etc.                          |
| `* *`    | administrative staff | schedule follow-up appointments                    | keep track of patients' appointments                                 |
| `* *`    | doctor               | add mood status to appointment details             | keep track of patient health each time we meet                       |
| `* *`    | doctor               | sort patients by closest future appointment date   | see which patient to see next                                        |
| `* *`    | doctor               | find duplicate errors within the system            | not have erroneous appointments                                      |
| `* *`    | doctor               | organize appointments                              | arrange my schedule accordingly                                      |
| `* *`    | doctor               | set holidays/free days                             | disallow appointments during certain dates                           |
| `* * `   | doctor               | categorise patients based on certain factors       | easily track patients with certain statuses                          |
| `* * `   | doctor               | add list of allergies for a certain patient        | not prescribe them stuff that will kill them                         |
| `*`      | doctor               | view patient's medical history                     | make informed treatment decisions                                    |
| `*`      | doctor               | access test results for patients                   | review and discuss results with patients                             |
| `*`      | doctor               | set reminders for specific patient actions         | ensure follow-up on important tasks                                  |
| `*`      | doctor               | retrieve medical certificates of patients          | gather patient information quickly                                   |
| `*`      | doctor               | record the medications given to patients           | keep track of personal medication records of patients                |
| `*`      | administrative staff | search for patient files by name or ID             | quickly retrieve specific records                                    |
| `*`      | administrative staff | check prescription assigned by the doctor          | print out prescription for patient                                   |
| `*`      | doctor               | search up medicine to prescribe                    | give prescription to patient                                         |
| `*`      | doctor               | add notes to patient files                         | reference them during future visits                                  |
| `*`      | doctor               | change the time frame for receiving reminders      | receive reminders more frequently or less frequently                 |
| `*`      | doctor               | add guardian/parental contacts to patient          | contact patient indirectly                                           |
| `*`      | doctor               | update patient status                              | keep track of patient's condition                                    |
| `*`      | doctor               | copy treatments                                    | duplicate medication plans for similar patients                      |
| `*`      | doctor               | receive reminders on upcoming appointments         | prepare for them                                                     |
| `*`      | doctor               | retrieve specific treatment information            | treat them appropriately                                             |
| `*`      | doctor               | generate an automated document for a patient       | give it to them as reference                                         |

<br>

### Use cases

For all use cases below, unless specified otherwise, 
- The **System** is the `DocTrack` application
- The **Actor** is the `user`

<br>

#### Use case (UC01): Add a patient

**MSS**

1. User requests to add a patient.
2. DocTrack adds the patient.

    Use case ends.

**Extensions**

* 1a. User enters invalid parameters.

    * 1a1. DocTrack shows an error message.

      Use case resumes at step 1.
* 1b. User enters a patient that already exists.

    * 1b1. DocTrack shows an error message.

      Use case resumes at step 1.

<br>

#### Use case (UC02): Edit a patient

**MSS**
1. DocTrack <ins>shows a list of patients [(UC04)](#use-case-uc04-view-all-patients)</ins>.
2. User requests to edit a specific patient in the list with new details. 
3. DocTrack updates the patient with the new details.

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. DocTrack shows an error message.

      Use case resumes at step 2.

* 2b. The new patient details are invalid.

    * 2b1. DocTrack shows an error message.

      Use case resumes at step 2.

<br>

#### Use case (UC03): Delete a patient

**MSS**

1. DocTrack <ins>shows a list of patients [(UC04)](#use-case-uc04-view-all-patients)</ins>.
2. User requests to delete a specific patient in the list.
3. DocTrack deletes the patient.

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. DocTrack shows an error message.

      Use case resumes at step 2.

<br>

#### Use case (UC04): View all patients

**MSS**

1. User requests to list all patients.
2. DocTrack shows a list of patients.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

<br>

#### Use case (UC05): Find a patient by name

**MSS**

1. User requests to find a patient by name.
2. DocTrack shows the patient details.

    Use case ends.

**Extensions**

* 1a. User enters invalid parameters.

    * 1a1. DocTrack shows an error message.

      Use case resumes at step 1.

* 2a. The patient is not found.

  Use case ends.

<br>

#### Use case (UC06): Clear all patients 

**MSS**

1. User requests to clear all patients.
2. DocTrack clears all patients.

    Use case ends.

<br>

#### Use case (UC07): Add an appointment

**MSS**

1. User requests to add an appointment.
2. DocTrack adds the appointment.

    Use case ends.

**Extensions**

* 1a. User enters invalid parameters.

    * 1a1. DocTrack shows an error message.

      Use case resumes at step 1.

* 1b. User enters an appointment with the same date and time.

    * 1b1. DocTrack shows an error message.

      Use case resumes at step 1.

    
<br>

#### Use case (UC08): Edit an appointment

**MSS**

1. DocTrack <ins>shows a list of appointments [(UC10)](#use-case-uc10-view-all-appointments)</ins>.
2. User requests to edit a specific appointment in the list with new details.
3. DocTrack updates the appointment with the new details.

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. DocTrack shows an error message.

      Use case resumes at step 2.
* 2b. The new appointment details are invalid.

    * 2b1. DocTrack shows an error message.

      Use case resumes at step 2.

<br>

#### Use case (UC09): Delete an appointment

**MSS**

1. DocTrack <ins>shows a list of appointments [(UC10)](#use-case-uc10-view-all-appointments)</ins>.
2. User requests to delete a specific appointment in the list.
3. DocTrack deletes the appointment.

    Use case ends.

**Extensions**

* 2a. The given index is invalid.

    * 2a1. DocTrack shows an error message.

      Use case resumes at step 1.

<br>

#### Use case (UC10): View all appointments

**MSS**

1. User requests to list all appointments.
2. DocTrack shows a list of appointments.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

<br>

#### Use case (UC11): Find an appointment by patient name or date

**MSS**

1. User requests to find an appointment by patient name or date.
2. DocTrack shows the appointment details.

    Use case ends.

**Extensions**

* 1a. User enters invalid parameters.

    * 1a1. DocTrack shows an error message.

      Use case resumes at step 1.
* 2a. The appointment is not found.

  Use case ends.

<br>

#### Use case (UC12): Clear all appointments

**MSS**

1. User requests to clear all appointments.
2. DocTrack clears all appointments.

    Use case ends.

<br>


### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should work on any _reasonable system_ with good performance: common operation such as retrieving patient data must complete within 1 second, and complex operations must complete within 3 seconds.
3. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5. Should not require installation of additional packages.
6. Must not operate with dependency on any remote server.
7. No usage of DBMS.
8. Main product file must not exceed 100MB.
9. Documentation must not exceed 15MB.
10. Product should be designed for typing-preferred consumers, offering a CLI experience.
11. Product should be designed for a single user.
12. Product must function correctly on _standard resolutions_ and support scaling of 100%, 125%, 150%.
13. Data must be persistent, with all changes saved immediately to local storage.
14. Data files must be in a format that can be edited manually by advanced users.
15. Data file must remain usable and intact even with invalid input from the application.
16. Errors must trigger clear, user-friendly messages.
17. The software architecture must follow a modular design pattern, ensuring separation of concerns (UI, logic, storage) to facilitate future development and maintenance.
18. The codebase must be well-documented, with appropriate comments and adherence to coding standards.
19. A comprehensive technical guide should be provided for future developers, including instructions for maintenance and extension.
20. The product should be designed for users who prefer typing commands, offering a CLI or text-based interface.
21. The application is designed for a single user. Multi-user or shared usage on the same computer is not allowed, and data files must not be shared between users.

<br>

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS
* **Reasonable system**: A system with an OS matching the criteria above, with parts with a release date maximum 10 years from the current date
* **Standard resolutions**: 1920x1080 and 1080x720
* **Patient**: A person with details such as name, phone number, email, address, and status
* **Appointment**: A scheduled meeting between a patient and the user, with details such as date, time, sickness, and medicine
* **Appointment Type**: The type of appointment, such as consultation, follow-up, etc.
* **Status**: The status of the patient, such as recovered, hospitalised, etc.
* **User**: The person using the application
* **CLI**: Command Line Interface

#### GUI

* **Command box**: The box to type in enter commands
* **Result display/status bar**: The box to display the result of commands
* **Help window**: The window that displays the help information
* **Address list/list of persons**: List of address
* **Appointment list/list of appointments**: List of appointments

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

<br>

### Launch and shutdown

1. Initial launch
   1. Download the jar file and copy into an empty folder
   2. Open up a terminal, and run the following command: `java -jar DocTrack.jar`
      - Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences
   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by running the command: `java -jar DocTrack.jar`.<br>
       Expected: The most recent window size and location is retained.

### Adding a person

1. Adding a person
   1. Prerequisites: None
   2. Test case:`add person n/Elmo p/98765432 e/elmo@sesa.me a/Sesame Street st/recovering t/insurance`
   3. Expected: A new contact is added to the list.
   - The status bar shows the following:
     New person added:
     Name: Elmo
     Phone: 98765432
     Email: elmo@sesa.me
     Address: Sesame Street
     Tags: [[insurance]]
   - The list of persons should now have the new contact inside it.
    

### Deleting a person

1. Deleting a person while all persons are being shown
   1. Prerequisites: Multiple persons in the DocTrack. If they are not shown, do `list person`.
   2. Test case: `delete person 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. 
   Timestamp in the status bar is updated.
   3. Test case: `delete person -1`<br>
         Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.
   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
         Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

### Editing a person

1. Editing a person
    1. Prerequisites: At least one person is showing in the list.
    2. Test case:`edit person 1 p/90909090`
    3. Expected: The contact is edited.
    - The status bar shows the following: "Edited person.... Phone: 90909090...."
    - The list of persons should now have the edited contact inside it.

### Finding a person

1. Finding a person
    1. Prerequisites: At least one person in DocTrack. 
        - For a simple example, use the person added in the test case for adding a person.
    2. Test case:`find person n/Elmo`
    3. Expected: The list is updated to show all the people with names containing "elmo", non-case-sensitive.
    - The status bar shows the following: "Found `n` persons".
    - The `n` refers to the amount of people with names containing "elmo".
    - The list of persons should now only show people matching the criteria.

### Clearing person list

1. Clearing the person list 
    1. Prerequisites: At least one person in DocTrack, for proper testing.
   2. Test case: `clear person`
   3. Expected: The list is updated to show no persons.
   - The status bar shows the following: "Address book has been cleared!"
   - The list of appointments should also be empty.

### Adding an appointment

1. Adding a person
    1. Prerequisites: There must be a person with the PID 1.
    2. Test case:`add appt ty/Check up d/2024-10-16 12:30 i/1 s/Common Cold m/Paracetamol`
    3. Expected: A new appointment is added to the list.
      The status bar shows the following:
      New appointment added:
      Appointment Type: Check up
      Date and Time: October 16, 2024, 12:30 PM
      Sickness: Common Cold
      Medicine: Paracetamol
    - The list of appointments should now have the new appointment inside it.

### Deleting an appointment

1. Deleting a person while all persons are being shown
    1. Prerequisites: Multiple appointments in DocTrack. If they are not shown, do `list appt`.
    2. Test case: `delete appt 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted appointment shown in the status message.
       Timestamp in the status bar is updated.
    3. Test case: `delete appt -1 `<br>
       Expected: No appointment is deleted. Error details shown in the status message. Status bar remains the same.
    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: No appointment is deleted. Error details shown in the status message. Status bar remains the same.
   
### Editing an appointment

1. Editing a person
    1. Prerequisites: At least one person is showing in the list.
    2. Test case:`edit appt 1 ty/Health Checkup`
    3. Expected: The appointment is edited.
    - The status bar shows the following: "Edited Appointment: Health Checkup; ....."
    - The list of appointments should now have the edited appointment inside it.

### Finding an appointment

1. Finding an appointment
    1. Prerequisites: At least one appointment in the DocTrack book.
        - For a simple example, use the appointment added in the test case for adding a person.
    2. Test case:`find appt n/[NAME]`
    3. Expected: The list is updated to show all the appointments with names containing "[NAME]", non-case-sensitive.
    - The status bar shows the following: "Found `x` appointments".
   - The `x` refers to the amount of appointment for people with names containing "elmo".
    - The list of appointments should now only show appointments matching the criteria.

### Clearing person list

1. Clearing the appointment list
    1. Prerequisites: At least one person in DocTrack, for proper testing.
    2. Test case: `clear appt`
    3. Expected: The list is updated to show no appointment.
    - The status bar shows the following: "Appointment book has been cleared!"

### Saving data

1. Dealing with missing/corrupted data files
    1. Test case: missing data file
       1. Navigate to `../data` and delete the `.json` files.
       2. Run the application 
       3. Expected: The application should still run, with sample data shown in the list.
    2. Test case: corrupted data file
       1. Navigate to `../data` and edit the `.json` file, adding a random `/` at the end.
       2. Run the application
       3. Expected: The application should still run, with sample data shown in the list.

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Appendix: Planned enhancements**
Team size: 5

1. **When an invalid command is entered, suggest possible valid commands.**
   * **Flaw:** When a user types an unknown command, the result display box shows `Unknown Command`, which is not informative.
   * **Enhancement:** The result display box will suggest possible valid commands for the user to enter.
   * **Sample Input:** User enters: `sort`
   * **Expected Output:** The result display box shows `Unknown Command. Did you mean: list, find, clear, add, edit, delete, exit, or help?`

2. **Modify date parsing to check for leap years**
* **Flaw:** When a user enters February 29 for a non-leap year, the date automatically adjusts to February 
  28, which may be confusing.
* **Enhancement:** Modify the date parsing logic to check for leap years.
  * If the user enters February 29 for a non-leap year, display an error message indicating that the date is invalid. 
  * If the user enters February 29 for a leap year, the date is accepted.
* **Sample Input:** User enters `2023-02-29` for an appointment date.
* **Expected Output:** The result display box shows `Invalid date: 2023 is not a leap year, so February 29 is not valid.`

<br>

--------------------------------------------------------------------------------------------------------------------

<br>

## **Appendix: Effort**
#### Difficulty Level
Our project presented a higher level of complexity compared to AB3. Our project involved handling multiple entity types, mainly persons and appointments, whereas AB3 manages only a single entity. This increased the requirements for command processing as each entity type has additional attributes and methods.

#### Challenges Faced
- **Integration of person and appointment entities**: To ensure that each appointment correctly linked to each patient, we introduced a `Patient` attribute to the `Appointment` class. 
- **Command Implementation**: Implementing commands for both entities required careful design to ensure that each command worked correctly for both entities.
- **Unique IDs**: To ensure each patient and appointment had a 
  unique ID to allow for easy retrieval and updating of data, we added a `personId` and `appointmentId` attribute to the `Person` and `Appointment` classes respectively.

#### Effort Required
Our project involved substantial effort in several key areas:
- **Design and Refactoring**: Extending the AB3 framework to handle two separate entity types required refactoring and designing new classes. 
- **Command Implementation**: In creating patient- and appointment-specific commands, we implemented additional parser classes and commands.
- **Testing and Debugging**: To ensure robust functionality, we implemented comprehensive test cases. This was necessary to ensure 
  that each command and feature worked as expected for both entity types.

#### Achievements
Our project successfully expanded AB3’s functionality, enabling the application to manage patients and appointments. Despite the challenges, our final solution provides a user-friendly interface and coherent command structure. Additionally, the design allows for potential future expansion to include other entity types without extensive restructuring, making the system both flexible and scalable.

<br>
