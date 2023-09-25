package com.lunch.dto;
public class Reservation {
    private int resId;
    private int custId;
    private int storeId;
    private String date;
    private int people; //cust_num

    public Reservation(int custId, int storeId, String date, int people) {
        this.custId = custId;
        this.storeId = storeId;
        this.date = date;
        this.people = people;
    }
    
    public Reservation(int resId, int custId, int storeId, String date, int people) {
		super();
		this.resId = resId;
		this.custId = custId;
		this.storeId = storeId;
		this.date = date;
		this.people = people;
	}


	public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "resId=" + resId +
                ", custId=" + custId +
                ", storeId=" + storeId +
                ", date='" + date + '\'' +
                ", people=" + people +
                '}';
    }
}
