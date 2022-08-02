package org.example.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/*
1. Goods : HashMap
2. Cash : float
3. Employee : HashMap
 */
public class Shop
{
    private static Shop INSTANCE = null;

    private Float cash;

    public static Shop getINSTANCE() {
       if(INSTANCE == null)
           INSTANCE = new Shop();
       return INSTANCE;
    }
    private Shop(){}

    public Float getCash()
    {
        return cash;
    }

    public void setCash(Float cash)
    {
        this.cash = cash;
    }

}
