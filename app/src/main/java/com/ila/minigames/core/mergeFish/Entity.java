package com.ila.minigames.core.mergeFish;

import androidx.annotation.NonNull;

public class Entity {
    private int id;
    private int x;
    private int y;

    // Default constructor (often required by persistence frameworks)
    public Entity() {
    }

    public Entity(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for x
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    // Getter and Setter for y
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}