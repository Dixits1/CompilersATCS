package parser;

import java.util.Scanner;

public class Parser_Real {
    private String currentToken;
    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        currentToken = scanner.next();
    }

    // Write a private method called eat, which should take in a String representing the expected token
    private void eat(String expectedToken) {
        if (currentToken.equals(expectedToken)) 
        {
            currentToken = scanner.next();
        } 
        else 
        {
            System.out.println("Expected " + expectedToken + " but found " + currentToken);
            System.exit(1);
        }
    }
    
}
