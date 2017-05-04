import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.*;

public class Document {
    private String name;
    private String fileContent;
    private String[] words;
    private Map<String, Integer> wordCounts;

    public Document(File f) {
        name = f.getName();
        try {
            fileContent = Jsoup.parse(new String(Files.readAllBytes(Paths.get(f.getPath())))).text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        words = fileContent.split("\\s+");
        wordCounts = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase();
            if (wordCounts.containsKey(word)) {
                wordCounts.put(word, wordCounts.get(word) + 1);
            } else {
                wordCounts.put(word, 1);
            }
        }
    }

    public int wordOccurences(String word) {
        return wordCounts.getOrDefault(word.toLowerCase(), 0);
    }

    public double wordFrequency(String word) {
        return (double) wordOccurences(word) / (double) words.length;
    }

    public String getName() {
        return name;
    }

    public boolean containsWord(String word) {
        return wordCounts.containsKey(word.toLowerCase());
    }

    public int numWords() {
        return words.length;
    }
}
