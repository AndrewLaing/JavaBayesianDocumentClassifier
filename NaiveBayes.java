/**
 * Author: Andrew Laing
 * Email:  parisianconnections@gmail.com
 * Date:   26/12/2016.
 */

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class NaiveBayes implements Serializable
{
    private HashMap2D featureCount;
    private HashMap1D categoryCount;

    private double weight;
    private double assumedProb;

    private String defaultCategory;

    //////////////////////////////////////////////////////////////////////////////////
    // Notes: It is best to use Constructor #1 or Constructor #3
    //        The weight and assumed probability are used to fine tune the classifier.
    //        The default category is returned when one cannot yet be calculated
    //////////////////////////////////////////////////////////////////////////////////

    /**
     * No-args constructor
     */
    public NaiveBayes()
    {
        featureCount = new HashMap2D();
        categoryCount = new HashMap1D();
        this.weight = 1.0;
        this.assumedProb = 1.0;
        this.defaultCategory = "Unclassified";
    }

    /**
     * Constructor #1
     * @param weight A weight
     * @param assumedProb An assumed probability
     */
    public NaiveBayes(double weight, double assumedProb)
    {
        featureCount = new HashMap2D();
        categoryCount = new HashMap1D();
        this.weight = weight;
        this.assumedProb = assumedProb;
        this.defaultCategory = "Unclassified";
    }


    /**
     * Constructor #2
     * @param defaultCategory Default category to return
     */
    public NaiveBayes(String defaultCategory)
    {
        featureCount = new HashMap2D();
        categoryCount = new HashMap1D();
        this.weight = 1.0;
        this.assumedProb = 1.0;
        this.defaultCategory = defaultCategory;
    }


    /**
     * Constructor #3
     * @param weight A weight
     * @param assumedProb An assumed probability
     * @param defaultCategory Default category to return
     */
    public NaiveBayes(double weight, double assumedProb, String defaultCategory)
    {
        featureCount = new HashMap2D();
        categoryCount = new HashMap1D();
        this.weight = weight;
        this.assumedProb = assumedProb;
        this.defaultCategory = defaultCategory;
    }


    // Set methods are provided so the classifier can be tweaked during running
    public void setAssumedProb(double assumedProb) {
        this.assumedProb = assumedProb;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDefaultCategory(String defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    /**
     * The method incrementFC increases the count in featureCount of a
     * feature/category pair.
     * @param feature A feature
     * @param category A category
     */
    private void incrementFC(String feature, String category)
    {
        featureCount.augment(feature, category);
    }


    /**
     * The method incrementCC increases the count in categoryCount of a category
     * @param category A category
     */
    private void incrementCC(String category)
    {
        categoryCount.augment(category);
    }


    /**
     * The method FCount returns the number of times that a feature has appeared
     * in a category from featureCount.
     * @param feature A feature
     * @param category A category
     * @return The number of times that a feature has appeared  in a category from featureCount.
     */
    private double FCount(String feature, String category)
    {
        return featureCount.getValue(feature, category);
    }


    /**
     * The method CCount returns the number of times a category from categoryCount
     * has been added.
     * @param category A category
     * @return The number of times a category from categoryCount has been added.
     */
    private double CCount(String category)
    {
        return categoryCount.getElement(category);
    }


    /**
     * The method totalCount returns the number of times all categories from
     * categoryCount have been added.
     * @return The number of times all categories from categoryCount have been added.
     */
    private double totalCount()
    {
        return categoryCount.getSumOfValues();
    }


    /**
     * The method categories adds all of the categories stored in categoryCount to
     * the List passed to it.
     * @param cat A List to add all of the categories stored in categoryCount to.
     */
    private void categories(List<String> cat)
    {
        categoryCount.getKeys(cat);
    }


    /**
     * The method train trains the classifier by incrementing the counts in
     * categoryCount and featureCount.
     * @param features A list of features
     * @param category A category
     */
    public void train(List<String> features, String category)
    {
        for (String feature : features)
            incrementFC(feature,category);

        incrementCC(category);
    }


    /**
     * This overloaded version of the train method accepts a single feature and a category
     * @param feature A single feature
     * @param category A category
     */
    public void train(String feature, String category)
    {
        incrementFC(feature,category);
        incrementCC(category);
    }


    /**
     * The method featureProbability returns the probability of a feature appearing
     * in a category
     * @param feature A feature
     * @param category A category
     * @return The probability of a feature appearing in a category
     */
    private double featureProbability(String feature, String category)
    {
        if(CCount(category)==0.0) return 0.0;
        return FCount(feature, category) / CCount(category);
    }


    /**
     * The method weightedProbability returns the weighted probability of a feature
     * appearing in a category
     * @param feature A feature
     * @param category A category
     * @return The weighted probability of a feature appearing in a category
     */
    private double weightedProbability(String feature, String category)
    {
        // Calculate the current probability
        double basicProbability = featureProbability(feature, category);

        // Count the number of times this feature has appeared in all categories
        double total = featureCount.getSumOfAllValues(feature);

        // Return the weighted average
        return ((weight * assumedProb)+(total * basicProbability)) / (weight + total);
    }


    /**
     * The method documentProbability returns the probability of the category
     * existing in all features
     * @param features A List of features
     * @param category A category
     * @return The probability of the category existing in all features
     */
    private double documentProbability(List<String> features, String category)
    {
        double probability = 1.0;

        for(String feature : features)
            probability *= weightedProbability(feature, category);

        return probability;
    }


    /**
     * The method probability returns the calculated probability for the category
     * passed to it.
     *
     * @param features A List of features
     * @param category A category
     * @return The calculated probability for the category passed to the method
     */
    private double probability(List<String> features, String category)
    {
        double categoryProbability = CCount(category)/totalCount();
        double documentProbability = documentProbability(features, category);

        return categoryProbability * documentProbability;
    }


    /**
     * The method classify returns the most probable category based upon the
     * List of features passed.
     * @param features A List of features
     * @return The most probable category based upon the List of features passed
     * to the method.
     */
    public String classify(List<String> features)
    {
        List<String> cats = new ArrayList<String>();
        double max = 0.0;
        double catProbability = 0.0;
        String best = defaultCategory;

        // fill cats with the categories
        categories(cats);

        for (String category : cats )
        {
            catProbability = probability(features, category);
            if(catProbability > max) {
                max = catProbability;
                best = category;
            }
        }
        //System.out.println("Probability = " + max);
        return best;
    }
}
