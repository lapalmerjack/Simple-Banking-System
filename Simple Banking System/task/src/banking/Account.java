package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Account {

     List<Card> cards;
    private Card tempAccount;

    public Account() {
        this.cards = new ArrayList<>();
        this.tempAccount = new Card();


    }

    public void addingCards(String fileName){
        Card card = new Card();
       card.creditCardNumber();
        card.setPin();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        System.out.println("");
        cards.add(card);
        DatabaseEditor databaseEditor = new DatabaseEditor();
        databaseEditor.insert(card.getCardNumber(),card.getPin(), card.getBalance(), fileName);


    }
    public void addingCards(String number, String pin, int balance){
        Card card = new Card(number,pin,balance);
        cards.add(card);

    }


    public boolean loggingIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        String cardNumberEntry = scanner.nextLine();
        System.out.println("Enter your pin");
        String pinEntry = scanner.nextLine();
        for (Card search: this.cards) {
            if (cardNumberEntry.equals(search.getCardNumber()) && pinEntry.equals(search.getPin())) {
                System.out.println("");
                System.out.println("You have successfully logged in!");
                System.out.println("");
                this.tempAccount = search;
                return true;
            }

        }
        System.out.println("");
        System.out.println("Wrong number or pin!");
        System.out.println("");

       return false;
    }

    public Card getTempAccount() {
        return tempAccount;
    }

    public void addIncome (String fileName) {
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter income:");
        int income = scanner.nextInt();
        this.tempAccount.additionFromBalance(income);
        DatabaseEditor databaseEditor = new DatabaseEditor();
        databaseEditor.update(tempAccount.getCardNumber(),income,fileName);

        System.out.println("Income was added!");

    }

    public void accountListings(){
        this.cards.forEach(System.out::println);

    }

    public void doTransfer (String fileName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transfer");
        System.out.println("Enter Card Number");
        String cardTransferNumber = scanner.nextLine();
        if(this.passLuhns(cardTransferNumber)){
            if(this.sameAccount(cardTransferNumber)){
            if(this.cardNumberOnFile(cardTransferNumber)) {
                System.out.println("Enter how much money you want to transfer: ");
                int moneyTransfer = scanner.nextInt();
                if (transferMath(moneyTransfer)) {
                    DatabaseEditor databaseEditor = new DatabaseEditor();
                    databaseEditor.updateSubtraction(this.tempAccount.getCardNumber(), moneyTransfer, fileName);
                    databaseEditor.update(cardTransferNumber, moneyTransfer, fileName);
                }
            }

            }
        }



    }

    private boolean sameAccount(String cardNumber){
        if(!tempAccount.getCardNumber().equals(cardNumber)){
            return true;
        }else {
            System.out.println("You can't transfer money to the same account!");
            return false;
        }
    }

    private boolean transferMath(int moneyTransfer){
        if(moneyTransfer > tempAccount.getBalance()) {
            System.out.println("Not enough money!");
            return false;
        }else{
            this.tempAccount.subtractionFromBalance(moneyTransfer);
            return true;
        }
    }

    private boolean cardNumberOnFile(String cardNUmber) {
       for(Card cardsOnFile : this.cards) {
           if(cardsOnFile.getCardNumber().equals(cardNUmber)) {
               return true;
           }
       }
        System.out.println("Such a card does not exist.");
       return false;
    }

    private boolean passLuhns(String cardNumber){
        if(tempAccount.luhnAlgorithm(cardNumber)){
            return true;
        }else{
            System.out.println("Probably you made a mistake in the card number. Please try again.");
            return false;
        }
    }
    public void deletingCard (String fileName) {
        DatabaseEditor databaseEditor = new DatabaseEditor();
        databaseEditor.delete(fileName,tempAccount.getCardNumber());
        System.out.println("The account has been closed!");

    }


}
