package com.lunch.dto;
public class VisitedRecord {
    private int custID;
    private int storeId;
    private int resId;
    private String storeName;
    private float score;
    private String category;
    private String storeRegion;
    private String region;

    public VisitedRecord(int custID, int storeId, int resId, String storeName, float score, String category, String storeRegion, String region) {
        this.custID = custID;
        this.storeId = storeId;
        this.resId = resId;
        this.storeName = storeName;
        this.score = score;
        this.category = category;
        this.storeRegion = storeRegion;
        this.region = region;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStoreRegion() {
        return storeRegion;
    }

    public void setStoreRegion(String storeRegion) {
        this.storeRegion = storeRegion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "VisitedRecord{" +
                "custID=" + custID +
                ", storeId=" + storeId +
                ", resId=" + resId +
                ", storeName='" + storeName + '\'' +
                ", score=" + score +
                ", category='" + category + '\'' +
                ", storeRegion='" + storeRegion + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
