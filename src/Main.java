import java.util.*;
import org.jsoup.*;

public class Main {
    public static void main(String[] args) {
        DocumentSet docs = new DocumentSet("../Presidents/");

		Scanner inputScanner = new Scanner(System.in);
		System.out.print(">");
		String cmd = inputScanner.next();

		while(!cmd.equals("q")){

	        List<ScoredDoc> scores = docs.bm25(cmd);
	        scores.sort((d1, d2) -> ((d1.score - d2.score) < 0) ? -1 : 1);

	        for (int i = 0; i < scores.size(); i++) {
	            System.out.println(scores.get(i).doc.getName() + ": " + scores.get(i).score);
	        }

			System.out.print(">");
			cmd = inputScanner.next();
    	}
    }
}
