package controller;

import DAO.AppointmentsDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import utilities.TimeHelper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is used to display the appointments' information.
 */
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

    /**
     * Displays the appointments for this week.
     * Appointments are displayed based on the current week of Sunday - Saturday.
     */
    @FXML
    void onActionWeekRadioButton(ActionEvent event) {
        appointmentsTableView.setItems(new AppointmentsDAO().getThisWeeksAppointments());
    }

    /**
     * Displays appointments for this month.
     * Appointments are displayed based the current full month.
     */
    @FXML
    void onActionMonthRadioButton(ActionEvent event) {
        appointmentsTableView.setItems(new AppointmentsDAO().getThisMonthsAppointments());
    }

    /**
     * Displays all appointments.
     */
    @FXML
    void onActionAllRadioButton(ActionEvent event) {
        appointmentsTableView.setItems(new AppointmentsDAO().read());
    }

    /// Appointments Methods ///

    /**
     * Deletes the appointment selected by the user.
     * When the user selects an appointment, it is deleted from the database based on the unique appointment id.
     * The user is presented with confirmation prompt, and the tableview is refreshed to reflect the changes.
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        Alert alertConf = new Alert(Alert.AlertType.CONFIRMATION);

        if(appointmentsTableView.getSelectionModel().isEmpty()){
            alertInfo.setHeaderText("Please select an appointment to delete");
            alertInfo.showAndWait();
            return;
        } else {
            alertConf.setTitle("Confirmation Dialog");
            alertConf.setHeaderText("Are you sure you want to delete this appointment?");
            alertConf.setContentText("Press ok to delete, and cancel to go back");

            Optional<ButtonType> result = alertConf.showAndWait();
            if (result.get() == ButtonType.OK){
                // get users selection
                Appointments selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
                int selection = selectedAppointment.getAppointmentId();
                AppointmentsDAO dao = new AppointmentsDAO();
                dao.delete(selection);

                // Appointment_ID and type of appointment canceled.
                int id = selectedAppointment.getAppointmentId();
                String type = selectedAppointment.getType();
                alertInfo.setHeaderText("Appointment ID #" + id + " \n" + type + " has been deleted");
                alertInfo.showAndWait();

                // refresh the tableview
                appointmentsTableView.setItems(dao.read());
            } else {
                return;
            }
        }
    }

    /**
     * This method gets the user selection from the tableview and then passes that selection to the modify form.
     * @throws IOException catches input output errors.
     */
    @FXML
    void onActionOpenModifyForm(ActionEvent event) throws IOException {
        // if the user selects the modify button, without selecting an item display an alert box
        if(appointmentsTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Please select an appointment to modify");
            alert.showAndWait();
            return;
        } else {
            // get the appointment
            Appointments selectedItem = appointmentsTableView.getSelectionModel().getSelectedItem();
            // pass the appointment to modify form
            ModifyAppointment.holdAppData(selectedItem);
        }
        displayNewScreen(modifyButton, "/view/ModifyAppointment.fxml");
    }

    /**
     * This method opends the add form.
     * @throws IOException catches input output errors.
     */
    @FXML
    void onActionOpenAddForm(ActionEvent event) throws IOException {
        displayNewScreen(addButton, "/view/AddAppointment.fxml" );
    }

    /// Transfer Screen Methods ///

    /**
     * This method opens report window.
     * @throws IOException catches input output errors.
     */
    @FXML
    void onActionOpenReportsWindow(ActionEvent event) throws IOException {
        displayNewScreen(viewReportsButton, "/view/Reports.fxml");
    }

    /**
     * This method opens Customer window.
     * @throws IOException catches input output errors.
     */
    @FXML
    void onActionOpenCustomersWindow(ActionEvent event) throws IOException {
        displayNewScreen(viewCustomersButton, "/view/Customers.fxml" );
    }

    /**
     * Initialize Method.
     * This method is from the interface Initializable, and is overridden here.
     * The method is loaded(initialized) when this controller gets called by the display method in LogInScreen.
     * It contains instructions to set the TableView with the data it will be working with.
     */
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
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTimeAsString"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTimeAsString"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }
}
