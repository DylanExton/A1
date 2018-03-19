//Dylan Exton | 1284042 & Chris Johnson | 1280366

//Import the IO package
import java.io.*;

public class MakeRuns {

    public static boolean isStringInteger(String number){
        try{
            Integer.parseInt(number);
        }catch(Exception e ){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        //Check if there are arguments to be resolved
        if(args.length != 2 || isStringInteger(args[0])){
            System.out.println("Usage: MakeRuns <int heapSize, filename file.txt>");
            return;
        }


    }
}