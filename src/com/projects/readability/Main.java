package com.projects.readability;
import com.projects.readability.analyzeReadability;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "./src/com/projects/readability/input.txt";
        Scanner scanner = new Scanner(System.in);
        double ariScore, fkScore, smogScore, clScore;
        String textARIScore, textFKScore, textSMOGScore, textCLScore;
        String choice;
        DecimalFormat df = new DecimalFormat("#.##");

        analyzeReadability document = new analyzeReadability(filePath);

        System.out.println("The text is:");
        System.out.println(document.getWholeDocument());
        System.out.println("\nWords: " + document.getWordCount());
        System.out.println("Sentences: " + document.getSentenceCount());
        System.out.println("Characters: " + document.getVisibleCharsCount());
        System.out.println("Syllables: " + document.getSyllableCount());
        System.out.println("Polysyllables: " + document.getPolySyllableCount());
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all: )");
        choice = scanner.nextLine(); //gets choice
        ariScore = document.getaRScore();
        fkScore = document.getFKScore();
        smogScore = document.getSmogScore();
        clScore = document.getcLIScore();
        textARIScore = "Automated Readability Index: " + df.format(ariScore) + " (about " + (int)document.intendedAge(ariScore) + " year olds).";
        textFKScore = "Flesch-Kincaid readability tests: " + df.format(fkScore) + " (about " + (int)document.intendedAge(fkScore) + " year olds).";
        textSMOGScore = "Simple Measure of Gobbledygook: " + df.format(smogScore) + " (about " + (int)document.intendedAge(smogScore) + " year olds).";
        textCLScore = "Coleman-Liau index: " + df.format(clScore) + " (about " + (int)document.intendedAge(clScore) + " year olds).";

        switch (choice) {
            case "all":
                System.out.println(textARIScore + "\n" + textFKScore + "\n" + textSMOGScore + "\n" + textCLScore);
                break;
            case "ARI":
                System.out.println(textARIScore);
                break;
            case "FK":
                System.out.println(textFKScore);
                break;
            case "SMOG":
                System.out.println(textSMOGScore);
                break;
            case "CL":
                System.out.println(textCLScore);
                break;


        }



        /* testing
        System.out.println("\n\n");
        System.out.println("polysyllCount:" + document.getPolySyllableCount());
        System.out.println("senetnceCount:" + document.getSentenceCount());
        System.out.println("WordCount:" + document.getWordCount());
        System.out.println("VisibleCharCount:" + document.getVisibleCharsCount());
        for (String temp : document.getSentences()) {
            System.out.print(temp + " ");
        }
        */


/*
        try {

            DecimalFormat df = new DecimalFormat("#.##");

            System.out.println("The text is:\n" + wholeDocument);
            System.out.print("\nWords: " + countWords + "\nSentences: " + countSentences + "\nCharacters: " + countChars);
            System.out.print("\nThe score is: " + df.format(score));
            System.out.print("\nThis text should be understood by ");


            System.out.print(" year olds.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

*/
    }





}
