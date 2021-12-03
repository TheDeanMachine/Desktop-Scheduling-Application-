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
    private TableColumn<?, ?> appointment_ID;

    @FXML
    private TableColumn<?, ?> title;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private TableColumn<?, ?> start;

    @FXML
    private TableColumn<?, ?> end;

    @FXML
    private TableColumn<?, ?> customer_ID;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private ComboBox<?> monthComboBox;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private ListView<?> listViewResult;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private ListView<?> listViewResults2;

    @FXML
    void onActionBackToMain(ActionEvent event) {

    }

}
