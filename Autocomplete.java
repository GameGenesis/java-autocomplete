import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Autocomplete {

    public final static Path filePath = Path.of("data.txt");
    public static String data = "";
    public static String prompt = "";
    public static int contextWords = 2;

    public static void main(String[] args) {
        data = getData(filePath).toLowerCase();
        prompt = Str.input("Enter text to autocomplete: ");
        String promptWords[] = prompt.split(" ");
        String context = promptWords[promptWords.length - contextWords];
        for (int i = contextWords - 1; i > 0; i--) {
            context = String.join(" ", context, promptWords[promptWords.length - i]);
        }
        context = (context + " ").toLowerCase();
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