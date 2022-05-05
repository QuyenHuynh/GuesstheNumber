import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String name = "";
    static boolean validName = false;
    static boolean victory = false;
    static int min = 1;
    static int max = 20;
    static int range = max - min + 1;
    static int randomNumber = (int) (Math.random() * range) + min;
    static int numOfGuesses = 0;
    static String playAgain = "";
    static boolean validPlayAgain = false;

    public static void main(String[] args) {
        getName();
        startGame();
    }

    public static void getName() {
        System.out.println("Hello! What is your name?");
        Scanner scan = new Scanner(System.in);
        name = scan.nextLine();

        while (!validName) {
            try {
                validNameCheck(name);
            } catch (CustomException e) {
                e.getMessage();
                name = scan.nextLine();
            }
        }
    }

    public static void startGame(){
        System.out.printf("Well, %s, I am thinking of a number between 1 and 20.%n", name);
        System.out.println("Take a guess.");
        System.out.println("Debug session: The random number is... " + randomNumber);

        Scanner scan = new Scanner(System.in);
        int guess = 0;

        while (!victory || guess != randomNumber) {
            try {
                guess = scan.nextInt();
                if (guess >= 1 && guess <= 20) {
                    numOfGuesses++;

                    if (guess > randomNumber) {
                        System.out.println("That's too high. Try again.");
                        System.out.println("Debug session: The random number is... " + randomNumber);
                    }
                    if (guess < randomNumber) {
                        System.out.println("That's too low. Try again.");
                        System.out.println("Debug session: The random number is... " + randomNumber);
                    }
                    if (guess == randomNumber) {
                        victory = true;

                        if (numOfGuesses == 1) {
                            System.out.printf("Good job, %s! You guessed my number in 1 guess!%n", name);
                        } else {
                            System.out.printf("Good job, %s! You guessed my number in %d guesses!%n", name, numOfGuesses);
                        }
                        playAgainPrompt();
                    }
                } else {
                    System.out.println("Your guess is out of range! (1-20)");
                    System.out.println("Debug session: The random number is... " + randomNumber);
                }
            } catch (InputMismatchException e) {
             System.out.println("Your guess is not a number. Try again! (1-20)");
             System.out.println("Debug session: The random number is... " + randomNumber);
             scan.next();
            }
        }
    }

    public static void playAgainPrompt() {
        System.out.println("Would you like to play again? (y/n)");
        Scanner scan = new Scanner(System.in);
        playAgain = scan.nextLine();

        while (!validPlayAgain) {
            try {
                validPlayAgainCheck(playAgain);
            } catch (CustomException e) {
                e.getMessage();
                playAgain = scan.nextLine();
            }
        }
    }

    public static void resetGame() {
        victory = false;
        randomNumber = (int) (Math.random() * range) + min;
        numOfGuesses = 0;
        playAgain = "";
        startGame();
    }

    static void validPlayAgainCheck(String playAgain) throws CustomException {

//        check for empty string
        if (playAgain != null && playAgain.length() > 0) {
            if (playAgain.equals("y") || playAgain.equals("Y")) {
                validPlayAgain = true;
                System.out.println("Hooray!");
                resetGame();
            } else if (playAgain.equals("n") || playAgain.equals("N")) {
                validPlayAgain = true;
                System.out.println("Goodbye!");
            } else {
//                throws an error if it's anything other than y/Y or n/N
                System.out.println("Not a valid answer. Please enter 'y' or 'n'");
                throw new CustomException();
            }
        } else {
            System.out.println("Input is blank. Would you like to play again? Please enter 'y' or 'n'");
            throw new CustomException();
        }
        validPlayAgain = true;
    }

    static void validNameCheck(String name) throws CustomException {
        //No special characters. Letters A-Z and numbers are fine.
        Pattern pattern = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher matcher = pattern.matcher(name);

        if (name.isBlank()) {
            // String is blank
            System.out.println("The input is blank! Try again!");
            throw new CustomException();
        }
        if (matcher.find()) {
            // String contains special characters
            System.out.println("No special characters are allowed! Try again!");
            throw new CustomException();
        }
        validName = true;
    }

    static class CustomException extends Exception {
        public CustomException() {
            // Call constructor of parent Exception
            super();
        }
    }
}

