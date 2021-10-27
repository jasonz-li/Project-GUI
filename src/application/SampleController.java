package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SampleController {

    Roster roster = new Roster();

    @FXML
    private TextField credits;

    @FXML
    private ToggleGroup localOrInternational;

    @FXML
    private ToggleGroup major;

    @FXML
    private TextField name;

    @FXML
    private TextArea message;

    @FXML
    private ToggleGroup resident;

    @FXML
    private RadioButton tristate;

    @FXML
    private ToggleGroup tristateTrue;

    @FXML
    private RadioButton ny;

    @FXML
    private RadioButton ct;

    @FXML
    private RadioButton international;

    @FXML
    private CheckBox abroad;

    @FXML
    private TextField tuitionDueSingle;

    @FXML
    private RadioButton res;

    @FXML
    private RadioButton nonres;


    @FXML
    void toggleTristate(ActionEvent event) {
        ny.setDisable(false);
        ct.setDisable(false);
        abroad.setDisable(true);
        abroad.setSelected(false);
    }

    @FXML
    void toggleInternational(ActionEvent event) {
        abroad.setDisable(false);
        ny.setDisable(true);
        ct.setDisable(true);
        ny.setSelected(false);
        ct.setSelected(false);
    }


    @FXML
    void deselect(ActionEvent event) {
        tristate.setSelected(false);
        tristate.setDisable(true);
        international.setSelected(false);
        international.setDisable(true);
        ny.setSelected(false);
        ct.setSelected(false);
        ny.setDisable(true);
        ct.setDisable(true);
        abroad.setSelected(false);
        res.setSelected(false);
        nonres.setSelected(false);
    }

    @FXML
    void enableOptions(ActionEvent event) {
        tristate.setDisable(false);
        international.setDisable(false);
    }

    @FXML
    void disableOptions(ActionEvent event) {
        tristate.setDisable(true);
        international.setDisable(true);
        tristate.setSelected(false);
        international.setSelected(false);
        ny.setSelected(false);
        ct.setSelected(false);
        ny.setDisable(true);
        ct.setDisable(true);
    }


    @FXML
    void addStudent(ActionEvent event) {
        if (name.getText().isEmpty()){
            message.appendText("Name is empty.\n");
            return;
        }
        if (major.getSelectedToggle() == null) {
            message.appendText("Major not selected.\n");
            return;
        }
        if (resident.getSelectedToggle() == null) {
            message.appendText("Residency status not selected.\n");
            return;
        }
        if (tristate.isSelected()) {
            if (tristateTrue.getSelectedToggle() == null) {
                message.appendText("Please indicate which location you are from in the tristate area.\n");
                return;
            }
        }
        String studentName = name.getText();
        RadioButton majorButton = (RadioButton) major.getSelectedToggle();
        String major = majorButton.getText();
        try {
            int creditHours = Integer.parseInt(credits.getText());
        } catch (NumberFormatException e) {
            message.appendText("Non-numeric input.\n");
            return;
        }
        int creditHours = Integer.parseInt(credits.getText());
        if (creditHours < 3 || creditHours > 24) {
            message.appendText("Invalid amount of credits. Credit amount should be between 3-24.\n");
            return;
        }

        if (res.isSelected()) {
            Resident resident = new Resident(studentName, major, creditHours);
            if (checkRosterDuplicate(resident, roster)) {
                message.appendText("Student already in roster.\n");
                return;
            }
            else {
                roster.add(resident);
                message.appendText("Student added.\n");
                return;
            }
        }
        else if (nonres.isSelected()) {
            if (tristate.isSelected()) {
                RadioButton tristateButton = (RadioButton) tristateTrue.getSelectedToggle();
                String state = tristateButton.getText();
                TriState tristate = new TriState(studentName, major, creditHours, state);
                if (checkRosterDuplicate(tristate, roster)){
                    message.appendText("Student already in the roster.\n");
                }
                else {
                    roster.add(tristate);
                    message.appendText("Student added.\n");
                }
            }
            else if (international.isSelected()) {
                boolean studyingAbroad = abroad.isSelected();
                International international = new International(studentName, major, creditHours, studyingAbroad);
                if (checkRosterDuplicate(international, roster)){
                    message.appendText("Student already in the roster.\n");
                    return;
                }
                else{
                    roster.add(international);
                    message.appendText("Student added.\n");
                    return;
                }
            }
            else {
                NonResident nonresident = new NonResident(studentName, major, Integer.parseInt(credits.getText()));
                if (checkRosterDuplicate(nonresident, roster)) {
                    message.appendText(("Student already in the roster.\n"));
                    return;
                }
                else {
                    roster.add(nonresident);
                    message.appendText("Student added.\n");
                    return;
                }
            }
        }
    }



    /**
     * Checks to see if student is already in the roster.
     * @param student student name
     * @param roster roster
     * @return true if student is already in the roster, otherwise returns false.
     */
    private boolean checkRosterDuplicate (Student student, Roster roster){
        for (int i = 0; i < roster.getSize(); i++) {
            if (roster.getRoster()[i].getProfile().getName().equals(student.getProfile().getName())
                    && roster.getRoster()[i].getProfile().getMajor().equals(student.getProfile().getMajor())) {
                return true;
            }
        }
        return false;
    }
}




