import java.util.*;

public class StupidBackoffLanguageModel implements LanguageModel {

    /**
     * Initialize your data structures in the constructor.
     */
    protected Map<String, Integer> words; // set of words that occur in training
    protected Map<ArrayList<String>, Integer> biWords; // set of words that occur in training
    protected int wordsSize;
    protected int biWordsSize;

    public StupidBackoffLanguageModel(HolbrookCorpus corpus) {
        words = new HashMap<>();
        biWords = new HashMap<>();
        train(corpus);
    }

    /**
     * Takes a corpus and trains your language model.
     * Compute any counts or other corpus statistics in this function.
     */
    public void train(HolbrookCorpus corpus) {
        // TODO: your code here
        for (Sentence sentence : corpus.getData()) { // iterate over sentences
            for (Datum datum : sentence) { // iterate over words
                String word = datum.getWord(); // get the actual word
                if (words.containsKey(word)) {
                    words.replace(word, words.get(word) + 1);
                } else words.put(word, 2);
            }
            for (int i = 0; i < sentence.size() - 1; i++) { // iterate over words
                ArrayList<String> biWords = new ArrayList<>();
                biWords.add(sentence.get(i).getWord());
                biWords.add(sentence.get(i + 1).getWord());
                if (this.biWords.containsKey(biWords)) {
                    this.biWords.replace(biWords, this.biWords.get(biWords) + 1);
                } else this.biWords.put(biWords, 1);
            }
        }
        for (int i : words.values()) {
            wordsSize += i;
        }
        for (int i : biWords.values()) {
            biWordsSize += i;
        }
    }

    /**
     * Takes a list of strings as argument and returns the log-probability of the
     * sentence using your language model. Use whatever data you computed in train() here.
     */
    public double score(List<String> sentence) {
        // TODO: your code here
        double score = 0.0;
        int word1Count;
        int biWordCount;
        for (int i = 0; i < sentence.size() - 1; i++) { // iterate over words in the sentence
            String word1 = sentence.get(i);
            String word2 = sentence.get(i + 1);
            ArrayList<String> biWords = new ArrayList<>();
            biWords.add(word1);
            biWords.add(word2);
            word1Count = this.words.get(word1) == null ? 1 : words.get(word1);
            biWordCount = this.biWords.get(biWords) == null ? 0 : this.biWords.get(biWords);

            score += Math.log(((double) biWordCount + 1.0) / ((double) word1Count + (double) wordsSize)); // wordsSize - is our V
        }
        // NOTE: a simpler method would be just score = sentence.size() * - Math.log(words.size()).
        // we show the 'for' loop for insructive purposes.
        return score;
    }

    public static void main(String[] args) {
        String trainPath = "pa2-autocorrect-v1\\data\\holbrook-tagged-train.dat";
        HolbrookCorpus trainingCorpus = new HolbrookCorpus(trainPath);

        String devPath = "pa2-autocorrect-v1\\data\\holbrook-tagged-dev.dat";
        HolbrookCorpus devCorpus = new HolbrookCorpus(devPath);
        String fileName = "pa2-autocorrect-v1\\data\\count_1edit.txt";

        System.out.println("Stupid Backoff Language Model: ");
        StupidBackoffLanguageModel sbLM = new StupidBackoffLanguageModel(trainingCorpus);
        SpellCorrect sbSpell = new SpellCorrect(sbLM, trainingCorpus, fileName);
        SpellingResult sbOutcome = sbSpell.evaluate(devCorpus);
        System.out.println(sbOutcome.toString());
    }
}
