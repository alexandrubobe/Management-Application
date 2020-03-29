package nl.tudelft.oopp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.entities.Building;
import nl.tudelft.oopp.entities.Room;
import nl.tudelft.oopp.entities.RoomReservation;
import nl.tudelft.oopp.entities.TimeSlot;
import nl.tudelft.oopp.entities.Type;
import nl.tudelft.oopp.repositories.BuildingRepository;
import nl.tudelft.oopp.repositories.RoomRepository;
import nl.tudelft.oopp.repositories.RoomReservationRepository;
import nl.tudelft.oopp.repositories.TimeSlotRepository;
import nl.tudelft.oopp.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableJpaRepositories("nl.tudelft.oopp.repositories")


@Controller
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @GetMapping("/getListOfRooms")
    @ResponseBody
    public List<Room> getListOfRooms() {
        return roomRepository.findAll();
    }

    /*
    Method adds a new room to the table. It takes in the name of the building and the type id (to setup FK)
     */

    /**
     * add a room to the database.
     * @param capacity the capacity of the room.
     * @param roomName the name of the room.
     * @param buildingName the name of the building in which this room is placed.
     * @param typeId the type of the room.
     * @author Scott.
     */
    @PostMapping("addRoomToDB/{capacity}/{roomName}/{buildingName}/{typeId}")
    @ResponseBody
    public void addRoom(@PathVariable (value = "capacity") int capacity,
                            @PathVariable (value = "roomName") String roomName,
                            @PathVariable (value = "buildingName") String buildingName,
                            @PathVariable (value = "typeId") int typeId) {
        Room room = new Room(capacity, roomName);

        Optional<Building> b = buildingRepository.findById(buildingName);
        Building building = b.get();
        room.setBuilding(building);

        Optional<Type> t = typeRepository.findById(typeId);
        Type type = t.get();
        room.setType(type);

        roomRepository.save(room);
        System.out.println("room added successfully");
    }

    /**
     * delete a room from the database.
     * @param roomId the id of the room to delete
     * @author Scott.
     */
    @DeleteMapping("deleteRoom/{roomId}")
    @ResponseBody
    public void deleteRoom(@PathVariable(value = "roomId") int roomId) {
        try {
            Optional<Room> r = roomRepository.findById(roomId);
            Room room = r.get();
            List<TimeSlot> timeSlots = room.getTimeslots();
            for (TimeSlot timeslot : timeSlots) {
                timeslot.getRoom().getTimeslots().remove(timeslot);

                List<RoomReservation> roomReservations = timeslot.getRoomReservations();
                for (RoomReservation roomReservation : roomReservations) {
                    roomReservation.getUser_fk().getRoomReservations().remove(roomReservation);
                    roomReservation.getTimeslot_fk().getRoomReservations().remove(roomReservation);
                    roomReservationRepository.delete(roomReservation);
                }
                timeSlotRepository.delete(timeslot);
            }
            room.getType().getListOfRooms().remove(room);
            room.getBuilding().getRooms().remove(room);
            roomRepository.delete(room);

        } catch (Exception e) {
            throw new IllegalArgumentException("this input does not exist");
        }
    }
}
