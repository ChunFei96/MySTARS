
/*
*  TODO
*
*  Prepare user login accounts (username,password, salt,hash)
*  Manually write / update it into user data file
*  Read user login info txt
*  Authenticate the login credentials
*
*
*
* */

//https://www.appsdeveloperblog.com/encrypt-user-password-example-java/ (Usng)

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Account {

    private String password;
    private String username;
    private EnumHelper.UserRole userRole;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserRole(EnumHelper.UserRole userRole){
        this.userRole = userRole;
    }

    public EnumHelper.UserRole getUserRole(){
        return userRole;
    }

    //Constructor
    public Account(String username, String password, EnumHelper.UserRole role) {
        this.password = password;
        this.username = username;
        this.userRole = role;
        System.out.println("username: <<" +  username + ">>");
        System.out.println("password: <<" +  password + ">>");
    }

    //Create new User account
    public void addAccount(){

        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // getPassword(): retrieve the user's input password to generate Secure password
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(getPassword(), salt);

        // Print out protected password
        System.out.println("My secure password: " + mySecurePassword);
        System.out.println("Salt value: " + salt);

        //Content format: <username>,<password>,<salt>,<securePassword>,<role>
        String content = getUsername() + "," + getPassword() + "," + salt + "," + mySecurePassword + "," + getUserRole() ;

        IOUtills userCredentials = new IOUtills(getUsername(),"txt",content,"UserTable");
        IOUtills.createDirectory("UserTable");
        IOUtills.WriteFile();
    }

    public Login getAccountInfo(){

        String inputFile = System.getProperty("user.dir") + "/UserTable/" + getUsername() +  ".txt";

        //Check against UserTable to find user data
        if(Files.notExists(Path.of(inputFile))){
            //System.out.println("Please try login again!");
            return null;
        }

        IOUtills.ReadFile(inputFile);
        ArrayList<String> output = IOUtills.getFileInput(); //get the file data

        String[] userData = output.get(0).split(",");

        if(userData != null){

            Login currentUser = new Login(userData[0],userData[1]);
            currentUser.setSalt(userData[2]);
            currentUser.setSecurePassword(userData[3]);
            currentUser.setMyRole(EnumHelper.UserRole.valueOf(userData[4]));

            return currentUser;
        }
        return null;
    }

    public Student getStudentProfile(){
        String inputFile = System.getProperty("user.dir") + "/StudentProfile/" + getUsername() +  ".txt";

        //Check against UserTable to find user data
        if(Files.notExists(Path.of(inputFile))){
            //System.out.println("Please try login again!");
            return null;
        }

        IOUtills.ReadFile(inputFile);
        ArrayList<String> output = IOUtills.getFileInput(); //get the file data

        String[] userData = output.get(0).split(",");

        if(userData != null){

            Student student = new Student(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5],
                    EnumHelper.UserRole.valueOf(userData[6]), EnumHelper.Gender.valueOf(userData[7]), userData[8]);
            return student;
        }
        return null;
    }

    public Admin getAdminProfile(){
        String inputFile = System.getProperty("user.dir") + "/AdminProfile/" + getUsername() +  ".txt";

        //Check against UserTable to find user data
        if(Files.notExists(Path.of(inputFile))){
            //System.out.println("Please try login again!");
            return null;
        }

        IOUtills.ReadFile(inputFile);
        ArrayList<String> output = IOUtills.getFileInput(); //get the file data

        String[] userData = output.get(0).split(",");

        if(userData != null){

            Admin admin = new Admin(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5],
                    EnumHelper.UserRole.valueOf(userData[6]), EnumHelper.Gender.valueOf(userData[7]));
            return admin;
        }
        return null;
    }
}
