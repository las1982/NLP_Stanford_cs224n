import java.util.List;

public class CustomLanguageModel implements LanguageModel {

    /**
     * Initialize your data structures in the constructor.
     */
    public CustomLanguageModel(HolbrookCorpus corpus) {
        train(corpus);
    }

    /**
     * Takes a corpus and trains your language model.
     * Compute any counts or other corpus statistics in this function.
     */
    public void train(HolbrookCorpus corpus) {
        // TODO: your code here
    }

    /**
     * Takes a list of strings as argument and returns the log-probability of the
     * sentence using your language model. Use whatever data you computed in train() here.
     */
    public double score(List<String> sentence) {
        // TODO: your code here
        return 0.0;
    }

    public static void main(String[] args) {
        String trainPath = "pa2-autocorrect-v1\\data\\holbrook-tagged-train.dat";
        HolbrookCorpus trainingCorpus = new HolbrookCorpus(trainPath);

        String devPath = "pa2-autocorrect-v1\\data\\holbrook-tagged-dev.dat";
        HolbrookCorpus devCorpus = new HolbrookCorpus(devPath);
        String fileName = "pa2-autocorrect-v1\\data\\count_1edit.txt";

        System.out.println("Custom Language Model: ");
        CustomLanguageModel customLM = new CustomLanguageModel(trainingCorpus);
        SpellCorrect customSpell = new SpellCorrect(customLM, trainingCorpus, fileName);
        SpellingResult customOutcome = customSpell.evaluate(devCorpus);
        System.out.println(customOutcome.toString());
    }
}
