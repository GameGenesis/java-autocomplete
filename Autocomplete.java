import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Autocomplete {

    public final static Path filePath = Path.of("data.txt");
    public static String data = "";

    public static void main(String[] args) {
        data = getData(filePath).toLowerCase();
    }

    public static String getData(Path filePath) {
        String inputString = "";

        try {
            inputString = Files.readString(filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return inputString;
    }

    
}