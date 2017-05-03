import java.util.*;

public class Main {
    public static void main(String[] args) {
        DocumentSet docs = new DocumentSet("Presidents/");
        List<ScoredDoc> scores = docs.score("nobel prize");
        scores.sort((d1, d2) -> ((d1.score - d2.score) < 0) ? 1 : -1);

        for (int i = 0; i < scores.size() && i < 10; i++) {
            if (scores.get(i).score <= 0) {
                break;
            }
            System.out.println(i+1 + ". " + scores.get(i).doc.getName() + ": " + scores.get(i).score);
        }
    }
}
