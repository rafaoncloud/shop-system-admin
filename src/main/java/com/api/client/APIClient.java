package com.api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Scanner;

public class APIClient {

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("******** Welcome to Kafka Shop System Administrator ********");
        int minutes;
        String itemName;
        String input = "";
        try {
            while (!input.equalsIgnoreCase("0")) {
                System.out.println("Administrator Queries:");
                System.out.println("1 - Number of item ever sold");
                System.out.println("2 - Number of units of each item sold over the last x minutes");
                System.out.println("3 - Maximum price of each item sold over the last x minutes");
                System.out.println("4 - Average number of purchases of each item");
                System.out.println("5 - Revenue, expenses and profit of the shop so far");
                System.out.println("6 - Item providing the higest profit over the last x minutes");
                System.out.println("7 - Query over a range of products for average sales price");
                System.out.println("0 - Exit");
                input = read();
                switch (input) {
                    case "1":
                        totalSoldItems();
                        break;
                    case "2":
                        System.out.println("Last minutes:");
                        input = read();
                        try{
                            minutes = Integer.parseInt(input);
                        }catch (Exception e){
                            continue;
                        }
                        soldUnitsOfEachItem(minutes);
                        break;
                    case "3":
                        System.out.println("Last minutes:");
                        input = read();
                        try{
                            minutes = Integer.parseInt(input);
                        }catch (Exception e){
                            continue;
                        }
                        maximumPriceOfEachItemSold(minutes);
                        break;
                    case "4":
                        input = read();
                        averageNumberShipmentsOfEachItemSold();
                        break;
                    case "5":
                        input = read();
                        shopRevenueExpensesProfit();
                        break;
                    case "6":
                        System.out.println("Last minutes:");
                        input = read();
                        try{
                            minutes = Integer.parseInt(input);
                        }catch (Exception e){
                            continue;
                        }
                        System.out.println("Item:");
                        input = read();
                        itemHighestProfit(minutes,input);
                        break;
                    case "7":
                        System.out.println("Item:");
                        input = read();
                        itemsAverageSoldPrice(input);
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some error occurred. The server is not online or the request received timeout.");
            System.out.println("******** Administrator closed ********");
        }
    }

    private static String read(){
        System.out.print("> ");
        String input = scanner.nextLine();
        return input;
    }

    private static String request(String path) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080" + path);
        Response response = target.request().get();
        String jsonString = response.readEntity(String.class);
        return jsonString;
    }

    public static void totalSoldItems() {
        String jsonString = request("/total-sold-items");
        System.out.println("Response: " + jsonString);
    }

    public static void soldUnitsOfEachItem(final int minutes) {
        String jsonString = request("/maximum-price-of-each-item-sold?minutes=" + minutes);
        System.out.println("Response: " + jsonString);
    }

    public static void maximumPriceOfEachItemSold(Integer minutes) {
        String jsonString = request("/average-number-shipments-each-item-sold?minutes=" + minutes);
        System.out.println("Response: " + jsonString);
    }

    public static void averageNumberShipmentsOfEachItemSold() {
        String jsonString = request("/average-number-shipments-each-item-sold");
        System.out.println("Response: " + jsonString);
    }

    public static void shopRevenueExpensesProfit() {
        String jsonString = request("/shop-status");
        System.out.println("Response" + jsonString);
    }

    public static void itemHighestProfit(final int minutes, final String item) {
        String jsonString = request("/highest-profit-item?minutes=" + minutes + "&item=" + item);
        System.out.println("Response" + jsonString);
    }

    public static void itemsAverageSoldPrice(String item) {
        String jsonString = request("/item-average-sold-price?item=" + item);
        System.out.println("Response" + jsonString);
    }
}
