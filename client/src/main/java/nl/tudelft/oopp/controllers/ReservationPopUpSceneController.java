package nl.tudelft.oopp.controllers;

import static nl.tudelft.oopp.MainApp.main;
import static nl.tudelft.oopp.MainApp.switchScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.MainApp;
import nl.tudelft.oopp.communication.Room;

public class ReservationPopUpSceneController implements Initializable {
    @FXML private Text reservationUserName;
    @FXML private Text building;
    @FXML private Text room;
    @FXML private Text date;
    @FXML private Text time;

    @FXML private Text resConfirmed;
    @FXML private Button cancelButton;
    private int roomID;
    private String startTime;
    private String endTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reservationUserName.setText(MainApp.user.getUserName());
        room.setText(RoomReservationSceneController.getRoomName());
        building.setText(RoomReservationSceneController.getBuildingName());
        date.setText(RoomReservationSceneController.getDay());
        time.setText(RoomReservationSceneController.getStartTime() + "-" + RoomReservationSceneController.getEndTime());
        this.roomID = RoomReservationSceneController.getRoomID();
        this.startTime = RoomReservationSceneController.getStartTime();
        this.endTime = RoomReservationSceneController.getEndTime();
    }

    @FXML
    void backBtnHandler() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void makeNewReservation() {
        System.out.println("its working 1");
        /**
         * TODO: Make a method in ServerCommunication.java that makes a url to the DB to make a new reservation
         * TODO: Call that method here, and then if successful, inform the user it was (resConfirmed.setText("...."))
         */
    }
}
