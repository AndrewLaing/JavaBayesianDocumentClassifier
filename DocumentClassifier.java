/**
 * Author:          Andrew Laing
 * Email:           parisianconnections@gmail.com
 * Last updated:    07/06/2019.
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

public class DocumentClassifier
{
    public static void trainClassifier(NaiveBayes nb) throws Exception
    {
        List nGrams;
        String filename = "", category = "";

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


    public static void classifyADocument(NaiveBayes nb) throws Exception
    {
        String filename = "", result = "";
        List<String> testDoc = new ArrayList<String>();

        filename = Utilities.getOpenFilename();

        // Create nGrams from the contents of the test file and
        // add them to the testDoc List
        NGramMaker.getNGramsFromFile(testDoc, filename);

        // Classify the document and show the result in a dialogue box
        result = filename + " is classified as ";
        result = result + nb.classify(testDoc);
        
        // Display the filename and its classification
        JOptionPane.showMessageDialog(null, result);
    }


    public static void main(String[] args) throws IOException
    {
        ////////////////////////////////////////////////////////////////
        // TRAIN THE CLASSIFIER ////////////////////////////////////////
        ////////////////////////////////////////////////////////////////

        // Initialise the minimum and maximum sizes of the nGrams
        Utilities.initialiseNGramMakerMinMax(2, 5);

        // Create a NaiveBayes classifier instance
        NaiveBayes nb = new NaiveBayes(0.3, 0.2);

        // Let the user know what is happening
        JOptionPane.showMessageDialog(null, "First, load the training files and classify them.");

        // Train the classifier
        int continueTraining = 1;

        while(continueTraining==1)
        {
            try {
                trainClassifier(nb);
            }
            catch (Exception e) {
                System.out.println("Error: Unable to train classifier with this file.");
            }

            int dialogResult = JOptionPane.showConfirmDialog (null,
                    "Would you like to load another training document?");
            if(dialogResult != JOptionPane.YES_OPTION){
                continueTraining=0;
            }
        }

        ////////////////////////////////////////////////////////////////
        // CLASSIFY DOCUMENTS //////////////////////////////////////////
        ////////////////////////////////////////////////////////////////
        JOptionPane.showMessageDialog(null, "Now, load the file to classify.");

        int continueClassifying = 1;

        while(continueClassifying==1)
        {
            try {
                classifyADocument(nb);
            }
            catch (Exception e) {
                System.out.println("Error: Unable to classify document.");
            }

            int dialogResult = JOptionPane.showConfirmDialog (null,
                    "Would you like to classify another document?");
            if(dialogResult != JOptionPane.YES_OPTION){
                continueClassifying=0;
            }
        }
    }
}
