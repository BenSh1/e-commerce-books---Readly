import javax.xml.transform.Result;
import java.sql.*;
import java.io.*;
import java.util.Scanner;


public class handleDataBase {
    public static void main(String[] args) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/eCommerceDB","postgres","ciargch98");
            //System.out.println(connection);
            System.out.println("Connection has been established!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");

            //some of the data from database will be printed to the screen
            while (resultSet.next())
            {
                System.out.println("User id: " + resultSet.getString("customer id")+ " ,email: " + resultSet.getString("email") /*+" and created at: " + resultSet.getString("created_at")*/ );
            }


            //entered some of data to the database eCommerceDB , you can see the data in intellij or in pgadmin4
            /*
            statement.execute("insert into users values (4, 'Natali', 'mypassword', 'Natali1@gmail.com' ,'Natali levi' )");
            System.out.println("row inserted!");
            statement.execute("insert into users values (5, 'Gili', 'mypassword', 'Gili1@gmail.com' ,'Gili levi' )");
            System.out.println("row inserted!");
            */

            statement.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
