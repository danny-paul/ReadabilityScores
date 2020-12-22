package com.projects.readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class analyzeReadability {

    private String wholeDocument;
    private String sentenceArr[];
    private String wordArr[][];
    private String visibleChars;
    private int wordCount, visibleCharsCount, sentenceCount, syllableCount, polySyllableCount;
    private double aRScore, FKScore, smogScore, cLIScore;


    public analyzeReadability(String filePath) {
        this.wordCount = 0; this.visibleCharsCount = 0; this.sentenceCount = 0;

        try {
            //implements non-default values for attributes
            this.wholeDocument = readFileAsString(filePath); //whole document -> single string
            this.getSentences();
            this.getWords();
            this.getVisibleChars();
            this.countSentences();
            this.countWords();
            this.countVisibleChars();


        } catch (Exception e) {
            System.out.println("In constructor\n" + e.getMessage());
        }
    }
    public String getWholeDocument() {
        return wholeDocument;
    }
    public String[] getSentences() {
        sentenceArr = wholeDocument.split("[.!?]"); //splits by sentence //splits via sentence
        String[] temp = new String[sentenceArr.length]; //this copy will be returned

        for (int i = 0; i < sentenceArr.length; i++) {
            sentenceArr[i] = sentenceArr[i].trim(); //removes whitespace
            temp[i] = sentenceArr[i];
        }
        return temp; //prevents access to private data
    }

    public void countSentences() {
        sentenceCount = sentenceArr.length;
    }

    public int getSentenceCount() {
        countSentences();
        return sentenceCount;
    }

    public String[][] getWords() {
        //values and modification for words
        wordArr = new String[sentenceArr.length][]; //initializes first dimension
        String temp[][] = new String[sentenceArr.length][];

        for (int i = 0; i < sentenceArr.length; i++) {
            sentenceArr[i] = sentenceArr[i].replace(",", ""); //remove commas for accurate word array
            wordArr[i] = sentenceArr[i].split(" "); //sentences -> words (2-D array)
            temp[i] = sentenceArr[i].split(" "); //will be returned instead of private data
        }
        return temp;
    }

    public void countWords() {
        wordCount = 0;
        for (int i = 0; i < wordArr.length; i++) { //no stale data
            wordCount += wordArr[i].length; //adds number of words per sentence
        }
    } //to private

    public int getWordCount() {
        countWords();
        return wordCount;
    }

    public void countVisibleChars() {
        //values and modifications for visibleChars
        visibleChars = wholeDocument.replaceAll("\\s", ""); //document -> chars w/o whitespace
        visibleCharsCount = visibleChars.length();
    } //to private

    public String getVisibleChars() {
        String temp;

        countVisibleChars();
        temp = visibleChars; //prevents access to private data
        return temp;
    }

    public int getVisibleCharsCount() {
        countVisibleChars();
        return visibleCharsCount;
    }
/*
    public void countSyllablesAndPoly() {
       int countA = 0, countE = 0, countI = 0, countO = 0, countU = 0, countY = 0, sylPerWord = 0;
       syllableCount = 0; polySyllableCount = 0;

       for (int i = 0; i < wordArr.length; i++) {
           for (int j = 0; j < wordArr[i].length; j++) {
               System.out.print(wordArr[i][j] + " ");
               if (wordArr[i][j].contains("a") || wordArr[i][j].contains("A")) {countA++;}
               if (wordArr[i][j].contains("i") || wordArr[i][j].contains("I")) {countI++;}
               if (wordArr[i][j].contains("o") || wordArr[i][j].contains("O")) {countO++;}
               if (wordArr[i][j].contains("u") || wordArr[i][j].contains("U")) {countU++;}
               if (wordArr[i][j].contains("y") || wordArr[i][j].contains("Y")) {countY++;}
               if (wordArr[i][j].contains("e") || wordArr[i][j].contains("E")) {
                   if (wordArr[i][j].charAt(wordArr[i][j].length() - 1) != 'e' &&
                           wordArr[i][j].charAt(wordArr[i][j].length() - 1) != 'E') {
                       countE++;
                   }
               }
               //System.out.println("A:" + countA + " E:" + countE + " I:" + countI + " O:" + countO + " U:" + countU + " Y:" + countY + "\n");
               syllableCount += (countA + countI + countO + countU + countY + countE);
               sylPerWord = countA + countI + countO + countU + countY + countE;
               if (sylPerWord > 2) {
                   polySyllableCount++;
                   System.out.println("\n\n" + polySyllableCount + "Polysyllable in:" + wordArr[i][j] + "\n");
               }
               countA = countI = countO = countU = countY = countE = 0; //reset for next word
           }
       }
    } //to private
*/

    public void countSyllablesAndPoly() {
        int countA = 0, countE = 0, countI = 0, countO = 0, countU = 0, countY = 0, sylPerWord = 0;
        syllableCount = 0;
        polySyllableCount = 0;
        String substringCompare;
        boolean vowelThenConsonant;

        for (int i = 0; i < wordArr.length; i++) {
            for (int j = 0; j < wordArr[i].length; j++) {
                //System.out.println("WholeWord:" + wordArr[i][j] + "\n"); //test- prints word being analyzed
                for (int k = 0; k < wordArr[i][j].length(); k++) { //increment through chars of word in 2-D Array

                    if (k < wordArr[i][j].length() - 1) { //all but last char
                        substringCompare = wordArr[i][j].substring(k, k + 2); //consecutive letters
                        vowelThenConsonant = substringCompare.matches(".[^AaEeIiOoUuYy]?");
                    } else { //last character in word
                        substringCompare = wordArr[i][j].substring(wordArr[i][j].length() - 1); //last char
                        vowelThenConsonant = true;
                    }
                    //System.out.println("\tSubstringCompare:" + substringCompare + "\n");
                    switch (wordArr[i][j].charAt(k)) {
                        case 'a':
                        case 'A':
                            if (vowelThenConsonant) {
                                countA++;
                            }
                            break;
                        case 'i':
                        case 'I':
                            if (vowelThenConsonant) {
                                countI++;
                            }
                            break;
                        case 'o':
                        case 'O':
                            if (vowelThenConsonant) {
                                countO++;
                            }
                            break;
                        case 'u':
                        case 'U':
                            if (vowelThenConsonant) {
                                countU++;
                            }
                            break;
                        case 'y':
                        case 'Y':
                            if (vowelThenConsonant) {
                                countY++;
                            }
                            break;
                        case 'e':
                        case 'E':
                            if (vowelThenConsonant && k != wordArr[i][j].length() - 1) {
                                countE++;
                            }
                            break;
                        default:
                            //System.out.println("\tConsonant:" + wordArr[i][j].charAt(k) + "\n");
                            break;
                    }
                }

                sylPerWord = countA + countI + countO + countU + countY + countE;
                if (sylPerWord == 0) {sylPerWord = 1;} //when 0 vowels found
                //System.out.println("\tTotalSyllablesBefore:" + syllableCount);
                syllableCount += (sylPerWord);

                //System.out.println("\tA:" + countA + " E:" + countE + " I:" + countI + " O:" + countO + " U:" + countU + " Y:" + countY + "\n"); //test
                //System.out.println("\tSyllablesOfWord:"+ sylPerWord);
                //System.out.println("\tTotalSyllablesAfter:" + syllableCount);
                if (sylPerWord > 2) {
                    polySyllableCount++;
                    //System.out.println("\t" + polySyllableCount + "Polysyllable in:" + wordArr[i][j]); //test
                }
                countA = countI = countO = countU = countY = countE = 0; //reset for next word
            }
        }
    }

    public int getSyllableCount() {
        countSyllablesAndPoly(); //no stale data
        return syllableCount;
    }

    public int getPolySyllableCount() {
        countSyllablesAndPoly(); //no stale data
        return polySyllableCount;
    }

    public double getaRScore() {
        aRScore = 4.71 * ((double)visibleCharsCount/wordCount) + 0.5 * ((double)wordCount/sentenceCount) - 21.43;
        return aRScore;
    }

    public double getFKScore() {
        FKScore = (0.39 * ((double)wordCount/sentenceCount) + 11.8 * ((double)syllableCount/wordCount)- 15.59);
        return FKScore;
    }

    public double getSmogScore() {
        smogScore = 1.043 * Math.sqrt(polySyllableCount * ((double)30/sentenceCount)) + 3.1291;
        return smogScore;
    }

    public double getcLIScore() {
        cLIScore = 0.0588 * ((double)visibleCharsCount/wordCount) * 100 - 0.296 * ((double)sentenceCount/wordCount) * 100 - 15.8;
        return cLIScore;
    }

    //returns upper bounds of age range as a double
    public double intendedAge(double score) {
        double age;
        switch ((int) Math.ceil(score)) {
            case 1:
                age = 6;
                break;
            case 2:
                age = 7;
                break;
            case 3:
                age = 9;
                break;
            case 4:
                age = 10;
                break;
            case 5:
                age = 11;
                break;
            case 6:
                age = 12;
                break;
            case 7:
                age = 13;
                break;
            case 8:
                age = 14;
                break;
            case 9:
                age = 15;
                break;
            case 10:
                age = 16;
                break;
            case 11:
                age = 17;
                break;
            case 12:
                age = 18;
                break;
            case 14:
            case 13:
            default:
                age = 24;
                break;
        }
        return age;
    }

    //returns whole document as string
    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}

