package org.example.dao;

import org.example.models.Check;
import org.example.models.Client;
import org.example.models.EmployeeType;
import org.example.models.Good;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckDAO
{
    Check check;


    public CheckDAO() {
    }

    public Check createCheck(HashMap<Good, Integer> basket, String cashier, Client client)
    {
        check = new Check(basket, cashier);
        String id = String.valueOf(client.getId());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("check.txt", true));
            BufferedWriter clientWriter = new BufferedWriter(new FileWriter("E:\\Java\\ShopApp\\untitled 2\\ClientChecks\\" + id + ".txt")))
        {
            Float totalPrice = 0F;
            for (Map.Entry<Good, Integer> good : basket.entrySet())
            {
                writer.write(good.getKey().getName() + "," + good.getKey().getPrice() + "," +
                            good.getValue() + ";");
                clientWriter.write(good.getKey().getName() + "," + good.getKey().getPrice() + "," +
                        good.getValue() + ";");
                totalPrice += good.getKey().getPrice() * good.getValue();
            }
            writer.write("!" + totalPrice + "!" + (check.getSellTime()) + "!" + (cashier) + "!" + id + "!");
            clientWriter.write("!" + totalPrice + "!" + (check.getSellTime()) + "!" + (cashier) + "!" + id + "!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return check;
    }
}
