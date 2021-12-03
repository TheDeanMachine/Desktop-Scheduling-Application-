package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

public class Appointments {

    @FXML
    private TableView<?> appointmentsTableView;

    @FXML
    private TableColumn<?, ?> appointmentIdColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> locationColumn;

    @FXML
    private TableColumn<?, ?> contactColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> startColumn;

    @FXML
    private TableColumn<?, ?> endColumn;

    @FXML
    private TableColumn<?, ?> customerIdColumn;

    @FXML
    private TableColumn<?, ?> userIdColumn;

    @FXML
    private Button delteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button addButton;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private ToggleGroup weekMonthAllToggelGroup;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private Button viewCustomersButton;

    @FXML
    private Button viewReportsButton;

    @FXML
    void onActionAllRadioButton(ActionEvent event) {

    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionMonthRadioButton(ActionEvent event) {

    }

    @FXML
    void onActionOpenAddForm(ActionEvent event) {

    }

    @FXML
    void onActionOpenCustomersWindow(ActionEvent event) {

    }

    @FXML
    void onActionOpenModifyForm(ActionEvent event) {

    }

    @FXML
    void onActionOpenReportsWindow(ActionEvent event) {

    }

    @FXML
    void onActionWeekRadioButton(ActionEvent event) {

    }

}
