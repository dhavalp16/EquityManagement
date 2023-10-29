package com.example.demo;

public class User {
    int amount;
    String name, category;

    public User(String name) {
        this.name = name;
    }

    public User( String name, int amount, String category) {
        this.amount = amount;
        this.name = name;
        this.category = category;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {this.category = category;}

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
