import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentSet {
    private static final double K1 = 1.2;
    private static final double B = .75;

    private List<Document> documents;
    private int averageLength;

    public DocumentSet(String directory) {
        File file = new File(directory);
        documents = new ArrayList<>();
        for (File f : file.listFiles()) {
            documents.add(new Document(f));
        }

        int totalLength = 0;
        for (Document d : documents) {
            totalLength += d.numWords();
        }
        averageLength = totalLength / documents.size();
    }

    public List<ScoredDoc> bm25(String query) {
        String[] words = query.split(" ");
        Map<Document, Double> scores = new HashMap<>();

        for (Document d : documents) {
            scores.put(d, 0.0);
        }

        for (String word : words) {
            double idf = inverseDocumentFrequency(word);
            for (Document d : documents) {
                double frequency = d.wordFrequency(word);
                double score = (frequency * (K1 + 1)) / (frequency + (K1 * (1 - B + (B * (d.numWords() / averageLength)))));
                scores.put(d, scores.get(d) + score);
            }
        }
        List<ScoredDoc> ret = new ArrayList<>();
        for (Document d : scores.keySet()) {
            ret.add(new ScoredDoc(d, scores.get(d)));
        }

        return ret;
    }

    private double inverseDocumentFrequency(String word) {
        int numContaining = 0;
        for (Document d : documents) {
            if (d.containsWord(word)) {
                numContaining++;
            }
        }
        return Math.log10((documents.size() - numContaining + .5)/(numContaining + .5));
    }

    public void printOccurences(String word) {
        for (Document doc : documents) {
            System.out.println(doc.getName() + ": " + doc.wordOccurences(word));
        }
    }
}
