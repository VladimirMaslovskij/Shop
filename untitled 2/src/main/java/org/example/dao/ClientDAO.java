package org.example.dao;

import ShopExceptions.GoodNotFoundException;
import org.example.models.Check;
import org.example.models.Client;
import org.example.models.Good;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
addGoodToBasket
removeGoodFromBasket
buy
addCheck
 */

// add, save,
public class ClientDAO
{

    private Client client;

    public Client getClient() {
        return client;
    }

    public ClientDAO(Client client)
    {
        this.client = client;
    }

    public void addGoodToBasket(String name, Integer amount) throws GoodNotFoundException {
        ShopDAO shopDAO = new ShopDAO();
        HashMap<Good, Integer> store = shopDAO.findAllGoods();
        if (store.size() > 0)
        {
            boolean isInclude = false;
            for (Map.Entry<Good, Integer> goodInfo : store.entrySet())
            {
                if (name.equals(goodInfo.getKey().getName()))
                {
                    Good good = goodInfo.getKey();
                    if (goodInfo.getValue() >= amount)
                    {
                        if(client.getBasket().containsKey(good))
                            client.getBasket().put(good, client.getBasket().get(good) + amount);
                        else
                            client.getBasket().put(good, amount);
                    } else
                    {
                        if(client.getBasket().containsKey(good))
                            client.getBasket().put(good, client.getBasket().get(good) + goodInfo.getValue());
                        else
                            client.getBasket().put(good, goodInfo.getValue());
                    }
                    isInclude = true;
                }
            }
            if (!isInclude)
            {
                throw new GoodNotFoundException("This good not found in shop");
            }
        } else
            throw new GoodNotFoundException("Sorry. The shop is empty, come back tomorrow");
    }
    public void removeGoodFromBasket(String name, Integer amount) throws GoodNotFoundException {
        HashMap<Good, Integer> basket = client.getBasket();
        if (basket.size() > 0)
        {
            boolean isInclude = false;
            for (Map.Entry<Good, Integer> goodInBasket : basket.entrySet()) {
                if (goodInBasket.getKey().getName().equals(name)) {
                    isInclude = true;
                    if (amount < goodInBasket.getValue())
                        client.getBasket().put(goodInBasket.getKey(), goodInBasket.getValue() - amount);
                    else
                        client.getBasket().remove(goodInBasket.getKey());
                }
            }
            if (!isInclude)
                throw new GoodNotFoundException("Basket not exist this good");
        } else
            throw new GoodNotFoundException("Basket is empty");
    }

    public Float calculateBasket(HashMap<Good, Integer> basket)
    {
        Float total = 0F;

        for (Map.Entry<Good, Integer> good : basket.entrySet())
            total += good.getKey().getPrice() * good.getValue();

        if (total > client.getCash()) {
            return null;
        } else
        return total;
    }

    public void removeCash(Float sum)
    {
        client.setCash(client.getCash() - sum);
    }

    public void addCheck(Check check)
    {
        client.getOrdersHistory().add(check);
    }

}
//    {
//        File file = new File("text.txt");
//        try {
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
//            bufferedWriter.newLine();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//
//
//
//
//
//
//
//
//
//    }

