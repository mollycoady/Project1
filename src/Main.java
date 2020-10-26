
/***********************************************************************************************************************
 * CLASS: Main (Main.java)
 *
 * DESCRIPTION
 * This file contains a program that reads integers from a file, calculates the total number of monotonically increasing
 * and decreasing runs and writes them to an output file in a specified format
 *
 * COURSE AND PROJECT INFORMATION
 * CSE205 Object Oriented Programming and Data Structures, Fall 2020
 * Project Number: 01
 *
 * GROUP INFORMATION
 * AUTHOR 1: Molly Coady, mrcoady, mollycoady@gmail.com
 * AUTHOR 2: Ansell Scott, aescot10, aescot10@asu.edu
 * Author 3: Paul Province
 **********************************************************************************************************************/


import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Main {
    final int RUNS_UP = 1;
    final int RUNS_DN = -1;

    public static void main(String[] pArgs) throws FileNotFoundException {
        Main mainObject = new Main();
        mainObject.run();
    }

    //This method runs the program to take input and create the desired output
    private void run(){

        ArrayList<Integer> numbers = getFileData("p1-in.txt");
        ArrayList<Integer> runsUp = findRuns(numbers, RUNS_UP);
        ArrayList<Integer> runsDown = findRuns(numbers, RUNS_DN);
        ArrayList<Integer> totalRuns = mergeLists(runsUp, runsDown);
        writeOutPutFile(totalRuns, "p1-runs.txt");
    }

    //This method counts the number of runs up or down in pList depending on the argument passed to pDir
    ArrayList<Integer> findRuns(ArrayList<Integer> pList, int pDir){
        ArrayList<Integer> listRunsCount = arrayListCreate(pList.size(), 0);

        int i = 0;
        int k = 0;
        while (i < pList.size()-1){

            int current = pList.get(i);
            int next = pList.get(i + 1);

            if(pDir == RUNS_UP && current <= next ){
                k++;
            }
            else if(pDir == RUNS_DN && current >= next){
                k++;
            }
            else{
                if (k != 0){
                    // added to increment the value of k at index k
                    listRunsCount.set(k, listRunsCount.get(k) + 1);
                    k = 0;
                }
            }
            i++;
        }
        if(k != 0) {
            listRunsCount.set(k, listRunsCount.get(k)+1);
        }
        return listRunsCount;
    }

    // This method merges the ArrayList of runs up and the ArrayList of runs down into one ArrayList of total runs
    private ArrayList<Integer> mergeLists(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDownCount) {
        ArrayList<Integer> listRunsCount = arrayListCreate(pListRunsUpCount.size(), 0);
        for(int i = 0; i < pListRunsUpCount.size(); i++) {
            listRunsCount.set(i, pListRunsDownCount.get(i) + pListRunsUpCount.get(i));
        }
        return listRunsCount;
    }

    //This method gains access to the input file and opens it
    private ArrayList<Integer> getFileData(String filePath) {
        Scanner in = null;
        ArrayList<Integer> inputList = new ArrayList<>();
        try {

            in = new Scanner(new File(filePath));
            inputList = arrayListCreate(in);

        } catch (FileNotFoundException pException) {
            System.out.println("Oops, could not open 'p1-in.txt' for reading. The program is ending.");
            System.exit(-100);

        }
        finally{
            if (in != null){
                in.close();
            }
        }
        return inputList;
    }

    //This method converts the integers in the input file into an ArrayList
    private ArrayList<Integer> arrayListCreate(Scanner in){
        ArrayList<Integer> list = new ArrayList<>();

        while (in.hasNextInt() == true){
            int integerFromFile = in.nextInt();
            list.add(integerFromFile);
        }

        return list;
    }

    // This method creates an array of a given size and initializes values to the defaultValue parameter
    ArrayList<Integer> arrayListCreate(int size, int defaultValue ){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++){
            list.add(defaultValue);
        }
        return list;
    }


    // This method writes to the output file in the required format
    private void writeOutPutFile(ArrayList<Integer> listRunsCount, String outPutFile){
        int sumOfRuns = getSum(listRunsCount);
        PrintWriter out = null;

        try{
            out = new PrintWriter(new File (outPutFile));

            out.printf("runs_total: %d%n", sumOfRuns);

            for (int i = 1; i < listRunsCount.size(); i++ ){
                out.printf("runs_%d: %d%n", i, listRunsCount.get(i));
            }

        }

        catch (FileNotFoundException ex){
            System.out.println("Oops, could not open 'p1-runs.txt' for writing. The program is ending.");
            System.exit(-200);
        }
        finally{
            if (out != null){
                out.close();
            }

        }

    }


    // This method gets the sum of the elements in listRunsCount
    int getSum(ArrayList<Integer> listRunsCount){
        int sum = 0;

        for (int numberOfRuns: listRunsCount){
            sum = sum + numberOfRuns;

        }
        return sum;
    }
}

