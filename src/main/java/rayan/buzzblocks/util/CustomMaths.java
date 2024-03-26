package rayan.buzzblocks.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomMaths {

    public static List<Double> preCalculateCumulativeProbabilities(List<Double> probabilities){
        // Calculate cumulative probabilities
        List<Double> cumulativeProbabilities = new ArrayList<>();
        double cumulativeProbability = 0;
        for (double probability : probabilities) {
            cumulativeProbability += probability;
            cumulativeProbabilities.add(cumulativeProbability);
        }
        return cumulativeProbabilities;
    }
    public static int weightedRandomSelection(List<Double> cumulativeProbabilities) {
        // Generate a random number between 0 and 1
        Random random = new Random();
        double rand = random.nextDouble();

        // Find the index of the first cumulative probability greater than the random number
        for (int i = 0; i < cumulativeProbabilities.size(); i++) {
            if (rand < cumulativeProbabilities.get(i)) {
                return i;
            }
        }

        // Should not reach here
        throw new IllegalStateException("Weighted random selection failed.");
    }
}
