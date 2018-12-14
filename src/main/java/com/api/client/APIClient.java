package com.api.client;

import com.KafkaShop;
import com.data.Item;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Scanner;

public class APIClient {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String input = "";

        System.out.println("******** Welcome to Kafka Shop System Administrator ********");

        try {
            while (!input.equalsIgnoreCase("0")) {
                System.out.println("Administrator Queries:");
                System.out.println("1 - Number of item ever sold");
                System.out.println("2 - Number of units of each item sold over the last 5 minutes");
                System.out.println("3 - Maximum price of each item sold over the last 5 minutes");
                System.out.println("4 - Average number of purchases of each item");
                System.out.println("5 - Revenue, expenses and profit of the shop so far");
                System.out.println("6 - Item providing the highest profit over the last 5 minutes");
                System.out.println("7 - Query over a range of products for average sales price");
                System.out.println("0 - Exit");

                input = read();

                switch (input) {
                    case "1":
                        totalSoldItems();
                        break;
                    case "2":
                        soldUnitsOfEachItemLast5Minutes();
                        break;
                    case "3":
                        maximumPriceOfEachItemSold();
                        break;
                    case "4":
                        averageNumberShipmentsOfEachItemSold();
                        break;
                    case "5":
                        shopRevenueExpensesProfit();
                        break;
                    case "6":
                        itemHighestProfit();
                        break;
                    case "7":
                        itemsAverageSoldPrice();
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("Some error occurred. The server is not online or the request received timeout.");
            System.out.println("******** Administrator closed ********");
        }
    }

    private static String read() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    private static String request(String path) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080" + path);
        Response response = target.request().get();

        return response.readEntity(String.class);
    }

    private static void print(String key, int value, String string) {
        System.out.println(key + ": " + value + " " + string);
    }

    private static void print(String key, int value) {
        System.out.println(key + ": " + value);
    }

    private static void totalSoldItems() {
        String jsonString = request("/total-sold-items");
        System.out.println("Total number of purchases: " + jsonString);
    }

    private static void soldUnitsOfEachItemLast5Minutes() {
        String jsonString = request("/units-sold-each-item");
        try {
            List<Item> products = KafkaShop.deserializeItemsFromJSON(jsonString);
            System.out.println("**** Response ****");
            for (Item product : products) {
                print(product.getName(), product.getAmount(), "purchases");
            }
            System.out.println("******************");
        } catch (Exception e) {
            System.out.println("Response: " + jsonString);
        }
    }

    private static void maximumPriceOfEachItemSold() {
        String jsonString = request("/maximum-price-of-each-item-sold");
        try {
            List<Item> products = KafkaShop.deserializeItemsFromJSON(jsonString);
            System.out.println("**** Response ****");
            for (Item product : products) {
                print(product.getName(), product.getPrice() * product.getAmount(), "maximum price");
            }
            System.out.println("******************");
        } catch (Exception e) {
            System.out.println("Response: " + jsonString);
        }
    }

    private static void averageNumberShipmentsOfEachItemSold() {
        String jsonString = request("/average-number-shipments-each-item-sold");
        System.out.println("Response: " + jsonString);
    }

    private static void shopRevenueExpensesProfit() {
        String jsonString = request("/shop-status");
        System.out.println("Response: " + jsonString);
    }

    private static void itemHighestProfit() {
        String jsonString = request("/highest-profit-item");
        System.out.println("Response: " + jsonString);
    }

    private static void itemsAverageSoldPrice() {
        String jsonString = request("/item-average-sold-price");
        System.out.println("Response: " + jsonString);
    }
}
