//Dylan Exton | 1284042 & Chris Johnson | 1280366

//Import the relevant packages from the IO package
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
        if(args.length != 2 || !isStringInteger(args[0]) || Integer.parseInt(args[0]) < 1){
            System.out.println("Usage: MakeRuns <int heapSize, filename file.txt>");
            return;
        }

        //Create the variables that will be used
        int arrayMax = Integer.parseInt(args[0]);
        int useableArray = arrayMax;
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
            //Loop through till the end of the file
            while(input.available() > 0){
                //Fill the array
                for(int i = 0; i< arrayMax; i++){
                    heap[i] =  String.valueOf(input.read());
                }

            }


        }catch(IOException e){
            System.err.println(e.getMessage());
        }





    }
}