import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import scanner.Scanner;

public class Main 
{
    public static final String INPUT_DIR = "testFiles/";

    public static void main(String[] args) throws Exception 
    {
        String fName = "ScannerTest.txt";
        // String fName = "scannerTestAdvanced.txt";

        InputStream inStream = new FileInputStream(new File(INPUT_DIR + fName));
        Scanner scanner = new Scanner(inStream);
        String next = "";
        while(!"END".equals(next)) 
        {
            next = scanner.nextToken();
            System.out.println(next);
        }
    }
}
