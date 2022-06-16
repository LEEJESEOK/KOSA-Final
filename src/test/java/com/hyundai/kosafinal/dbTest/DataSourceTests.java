package com.hyundai.kosafinal.dbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataSourceTests {
  static {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }//end static

  @Value("${spring.db1.datasource.url}")
  String productURL;

  @Value("${spring.db2.datasource.url}")
  String userOrderURL;

  @Value("${spring.db1.datasource.username}")
  String productUsername;

  @Value("${spring.db2.datasource.username}")
  String userOrderUsername;

  @Value("${spring.db1.datasource.password}")
  String productPassword;

  @Value("${spring.db2.datasource.password}")
  String userOrderPassword;

  @Test
  public void product_TestConnection() {
    try {
      Connection con =
        DriverManager.getConnection(
          productURL, productUsername, productPassword);
      System.out.println("con: " + con);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }//end try
  }//end test

  @Test
  public void userOrder_TestConnection() {
    try {
      Connection con =
        DriverManager.getConnection(
          userOrderURL, userOrderUsername, userOrderPassword);
      System.out.println("con: " + con);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }//end try
  }//end test
}//end class
