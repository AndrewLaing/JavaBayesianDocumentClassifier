/**
 * Author: Andrew Laing
 * Email:  parisianconnections@gmail.com
 * Date:   26/12/2016.
 */

import java.util.ArrayList;
import java.util.regex.*;
import java.util.List;
import java.io.*;

public class NGramMaker
{
    // Note this regex allows words with numbers in them as long as the word begins with a letter
    private final static String wordSplitter = "[a-zA-Z]+(?:-[a-zA-Z]+)*[\\p{Alnum}]*(?:'[a-zA-Z]+)*";

    // Compile the regex once because it will be reused many times
    private final static Pattern pattern1 = Pattern.compile(wordSplitter);

    // The default minimum and maximum sizes for the nGrams
    private static int ngramMin = 2;
    private static int ngramMax = 4;


    public static void setNgramMin(int min) {
        ngramMin = min;
    }

    public static void setNgramMax(int max) {
        ngramMax = max;
    }


    /**
     * The method getTokens tokenises the string passed and adds the tokens
     * to the List passed
     * @param tokens The List to add the tokens to
     * @param toSplit The string to tokenise
     */
    public static void getTokens(List tokens, String toSplit)
    {
        // replace abnormal punctuations to normal ones
        toSplit = toSplit.replace('’', '\'');
        toSplit = toSplit.replace('­','-');

        Matcher match = pattern1.matcher(toSplit);

        while (match.find()) {
            tokens.add(match.group());
        }
    }


    /**
     * The method getTokens tokenises a file's contents and adds the tokens
     * to the List passed
     * @param tokens The List to add the tokens to
     * @param filename The name of the file to tokenise
     * @throws IOException
     */
    public static void tokeniseFile(List tokens, String filename) throws IOException
    {
        String line;
        // Open the file
        FileReader freader = new FileReader(filename);
        BufferedReader inputFile = new BufferedReader(freader);

        // Read the first name from the file
        line = inputFile.readLine();

        // Process and read lines until no more are found in the file
        while(line != null)
        {
            getTokens(tokens, line);
            line = inputFile.readLine();
        }

        // Close the file
        inputFile.close();
    }


    /**
     * The method getNGrams stores nGrams created from a List of tokens
     * into a List passed to it.
     * @param tokens The List of tokens to create nGrams from
     * @param nGrams The List to store the nGrams into
     * @param n The size of the nGrams to create
     */
    public static void getNGrams(List tokens, List nGrams, int n)
    {
        if(n<=0) return; // idiot checks

        String temp = "";

        for(int i=0; i < tokens.size()-n+1; i++)
        {
            for(int j=0; j<n; j++)
                temp += tokens.get(i+j) + " ";

            nGrams.add(temp.trim());
            temp = "";
        }
    }


    /**
     * The method getNGramsFromFile stores nGrams created from a text file
     * into the List passed to it.
     * @param nGrams The List to store the nGrams into
     * @param filename The file to read in
     * @throws IOException
     */
    public static void getNGramsFromFile(List nGrams, String filename) throws IOException
    {
        List tokens = new ArrayList();
        tokeniseFile(tokens, filename);

        String temp = "";

        // Create nGrams and add them to the List
        for(int n=ngramMin; n<=ngramMax; n++)
        {
            for(int i=0; i < tokens.size()-n+1; i++)
            {
                for(int j=0; j<n; j++)
                    temp += tokens.get(i+j) + " ";

                nGrams.add(temp.trim());
                temp = "";
            }
        }
    }
}
