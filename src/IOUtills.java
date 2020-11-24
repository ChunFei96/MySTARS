
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handle all the read and write to txt file action
 * @author Tan Wen Jun & Mo Naiming
 * @version 1.0
 * @since 2020-11-10
 */
public class IOUtills {
    /**
     * file name
     */
    private static String filename;
    /**
     * file type (.txt)
     */
    private static String filetype;
    /**
     * file context (concatenated by comma)
     */
    private static String content;
    /**
     * file directory path
     */
    private static String directoryName;
    /**
     * list of file input
     */
    private static ArrayList<String> fileInput = new ArrayList<String>();

    /**
     * Get file name
     * @return name in string
     */
    public static String getFilename() {
        return filename;
    }

    /**
     * Set file name
     * @param filename
     */
    public static void setFilename(String filename) {
        IOUtills.filename = filename;
    }

    /**
     * Get file type (.txt file)
     * @return
     */
    public static String getFiletype() {
        return filetype;
    }

    /**
     * Set file type
     * @param filetype
     */
    public static void setFiletype(String filetype) {
        IOUtills.filetype = filetype;
    }

    /**
     * Get file content
     * @return content in string
     */
    public static String getContent() {
        return content;
    }

    /**
     * Set file content
     * @param content
     */
    public static void setContent(String content) {
        IOUtills.content = content;
    }

    /**
     * Get file directory path
     * @return directory path in string
     */
    public static String getDirectoryName() {
        return directoryName;
    }

    /**
     * Set file directory path
     * @param directoryName
     */
    public static void setDirectoryName(String directoryName) {
        IOUtills.directoryName = directoryName;
    }

    /**
     * Get list of file input
     * @return array list of file input
     */
    public static ArrayList<String> getFileInput() {
        return fileInput;
    }

    /**
     * Set list of file input
     * @param fileInput
     */
    public static void setFileInput(ArrayList<String> fileInput) {
        IOUtills.fileInput = fileInput;
    }

    /**
     * Usage: Create new directory
     */
    public static void createDirectory(String newFolderName){
        //File file = new File("C:/Users/USER/Documents/MySTARS" + "/" + newFolderName);
        File file = new File("C:/Users/USER/Documents/MySTARS"  + "/" + newFolderName);

        // true if the directory was created, false otherwise
        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Directory already exists!");
        }
    }

    /**
     * Create a IOUtills object with attributes below
     * @param filename file name
     * @param filetype file type (.txt)
     * @param content content with comma concatenated
     * @param directoryName file directory path
     */
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
}


