/**
 * Author: Andrew Laing
 * Email:  parisianconnections@gmail.com
 * Date:   26/12/2016.
 */

import java.io.*;
import java.util.*;

public class DocumentClassifier
{
    public static void main(String[] args) throws IOException
    {
        ////////////////////////////////////////////////////////////////
        // TRAIN THE CLASSIFIER ////////////////////////////////////////
        ////////////////////////////////////////////////////////////////

        List nGrams;
        String filename = "", category = "";

        // Initialise the minimum and maximum sizes of the nGrams
        Utilities.initialiseNGramMakerMinMax(2, 5);

        // Create a NaiveBayes classifier
        NaiveBayes nb = new NaiveBayes(0.3, 0.2);

        for(int i=0; i<3; i++) {
            // Create a new ArrayList to store the nGrams in
            nGrams = new ArrayList();

            // get the name of the file
            filename = Utilities.getOpenFilename();

            // Create nGrams from the contents of the text file and
            // add them to the nGrams List
            NGramMaker.getNGramsFromFile(nGrams, filename);

            category = Utilities.getCategory();

            nb.train(nGrams, category);
        }


        ////////////////////////////////////////////////////////////////
        // CLASSIFY A DOCUMENT /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////

        List<String> testDoc = new ArrayList<String>();

        filename = Utilities.getOpenFilename();

        // Create nGrams from the contents of the test file and
        // add them to the testDoc List
        NGramMaker.getNGramsFromFile(testDoc, filename);

        // Classify the document
        System.out.println(filename + " is classified as " + nb.classify(testDoc));

    }
}
