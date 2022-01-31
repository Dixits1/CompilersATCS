import java.io.File;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        FileInputStream inStream = new FileInputStream(new File("ScannerTest.txt"));
        Scanner scanner = new Scanner(inStream);

        while(scanner.hasNext()) {
            System.out.println(scanner.nextToken());
        }
    }
}
