package com.example.fiszkapp;

public class FlashCard {
    int id;
    String front;
    String back;

    public FlashCard(int id, String front, String back) {
        this.id = id;
        this.front = front;
        this.back = back;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

}
