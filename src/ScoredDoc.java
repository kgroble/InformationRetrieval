// Wrapper for a document, score pair.
public class ScoredDoc {
    public Document doc;
    public double score;

    public ScoredDoc(Document doc, double score) {
        this.doc = doc;
        this.score = score;
    }
}
