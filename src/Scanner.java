
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * @author Arjun Dixit
 *  
 * Usage:
 * <Insert a comment that shows how to use this object>
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

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
    }

    public String nextToken() throws ScanErrorException {
        String token;

        if(eof) {
            token = "END";
        }
        else {
            while(isWhiteSpace(currentChar)) {
                eat(currentChar);
            }

            if(isDigit(currentChar)) {
                token = scanNumber();
            }
            else if(isLetter(currentChar)) {
                token = scanIdentifier();
            }
            else {
                token = scanOperand();
            }
        }
        return token;
    }

    private void getNextChar() {
        int curCharInt;

        try {
            curCharInt = in.read();

            if (curCharInt == -1) {
                eof = true;
            }
            else {
                currentChar = (char) curCharInt;
            }
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    } 

    public boolean hasNext() {
        return !eof;
    }

    private void eat(char curChar) throws ScanErrorException {
        if (currentChar == curChar) {
            getNextChar();
        }
        else {
            throw new ScanErrorException("Illegal character - expected \'" + currentChar + "\' and found \'" + curChar + "\'.");
        }
    }

    public static boolean isDigit(char curChar) {
        return curChar >= '0' && curChar <= '9';
    }

    public static boolean isLetter(char curChar) {
        return (curChar >= 'a' && curChar <= 'z') || (curChar >= 'A' && curChar <= 'Z');
    }

    public static boolean isWhiteSpace(char curChar) {
        return " \t\r\n".contains(String.valueOf(curChar));
    }

    private String scanNumber() throws ScanErrorException {
        String curToken = "" + currentChar;
        eat(currentChar);

        while(isDigit(currentChar)) {
            curToken += currentChar;
            eat(currentChar);
        }

        return curToken;
    }

    private String scanIdentifier() throws ScanErrorException {
        String curToken = "" + currentChar;
        eat(currentChar);

        while(isDigit(currentChar) || isLetter(currentChar)) {
            curToken += currentChar;
            eat(currentChar);
        }

        return curToken;
    }

    private String scanOperand() throws ScanErrorException {
        String curToken = "" + currentChar;

        if (!"=+-*/%()".contains(String.valueOf(currentChar))) {
            throw new ScanErrorException("No lexeme recognized.");
        }
        else {
            eat(currentChar);
        }

        return curToken;
    }
}
