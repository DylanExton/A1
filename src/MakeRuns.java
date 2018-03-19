//Dylan Exton | 1284042 & Chris Johnson | 1280366

//Import the IO package
import java.io.*;

public class MakeRuns {

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

    public static void main(String[] args) {
        //Check if there are arguments in the right order and type
        if(args.length != 2 || isStringInteger(args[0])){
            System.out.println("Usage: MakeRuns <int heapSize, filename file.txt>");
            return;
        }


    }
}