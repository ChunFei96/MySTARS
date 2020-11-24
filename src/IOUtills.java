
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * IOUtills - helper class for I/O
 */
public class IOUtills {


    private static String filename;
    private static String filetype;
    private static String content;
    private static String directoryName;
    private static ArrayList<String> fileInput = new ArrayList<String>();

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        IOUtills.filename = filename;
    }

    public static String getFiletype() {
        return filetype;
    }

    public static void setFiletype(String filetype) {
        IOUtills.filetype = filetype;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        IOUtills.content = content;
    }

    public static String getDirectoryName() {
        return directoryName;
    }

    public static void setDirectoryName(String directoryName) {
        IOUtills.directoryName = directoryName;
    }

    public static ArrayList<String> getFileInput() {
        return fileInput;
    }

    public static void setFileInput(ArrayList<String> fileInput) {
        IOUtills.fileInput = fileInput;
    }

    /**
     * Usage: Create new directory
     */
    public static void createDirectory(String newFolderName){
        //File file = new File("C:/Users/USER/Documents/MySTARS" + "/" + newFolderName);
        File file = new File(System.getProperty("user.dir") + "/" + newFolderName);

        // true if the directory was created, false otherwise
        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Directory already exists!");
        }
    }

    public IOUtills(String filename,String filetype,String content,String directoryName) {
        setFilename(filename);
        setFiletype(filetype);
        setContent(content);
        setDirectoryName(directoryName);
    }

    /**
     * Usage: Read file
     */
    public static void ReadFile(String filepath){
        try {
            ArrayList<String> output = new ArrayList<String>();

            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                output.add(data);
                //System.out.println(data);
            }

            setFileInput(output);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Usage: Write file with overwrite the original file content
     */
    public static void WriteFile(){
        try {

            String _filepath = getFilename()  + "." + getFiletype();

            if(!getDirectoryName().isEmpty()){
               //_filepath = "C:\\Users\\USER\\Documents\\MySTARS\\" + getDirectoryName() + File.separator + _filepath ;
                _filepath = getDirectoryName() + File.separator + _filepath ;
            }

            //File.separator => back or forward slash
            FileWriter myWriter = new FileWriter(_filepath);
            myWriter.write(getContent());
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Usage: Write file with appending new content to last row
     */
    public static void WriteFile(Boolean append){
        try {

            String _filepath = getFilename()  + "." + getFiletype();

            if(!getDirectoryName().isEmpty()){
                //_filepath = "C:\\Users\\USER\\Documents\\MySTARS\\" + getDirectoryName() + File.separator + _filepath ;
                _filepath = getDirectoryName() + File.separator + _filepath ;
            }

            //File.separator => back or forward slash
            FileWriter myWriter = new FileWriter(_filepath,append);
            myWriter.write(getContent() + "\n");
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Usage: Update file
     */
    public static void UpdateFile(){

    }
}


