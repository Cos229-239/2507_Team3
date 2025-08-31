package com.ila.minigames.core.mergeFish;

import androidx.annotation.NonNull;
import com.ila.utilitys.random.random_number;
public class Fish extends Entity {

    private String species; // Example: "Goldfish", "Salmon"
    private String color;   // Example: "Orange", "Silver"

    // Default constructor (often required by persistence frameworks)
    public Fish() {
        super(); // Calls the default constructor of the Entity class
    }

    // Constructor to initialize Fish properties including inherited ones
    public Fish(int id, int x, int y, String species, String color) {
        super(id, x, y); // Calls the constructor of the Entity class
        this.species = species;
        this.color = color;
    }

    // Getter and Setter for species
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    // Getter and Setter for color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public void swim()
    {
        setX(getX() + random_number.getInstance().getRandom(2,true,10));
        setY(getY() + random_number.getInstance().getRandom(2,true,10));
    }
}
