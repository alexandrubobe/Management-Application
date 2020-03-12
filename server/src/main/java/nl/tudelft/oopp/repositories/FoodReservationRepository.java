package nl.tudelft.oopp.repositories;

import nl.tudelft.oopp.entities.FoodReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodReservationRepository extends JpaRepository<FoodReservation, Integer> {
}
