package com.example.theostore;

public class Tray {

    private String name;
    private String info;
    private String currentlyOut;
    private String capacity;
    private String timestamp;

    public Tray(String name, String currentlyOut) {
        this.name = name;
        this.currentlyOut = currentlyOut;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getCurrentlyOut() {
        return currentlyOut;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
