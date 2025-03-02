package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    private static final String baseURL1 = "http://localhost:3001/";
    private static final String baseURL=" https://thin-zoos-ask.loca.lt/";

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get(baseURL);



        driver.quit();
    }
}