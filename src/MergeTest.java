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
            //Create the fileWriter
            BufferedWriter bw = new BufferedWriter(new FileWriter("tempFile"+accessfile+".txt",true));
            //Create the previous var
            char previous = ' ';
            //While there is stuff still in the file
            while(input.available() > 0){
                char current = (char) input.read();
                //If it is the next one in the run
                if(Character.toString(current).compareTo(Character.toString(previous)) > 0){
                    previous = current;
                    bw.write(current);
                    bw.newLine();
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
                    bw.newLine();
                    previous = current;
                }
            }
            bw.close();
            //Delete the Temp Files
           // removeTempFiles(numFiles);

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
