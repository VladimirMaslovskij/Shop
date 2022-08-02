package org.example;

import ShopExceptions.GoodNotEnoughException;
import ShopExceptions.GoodNotFoundException;
import org.example.dao.ClientDAO;
import org.example.dao.ShopDAO;
import org.example.models.Client;
import org.example.models.Good;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ClientDAOTest
{
    Client client = new Client(25000.0F);
    ClientDAO clientDAO;
    @Before
    public void initClientDAO()
    {
        clientDAO = new ClientDAO(client);
    }

    @Test
    public void addGoodInBasket()
    {
        try {
            clientDAO.addGoodToBasket("apple", 3);
            clientDAO.addGoodToBasket("candy", 3);
            clientDAO.addGoodToBasket("strawberry", 7);

            System.out.println(clientDAO.getClient().getBasket());

            Assert.assertEquals(2, clientDAO.getClient().getBasket().size());
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (GoodNotEnoughException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void removeFromBasket()
    {
        try {
            clientDAO.addGoodToBasket("apple", 3);
            clientDAO.addGoodToBasket("candy", 3);
            clientDAO.addGoodToBasket("bread", 5);
            System.out.println(clientDAO.getClient().getBasket());
            clientDAO.removeGoodFromBasket("apple", 1);
            System.out.println(clientDAO.getClient().getBasket());
            clientDAO.removeGoodFromBasket("fish", 2);
            clientDAO.removeGoodFromBasket("candy", 3);
            clientDAO.removeGoodFromBasket("bread", 5);
            System.out.println(clientDAO.getClient().getBasket());

            Assert.assertEquals(1, clientDAO.getClient().getBasket().size());
        } catch (GoodNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (GoodNotEnoughException e) {
            System.out.println(e.getMessage());
        }
    }


}
