//Dylan Exton | 1284042 & Chris Johnson | 1280366

//Import the relevant packages from the IO package
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MakeRuns {

    public static void main(String[] args) {
        //Check if there are arguments in the right order and type
        if(args.length != 2 || !isStringInteger(args[0]) || Integer.parseInt(args[0]) < 1){
            System.out.println("Usage: MakeRuns <int heapSize, filename file.txt>");
            return;
        }

        //Create the variables that will be used
        int arrayMax = Integer.parseInt(args[0]);
        int useableArray = arrayMax;
        String lastOutput = "";
        int runs = 0;
        //Creates an array the size of the heap
        String[] heap = new String[arrayMax];
        //Create a File object
        File file = new File(args[1]);

        //Check if the file exists
        if(!file.exists()){
            System.out.println(args[1] + " does not exist");
            return;
        }

        //Check that the file can be read from
        if(!(file.isFile() && file.canRead())){
            System.out.println(file.getName() + " cannot be read from");
        }

        try{
            //Create a new File Input stream to read the file
            FileInputStream input = new FileInputStream(file);
            //Current character that is being read
            char current;
            //Fill the array
                for(int i = 0; i< arrayMax; i++){
                    if(input.available() > 0) {
                        current = (char) input.read();
                        heap[i] = Character.toString(current);
                    }
                };
            //Setup the file for output
            BufferedWriter output = new BufferedWriter(new FileWriter(file.getName() + ".runs"));
            //Initial heap sort to upheap the heap.
           initialHeapify(heap,useableArray);
           //Set the last output to the root of the heap
           lastOutput = heap[0];
           //output the root of the heap
           outputRoot(heap,output);
           //replace the root of the heap
           replaceRoot(heap, input);
           //While there is still information in the file
           while(input.available() > 0){
               //While the array is still useable
            while(useableArray > 0) {
               //Heapify the heap
               heapify(heap, useableArray, 0);
               //Check if the root is greater or equal to the previous output
               if (heap[0].compareTo(lastOutput) >= 0){
                   //If it is, output the root to the end of the run
                   outputRoot(heap, output);
                   lastOutput = heap[0];
                   //if there is still another character in the input file
                   if (input.available() > 0) {
                       //replace the root
                       replaceRoot(heap, input);
                   } else {
                       //else set root to null then cut heap
                       heap[0] = null;
                       useableArray = cutHeap(heap, useableArray);
                   }
               }
               //if the root is smaller than the last output
               else {
                   //cut the heap and reheap the remaining heap
                   useableArray = cutHeap(heap, useableArray);
                   heapify(heap, useableArray, 0);
               }
            }
            //When the useable heap size is 0, reset and reheap
            runs += 1;
            lastOutput = "0";
            useableArray = arrayMax;
            if(heap[0] != null)
            initialHeapify(heap,useableArray);
           }
           //Close the reader
            input.close();
           //Close the writer
            output.close();
           //Send the number of runs to std error
            System.out.println("Sorting Success!");
            System.err.println("Number of Runs: " + runs);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
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