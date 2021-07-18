package banking;

import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private Account accounts;
    private DatabaseEditor databaseEditor;


    public UserInterface(Scanner scanner, Account accounts, DatabaseEditor databaseEditor) {
        this.scanner = scanner;
        this.accounts = accounts;
        this.databaseEditor = databaseEditor;
    }


    public void start(String fileName) {
        this.databaseEditor.createTable(fileName);
        this.databaseEditor.selectAll(fileName, this.accounts);

        while (true) {

            displayOptions();
            String options = scanner.nextLine();
            if (options.equals("1")) {
                System.out.println("");
                this.createAccount(fileName);
            } else if (options.equals("2")) {
                System.out.println("");
                this.loggingIn(fileName);
            } else if (options.equals("0")) {
                System.out.println("");
                System.out.println("Bye!");
                break;
            }else if (options.equals("88")){
                this.accounts.accountListings();
            }

        }

    }

    public static void displayOptions() {
        System.out.println("1. Create an account\n2. Log into account\n0. Exit");

    }

    public void createAccount(String fileName) {
        this.accounts.addingCards(fileName);

    }

    public void loggingIn(String fileName) {
        if (this.accounts.loggingIn()) {
            while (true) {
                System.out.println("1. Balance\n2. Add Income\n3. Do transfer\n" +
                        "4. Close account\n5. Log out\n0.Exit");
                String options = scanner.nextLine();
                if (options.equals("1")) {
                    System.out.println("");
                    System.out.println("Balance: " + this.accounts.getTempAccount().getBalance());
                    System.out.println("");
                }else if (options.equals("2")) {
                    this.accounts.addIncome(fileName);
                    this.accounts.cards.clear();
                    this.databaseEditor.selectAll(fileName,this.accounts);

                }else if(options.equals("3")) {
                    this.accounts.doTransfer(fileName);
                    this.accounts.cards.clear();
                    this.databaseEditor.selectAll(fileName,this.accounts);
                }else if(options.equals("4")){
                    this.accounts.deletingCard(fileName);
                    this.accounts.cards.clear();
                    this.databaseEditor.selectAll(fileName,this.accounts);
                    break;
                }else if (options.equals("5")) {
                    System.out.println("");
                    break;
                } else if (options.equals("0")) {
                    System.exit(0);
                }
            }
        }
    }


}
