import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import ast.Environment;
import ast.Program;
import emitter.Emitter;
import parser.Parser;
import scanner.Scanner;

/**
 * Main class used for testing the compiler.
 * @author Arjun Dixit
 * @version 3/9/2022
 */
public class Main 
{
    /**
     * Directory for testing files 
     */
    public static final String INPUT_DIR = "testFiles/";

    /**
     * Main method for running the scanner and parser on a given input file.
     * @param args specifies the arguments for the program
     * @throws Exception if an error occurs while scanning or parsing the input file
     */
    public static void main(String[] args) throws Exception 
    {
        String inputName = "parserTest9.txt";
        String outputName = "out.asm";

        InputStream inStream = new FileInputStream(new File(INPUT_DIR + inputName));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        Program program = parser.parseProgram();

        // execute program in Java
        program.exec(new Environment());

        // emit compiled assembly code
        Emitter e = new Emitter(outputName);
        program.compile(e);
    }
}
