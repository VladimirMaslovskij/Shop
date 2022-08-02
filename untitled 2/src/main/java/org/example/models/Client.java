package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Client:
1. CardNumber : String
2. BuyHistory : ArrayList
3. Basket : ArrayList
 */
public class Client
{

    HashMap<Good, Integer> basket = new HashMap<>();

    private long id = (long)(Math.random() * 999999999);
    Float cash;
    List<Check> ordersHistory = new ArrayList<>();

    public Float getCash() {
        return cash;
    }

    public long getId() {
        return id;
    }

    public HashMap<Good, Integer> getBasket() {
        return basket;
    }


    public List<Check> getOrdersHistory() {
        return ordersHistory;
    }

    public Client(Float cash) {
        this.cash = cash;
    }

    public void setCash(Float cash) {
        this.cash = cash;
    }
}
