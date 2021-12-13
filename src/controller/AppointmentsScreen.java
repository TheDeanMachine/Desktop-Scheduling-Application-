package controller;

import DAO.AppointmentsDAO;
import DAO.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentsScreen extends SuperController implements Initializable {

    /// Appointments TableView Fields fx:id ///
    @FXML
    private TableView<Appointments> appointmentsTableView;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIdColumn;

    @FXML
    private TableColumn<Appointments, String> titleColumn;

    @FXML
    private TableColumn<Appointments, String> descriptionColumn;

    @FXML
    private TableColumn<Appointments, String> locationColumn;

    @FXML
    private TableColumn<Appointments, String> contactColumn;

    @FXML
    private TableColumn<Appointments, String> typeColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endColumn;

    @FXML
    private TableColumn<Appointments, Integer> customerIdColumn;

    @FXML
    private TableColumn<Appointments, Integer> userIdColumn;

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

    /// Button Fields fx:id ///
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

        displayNewScreen(modifyButton, "/view/ModifyAppointment.fxml");
    }

    @FXML
    void onActionOpenAddForm(ActionEvent event) throws IOException {

        displayNewScreen(addButton, "/view/AddAppointment.fxml" );
    }

    /// Transfer Screen Methods ///
    @FXML
    void onActionOpenReportsWindow(ActionEvent event) throws IOException {

        displayNewScreen(viewReportsButton, "/view/Reports.fxml");
    }

    @FXML
    void onActionOpenCustomersWindow(ActionEvent event) throws IOException {

        displayNewScreen(viewCustomersButton, "/view/Customers.fxml" );
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the appointments' tableview with the data it will be working with
        appointmentsTableView.setItems(new AppointmentsDAO().read());

        // set the columns with the data
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }
}
