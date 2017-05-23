import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.stanford.nlp.simple.Sentence;
import org.jsoup.*;

public class Document {
    private String name;
    private String fileContent;
    private List<Statement> statements;

    public Document(File f) {
        name = f.getName();
        try {
            // Plaintext version of what you see when you visit the page (including links, headers, etc.)
            fileContent = Jsoup.parse(new String(Files.readAllBytes(Paths.get(f.getPath())))).text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Strip punctuation then split on whitespace.
//        words = fileContent.replaceAll("\\p{Punct}", "").split("\\s+");
        statements = new ArrayList<>();
        edu.stanford.nlp.simple.Document doc = new edu.stanford.nlp.simple.Document(fileContent);
        for (Sentence s : doc.sentences()) {
            Statement statement = new Statement(s);
            if (statement.getSubject() != null && !statement.getObject().isEmpty()) {
                System.out.println(statement);
                statements.add(statement);
            }
        }
    }


    public Optional<Statement> checkStatement(String query) {
        Statement queryStatement = new Statement(new Sentence(query));
//        System.out.println("Original: " + queryStatement.getOriginal());
//        System.out.println("Subject: " + queryStatement.getSubject());
//        System.out.println("Verb: " + queryStatement.getVerb());
//        System.out.println("Object: " + queryStatement.getObject());
        int i = statements.indexOf(queryStatement);
        if (i >= 0) {
            return Optional.of(statements.get(i));
        }
        return Optional.empty();
    }

    public String getName() {
        return name;
    }
}
