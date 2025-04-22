package com.example.shms.data.model;

public class Bed {
    private String bedNumber;
    private boolean isOccupied;
    private int id;

    public Bed(int id, String bedNumber, boolean isOccupied) {
        this.id = id;
        this.bedNumber = bedNumber;
        this.isOccupied = isOccupied;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}