import edu.stanford.nlp.simple.Sentence;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by kieran on 5/22/17.
 */
public class Statement {
    private Sentence sentence;
    private String subject;
    private String verb;
    private String object;
    private String original;

    public String getSubject() {
        return subject;
    }

    public String getVerb() {
        return verb;
    }

    public String getObject() {
        return object;
    }

    public String getOriginal() {
        return original;
    }

    public Statement(Sentence s) {
        this.sentence = s;
        this.original =  String.join(" ", sentence.originalTexts());
        for (int i = 0; i < s.length(); i++) {
            Optional<String> label = s.incomingDependencyLabel(i);
            if (label.isPresent()) {
               String word = label.get();
               if (word.equals("nsubj")) {
                   this.subject = s.word(i).toLowerCase();
               } else if (word.equals("root")) {
                   this.verb = s.word(i).toLowerCase();
               } else if (word.equals("dobj")) {
                   this.object = s.word(i).toLowerCase();
               }
            }
        }
    }

    public String toString() {
        return String.format("Original: %s\nSubject: %s\nVerb: %s\nObject: %s\n", original, subject, verb, object);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Statement)) {
            return false;
        }

        Statement other = (Statement) o;
        boolean subjEqual = (this.subject == null && other.subject == null) || (this.subject != null && other.subject != null && this.subject.equals(other.subject));
        boolean verbEqual = (this.verb == null && other.verb == null) || (this.verb != null && other.verb != null && this.verb.equals(other.verb));
        boolean objectEqual = (this.object == null && other.object == null) || (this.object != null && other.object != null && this.object.equals(other.object));
        return subjEqual && verbEqual && objectEqual;
    }
}
