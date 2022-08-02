package org.example.dao;

import ShopExceptions.GoodNotFoundException;
import ShopExceptions.ShopEmployeeException;
import org.example.models.EmployeeType;
import org.example.models.Good;
import org.example.models.Shop;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
ShopDAO
1. addGood;
2. findGood;
3. removeGood;
4. createCheck;
5. showGoods;
6. addEmployee
7.
 */
public class ShopDAO {

    private final Shop shop = Shop.getINSTANCE();


    public final Shop getShop() {
        return shop;
    }

    public void addGood(Good good, int amount) {
        HashMap<Good, Integer> goodsList = findAllGoods();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("goods.txt"))) {
            if (goodsList.size() > 0) {
                boolean isInclude = false;
                for (Map.Entry<Good, Integer> goodInfo : goodsList.entrySet()) {
                    if ((goodInfo.getKey().getName()).equals(good.getName())) {
                        goodInfo.setValue(goodInfo.getValue() + amount);
                        isInclude = true;
                    }
                }
                if (!isInclude)
                    goodsList.put(good, amount);
                for (Map.Entry<Good, Integer> goodInfo : goodsList.entrySet()) {
                    writer.write(goodInfo.getKey().getName() + "," + goodInfo.getKey().getPrice() + "," +
                            goodInfo.getValue() + "\n");
                }
            } else
                writer.write(good.getName() + "," + good.getPrice() + "," + amount + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<Good, Integer> findAllGoods() {
        HashMap<Good, Integer> allGoods = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("goods.txt"))) {
            while (bufferedReader.ready()) {
                String goodStr = bufferedReader.readLine();
                String[] goodInfo = goodStr.split(",", 3);
                allGoods.put(new Good(goodInfo[0], Float.valueOf(goodInfo[1])), Integer.parseInt(goodInfo[2]));
            }
        } catch (FileNotFoundException e) {
            File file = new File("goods.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return allGoods;
    }

    public void printGoodList(HashMap<Good, Integer> goods) {
        System.out.println("============== GOOD LIST ==============");
        for (Map.Entry<Good, Integer> goodInfo : goods.entrySet()) {
            System.out.println("Good: " + goodInfo.getKey().getName() + ", prise: " +
                    goodInfo.getKey().getPrice() + ", amount: " +
                    goodInfo.getValue());
        }
        System.out.println("\n");
    }

    public Good findGood(String name) throws GoodNotFoundException {
        Good resultGood = null;
        HashMap<Good, Integer> goods = findAllGoods();
        for (Map.Entry<Good, Integer> good : goods.entrySet()) {
            if (name.equals(good.getKey().getName()))
                resultGood = good.getKey();
        }
        if (resultGood == null)
            throw new GoodNotFoundException("This good not found in shop");
        return resultGood;
    }

    public boolean removeGood(String name) throws GoodNotFoundException {
        HashMap<Good, Integer> goodsList = findAllGoods();
        boolean isRemove = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("goods.txt"))) {
            if (goodsList.size() > 0) {
                Good key = null;
                for (Map.Entry<Good, Integer> goodInfo : goodsList.entrySet()) {
                    if ((goodInfo.getKey().getName()).equals(name)) {
                        key = goodInfo.getKey();
                        isRemove = true;
                    }
                }
                goodsList.remove(key);
                if (!isRemove) {
                    throw new GoodNotFoundException("This good not found in shop");
                }
                for (Map.Entry<Good, Integer> goodInfo : goodsList.entrySet()) {
                    writer.write(goodInfo.getKey().getName() + "," + goodInfo.getKey().getPrice() + "," +
                            goodInfo.getValue() + "\n");
                }
            } else {
                throw new GoodNotFoundException("The shop is empty");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return isRemove;
    }


    public boolean removeGood(String name, Integer amount) throws GoodNotFoundException {

        HashMap<Good, Integer> goodsList = findAllGoods();
        boolean isRemove = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("goods.txt"))) {
            if (goodsList.size() > 0) {
                Good key = null;
                for (Map.Entry<Good, Integer> goodInfo : goodsList.entrySet()) {
                    if ((goodInfo.getKey().getName()).equals(name)) {
                        if (amount >= goodInfo.getValue())
                            key = goodInfo.getKey();
                        else
                            goodInfo.setValue(goodInfo.getValue() - amount);
                        isRemove = true;
                    }
                }
                goodsList.remove(key);
                if (!isRemove) {
                    throw new GoodNotFoundException("This good not found in shop");
                }
                for (Map.Entry<Good, Integer> goodInfo : goodsList.entrySet()) {
                    writer.write(goodInfo.getKey().getName() + "," + goodInfo.getKey().getPrice() + "," +
                            goodInfo.getValue() + "\n");
                }
            } else {
                throw new GoodNotFoundException("The shop is empty");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return isRemove;
    }

    //    public Check createCheck(Map<Good, Integer> basket, String name)
//    {
//
//        return new Check(basket, name);
//    }

    public void addEmployee(String name, EmployeeType post) {
        HashMap<String, EmployeeType> state = getEmployees();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("state.txt"))) {
            if (state.size() > 0) {
                boolean isInclude = false;
                for (Map.Entry<String, EmployeeType> employee : state.entrySet()) {
                    if (employee.getKey().equals(name)) {
                        System.out.println("This employee still working in shop");
                        writer.write(employee.getKey() + "," + employee.getValue() + "\n");
                    }
                }
                if (!isInclude) {
                    state.put(name, post);
                }
                for (Map.Entry<String, EmployeeType> employee : state.entrySet()) {
                    writer.write(employee.getKey() + "," + employee.getValue() + "\n");
                }
            } else {
                writer.write(name + "," + post + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dismissEmployee(String name) {
        HashMap<String, EmployeeType> state = getEmployees();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("state.txt"))) {
            if (state.size() > 0) {
                boolean isInclude = false;
                String key = null;
                for (Map.Entry<String, EmployeeType> employeeInfo : state.entrySet()) {
                    if (employeeInfo.getKey().equals(name)) {
                        key = employeeInfo.getKey();
                        isInclude = true;
                    }
                }
                state.remove(key);
                for (Map.Entry<String, EmployeeType> employee : state.entrySet()) {
                    writer.write(employee.getKey() + "," + employee.getValue() + "\n");
                }
                if (!isInclude) {
                    System.out.println("This employee are not work in shop");
                }
            } else {
                System.out.println("Shop don't have a employees");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, EmployeeType> getEmployees() {
        HashMap<String, EmployeeType> state = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("state.txt"))) {
            while (reader.ready()) {
                String str = reader.readLine();
                String[] employeesInfo = str.split(",");
                state.put(employeesInfo[0], EmployeeType.valueOf(employeesInfo[1]));
            }
        } catch (FileNotFoundException e) {
            File stateFile = new File("state.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return state;
    }

    public void printState(HashMap<String, EmployeeType> hashMap) {
        System.out.println("============== SHOP STATE ==============");
        for (Map.Entry<String, EmployeeType> employeeInfo : hashMap.entrySet()) {
            System.out.println("Name: " + employeeInfo.getKey() + ", post: " + employeeInfo.getValue());
        }
        System.out.println("\n");
    }

    public void addCash(Float sum) {
        try (BufferedReader reader = new BufferedReader(new FileReader("shopCash.txt"))) {
            Float cash = 0F;
            String str = reader.readLine();
            if (str != null)
                cash = Float.valueOf(str);
            else
                cash = 0F;
            cash += sum;
            BufferedWriter writer = new BufferedWriter(new FileWriter("shopCash.txt"));
            writer.write(String.valueOf(cash));
            shop.setCash(cash);
            writer.close();
        } catch (FileNotFoundException e) {
            File cashFile = new File("shopCash.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCashierName() {
        String result = null;
        ArrayList<String> cashiers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("state.txt"))) {
            while (reader.ready()) {
                String str = reader.readLine();
                String[] employeesInfo = str.split(",");
                if (employeesInfo[1].equals("CASHIER"))
                    cashiers.add(employeesInfo[0]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int today = new Date().getDay();
        if (cashiers.size() != 0) {
            if (today % 2 == 0) {
                return cashiers.get((int) ((cashiers.size() / 2) * Math.random()));
            } else {
                return cashiers.get((int) ((cashiers.size() / 2) * Math.random()) + (cashiers.size() / 2));
            }
        } else
        {
            System.out.println("No cashiers in state");
            return null;
        }
    }
}
