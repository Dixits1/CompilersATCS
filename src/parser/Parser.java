package parser;

import java.util.HashMap;
import java.util.Map;

import scanner.*;

/**
 * Parser is a simple parser for Compilers and Interpreters (2014-2015) lab exercise 1.
 * The parser is used to parse an input stream of tokens and return an abstract syntax tree (AST)
 * @author Arjun Dixit
 * @version 3/9/2022
 */
public class Parser 
{

    private String currentToken;
    private Scanner scanner;
    private Map <String, Integer> variables;

    /**
     * Parser constructor for constructing a parser that
     * uses a Scanner object for input.
     * @param scanner
     * @throws ScanErrorException
     */
    public Parser(Scanner scanner) throws ScanErrorException 
    {
        this.scanner = scanner;
        currentToken = scanner.nextToken();
        variables = new HashMap<String, Integer>();
    }

    /**
     * eat is a private method that takes in a String representing the expected token
     * and compares it to the current token.  If the two tokens match, the current token
     * is set to the next token in the input stream.  If the two tokens do not match,
     * an error is thrown.
     * @param expectedToken
     * @throws ScanErrorException
     */
    private void eat(String expectedToken) throws ScanErrorException 
    {
        if (currentToken.equals(expectedToken)) 
        {
            currentToken = scanner.nextToken();
        } 
        else 
        {
            System.out.println("Expected " + expectedToken + " but found " + currentToken);
            System.exit(1);
        }
    }

    /**
     * parseNumber is a private method that parses a number token and returns the value of the number.
     * @return the value of the number
     * @throws ScanErrorException
     */
    private int parseNumber() throws ScanErrorException 
    {
        int num = Integer.parseInt(currentToken);
        eat(currentToken);
        return num;
    }


    /**
     * parseFactor is a private method that parses a factor token and returns the value of the factor.
     * @return the value of the factor
     * @throws ScanErrorException
     */
    public int parseFactor() throws ScanErrorException 
    {
        if (currentToken.equals("(")) 
        {
            eat("(");
            int num = parseFactor();
            eat(")");
            return num;
        } 
        else if (currentToken.equals("-")) 
        {
            eat("-");
            int num = parseFactor();
            return -num;
        } 
        else 
        {
            return parseNumber();
        }
    }

    /**
     * parseTerm is a private method that parses a term token and returns the value of the term.
     * @return the value of the term
     * @throws ScanErrorException
     */
    public int parseTerm() throws ScanErrorException 
    {
        int val = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/")) 
        {
            if (currentToken.equals("*")) 
            {
                eat("*");
                val *= parseFactor();
            } 
            else 
            {
                eat("/");
                val /= parseFactor();
            }
        }
        return val;
    }

    /**
     * parseExpression is a private method that parses an expression token and returns the value of the expression.
     * @return the value of the expression
     * @throws ScanErrorException
     */
    public int parseExpr() throws ScanErrorException 
    {
        int val = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-")) 
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                val += parseTerm();
            } 
            else 
            {
                eat("-");
                val -= parseTerm();
            }
        }
        return val;
    }

    /**
     * parseStatement is a private method that parses a statement.
     * @throws ScanErrorException
     */
    public void parseStatement() throws ScanErrorException 
    {
        if (currentToken.equals("WRITELN")) 
        {
            eat("WRITELN");
            eat("(");
            int val = parseExpr();
            eat(")");
            eat(";");
        } 
        else if (currentToken.equals("BEGIN")) 
        {
            eat("BEGIN");
            while (!currentToken.equals("END")) 
            {
                parseStatement();
            }
            eat("END");
            eat(";");
        } 
        else 
        {
            throw new ScanErrorException("Unexpected token: " + currentToken);
        }
    }

    /**
     * parseStatementWithVar is a private method that parses a statement with a variable.
     * @throws ScanErrorException
     */
    public void parseStatementWithVar() throws ScanErrorException 
    {
        if (currentToken.equals("WRITELN")) 
        {
            eat("WRITELN");
            eat("(");
            int val = parseExpr();
            eat(")");
            eat(";");
        } 
        else if (currentToken.equals("BEGIN")) 
        {
            eat("BEGIN");
            while (!currentToken.equals("END")) 
            {
                parseStatementWithVar();
            }
            eat("END");
            eat(";");
        } 
        else if (currentToken.equals("id")) 
        {
            String id = currentToken;
            eat("id");
            eat(":=");
            int val = parseExpr();
            eat(";");
            variables.put(id, val);
        } 
        else 
        {
            throw new ScanErrorException("Unexpected token: " + currentToken);
        }
    }

}
