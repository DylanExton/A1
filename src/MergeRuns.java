//Dylan Exton | 1284042 & Chris Johnson | 1280366

//Import the relevant packages from the IO package
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MergeRuns {

    public static void main(String[] args) {
        //Check if there are arguments in the right order and type
        if(args.length != 2 || !isStringInteger(args[0]) || Integer.parseInt(args[0]) < 1){
            System.out.println("Usage: MergeRuns <int k, filename file.txt>");
            return;
        }

        //Create the variables that will be used
        int numFiles = Integer.parseInt(args[0]);
        String line = null;
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
            for (int i=1; i <= numFiles; i++ ){
                String fileName = "tempFile" + i + ".txt";
                if (new File(fileName).createNewFile()) {
                    System.out.println("added " + fileName);
                }
                else {
                    System.out.println("file already exists");
                }

            }

            //Create a new File Input stream to read the file
            FileInputStream input = new FileInputStream(file);
            //Current character that is being read
            char current;
            char previous = ' ';
            //buffered writer
            BufferedWriter bw = new BufferedWriter(new FileWriter("tempFile1.txt"));
            //variable to count what file it is
            int fileCount = 1;
            //Read file and count number of runs
            while(input.available() > 0) {
                current = (char) input.read();
                if (Character.toString(current).compareTo(Character.toString(previous)) < 0) {
                    bw.flush();
                    fileCount++;
                    if(fileCount > numFiles - 1) {
                        fileCount = 1;
                    }
                    bw.close();
                    bw = new BufferedWriter(new FileWriter("tempFile" + fileCount + ".txt"));
                    System.out.println("Different run");
                    bw.append(current);
                    previous = current;

                }
                else {
                    System.out.println("Same run");
                    bw.append(current);
                    previous = current;
                }

            }
            bw.close();
            bw = new BufferedWriter(new FileWriter("tempFile1.txt"));
            //removeTempFiles(numFiles);
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
}