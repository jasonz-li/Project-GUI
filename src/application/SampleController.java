package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The "Controller" component of the MVC model. Holds all functionalities behind the buttons in the model
 *
 * @author Jason Li, John Leng
 */

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




    //john
    @FXML
    private Button AidButton;

    /**
     * Text-field for financial aid input
     */
    @FXML
    private TextField FinancialAid;

    /**
     * Pay tuition button
     */
    @FXML
    private Button PayButton;

    /**
     * Majors toggle group for tuition payment purposes
     */
    @FXML
    private ToggleGroup majorPayments;

    /**
     * Date picker for payments
     */
    @FXML
    private DatePicker paymentDate;

    /**
     * Text-field for payment amount
     */
    @FXML
    private TextField paymentAmount;

    /**
     * Button for print randomly
     */
    @FXML
    private Button printRandomButton;

    /**
     * Button for print by payment date
     */
    @FXML
    private Button printByDateButton;

    /**
     * Button for print by name
     */
    @FXML
    private Button printByNameButton;

    /**
     * Text-Field for payment and financial aid names
     */
    @FXML
    private TextField tName;

    @FXML
    void payTuition(ActionEvent event) {
        if (tName.getText().isEmpty()){
            message.appendText("Name is empty.\n");
            return;
        }
        if (majorPayments.getSelectedToggle() == null) {
            message.appendText("Major not selected.\n");
            return;
        }
        if(paymentAmount.getText().isEmpty()){
            message.appendText("Payment amount empty.\n");
            return;
        }
        if(paymentDate.getValue() == null){
            message.appendText("Payment date not selected.\n");
            return;
        }

        String paymentStudentName = tName.getText();
        RadioButton majorButton = (RadioButton) majorPayments.getSelectedToggle();
        String paymentMajor = majorButton.getText();
        String payAmount = paymentAmount.getText();

        Student targetStudent = roster.findStudent(paymentStudentName, paymentMajor);
        if(targetStudent == null){
            message.appendText("Student is not on the roster.\n");
            return;
        }
        if(targetStudent.getTotalCost() == 0){
            message.appendText("Calculate the Student's tuition.\n");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        String formattedValue = (paymentDate.getValue()).format(formatter);

        Date payDate = new Date(formattedValue);

        try {
            double payAmountDouble = Double.parseDouble(payAmount);
            if(payAmountDouble > targetStudent.getTotalCost()){
                message.appendText("Payment is greater than tuition.\n");
                return;
            }
            targetStudent.payTuition(payAmountDouble, payDate);
            message.appendText("Payment complete. " + targetStudent.getTotalCost() + " remains to be paid. " +
                    targetStudent.getTotalPayment() +" has been paid." + "\n");
        } catch (NumberFormatException e) {
            message.appendText("Non-numeric input for payment amount.\n");
            return;
        }

    }

    /**
     * Pays financial aid for Resident students, but only once. It checks for a valid student and that all fields are filled in.
     * Financial aid must be between $0-10000
     * @param event click event
     */
    @FXML
    void payFinancialAid(ActionEvent event) {
        if (tName.getText().isEmpty()){
            message.appendText("Name is empty.\n");
            return;
        }
        if (majorPayments.getSelectedToggle() == null) {
            message.appendText("Major not selected.\n");
            return;
        }
        if (FinancialAid.getText().isEmpty()){
            message.appendText("Financial aid is empty.\n");
            return;
        }
        String paymentStudentName = tName.getText();
        RadioButton majorButton = (RadioButton) majorPayments.getSelectedToggle();
        String paymentMajor = majorButton.getText();
        String financialAidString = FinancialAid.getText();

        Student targetStudent = roster.findStudent(paymentStudentName, paymentMajor);
        if(targetStudent == null){
            message.appendText("Student is not on the roster.\n");
            return;
        }
        if(targetStudent.getTotalCost() == 0){
            message.appendText("Calculate the Student's tuition.\n");
            return;
        }
        if (targetStudent instanceof Resident) {
            Resident newRes = (Resident) targetStudent;
            try {
                if(newRes.getFinancialAidPaid() == true){
                    message.appendText("Financial aid can only be used once\n");
                    return;
                }
                double financialAidDouble = Double.parseDouble(financialAidString);
                if(newRes.setFinancialAid(financialAidDouble) == false){
                    message.appendText("Financial aid must be a value from $0 to $10,000\n");
                    return;
                }
                newRes.setFinancialAidPaid(true);
                message.appendText("Financial aid complete. " + targetStudent.getTotalCost() + " remains to be paid. " +
                        targetStudent.getTotalPayment() +" has been paid." + "\n");
            } catch (NumberFormatException e) {
                message.appendText("Non-numeric input for payment amount.\n");
                return;
            }
        }else{
            message.appendText("The student has to be a resident to receive financial aid\n");
            return;
        }

    }

    /**
     * Prints the order of students in a random order
     * @param event click event
     */
    @FXML
    void printRandom(ActionEvent event) {
        String output = roster.printCurrentOrder();
        message.appendText("Print:" + "\n" + output + "\n");

    }

    /**
     * Prints the students by their payment date.
     * It uses their last payment date and only prints those who have printed.
     * @param event click event
     */
    @FXML
    void printByDate(ActionEvent event) {
        roster.printByPaymentDate();
        String output = roster.printPaymentDays();
        message.appendText("Print by Date:"  + "\n" + output + "\n");

    }

    /**
     * Prints the students by their name in alphabetical order.
     * @param event click event
     */
    @FXML
    void printByName(ActionEvent event) {
        roster.printByName();
        String output = roster.printCurrentOrder();
        message.appendText("Print by Name:"  + "\n" + output + "\n");

    }



    //end




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
        if(credits.getText().isEmpty()){
            message.appendText("Credit hours is empty.\n");
            return;
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

    /**
     * Calculates the tuition for all the students in the roster.
     * @param event click event
     */
    @FXML
    void calculateAll(ActionEvent event) {
        if (roster.getSize() == 0){
            message.appendText("Roster is empty.\n");
            return;
        }
        else{
            calculateAll(roster);
            message.appendText("Calculation completed.\n");
        }
    }


    /**
     * Calculates the tuition for a single student in the roster.
     * @param event click event
     */
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

    /**
     * Changes the study abroad status
     * @param event
     */
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
    private void calculateAll(Roster roster){
        for (int i = 0; i < roster.getSize(); i++){
            if (roster.getRoster()[i].getDate() == null){
                roster.getRoster()[i].tuitionDue();
            }
        }
    }

    /**
     * Changes the study abroad status of international student and recalculates tuition.
     * @param roster roster of students
     * @param student target international student
     * @param abroad study abroad status to be changed to
     */
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
                    message.appendText("Tuition updated.\n");

                }
            }
        }
    }
}
