package banking;

import javax.xml.crypto.Data;
import java.sql.*;

public class DatabaseEditor {


    public Connection connection (String filename){
        String url = "jdbc:sqlite:" + filename ;
        Connection con = null;
        try {
            con = DriverManager.getConnection(url);


        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return con;

    }


    public void createTable(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:" + filename;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                +"      id integer PRIMARY KEY,\n"
                +"      number TEXT,\n"
                +"      pin TEXT,\n"
                +"      balance INTEGER DEFAULT 0"

                +");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // used to insert new values into table
    public void insert (String cardNumber, String pin, int balance, String fileName) {

        String sql = "INSERT INTO card(number,pin,balance) VALUES (?,?,?)";

        try (Connection conn = this.connection(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, pin);
            pstmt.setInt(3,balance);


            pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update (String cardNumber, int balance, String fileName) {

        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try(Connection con = this.connection(fileName)){
            PreparedStatement ptsmt = con.prepareStatement(sql);
            ptsmt.setInt(1,balance);
            ptsmt.setString(2, cardNumber);

            ptsmt.executeUpdate();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void updateSubtraction (String cardNumber, int balance, String fileName) {

        String sql = "UPDATE card SET balance = balance - ? WHERE number = ?";

        try(Connection con = this.connection(fileName)){
            PreparedStatement ptsmt = con.prepareStatement(sql);
            ptsmt.setInt(1,balance);
            ptsmt.setString(2, cardNumber);

            ptsmt.executeUpdate();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void selectAll (String fileName, Account account){

        String sql = "SELECT number, pin, balance FROM card";

        try(Connection con = this.connection(fileName);
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql)){
                while(resultSet.next()){
                   account.addingCards(resultSet.getString("number"),
                           resultSet.getString("pin"), resultSet.getInt("balance"));

                }

        } catch(SQLException e){
        System.out.println(e.getMessage());
    }

    }

    public void delete (String fileName, String cardNumber) {
        String sql = "DELETE FROM card where number = ?";

        try (Connection con = this.connection(fileName);
        PreparedStatement preparedStatement = con.prepareStatement(sql)){

            preparedStatement.setString(1,cardNumber);

            preparedStatement.executeUpdate();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

