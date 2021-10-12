package com.epam.brest;

import com.epam.brest.calc.Calc;
import com.epam.brest.calc.CalcImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//values[0] = weight and values[1] = length
        BigDecimal[] values;
        BigDecimal pricePerKg, pricePerKm, result;
        Properties lengthProperties;
        Properties weightProperties;
        Calc calculator;

        try (Scanner scanner = new Scanner(System.in);
             FileInputStream weightStream = new FileInputStream("src/main/resources/weightPrice.properties");
             FileInputStream lengthStream = new FileInputStream("src/main/resources/lengthPrice.properties")) {

            weightProperties = new Properties();
            lengthProperties = new Properties();
            calculator = new CalcImpl();

            for (; ; ) {
                values = getValuesFromConsole(scanner);
                weightProperties.load(weightStream);
                lengthProperties.load(lengthStream);
                pricePerKg = new BigDecimal(Integer.parseInt(weightProperties.getProperty(values[0].toString())));
                System.out.println("Price for " + values[0] + " kg = " + pricePerKg);
                pricePerKm = new BigDecimal(Integer.parseInt(lengthProperties.getProperty(values[1].toString())));
                System.out.println("Price for " + values[1] + " km = " + pricePerKm);

                result = CalcImpl.handle(values[0], pricePerKg, values[1], pricePerKm);
                System.out.println("result = " + result);

                System.out.println("Enter 'q' to exit or 'c' to continue");
                if (scanner.hasNext("q")) return;
                if (scanner.hasNext("c")) {
                    scanner.next();
                    continue;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static BigDecimal[] getValuesFromConsole(Scanner scanner) {
        BigDecimal[] values = new BigDecimal[2];
        System.out.println("Enter weight:");
        values[0] = scanner.nextBigDecimal();
        System.out.println("Enter length:");
        values[1] = scanner.nextBigDecimal();
        return values;
    }
}