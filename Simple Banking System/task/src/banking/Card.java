package banking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Card {

    private String cardNumber;
    private String pin;
    private int balance;

    // Used to generate a new card
    public Card() {
        this.balance = 0;


    }

    // Used to add cards from the database to the List
    public Card(String cardNumber, String pin, int balance){
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public void subtractionFromBalance(int moneyTransfer) {
        this.balance -= moneyTransfer;
    }
    public void additionFromBalance(int moneyAddition) {
        this.balance += moneyAddition;
    }

    private String generatePin() {
        int num = ThreadLocalRandom.current().nextInt(9999);
        String formatted = String.format("%04d", num);


        return formatted;
    }

    public String getCardNumber() {
        return cardNumber;
    }


    public String getPin() {
        return this.pin;


    }

    public void setPin(){
     this.pin = generatePin();

    }

    public void creditCardNumber(){

        while (true){
            StringBuilder sb = new StringBuilder("400000");
            for(int i = 7; i <= 16; i++){
                Random random = new Random();
                int randomNumber = random.nextInt(10);
                sb.append(randomNumber);
            }

            if(luhnAlgorithm(sb.toString())){
                this.cardNumber = sb.toString();
                break;
            }

        }

    }


    public boolean luhnAlgorithm(String number){
        String[] parts = number.split("");
        int lastNumber = Integer.parseInt(parts[parts.length-1]);
        List<String> cardNumber = new ArrayList<>(Arrays.asList(parts));
        int index = cardNumber.size()-1;
         cardNumber.remove(index);
        List<Integer>intCardNumber = stringToInteger(cardNumber);
        int sum = 0;
        for(int i = 0; i < intCardNumber.size(); i++) {
            if(i % 2 == 0){
                int multiply = 2 * intCardNumber.get(i);
                intCardNumber.set(i,multiply);
            }
        }
        for(int i = 0; i < intCardNumber.size(); i ++) {
            if(intCardNumber.get(i) > 9){
                int minus = intCardNumber.get(i) - 9;
                intCardNumber.set(i,minus);
            }
        }
        for (Integer integer : intCardNumber) {
            sum += integer;
        }

        int lastCase = sum + lastNumber;
        if(lastCase % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }

    private List<Integer>stringToInteger(List<String>oldString){
        List<Integer>intNumber = new ArrayList<>();
        for(int i = 0; i< oldString.size(); i++){
            intNumber.add(Integer.parseInt(oldString.get(i)));
        }
        return intNumber;
    }

    public int getBalance() {
        return balance;
    }


    @Override
    public String toString() {
        return this.cardNumber + " " + this.pin + " " + this.balance;
    }
}
