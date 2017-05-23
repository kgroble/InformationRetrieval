import edu.stanford.nlp.simple.Sentence;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Statement {
    private Sentence sentence;
    private String subject;
    private String verb;
    private Set<String> objects;
    private String original;

    public String getSubject() {
        return subject;
    }

    public String getVerb() {
        return verb;
    }

    public Set<String> getObject() {
        return objects;
    }

    public String getOriginal() {
        return original;
    }

    public Statement(Sentence s) {
        this.objects = new HashSet<>();
        int root = -1;
        this.sentence = s;
        this.original = sentence.text();
        for (int i = 0; i < s.length(); i++) {
            Optional<String> label = s.incomingDependencyLabel(i);
            if (label.isPresent()) {
                String depLabel = label.get();
                if (depLabel.equals("root")) {
                    Optional<Integer> index = s.governor(i);
                    if (index.isPresent() && index.get() == -1) {
                        this.verb = s.word(i).toLowerCase();
                        root = i;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < s.length(); i++) {
            Optional<String> label = s.incomingDependencyLabel(i);
            if (label.isPresent()) {
               String depLabel = label.get();
               if (depLabel.equals("nsubj")) {
                   if (this.subject == null) {
                       Optional<Integer> index = s.governor(i);
                       if (index.isPresent() && index.get() == root) {
                           this.subject = s.word(i).toLowerCase();
                       }
                   }
               } else if (!depLabel.equals("punct") && !depLabel.equals("cc")){
                   Optional<Integer> index = s.governor(i);
                   if (index.isPresent() && index.get() == root) {
                       this.objects.add(s.word(i).toLowerCase());
                   }
               }
            }
        }
    }

    public String toString() {
        return String.format("Original: %s\nSubject: %s\nVerb: %s\nObjects: %s\n", original, subject, verb, String.join(", ", objects));
    }

    public boolean equals(Object o) {
        if (!(o instanceof Statement)) {
            return false;
        }

        Statement other = (Statement) o;
        boolean subjEqual = (this.subject == null && other.subject == null) || (this.subject != null && other.subject != null && this.subject.equals(other.subject));
        boolean verbEqual = (this.verb == null && other.verb == null) || (this.verb != null && other.verb != null && this.verb.equals(other.verb));
        Set<String> intersection = new HashSet(this.objects);
        intersection.retainAll(other.objects);
        boolean objectEqual = !intersection.isEmpty();
        return subjEqual && verbEqual && objectEqual;
    }
}
