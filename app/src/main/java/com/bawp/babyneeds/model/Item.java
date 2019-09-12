package com.bawp.babyneeds.model;

public class Item {
    private int id;
    private String itemDay;
    private String itemBreakFast;
    private String itemLunch;
    private String itemDinner;
    private String dateItemAdded;

    public Item() {
    }

    public Item(String itemDay, String itemBreakFast, String itemLunch, String itemDinner, String dateItemAdded) {
        this.itemDay = itemDay;
        this.itemBreakFast = itemBreakFast;
        this.itemLunch = itemLunch;
        this.itemDinner = itemDinner;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(int id, String itemDay, String itemBreakFast, String itemLunch, String itemDinner, String dateItemAdded) {
        this.id = id;
        this.itemDay = itemDay;
        this.itemBreakFast = itemBreakFast;
        this.itemLunch = itemLunch;
        this.itemDinner = itemDinner;
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemDay() {
        return itemDay;
    }

    public void setItemDay(String itemDay) {
        this.itemDay = itemDay;
    }

    public String getItemBreakFast() {
        return itemBreakFast;
    }

    public void setItemBreakFast(String itemBreakFast) {
        this.itemBreakFast = itemBreakFast;
    }

    public String getItemLunch() {
        return itemLunch;
    }

    public void setItemLunch(String itemLunch) {
        this.itemLunch = itemLunch;
    }

    public String getItemDinner() {
        return itemDinner;
    }

    public void setItemDinner(String itemDinner) {
        this.itemDinner = itemDinner;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
