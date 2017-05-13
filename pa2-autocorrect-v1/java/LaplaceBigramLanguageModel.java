import java.util.*;

public class LaplaceBigramLanguageModel implements LanguageModel {

    /**
     * Initialize your data structures in the constructor.
     */
    protected Map<String, Integer> words; // set of words that occur in training
    protected int wordsSize;

    public LaplaceBigramLanguageModel(HolbrookCorpus corpus) {
        words = new HashMap<>();
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
        }
        for (int i : words.values()) {
            wordsSize += i;
        }
    }

    /**
     * Takes a list of strings as argument and returns the log-probability of the
     * sentence using your language model. Use whatever data you computed in train() here.
     */
    public double score(List<String> sentence) {
        // TODO: your code here
        double score = 0.0;
//        double probability = Math.log(1.0 / words.size()); // uniform log-probability of log(1/V)
        int wordCount;
        for (String word : sentence) { // iterate over words in the sentence
            wordCount = words.get(word) == null ? 1 : words.get(word);
            score += Math.log((double) wordCount / (double) wordsSize);
        }
        // NOTE: a simpler method would be just score = sentence.size() * - Math.log(words.size()).
        // we show the 'for' loop for insructive purposes.
        return score;
    }
}
