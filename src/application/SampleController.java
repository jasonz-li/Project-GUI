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
    private Button tuitionDueButton;



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
            if (checkRosterDuplicate(resident, roster)) { ;
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

    @FXML
    void removeStudent(ActionEvent event){
        if (name.getText().isEmpty()){
            message.appendText("Name is empty.\n");
            return;
        }
        if (major.getSelectedToggle() == null) {
            message.appendText("Major not selected.\n");
            return;
        }
        String studentName = name.getText();
        RadioButton majorButton = (RadioButton) major.getSelectedToggle();
        String major = majorButton.getText();

        Student target = roster.findStudent(studentName, major);
        if (target != null){
            roster.remove(target);
            message.appendText("Student removed from the roster.\n");
        }
        else{
            message.appendText("Student is not in the roster.\n");
        }
    }

    @FXML
    void calculateTuitionSingle(ActionEvent event) {
        if (name.getText().isEmpty()){
            message.appendText("Name is empty.\n");
            return;
        }
        if (major.getSelectedToggle() == null) {
            message.appendText("Major not selected.\n");
            return;
        }
        String studentName = name.getText();
        RadioButton majorButton = (RadioButton) major.getSelectedToggle();
        String major = majorButton.getText();
        Student student = roster.findStudent(studentName, major);
        if (student != null){
            calculateSingle(roster, student);
            tuitionDueSingle.setText(String.valueOf(student.getTotalCost()));
        }
        else{
            message.appendText("Student is not in the roster.\n");
        }
    }

    @FXML
    void abroadStatusChange(ActionEvent event) {
        RadioButton majorButton = (RadioButton) major.getSelectedToggle();

        if (name.getText().isEmpty()){
            message.appendText("Name field cannot be empty.\n");
            return;
        }
        else if (majorButton == null){
            message.appendText("Major field cannot be empty.\n");
            return;
        }
        String studentName = name.getText();
        String major = majorButton.getText();
        boolean studyingAbroad = abroad.isSelected();
        if (roster.findStudent(studentName, major) == null) {
            message.appendText("Could not find the international student.\n");
        }
        else {
            Student student = roster.findStudent(studentName, major);
            changeAbroad(roster, student, studyingAbroad);
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



    /**
     * Initializes the tuition for a single student in the roster.
     * @param roster roster of students
     */
    private void calculateSingle(Roster roster, Student student){
        for (int i = 0; i < roster.getSize(); i++){
            if (roster.getRoster()[i].getDate() == null &&
            roster.getRoster()[i].getProfile().getName().equals(student.getProfile().getName()) &&
            roster.getRoster()[i].getProfile().getMajor().equals(student.getProfile().getMajor())){
                roster.getRoster()[i].tuitionDue();
            }
        }
    }

    /**
     * Initializes the tuition for all students in the roster.
     * @param roster roster of students
     */
    private void calculateAll(Roster roster, Student student){
        for (int i = 0; i < roster.getSize(); i++){
            if (roster.getRoster()[i].getDate() == null){
                roster.getRoster()[i].tuitionDue();
            }
        }
    }


    private void changeAbroad(Roster roster, Student student, boolean abroad){
        String major = student.getProfile().getMajor();
        String name = student.getProfile().getName();
        boolean moreThan12 = false;
        International international = International.class.cast(student);
        if (international.studyingAbroad == abroad){
            message.appendText("Nothing to change.\n");
            return;
        }
        else{
            international.studyingAbroad = abroad;
            if (international.getCreditHours() >= 12){
                student.setCreditHours(12);
                student.setTotalPayment(0.00);
                moreThan12 = true;
                if (student.getDate() == null){
                    student.tuitionDue();
                    message.appendText("Tuition updated.\n");
                }
                else{
                    student.getDate().setDateCleared(true);
                    if (moreThan12){
                        student.tuitionDue();
                        student.setTotalPayment(0.00);
                    }
                    else{
                        student.tuitionDue();
                    }
                    message.appendText("Tuition updated.");

                }
            }
        }
    }
}





