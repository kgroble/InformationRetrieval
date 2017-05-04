import java.util.*;
import org.jsoup.*;

public class Main {
    public static void main(String[] args) {
        DocumentSet docs = new DocumentSet("Presidents/");

		Scanner inputScanner = new Scanner(System.in);
		System.out.print("> ");
		String cmd = inputScanner.nextLine().trim();

		while(!cmd.equals("q")){
	        List<ScoredDoc> scores = docs.score(cmd);
	        scores.sort((d1, d2) -> ((d1.score - d2.score) < 0) ? 1 : -1);
			double maxScore = scores.get(0).score;
	        for (int i = 0; i < scores.size() && i < 10 && scores.get(i).score > 0; i++) {
	        	String outLine = String.format("%d. %s (%1.3f)", i+1, scores.get(i).doc.getName(), scores.get(i).score/maxScore);
	            System.out.println(outLine);
	        }

			System.out.print("> ");
			cmd = inputScanner.nextLine().trim();
    	}
    }
}
