package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Reports {

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> reportsTableView;

    @FXML
    private TableColumn<?, ?> appointmentIdColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> startColumn;

    @FXML
    private TableColumn<?, ?> endColumn;

    @FXML
    private TableColumn<?, ?> customerIdColumn;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private ComboBox<?> monthComboBox;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private ListView<?> resultListView1;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private ComboBox<?> divisionComboBox;

    @FXML
    private ListView<?> resultListView2;

    @FXML
    void onActionBackToMain(ActionEvent event) {

    }

}
