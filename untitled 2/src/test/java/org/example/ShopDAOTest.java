package org.example;

import static org.junit.Assert.assertTrue;

import ShopExceptions.GoodNotFoundException;
import ShopExceptions.ShopEmployeeException;
import org.example.dao.CheckDAO;
import org.example.dao.ShopDAO;
import org.example.models.Check;
import org.example.models.EmployeeType;
import org.example.models.Good;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class ShopDAOTest
{
    ShopDAO shopDAO;
    @Before
    public void initShopDAO()
    {
        shopDAO = new ShopDAO();
    }

    @Test
    public void addGoodTest()
    {
        System.out.println("!!!!!!!!!!!!!!ADD GOOD TEST!!!!!!!!!!!!!!!");
        Good chicken = new Good("chicken", 150F);
        Good apple = new Good("apple", 50F);
        Good candy = new Good("candy", 190F);
        Good banana = new Good("banana", 48F );
        Good bread = new Good("bread", 27F);

        shopDAO.addGood(candy, 150);
        shopDAO.addGood(apple, 54);
        shopDAO.addGood(banana, 76);
        shopDAO.addGood(chicken, 89);
        shopDAO.addGood(bread, 50);

        shopDAO.printGoodList(shopDAO.findAllGoods());
        int expectedSize = 5;
        Assert.assertEquals(expectedSize, shopDAO.findAllGoods().size());
    }


    @Test
    public void removeGoodTest()
    {
        System.out.println("!!!!!!!!!!!!!!REMOVE GOOD TEST!!!!!!!!!!!!!!!");
        Good chicken = new Good("chicken", 150F);
        Good apple = new Good("apple", 50F);
        Good candy = new Good("candy", 190F);
        Good banana = new Good("banana", 48F );
        Good bread = new Good("bread", 27F);

        shopDAO.addGood(candy, 150);
        shopDAO.addGood(apple, 54);
        shopDAO.addGood(banana, 76);
        shopDAO.addGood(chicken, 89);
        shopDAO.addGood(bread, 50);

        System.out.println("=============Print before removing============");

        shopDAO.printGoodList(shopDAO.findAllGoods());

        // removing good from shop

        try {
            shopDAO.removeGood("apple", 10);

            System.out.println("=============Print after removing============");

            shopDAO.printGoodList(shopDAO.findAllGoods());

            shopDAO.removeGood("candy");
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("=============Print after removing============");

        shopDAO.printGoodList(shopDAO.findAllGoods());

        int expectedSize = 4;
        Assert.assertEquals(expectedSize, shopDAO.findAllGoods().size());
    }

    @Test
    public void addEmployee()
    {
        System.out.println("!!!!!!!!!!!!!!ADD EMPLOYEE TEST!!!!!!!!!!!!!!!");


            shopDAO.addEmployee("Maxim", EmployeeType.DIRECTOR);
            shopDAO.addEmployee("Dmitriy", EmployeeType.FINANCE_MANGER);
            shopDAO.addEmployee("Ivan", EmployeeType.CASHIER);
            shopDAO.addEmployee("DmitriyLeonov", EmployeeType.CLEANER);
            shopDAO.addEmployee("Anna", EmployeeType.CASHIER);
            shopDAO.addEmployee("Anna", EmployeeType.DIRECTOR);

        shopDAO.printState(shopDAO.getEmployees());

        int expectedSize = 5;
        Assert.assertEquals(expectedSize, shopDAO.getEmployees().size());
    }

    @Test
    public void dismissEmployee()
    {
        System.out.println("!!!!!!!!!!!!!!DISMISS EMPLOYEE TEST!!!!!!!!!!!!!!!");

            shopDAO.addEmployee("Maxim", EmployeeType.DIRECTOR);
            shopDAO.addEmployee("Dmitriy", EmployeeType.FINANCE_MANGER);
            shopDAO.addEmployee("Ivan", EmployeeType.CASHIER);
            shopDAO.addEmployee("DmitriyLeonov", EmployeeType.CLEANER);
            shopDAO.addEmployee("Anna", EmployeeType.CASHIER);
            shopDAO.addEmployee("Anna", EmployeeType.DIRECTOR);

            System.out.println("Print state before dismissed employees");
            shopDAO.printState(shopDAO.getEmployees());

            shopDAO.dismissEmployee("Dmitriy");
            shopDAO.dismissEmployee("Egor");
            shopDAO.addEmployee("Sergey", EmployeeType.FINANCE_MANGER);


        System.out.println("Print state after dismissed employees");
        shopDAO.printState(shopDAO.getEmployees());

        int expectedSize = 5;
        Assert.assertEquals(expectedSize, shopDAO.getEmployees().size());
    }

    @Test
    public void findGoodTest()
    {
        Good apple = new Good("apple", 500F);
        Good result = null;
        // adding goods to shop
        shopDAO.addGood(apple, 100);

        try {
            result = shopDAO.findGood("apple");
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Assert.assertNotNull(result);

    }
}
