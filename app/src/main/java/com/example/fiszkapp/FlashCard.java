package com.example.fiszkapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FlashCard {
    int id;
    String front;
    String back;
    int priority;

    public FlashCard(int id, String front, String back, int priority) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.priority = priority;
    }

    //domyślna wartość proirytetu to 0
    //więc im mniejszy priorytet
    //tym użytkownik mniej zna fiszkę
    //np. priorytet 0 - użytkownik nie za bardzo zna fiszkę
    //priorytet 100 - użytkownik już dobrze zna fiszkę

    //przy dobrej odpowiedzi
    //priorytet p = (p+1)*2
    //przy złej odpowiedzi
    //p = floor(p/2);

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public FlashCard rightAnswer()
    {
        this.priority = (this.priority+1) * 2;
        return this;
    }

    public FlashCard wrongAnswer()
    {
        this.priority /=2;
        return this;
    }

    public static List<FlashCard> shuffleList(List<FlashCard> list)
    {
        Collections.shuffle(list);
        return list;
    }
}
