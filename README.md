# DNAProcessing
DNA Processing
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
