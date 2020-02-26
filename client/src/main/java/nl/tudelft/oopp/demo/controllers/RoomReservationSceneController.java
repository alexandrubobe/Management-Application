package nl.tudelft.oopp.demo.controllers;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class RoomReservationSceneController implements Initializable {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> buildingComboBox;
    @FXML private ComboBox<String> timeslotComboBox;
    @FXML private ComboBox<String> roomTypeComboBox;

    @FXML private VBox roomList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: populate combo boxes and show available rooms

    }

    /**
     * Search button handler
     * @param actionEvent
     * @throws URISyntaxException
     */
    public void searchButtonHandler(ActionEvent actionEvent) throws URISyntaxException {
        String[] rooms = ServerCommunication.getRooms(datePicker.getValue(),
                buildingComboBox.getValue(), timeslotComboBox.getValue(),
                roomTypeComboBox.getValue());

        for (String str : rooms) {
            roomList.getChildren().add(new Text(str));
        }
    }

    public void accountButtonHandler(MouseEvent mouseEvent) {
    }
}
