package nl.tudelft.oopp.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class Food {
    @Id
    //food_id is representing the name of the food
    private String foodId;
    private int price;

    public Food() {
    }

    public Food(String foodId, int price) {
        this.foodId = foodId;
        this.price = price;
    }

    public String getFood_id() {
        return foodId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
