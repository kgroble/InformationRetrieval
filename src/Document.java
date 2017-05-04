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
    private Map<Bigram, Integer> bigramCounts;

    public Document(File f) {
        name = f.getName();
        try {
            fileContent = Jsoup.parse(new String(Files.readAllBytes(Paths.get(f.getPath())))).text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        words = fileContent.split("\\s+");
        wordCounts = new HashMap<>();
        bigramCounts = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (wordCounts.containsKey(word)) {
                wordCounts.put(word, wordCounts.get(word) + 1);
            } else {
                wordCounts.put(word, 1);
            }

            if (i < words.length - 1) {
                String w1 = word;
                String w2 = words[i+1].toLowerCase();
                Bigram b = new Bigram(w1, w2);
                if (bigramCounts.containsKey(b)) {
                    bigramCounts.put(b, bigramCounts.get(b) + 1);
                } else {
                    bigramCounts.put(b, 1);
                }
            }
        }
    }

    public int wordOccurrences(String word) {
        return wordCounts.getOrDefault(word.toLowerCase(), 0);
    }

    public int bigramOccurrences(String w1, String w2) {
        w1 = w1.toLowerCase();
        w2 = w2.toLowerCase();
        return bigramCounts.getOrDefault(new Bigram(w1, w2), 0);
    }

    public double wordFrequency(String word) {
        return (double) wordOccurrences(word) / (double) words.length;
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

    public int numBigrams() {
        return words.length - 1;
    }
}
