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

    public List<ScoredDoc> score(String query) {
        List<ScoredDoc> ret = new ArrayList<>();
//        Map<Document, Double> scores = bm25(query);
        Map<Document, Double> scores = bigrams(query);
        for (Document d : scores.keySet()) {
            ret.add(new ScoredDoc(d, scores.get(d)));
        }

        return ret;
    }

    private Map<Document, Double> bm25(String query) {
        Map<Document, Double> scores = new HashMap<>();
        String[] words = query.split(" ");
        for (Document d : documents) {
            scores.put(d, 0.0);
        }

        for (String word : words) {
            double idf = inverseDocumentFrequency(word);
            for (Document d : documents) {
                double frequency = d.wordFrequency(word);
                double score = idf * ((frequency * (K1 + 1)) / (frequency + (K1 * (1 - B + (B * (d.numWords() / averageLength))))));
                scores.put(d, scores.get(d) + score);
            }
        }
        return scores;
    }

    private Map<Document, Double> bigrams(String query) {
        Map<Document, Double> scores = new HashMap<>();
        String[] words = query.split(" ");
        for (Document d : documents) {
            scores.put(d, 0.0);
        }

        for (Document d : documents) {
            int hits = 0;
            for (String w1 : words) {
                for (String w2 : words) {
                    hits += d.bigramOccurrences(w1, w2);
                }
            }
            scores.put(d, ((double) hits) / ((double) d.numBigrams()));
        }
        return scores;
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
            System.out.println(doc.getName() + ": " + doc.wordOccurrences(word));
        }
    }
}
