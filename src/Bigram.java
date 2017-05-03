public class Bigram {
    public String w1;
    public String w2;

    public Bigram(String w1, String w2) {
        this.w1 = w1;
        this.w2 = w2;
    }

    public boolean equals(Object o) {
        if (o instanceof Bigram) {
            return ((Bigram) o).w1.equals(this.w1)
                    && ((Bigram) o).w2.equals(this.w2);
        }
        return false;
    }

    public int hashCode() {
        int hash = 13;
        hash = 23 * 13 + w1.hashCode();
        hash = 23 * 13 + w2.hashCode();
        return hash;
    }

}
