package org.example.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Check
{
    private Map<Good, Integer> goodList;
    private String cashier;
    private LocalDateTime sellTime;



    public Check(Map<Good, Integer> goodList, String cashier ) {
        this.goodList = goodList;
        this.cashier = cashier;
        this.sellTime = LocalDateTime.now();
    }

    public void printCheck() {
        System.out.println("====================CHECK====================");
        float finishPrice = 0;
        for (Map.Entry<Good, Integer> good : goodList.entrySet())
        {
            System.out.println("Good : " + good.getKey().getName() +
                                ",   price : " + good.getKey().getPrice() +
                                ",   amount : " + good.getValue());
            finishPrice += good.getKey().getPrice() * good.getValue();
        }
        System.out.println("Total price : " + finishPrice);
        System.out.println("Cashier : " + cashier);
        System.out.println(sellTime);
    }

    public Map<Good, Integer> getGoodList() {
        return goodList;
    }

    public String getCashier() {
        return cashier;
    }

    public LocalDateTime getSellTime() {
        return sellTime;
    }
}

