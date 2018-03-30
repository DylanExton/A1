import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MergeTest {
    public static void main(String[] args) {
        //Check if there are arguments in the right order and type
        if (args.length != 2 || !isStringInteger(args[0]) || Integer.parseInt(args[0]) < 1) {
            System.out.println("Usage: MergeRuns <int k, filename file.txt>");
            return;
        }

        //Create the variables that will be used
        int numFiles = Integer.parseInt(args[0]);
        String line = null;
        //Create a File object
        File file = new File(args[1]);

        //Check if the file exists
        if (!file.exists()) {
            System.out.println(args[1] + " does not exist");
            return;
        }

        //Check that the file can be read from
        if (!(file.isFile() && file.canRead())) {
            System.out.println(file.getName() + " cannot be read from");
        }

        try {
            //Create all the files
            for (int i = 1; i <= numFiles; i++) {
                String fileName = "tempFile" + i + ".txt";
                if (new File(fileName).createNewFile()) {
                    System.out.println("added " + fileName);
                } else {
                    System.out.println("file already exists");
                }
            }
            //Create a new File Input stream to read the file.runs
            FileInputStream input = new FileInputStream(file);
            //Keep count of the file that we are accessing
            int accessfile = 1;
            //To make sure the second to last file only has one run
            boolean secondToLast = false;
            int dataNum = 0;
            //Create the fileWriter
            BufferedWriter bw = new BufferedWriter(new FileWriter("tempFile"+accessfile+".txt",true));
            //Create the previous var
            char previous = ' ';
            //While there is stuff still in the file
            while(input.available() > 0){
                char current = (char) input.read();
                while(current == 13 || current == 10) {
                    current = (char) input.read();
                }
                //If it is the next one in the run
                if(Character.toString(current).compareTo(Character.toString(previous)) > 0){
                    previous = current;
                    bw.write(current);
                    dataNum++;
                }
                else{
                    bw.close();
                    accessfile++;
                    if(accessfile == numFiles-1){
                        if(secondToLast == false){
                            secondToLast = true;
                        }
                        else{
                            accessfile = 1;
                        }
                    }
                    else if(accessfile > numFiles -1){
                        accessfile = 1;
                    }
                    bw = new BufferedWriter(new FileWriter("tempFile"+accessfile+".txt",true));
                    bw.write(current);
                    dataNum ++;
                    previous = current;
                }
            }
            bw.close();
            input.close();
            System.out.println("Number of Data piooints: "+ dataNum);
            //--------Runs are Distributed Fully Here--------//
            //Implement heap and sort them till only one file left

            //Create the heap
            String[] heap = new String[2*(numFiles-1)];
            //Count the number of passes
            int passes = 0;
            //The previous output
            String lastOut = "";
            //The amount of the heap still in use
            int arrayFree = 2*(numFiles-1)-1;
            //To decide what file to use as output
            int fileNum = numFiles-1;
            //Current file being read
            int curfile = 1;
            //open a writer for the last file
            bw = new BufferedWriter(new FileWriter("tempFile"+ numFiles + ".txt"));

            //Create an array of readers
            FileInputStream[] inputs = new FileInputStream[numFiles];
            for(int i = 0; i < numFiles; i++){
                FileInputStream f = new FileInputStream(new File("tempFile" +(i+1)+".txt"));
                inputs[i] = f;
            }
            int heapInd = 0;

            while(heap[arrayFree] == null){
                for(int i = 0; i < numFiles-1; i ++) {
                    char current = (char) inputs[i].read();
                    heap[heapInd] = Character.toString(current);
                    heapInd++;
                }
            }
            initialHeapify(heap, arrayFree);
            



            //Delete the Temp Files
           //removeTempFiles(numFiles);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //To check if the integer that the user has input is actually an integer
    public static boolean isStringInteger(String number){
        //try to parse the integer
        try{
            Integer.parseInt(number);
        }
        //If it catches, is not an integer
        catch(Exception e ){
            return false;
        }
        //else return true
        return true;
    }

    //
    public static void removeTempFiles(int j) {
        for (int i=0; i <= j; i++) {
            try {
                Files.deleteIfExists(Paths.get("tempFile" + i + ".txt"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Outputs the root of the heap to the file
    public static void outputRoot(String[] array, BufferedWriter bw){
        try {
            String toGo = array[0];
            bw.write(toGo);
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Will heapify a subtree to keep it in heap order. Will recursively heapify the lower subtrees if a change is made
    public static void heapify(String[] array, int useSize, int index){

        //Setup the indexes for the specified subtree
        int smallest = index;
        int left = 2*index + 1;
        int right = 2*index +2;

        //Checks if the left child is in the range, and if it is smaller than the root of the subtree
        if(left < useSize && (array[left].compareTo(array[smallest]) < 0)){
            //If it is, swap it to be the smallest
            smallest = left;
        }

        //Checks if the right child is in the range and if it is smaller than the smallest value found so far (root||left)
        if( right < useSize && (array[right].compareTo(array[smallest]) < 0)){
            //If it is, swap it as the smallest
            smallest = right;
        }

        //If the smallest is not the root
        if(smallest != index){
            //Move the root into another reference
            String swap = array[index];
            //swap the root with the smallest
            array[index] = array[smallest];
            //make the root a child of the smallest
            array[smallest] = swap;

            //run heapify on the subtree of the swapped value
            heapify(array, useSize, smallest);
        }

    }

    //For the initial heapify, needs to run on all of the indicies as it assumes the heap is already in heap order
    public static void initialHeapify(String[] arr, int maxSize){
        for(int i = maxSize/2 -1; i >= 0; i--){
            heapify(arr, maxSize, i);
        }
    }

    //Reduces the size of the heap by 1
    public static int cutHeap(String[] arr, int endIndex){
        String top = arr[endIndex-1];
        arr[endIndex-1] = arr[0];
        arr[0] = top;

        return endIndex-1;
    }

    //Replaces the root of the heap once it has been output to the file
    public static void replaceRoot(String[] arr, FileInputStream i){
        try {

            char current = (char) i.read();
            arr[0] = Character.toString(current);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
