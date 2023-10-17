package com.example.demo;

public class User {
    int num, amount;
    String name, type;

    public User(int num, String name, int amount, String type) {
        this.num = num;
        this.amount = amount;
        this.name = name;
        this.type = type;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
