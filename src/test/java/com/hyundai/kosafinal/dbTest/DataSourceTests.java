package com.hyundai.kosafinal.dbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
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

  @Test
  public void TestConnection() {
    try {
      Connection con =
        DriverManager.getConnection(
          "jdbc:oracle:thin:@finaldb_high?TNS_ADMIN=//Users//yujihun/Documents//wallet//Wallet_finaldb"
          ,"kosafinal","Guseo4rlghkdlxld");
      System.out.println("con: " + con);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }//end try
  }//end test
}//end class
