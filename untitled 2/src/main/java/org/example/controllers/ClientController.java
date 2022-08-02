package org.example.controllers;

import ShopExceptions.GoodNotFoundException;
import ShopExceptions.ShopEmployeeException;
import org.example.dao.CheckDAO;
import org.example.dao.ClientDAO;
import org.example.dao.ShopDAO;
import org.example.models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ClientController
{

    public static void main(String[] args)
    {
        ShopDAO shopDAO = new ShopDAO();
        Client client = new Client(17000F);
        ClientDAO clientDAO = new ClientDAO(client);
        CheckDAO checkDAO = new CheckDAO();

        Scanner sc = new Scanner(System.in);
        interfaceMenu();
        int clientChose = sc.nextInt();
        while (true)
        {
            switch (clientChose)
            {
                case 1:
                    showGoods();
                    break;
                case 2:
                    System.out.println("Enter the name of good and count");
                    String good = sc.nextLine();
                    int count = sc.nextInt();
                    try {
                        clientDAO.addGoodToBasket(good, count);
                    } catch (GoodNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter the name of good");
                    String goodName = sc.nextLine();
                    try {
                        shopDAO.findGood(goodName);
                    } catch (GoodNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    clientBuy(clientDAO);
                    break;
                case 5:
                    System.out.println("Enter the name of good and count");
                    String goods = sc.nextLine();
                    int amount = sc.nextInt();
                    try {
                        clientDAO.removeGoodFromBasket(goods, amount);
                    } catch (GoodNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Enter the id number of operation");
                    long id = sc.nextLong();
                    goodBack(id);
            }
        }
    }


    public static void showGoods()
    {
        ShopDAO shopDAO = new ShopDAO();
        HashMap<Good, Integer> store = shopDAO.findAllGoods();
        shopDAO.printGoodList(store);
    }


    public static void clientBuy(ClientDAO clientDAO)
    {
        ShopDAO shopDAO = new ShopDAO();
        Client denis = new Client(12500F);
        CheckDAO checkDAO = new CheckDAO();
            HashMap<Good, Integer> basket = clientDAO.getClient().getBasket();
            Float sumOfBuy = clientDAO.calculateBasket(basket);
            clientDAO.removeCash(sumOfBuy);
            shopDAO.addCash(sumOfBuy);

            for (Map.Entry<Good, Integer> goodInBasket : basket.entrySet()) {
                try{
                    shopDAO.removeGood(goodInBasket.getKey().getName(), goodInBasket.getValue());
                } catch (GoodNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        clientDAO.getClient().getBasket().clear();
        String cashierName = shopDAO.getCashierName();
        Check check = checkDAO.createCheck(basket, cashierName, clientDAO.getClient());
        check.printCheck();
    }

    public static void goodBack(long id)
    {
        // Проверка соответствует ли id чека одному из чеков в хранилище, и если да, то вернуть товары
        // по количеству в магазин и вернуть бабки клиенту
    }

    public static void interfaceMenu()
    {
        System.out.println("1. Show all goods;" +
                            "2. add good to basket;" +
                            "3. find good;" +
                            "4. buy goods from basket;" +
                            "5. remove goods from basket;"+
                            "6. back good in shop");
    }

}
