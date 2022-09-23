import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        
        int index = data.indexOf(context);
        int dataLength = data.length();

        String complete = "";
        String completeWord = "";
        int wordIndex = 0;

        List<Integer> contextIndex = new ArrayList<>();
        List<String> contextComplete = new ArrayList<>();

        while (index >= 0) {
            contextIndex.add(index);
            index = data.indexOf(context, index + 1);

            int beginIndex = index + context.length();
            int endIndex = index + context.length() + 20;

            if (index >= 0 && endIndex <= dataLength) {
                complete = data.substring(beginIndex, endIndex);
                wordIndex = 0;
                completeWord = complete.split(" ")[wordIndex];
                
                while (Pattern.matches("\\p{Punct}", completeWord)) {
                    wordIndex++;
                    completeWord = complete.split(" ")[wordIndex];
                }

                contextComplete.add(completeWord);
            }
        }

        Map<String, Integer> frequencyMap = new HashMap<String, Integer>();

        for (int i = 0; i < contextComplete.size(); i++) {
            frequencyMap.put(contextComplete.get(i), Collections.frequency(contextComplete, contextComplete.get(i)));
        }

        Str.print(frequencyMap.toString());
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