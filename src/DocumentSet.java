import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentSet {
    // configurable constants
    private static final double K1 = 1.2;
    private static final double B = .75;
    private static final double EPSILON = .001;

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
        Map<Document, Double> bm25Scores = bm25(query);
        double maxBM25 = 0;
        for (double score : bm25Scores.values()) {
            if (score > maxBM25) {
                maxBM25 = score;
            }
        }
        if (maxBM25 == 0.0) {
            maxBM25 = 1;
        }
        Map<Document, Double> bigramScores = bigrams(query);
        double maxBigram = 0;
        for (double score : bigramScores.values()) {
            if (score > maxBigram) {
                maxBigram = score;
            }
        }
        if (maxBigram == 0.0) {
            maxBigram = 1;
        }
        boolean useBigram = query.contains(" ");
        for (Document d : bigramScores.keySet()) {
            double score = (bm25Scores.get(d) / maxBM25)
                    * ((useBigram ? bigramScores.get(d) : 1) / maxBigram);
            ret.add(new ScoredDoc(d, score));
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
            // Put a floor on this to prevent negative scores.
            double idf = Math.max(inverseDocumentFrequency(word), EPSILON);
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

}
