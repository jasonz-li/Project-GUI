package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SampleController {

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
    void toggleTristate(ActionEvent event) {
        message.appendText("Tristate selected.\n");
        ny.setDisable(false);
        ct.setDisable(false);
        abroad.setDisable(true);
    }

    @FXML
    void toggleInternational(ActionEvent event) {
        message.appendText("International selected.\n");
        abroad.setDisable(false);
        ny.setDisable(true);
        ct.setDisable(true);
    }

    @FXML
    void checkCredits(KeyEvent event) {
        try{
            int studentCredits = Integer.parseInt(credits.getText());
            if (studentCredits < 3  || studentCredits > 24) {
                message.appendText("Invalid amount of credits. Credit amount should be between 3-24.");
            }
        }
        catch (NumberFormatException e) {
            message.appendText("Non-numeric value entered.");
        }
    }

    @FXML
    void addStudent(ActionEvent event) {
        String studentName = name.getText();
        String studentMajor = major.getSelectedToggle().toString();
        int creditHours = Integer.parseInt(credits.getText());

    }



}
