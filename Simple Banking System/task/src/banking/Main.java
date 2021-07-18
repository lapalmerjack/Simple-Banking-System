package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



  Scanner scanner = new Scanner(System.in);
  Account accounts = new Account();
  DatabaseEditor databaseEditor = new DatabaseEditor();

  UserInterface ui = new UserInterface(scanner,accounts, databaseEditor);

      if (args[0].equals("-fileName") && args[1] != null){
        ui.start(args[1]);
      }else{
        System.out.println("Database not supplied.");
      }








    }
}