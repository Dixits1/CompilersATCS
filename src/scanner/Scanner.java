package scanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1. 
 * The scanner is used to scan in an input stream and return tokens sequentially.
 * @author Arjun Dixit
 * @version 2/1/2022
 *  
 * Usage:
 * - scan in the input character sequence using an InputStream or String provided to the constructor
 * - use nextToken to retrieve the value of the next token in the input stream
 * - use hasNext to determine if another token exists in the input stream
 */
public class Scanner
{
    private static final String[] VALID_OPERANDS = new String[]{
        ":=", 
        "<>", ">=", "<=", "=", "<", ">",
        "+", "-", "*", "/", "%", 
        "(", ")"
    };

    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    private List<String> validSingles;
    private List<String> validDoubleInits;

    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>));
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
        initOperandHelpers();
    }
    
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
        initOperandHelpers();
    }

    /**
     * Reads in the next token in the input stream by sequentially reading characters using the 
     * following protocol:
     * - skip whitespaces
     * - for all other characters, categorize upcoming token and read in the token accordingly
     * @return returns the next token in the sequence of tokens; if the end-of-file is reached, 
     *         returns "EOD"
     * @throws ScanErrorException if an error occurs while scanning in the input stream of 
     *         characters
     */
    public String nextToken() throws ScanErrorException 
    {
        String token;
        String eofToken = "END";

        if(eof) 
        {
            token = eofToken;
        }
        else 
        {
            while(isWhiteSpace(currentChar)) 
            {
                eat(currentChar);
            }

            if(isDigit(currentChar)) 
            {
                token = scanNumber();
            }
            else if(isLetter(currentChar)) 
            {
                token = scanIdentifier();
            }
            else if(currentChar == '/')
            {
                getNextChar();
                if(currentChar == '/') 
                {
                    while(currentChar != '\n') 
                    {
                        getNextChar();
                    }
                    token = null;
                }
                else 
                {
                    return "/";
                }
            }
            else if(currentChar == '.') 
            {
                token = eofToken;
                eof = true;
            }
            else if(currentChar == ';') 
            {
                while(currentChar != '\n') 
                {
                    getNextChar();
                }
                token = ";";
            }
            else if(currentChar == ',')
            {
                eat(currentChar);
                token = ",";
            }
            else 
            {
                token = scanOperand();
            }
        }

        // System.out.println(token);

        return token;
    }

    /**
     * Reads in the next character as a char type and stores it in the currentChar instance variable
     * If the next character does not exist, the eof instance variable is set to true, indicating 
     * that the end of the file has been reached
     * If an error occurs while reading the input, the error is printed and the program is aborted 
     * imemediately
     */
    private void getNextChar() 
    {
        int curCharInt;

        try 
        {
            curCharInt = in.read();

            if (curCharInt == -1) 
            {
                eof = true;
            }
            else 
            {
                currentChar = (char) curCharInt;
            }
        } 
        catch (IOException e) 
        {
            System.out.println(e);
            System.exit(1);
        }
    } 

    /**
     * @return returns true if the input contains a next token; false otherwise.
     */
    public boolean hasNext() 
    {
        return !eof;
    }

    /**
     * Verifies that curChar is the current character in the input (currentChar) and reads the 
     * next character in the input stream.
     * @param curChar specifies the character to verify
     * @throws ScanErrorException if curChar does not match currentChar
     */
    private void eat(char curChar) throws ScanErrorException 
    {
        if (currentChar == curChar) 
        {
            getNextChar();
        }
        else 
        {
            throw new ScanErrorException(
            "Illegal character - expected \'" + currentChar + "\' and found \'" + curChar + "\'."
                );
        }
    }

    /**
     * Checks if curChar is a digit, where a digit is defined by the set of characters from 0-9
     * @param curChar specifies the current character to check if it is a digit
     * @return returns true if curChar is a digit; false otherwise
     */
    public static boolean isDigit(char curChar) 
    {
        return curChar >= '0' && curChar <= '9';
    }

    /**
     * Checks if curChar is a letter, where a letter is defined by the set of characters 
     * from a-z or A-Z
     * @param curChar specifies the current character to check if it is a letter
     * @return returns True if curChar is a letter; false otherwise
     */
    public static boolean isLetter(char curChar) 
    {
        return (curChar >= 'a' && curChar <= 'z') || (curChar >= 'A' && curChar <= 'Z');
    }

    /**
     * Checks if curChar is a whitespace, defined by one of the following characters: 
     * [' ', '\t', '\r', '\n']
     * @param curChar specifies the current character to check if it is a whitespace
     * @return returns true if curChar is a whitespace; false otherwise
     */
    public static boolean isWhiteSpace(char curChar) 
    {
        return " \t\r\n".contains(String.valueOf(curChar));
    }

    /**
     * Scans the input for a number using the following regular expression string: digit(digit)*
     * where a digit is defined by the set of characters from 0-9
     * @return the scanned number
     * @throws ScanErrorException if an error occurs while scanning the input
     */
    private String scanNumber() throws ScanErrorException 
    {
        String curToken = "" + currentChar;
        eat(currentChar);

        while(isDigit(currentChar)) 
        {
            curToken += currentChar;
            eat(currentChar);
        }

        return curToken;
    }

    /**
     * Scans the input for an identifier using the following regular expression string: 
     * letter (letter | digit)*
     * where a letter is defined by the set of letters from a-z or A-Z
     * and a digit is defined by the regex of [0-9]
     * @return the scanned identifier
     * @throws ScanErrorException if an error occurs while scanning the input
     */
    private String scanIdentifier() throws ScanErrorException 
    {
        String curToken = "" + currentChar;
        eat(currentChar);

        while(isDigit(currentChar) || isLetter(currentChar)) 
        {
            curToken += currentChar;
            eat(currentChar);
        }

        return curToken;
    }

    /**
     * Scans the input for one of the following operands:
     *  ":=", 
     *  "<>", ">=", "<=", "=", 
     *  "+", "-", "*", "/", "%", 
     *  "(", ")"
     * @return the scanned operand
     * @throws ScanErrorException if the currentChar does not match the regex or if an error 
     * occurs while scanning the input
     */
    private String scanOperand() throws ScanErrorException 
    {
        String curToken = String.valueOf(currentChar);
        String initToken = curToken;

        if(validDoubleInits.contains(String.valueOf(currentChar))) 
        {
            eat(currentChar);
            curToken += currentChar;
            for (String op : VALID_OPERANDS) 
            {
                if(op.equals(curToken)) 
                {
                    eat(currentChar);
                    return curToken;
                }
            }
        }
        
        if(validSingles.contains(initToken)) 
        {
            if(validDoubleInits.contains(String.valueOf(initToken))) 
            {
                return initToken;
            }
            else
            {
                eat(currentChar);
                return curToken;
            }
        }
        else 
        {
            throw new ScanErrorException("No lexeme recognized.");
        }
    }

    /**
     * Initializes the validSingles and validDoubleInits used to scan operands
     * in the scanOperands method
     */
    private void initOperandHelpers() 
    {
        validSingles = new ArrayList<String>();
        validDoubleInits = new ArrayList<String>();
        for(String operand : VALID_OPERANDS)
        {
            if(operand.length() == 1) 
            {
                validSingles.add(operand);
            }
            if(operand.length() == 2)
            {
                if(!validDoubleInits.contains(String.valueOf(operand.charAt(1))))
                {
                    validDoubleInits.add(String.valueOf(operand.charAt(0)));
                }
            }
        }
    }
}
