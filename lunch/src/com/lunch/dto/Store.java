package com.lunch.dto;

import java.util.HashMap;

public class Store {
    private int storeId;
    private String name;
    private String phone;
    private String category;
    private String region;
    private String time;
    private int tableNum;
    private int menuPrice;
    private double score;
    private HashMap<String, Integer> calendar;

    public Store(String name, String phone, String category, String region, int tableNum, String time, int menuPrice, double score) {
        this.name = name;
        this.phone = phone;
        this.category = category;
        this.region = region;
        this.tableNum = tableNum;
        this.time = time;
        this.menuPrice = menuPrice;
        this.score = score;
    }

    public Store(String name, String phone, String category, String region,int tableNum, String time, int menuPrice, double score, HashMap<String, Integer> calendar) {
        this.name = name;
        this.phone = phone;
        this.category = category;
        this.region = region;
        this.tableNum = tableNum;
        this.time = time;
        this.menuPrice = menuPrice;
        this.score = score;
        this.calendar = calendar;
    }
    

public int getStoreId() {
    return storeId;
}

public void setStoreId(int storeId) {
    this.storeId = storeId;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public int getTableNum() {
    return tableNum;
}

public void setTableNum(int tableNum) {
    this.tableNum = tableNum;
}

public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public String getRegion() {
    return region;
}

public void setRegion(String region) {
    this.region = region;
}

public String getTime() {
    return time;
}

public void setTime(String time) {
    this.time = time;
}

public int getMenuPrice() {
    return menuPrice;
}

public void setMenuPrice(int menuPrice) {
    this.menuPrice = menuPrice;
}

public double getScore() {
    return score;
}

public void setScore(float score) {
    this.score = score;
}

public HashMap<String, Integer> getCalendar() {
    return calendar;
}

public void setCalendar(HashMap<String, Integer> calendar) {
    this.calendar = calendar;
}

@Override
public String toString() {
    return "Store{" +
            "storeId=" + storeId +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", category='" + category + '\'' +
            ", region='" + region + '\'' +
            ", time='" + time + '\'' +
            ", menuPrice=" + menuPrice +
            ", score=" + score +
            ", calendar=" + calendar +
            '}';
}
}