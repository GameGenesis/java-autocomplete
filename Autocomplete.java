import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Autocomplete {

    public final static Path filePath = Path.of("data.txt");
    public static String data = "";
    public static String prompt = "";
    public static int contextWords = 4; // The number of words to look for (as context for the next word)
    public static int topSuggestions = 3; // The number of top suggestions to pick a random suggestion from
    public static int wordsToComplete = 100; // The number of words to autocomplete

    private static Random randomGenerator;

    public static void main(String[] args) {
        data = getData(filePath).toLowerCase();

        randomGenerator = new Random();

        prompt = Str.input("Enter text to autocomplete: ");

        for (int i = 0; i < wordsToComplete; i++) {
            prompt = completePrompt(prompt, data, contextWords);
        }

        Str.print(prompt);
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

    public static String completePrompt(String prompt, String data, int contextWords) {
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

                contextComplete.add(completeWord.strip());
            }
        }

        Map<String, Integer> frequencyMap = new HashMap<String, Integer>();

        for (int i = 0; i < contextComplete.size(); i++) {
            frequencyMap.put(contextComplete.get(i), Collections.frequency(contextComplete, contextComplete.get(i)));
        }

        // Map.Entry<String, Integer> maxEntry = null;

        // for (Map.Entry<String, Integer> entry : frequencyMap.entrySet())
        // {
        //     if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
        //     {
        //         maxEntry = entry;
        //     }
        // }

        List<String> keys = frequencyMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(topSuggestions).map(Map.Entry::getKey).collect(Collectors.toList());
        String nextEntry = keys.size() > 0 ? keys.get(randomGenerator.nextInt(keys.size())) : "";

        if (nextEntry.isBlank() && contextWords > 2) {
            return completePrompt(prompt, data, contextWords - 1);
        }
        else if (nextEntry.isBlank() && contextWords <= 2) {
            return String.join(" ", prompt, "and");
        }
        else {
            return String.join(" ", prompt, nextEntry);
        }
    }
}