/**
 * @Author: Alpha Kaba
 * @Since: 11-23-2019
 * @Version 1.0
 *
 * Description: The purpose of this program is to read a huge set of 
 * DNA bound together and prints the valid proteins.
 *
 * First, the program reads a standard table done by RNACodonTable()
 * which contains the codons and their corresponding amino and place 
 * them in a two 1-dimensional array. Then that array is sent to a 
 * method call sort() which sort all the two 1-dimensional array
 * simultaneously and place the sorted array in codonsWithAminos(). 
 * The sort method returns codonsWithAminos. And which in its turn is
 * returned by RNACodonTable().
 *
 * In addition, a new method is created and named codonLookUp() which
 * accepts a codon then returns its amino abbreviation. The 
 * RNACodonTable() is always recall everytime we a codon is received 
 * by codonLookUp() in order to do a binary search as the standard 
 * codon table is already sorted in RNACodonTable().
 *
 * Moreover, we have the main which reads the data to be processed. 
 * It reads three codons at the time then eliminate the first letter 
 * till it reaches "AUG" which means START. Then it reads three codons
 * and place them in RNA till RNA equals "STOP". RNA is sent each time
 * before stop into a new method call isValidRNA, which return the true
 * or false. If RNA is true, the RNA is directed to codonLookUp() which
 * returns the amino. And the aminos are added in a for loop.
 *
 * Finally, all proteins available in the data to be processed are 
 * printed. Then some possible RNA corresponding to those proteins are
 * printed using the method otherPossibleCodon(). There may be more 
 * than one codon sequence at least, because each amino abbreviation in
 * the RNA codon Table has at least two codons. So with only the first 
 * amino we can have at least to codon sequence to reproduce the same
 * protein. Then the junk DNA sequence is printed at the very end. 
 * 
 * All the output is directed to a file call "result".
 *
 */
package assignment6;

import java.io.PrintStream;
import java.io.File;
import java.util.Scanner;

public class Assignment6 {

public static PrintStream ps;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("Data to be processed.txt"));
        ps = new PrintStream("result.txt");
        int g = 1, i;
        String junk = "";
        String amino = "";
        String line, RNA;
        while (sc.hasNext()) {
            line = sc.next();
            for (i = 0; i < line.length() - 2; i++) {

                RNA = line.substring(i, i + 3);
                if (!RNA.equals("AUG")) { // collects the junk codon's 
                                          // letter at i before "START"

                    junk = junk + (line.substring(i, i + 1));

                }
                if (RNA.equals("AUG")) {

                    for (i = i + 3; !codonLookUp(RNA).equals("STOP") && 
                            !codonLookUp(RNA).equals("-1"); i = i + 3) 
                    {RNA = line.substring(i, i + 3);
                        if (isValidRNA(RNA) && !codonLookUp(RNA).equals
                                                            ("STOP")) {
                      amino = amino + codonLookUp(RNA);
                        }

                        RNA = line.substring(i, i + 3);
                    } // end of the inner for loop
                    junk = junk + " "; // prints a space between the 
                    // junk in order to distingush the beginning
                    // and the end of a junk
                    junk = junk + (line.substring(i, i + 1)); 
                    // collects the first junk's letter right after a
                    // "STOP"

                    ps.printf("\nProtein #%d: %s\n\n", g, amino);
                    ps.printf("Some possible codon sequences to "
                            + "Protein#%d are:\n\n", g);
                    otherPossibleCodon(amino);

                    amino = "";
                    g++;
                    ps.println();

                }
            } // end of the outer for loop

            // As I used the for loop from i=0 to the (line length) -2,
            // I need to add the remaining junk at the end of the data
            // to be processed
            junk = junk + line.substring((line.length()) - 2, 
                    line.length());
            ps.printf("\nThe junk DNA sequence of the data processed "
                    + "is: %s\n", junk);
        } // end while
    } // end main

// The method below populates a two 1-dimentional array and returns a 
// sorted codons table with their respective aminos.
    public static String[][] RNACodonTable() throws Exception {

        Scanner sc = new Scanner(new File("Data file.txt"));
        final int size = 64;
        int j = 0, k = 0, i = 0;
        String[] codons = new String[size];
        String[] amino = new String[size];
        while (sc.hasNext()) {
            String word = sc.next();
            if (i % 2 == 0) {
                codons[j] = word;
                j++;
            } else {
                amino[k] = word;
                k++;
            }
            i++;
        }// end for while

        sort(codons, amino);
        String[][] codonsWithAminos = {codons, amino};
        return (codonsWithAminos);
    } // end RNACodonTable

// This method sorts the two 1-dimensional array in alphabetical as 
// well as assuring that a codon is associated with its amino 
// abbreviation
    public static String[][] sort(String[] codons, String[] amino) {
        int j, pass;
        String hold, temp;
        boolean switched = true;

        for (pass = 0; pass < 63 && switched; pass++) {
            switched = false;
            for (j = 0; j < 63; j++) {
                if (codons[j].compareTo(codons[j + 1]) > 0) {
                    switched = true;
                    hold = codons[j];
                    codons[j] = codons[j + 1];
                    codons[j + 1] = hold;
                    temp = amino[j];
                    amino[j] = amino[j + 1];
                    amino[j + 1] = temp;

                } // end if
            } // end for j=0...   
        } // end for (pass = 0...

        String[][] codonsWithAminos = {codons, amino};

        return (codonsWithAminos);
    } // sort
    
    // The method below accepts a three-codon-letter as a string and
    // does a binary search and finally returns its amino abbreviation

    public static String codonLookUp(String codon) throws Exception {

        String[][] RNA = RNACodonTable();

        int low = 0;
        int high = 63;
        if (codon.equals("AUG")) {
            return "M";
        }

        while (low <= high) {
            int middle = (low + high) / 2;
            if (codon.compareTo(RNA[0][middle]) > 0) {
                low = middle + 1;
            } else if (codon.compareTo(RNA[0][middle]) < 0) {
                high = middle - 1;
            } else { // the item has been found
                return RNA[1][middle];
            }
        }// end binarySearch method
        return "-1";
    }  // end codonLookUp

// This method checks whether a read codon is valid or not; it returns
// true if it is valid and false otherwise
    public static boolean isValidRNA(String codon) {

        char c;
        for (int i = 0; i < 3; i++) {
            c = codon.charAt(i);
            if (c == 'A' || c == 'C' || c == 'G' || c == 'U') {
            } else {
                return false;
            }
        } // end for (int ...
        return true;
    } // end isValidRNA

// This method takes a valid protein and returns some possible condon
// it may have as an alternative DNA sequence
    public static void otherPossibleCodon(String protein) throws 
            Exception {
        int g = 0;
        String[][] RNA = RNACodonTable();
        String possibility = "";
        String[] codon = new String[120];
        String amino;
        for (int j = 0; j < protein.length(); j++) {

            amino = protein.substring(j, j + 1);
            for (int z = 0; z < 63; z++) {

                if (RNA[1][z].equals(amino)) {
                    codon[g] = RNA[0][z];
                    g++;
                }
            }
            if (j > 0) {
                possibility = possibility + codon[j];
            }
        } // end outer for loop

        for (int s = 0; s < 2; s++) {

            ps.println(codon[s] + possibility);
        }
    } // end otherPossibleCodon

} // end class
