//Dylan Exton | 1284042 & Chris Johnson | 1280366

//Import the relevant packages from the IO package
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class MergeRuns {

    public static void main(String[] args) {
        //Check if there are arguments in the right order and type
        if(args.length != 2 || !isStringInteger(args[0]) || Integer.parseInt(args[0]) < 1){
            System.out.println("Usage: MergeRuns <int k, filename file.txt>");
            return;
        }

        //Create the variables that will be used
        int arrayMax = Integer.parseInt(args[0]);
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
}
