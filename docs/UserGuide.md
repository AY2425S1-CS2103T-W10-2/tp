---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all contacts.

   - `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   - `delete 3` : Deletes the 3rd contact shown in the current list.

   - `clear` : Deletes all contacts.

   - `exit` : Exits the app.

1. Refer to the [Command Overview](#command-overview) below for details of each command.

---

## Interacting with DocTrack

All interactions with the DocTrack platform are done through a series of **commands** entered by the user. These commands allow you to perform various **tasks**, such as adding, editing, listing, and managing persons/appointments. The commands are designed to be flexible and user-friendly, allowing **data fields** in any order, handling multiple entries for specific fields.

Here are some **key points** to keep in mind when using commands:

- You should supply all the fields (words in `UPPER_CASE`) that are needed for a command.<br>
  e.g. in `add person n/NAME`, `NAME` is a field which can be used as `add person n/John Doe`.

- The `INDEX` in command formats like `person edit` and `person delete` refers to the index number (shown beside the person or appointment) in the displayed list. The index **must be a positive integer** 1, 2, 3, …​

- Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…`​ after them can be used zero times or more times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

- You can add fields in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- If you add extraneous fields for commands that do not take in fields (such as `help`, `list person`, `list appt`, `exit` and `clear`), they will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines. This is because some space characters surrounding line-breaks may be omitted when copied over.

---

## Command Overview

The following sections describe the various commands available in the DocTrack platform:

- [Person Commands](#person-commands)
- [Appointment Commands](#appointment-commands)
- [General Commands](#general-commands)

---

### Person Commands

A **person** is a patient with several fields: a name, a phone number, an email, an address and optional tags. These patients can be uniquely identified by their **patient ID (PID)** for easy reference. DocTrack allows you interact with patient information through different commands, which can be seen below.

<br>

##### Adding a person: `add person`

Adds a person to the address book.

Format: `add person n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:

- `add person n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add person n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

<br>

##### Listing all persons : `list person`

Shows a list of all persons in the address book.

Format: `list person`

<br>

##### Editing a person : `edit person`

Edits an existing person in the address book.

Format: `edit person INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

- Edits the person at the specified `INDEX`.
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

- `edit person 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
- `edit person 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

<br>

##### Locating persons by name: `find person`

Finds persons whose names contain any of the given keywords.

Format: `find person KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`
- Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

- `find person John` returns `john` and `John Doe`
- `find person alex david` returns `Alex Yeoh`, `David Li`<br><br>
  ![result for 'find person alex david'](images/findAlexDavidResult.png)

<br>

##### Deleting a person : `delete person`

Deletes the specified person from the address book.

Format: `delete person INDEX`

- Deletes the person at the specified `INDEX`.

Examples:

- `list` followed by `delete person 2` deletes the 2nd person in the address book.
- `find Betsy` followed by `delete person 1` deletes the 1st person in the results of the `find` command.

<br>

---

### Appointment Commands

<br>

TODO: Add appointment commands here.

---

### General Commands

**General commands** can help you with miscellaneous tasks related to patient and appointment management.

<br>

##### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

<br>

##### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

<br>

##### Exiting the program : `exit`

Exits the program.

Format: `exit`

<br>

##### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br>

##### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: What is JSON?<br>
**A**: [JSON](https://www.json.org/json-en.html) files are used to store the data of patients and appointments.
Examples:

<img src="images/json/addressbook.png" alt="Address Book JSON" width="300" height="auto">
<img src="images/json/appointmentBook.png" alt="Appointment Book JSON" width="300" height="auto">

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary

| Action     | Format, Examples                                                                                                                                                      |
| ---------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Clear**  | `clear`                                                                                                                                                               |
| **Delete** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                   |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                           |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                            |
| **List**   | `list`                                                                                                                                                                |
| **Help**   | `help`                                                                                                                                                                |
