package org.example.controllers;

import ShopExceptions.CashNotEnoughException;
import ShopExceptions.GoodNotEnoughException;
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
        boolean isTrue = true;
        while (isTrue)
        {
            int clientChose = sc.nextInt();
            switch (clientChose)
            {
                case 1:
                    showGoods();
                    interfaceMenu();
                    break;
                case 2:
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter the name of good and count");
                    String good = scanner.nextLine();
                    int count = scanner.nextInt();
                    try {
                        clientDAO.addGoodToBasket(good, count);
                    } catch (GoodNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (GoodNotEnoughException e) {
                        System.out.println(e.getMessage());
                    }
                    interfaceMenu();
                    break;
                case 3:
                    System.out.println("Enter the name of good");
                    String goodName = sc.nextLine();
                    try {
                        shopDAO.findGood(goodName);
                    } catch (GoodNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    interfaceMenu();
                    break;
                case 4:
                    clientBuy(client);
                    interfaceMenu();
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
                    interfaceMenu();
                    break;
                case 10:
                    isTrue = false;
                    break;
            }
        }
    }


    public static void showGoods()
    {
        ShopDAO shopDAO = new ShopDAO();
        HashMap<Good, Integer> store = shopDAO.findAllGoods();
        shopDAO.printGoodList(store);
    }


    public static void clientBuy(Client client)
    {
        ShopDAO shopDAO = new ShopDAO();
        ClientDAO clientDAO = new ClientDAO(client);
        CheckDAO checkDAO = new CheckDAO();
            HashMap<Good, Integer> basket = clientDAO.getClient().getBasket();
            Float sumOfBuy = clientDAO.calculateBasket(basket);
        try {
            clientDAO.removeCash(sumOfBuy);
        } catch (CashNotEnoughException e) {
            System.out.println(e.getMessage());
        }
        shopDAO.addCash(sumOfBuy);

            for (Map.Entry<Good, Integer> goodInBasket : basket.entrySet()) {
                try{
                    shopDAO.removeGood(goodInBasket.getKey().getName(), goodInBasket.getValue());
                } catch (GoodNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        String cashierName = shopDAO.getCashierName();
        Check check = checkDAO.createCheck(basket, cashierName, clientDAO.getClient());
        check.printCheck();
        clientDAO.getClient().getBasket().clear();
    }


    public static void interfaceMenu()
    {
        System.out.println("1. Show all goods;" + "\n" +
                            "2. add good to basket;" + "\n" +
                            "3. find good;" + "\n" +
                            "4. buy goods from basket;" + "\n" +
                            "5. remove goods from basket;"+ "\n" +
                            "6. back good in shop" + "\n" +
                            "10. exit" + "\n");
    }

}
