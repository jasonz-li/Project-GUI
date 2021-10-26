package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SampleController {

    @FXML
    private TextField num1;

    @FXML
    private TextField num2;

    @FXML
    private Button addButton;

    @FXML
    private TextField sum;

    @FXML
    private ToggleGroup major;
    
    @FXML
    void addition(ActionEvent event) {
    	int result = Integer.parseInt(num1.getText()) + Integer.parseInt(num2.getText());
    	sum.setText(String.valueOf(result));
    }
}

