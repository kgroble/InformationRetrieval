import java.util.*;
import org.jsoup.*;

public class Main {
    public static void main(String[] args) {
        // Modify as needed if your source directory is elsewhere. Can always use an absolute path.
        DocumentSet docs = new DocumentSet("Presidents/");

		Scanner inputScanner = new Scanner(System.in);
		System.out.println("Enter a query, or \"q\" (no quotes) to quit.");
		System.out.print("> ");

		// Documents have punctuation removed, so queries do too.
		String cmd = inputScanner.nextLine().trim().replaceAll("\\p{Punct}", "");

		while (!cmd.equals("q")) {
	        List<ScoredDoc> scores = docs.score(cmd);
	        scores.sort((d1, d2) -> ((d1.score - d2.score) < 0) ? 1 : -1);
			double maxScore = scores.get(0).score;

			// Print at most 10 results and their normalized scores. Stop if scores get non-positive.
	        for (int i = 0; i < scores.size() && i < 10 && scores.get(i).score > 0; i++) {
	        	String outLine = String.format("%d. %s (%1.3f)", i+1, scores.get(i).doc.getName(), scores.get(i).score/maxScore);
	            System.out.println(outLine);
	        }

			System.out.print("\n> ");
			cmd = inputScanner.nextLine().trim().replaceAll("\\p{Punct}", "");
    	}
    }
}
