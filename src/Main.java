import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import ast.Environment;
import ast.Program;
import parser.Parser;
import scanner.Scanner;

/**
 * Main class used for testing the compiler.
 * @author Arjun Dixit
 * @version 3/9/2022
 */
public class Main 
{
    public static final String INPUT_DIR = "testFiles/";

    /**
     * Main method for running the scanner and parser on a given input file.
     * @param args specifies the arguments for the program
     * @throws Exception if an error occurs while scanning or parsing the input file
     */
    public static void main(String[] args) throws Exception 
    {
        String fName = "test.txt";

        InputStream inStream = new FileInputStream(new File(INPUT_DIR + fName));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        Environment env = new Environment();
        Program program = parser.parseProgram();

        program.exec(env);
    }
}
