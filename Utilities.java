/**
 * Author: Andrew Laing
 * Email:  parisianconnections@gmail.com
 * Date:   26/12/2016.
 */

import javax.swing.*;
import java.io.*;

public class Utilities
{
    /**
     * The method SaveClassifier serialises the NaiveBayesian classifier
     * passed into the specified output file.
     * @param nb A reference toa NaiveBayesian classifier.
     * @param outputFilename The name of file to write the serialised classifier to.
     */
    public static void SaveClassifier(NaiveBayes nb, String outputFilename)
    {
        // serialise the classifier
        try
        {
            FileOutputStream fos =
                    new FileOutputStream(outputFilename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(nb);
            oos.close();
            fos.close();
            System.out.println("Classifier saved to " + outputFilename);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            System.exit(0);
        }
    }


    /**
     * The method LoadClassifier loads a serialised NaiveBayesian classifier from
     * the specified file into the NaiveBayes passed.
     * @param nb A reference to hold the loaded classifier.
     * @param inputFilename The name of the file that contains the serialised
     *                      NaiveBayesian classifier.
     * @return A reference to hold the loaded classifier.
     */
    public static NaiveBayes LoadClassifier(NaiveBayes nb, String inputFilename)
    {
        try
        {
            FileInputStream fis = new FileInputStream(inputFilename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            nb = (NaiveBayes) ois.readObject();
            ois.close();
            fis.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            System.exit(0);
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            System.exit(0);
        }

        return nb;
    }


    /**
     * The method getOpenFilename returns the name and path of a file selected
     * by the user from an open dialogue.
     * @return The name and path of a file selected by the user.
     * @throws IOException
     */
    public static String getOpenFilename() throws IOException
    {
        String filename = "";

        // Get the name of the file to get nGrams for
        JFileChooser getName1 = new JFileChooser();
        int status = getName1.showOpenDialog(null);

        if(status == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = getName1.getSelectedFile();
            filename = selectedFile.getPath();
        }
        else
            System.exit(0);

        return filename;
    }


    /**
     * The method getSaveFilename returns the name and path of a file selected
     * by the user from a save dialogue.
     * @return The name and path of a file selected by the user.
     * @throws IOException
     */
    public static String getSaveFilename() throws IOException
    {
        String filename = "";

        // Get the name of the file
        JFileChooser getName1 = new JFileChooser();
        int status = getName1.showSaveDialog(null);

        if(status == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = getName1.getSelectedFile();
            filename = selectedFile.getPath();
        }
        else
            System.exit(0);

        return filename;
    }


    /**
     * The method getCategory is used to get a String from the user representing
     * the category of a document.
     * @return The category of a document
     */
    public static String getCategory()
    {
        String category = JOptionPane.showInputDialog(null, "Enter the category for this document");
        return category;
    }

    /**
     * The method initialiseNGramMakerMinMax initialises the minimum and
     * maximum sizes of the nGrams that the NGramMaker will produce.
     * @param min The minimum sizes of the nGrams that the NGramMaker will produce.
     * @param max The maximum sizes of the nGrams that the NGramMaker will produce.
     */
    public static void initialiseNGramMakerMinMax(int min, int max)
    {
        NGramMaker.setNgramMin(min);
        NGramMaker.setNgramMax(max);
    }
}
