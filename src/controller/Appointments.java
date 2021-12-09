package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class Appointments extends SuperController {

    /// Appointments TableView Fields fx:id ///
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

    /// Appointments Button Fields fx:id ///
    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button addButton;

    /// Appointments Radio Fields fx:id ///
    @FXML
    private ToggleGroup weekMonthAllToggleGroup;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton allRadioButton;

    /// Transfer Button Fields fx:id ///
    @FXML
    private Button viewCustomersButton;

    @FXML
    private Button viewReportsButton;


    /// Radio Button Methods ///
    @FXML
    void onActionWeekRadioButton(ActionEvent event) {

    }

    @FXML
    void onActionMonthRadioButton(ActionEvent event) {

    }

    @FXML
    void onActionAllRadioButton(ActionEvent event) {

    }

    /// Appointments Methods ///
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionOpenModifyForm(ActionEvent event) throws IOException {

        displayNewScreen(modifyButton, "/view/ModifyAppointment.fxml", "Modify Appointment" );
    }

    @FXML
    void onActionOpenAddForm(ActionEvent event) throws IOException {

        displayNewScreen(addButton, "/view/AddAppointment.fxml", "Add Appointment" );
    }

    /// Transfer Methods ///
    @FXML
    void onActionOpenReportsWindow(ActionEvent event) throws IOException {

        displayNewScreen(viewReportsButton, "/view/Reports.fxml", "" );
    }

    @FXML
    void onActionOpenCustomersWindow(ActionEvent event) throws IOException {

        displayNewScreen(viewCustomersButton, "/view/Customers.fxml", "" );
    }

}
