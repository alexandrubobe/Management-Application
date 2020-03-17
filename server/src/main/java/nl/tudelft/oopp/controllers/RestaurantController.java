package nl.tudelft.oopp.controllers;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.entities.Building;
import nl.tudelft.oopp.entities.Menu;
import nl.tudelft.oopp.entities.Restaurant;
import nl.tudelft.oopp.repositories.BuildingRepository;
import nl.tudelft.oopp.repositories.MenuRepository;
import nl.tudelft.oopp.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableJpaRepositories("nl.tudelft.oopp.repositories")

@Controller
public class RestaurantController {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    /**
     * finds all restaurants in a building searched by name.
     * @param buildingName the name of the buildign to search
     * @return a list of restaurants in the given building
     * @Author Alex
     */
    @GetMapping("/ListRestaurants/{buildingName}")
    @ResponseBody
    public List<Restaurant> findRestaurantsInBuilding(@PathVariable String buildingName) {
        List<Restaurant> restaurantInBuilding;

        List<Building> b = buildingRepository.findByBuildingName(buildingName);
        Building building = b.get(0);

        restaurantInBuilding = restaurantRepository.getRestaurantsByBuildingName(building.getBuilding_Name());
        return restaurantInBuilding;
    }

    /**
     * finds all restaurants.
     * @return a list of restaurant
     * @Author Hidde Agterberg
     */
    @GetMapping("/getAllRestaurants")
    @ResponseBody
    public List<Restaurant> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.getAllRestaurants();
        return restaurants;
    }

    /**
     * This method adds a new restaurant to a specified building.
     * @param buildingName name of the building
     * @param menuId id of the building
     * @param restaurant the restaurant
     */
    @PostMapping("/AddRestaurant/{buildingName}/{menuId}")
    @ResponseBody
    public void addRestaurant(@PathVariable (value = "buildingName") String buildingName,
                              @PathVariable (value = "menuId") Integer menuId,
                              @RequestBody Restaurant restaurant) {
        Restaurant newRestaurant = restaurant;

        Optional<Building> b = buildingRepository.findById(buildingName);
        Building building = b.get();

        Optional<Menu> m = menuRepository.findMenuById(menuId);
        Menu menuObj = m.get();

        newRestaurant.setBuilding(building);
        newRestaurant.setMenu(menuObj);
        building.addRestaurant(newRestaurant);

        restaurantRepository.save(newRestaurant);
    }

}
