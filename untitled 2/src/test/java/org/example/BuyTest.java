package org.example;

import ShopExceptions.CashNotEnoughException;
import ShopExceptions.GoodNotEnoughException;
import ShopExceptions.GoodNotFoundException;
import org.example.dao.CheckDAO;
import org.example.dao.ClientDAO;
import org.example.dao.ShopDAO;
import org.example.models.Check;
import org.example.models.Client;
import org.example.models.Good;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BuyTest
{
    Client client = new Client(25000F);
    ClientDAO clientDAO;
    ShopDAO shopDAO;

    CheckDAO checkDAO;

    @Before
    public void initClientDAO()
    {
        clientDAO = new ClientDAO(client);
        shopDAO = new ShopDAO();
        checkDAO = new CheckDAO();
    }

    @Test
    public void buy()
    {
        try {
            Good chicken = new Good("chicken", 150F);
            Good apple = new Good("apple", 50F);
            Good candy = new Good("candy", 190F);
            Good banana = new Good("banana", 48F);
            Good bread = new Good("bread", 27F);

            shopDAO.addGood(candy, 150);
            shopDAO.addGood(apple, 54);
            shopDAO.addGood(banana, 76);
            shopDAO.addGood(chicken, 89);
            shopDAO.addGood(bread, 50);

            shopDAO.printGoodList(shopDAO.findAllGoods());

            clientDAO.addGoodToBasket("apple", 3);
            clientDAO.addGoodToBasket("candy", 6);
            clientDAO.addGoodToBasket("banana", 7);

            System.out.println(clientDAO.getClient().getBasket());

            HashMap<Good, Integer> clientBasket = clientDAO.getClient().getBasket();

            Float sumOfBuy = clientDAO.calculateBasket(clientBasket);
            try {
                clientDAO.removeCash(sumOfBuy);
            } catch (CashNotEnoughException e) {
                System.out.println(e.getMessage());
            }
            shopDAO.addCash(sumOfBuy);

            for (Map.Entry<Good, Integer> goodInBasket : clientBasket.entrySet()) {
                shopDAO.removeGood(goodInBasket.getKey().getName(), goodInBasket.getValue());
            }
//        Check check = new Check(clientBasket, shopDAO.getCashier);

            shopDAO.printGoodList(shopDAO.findAllGoods());

            System.out.println(clientDAO.getClient().getBasket());

            System.out.println("Shop cash = " + shopDAO.getShop().getCash() + "\n" + "Client cash = " +
                    clientDAO.getClient().getCash());
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (GoodNotEnoughException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void BuyWitchCheck()
    {
        System.out.println("SHOP BEFORE BUY");
        shopDAO.printGoodList(shopDAO.findAllGoods());

        try {
            clientDAO.addGoodToBasket("apple", 3);
            clientDAO.addGoodToBasket("candy", 6);
            clientDAO.addGoodToBasket("banana", 7);
            System.out.println("CLIENT BASKET");
            System.out.println(clientDAO.getClient().getBasket());

            HashMap<Good, Integer> clientBasket = clientDAO.getClient().getBasket();

            Float sumOfBuy = clientDAO.calculateBasket(clientBasket);
            try {
                clientDAO.removeCash(sumOfBuy);
            } catch (CashNotEnoughException e) {
                System.out.println(e.getMessage());
            }
            shopDAO.addCash(sumOfBuy);

            for (Map.Entry<Good, Integer> goodInBasket : clientBasket.entrySet()) {
                shopDAO.removeGood(goodInBasket.getKey().getName(), goodInBasket.getValue());
            }

            String cashierName = shopDAO.getCashierName();
            Check check = checkDAO.createCheck(clientBasket, cashierName, clientDAO.getClient());
            check.printCheck();

            clientDAO.getClient().getBasket().clear();
            System.out.println("SHOP AFTER BUY");
            shopDAO.printGoodList(shopDAO.findAllGoods());
            System.out.println("CLIENT BASKET AFTER BUY");
            System.out.println(clientDAO.getClient().getBasket());

            System.out.println("Shop cash = " + shopDAO.getShop().getCash() + "\n" + "Client cash = " +
                    clientDAO.getClient().getCash());
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (GoodNotEnoughException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void soutTest()
    {

    }

}
