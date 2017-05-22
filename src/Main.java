import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.jsoup.*;
import edu.stanford.nlp.simple.*;

public class Main {
    public static void main(String[] args) {
        parseHTML(new File("Presidents/Lincoln.txt"), new File("Parsed/Lincoln.txt"));
        // Modify as needed if your source directory is elsewhere. Can always use an absolute path.
        Document doc = new Document(new File("Parsed/Lincoln.txt"));

		Scanner inputScanner = new Scanner(System.in);
		System.out.println("Enter a statement, or \"q\" (no quotes) to quit.");
		System.out.print("> ");

		// Documents have punctuation removed, so queries do too.
		String cmd = inputScanner.nextLine().trim();

		while (!cmd.equals("q")) {
			Optional<Statement> found = doc.checkStatement(cmd);
			if (found.isPresent()) {
				System.out.println("Statement verified. Original source: " + found.get().getOriginal());
			} else {
				System.out.println("Statement could not be verified.");
			}
			System.out.print("\n> ");
			cmd = inputScanner.nextLine().trim();
    	}

//        Sentence foo = new Sentence("Coleman ate some pizza.");
//        for(int i=0; i < 4; i++) {
//            System.out.println(foo.incomingDependencyLabel(i).orElse(null));
//        }
//        System.out.println(foo.parse());

    }

    public static void parseHTML(File source, File dest) {
		try {
			String fileContent = Jsoup.parse(new String(Files.readAllBytes(Paths.get(source.getPath())))).text().replaceAll("\\[[\\d]+\\]", "");
			PrintWriter out = new PrintWriter(dest);
			out.println(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
