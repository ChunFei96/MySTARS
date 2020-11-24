
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

/**
 * Handle all the account action for user
 * @author Tan Wen Jun & Mo Naiming
 * @version 1.0
 * @since 2020-11-10
 */
public class Account {
    /**
     * user password
     */
    private String password;
    /**
     * user's username
     */
    private String username;
    /**
     * user role (student/admin)
     */
    private EnumHelper.UserRole userRole;

    /**
     * Get the user password
     * @return this user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user password
     * @param password raw password input to be encrypted when create account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the user's username
     * @return this user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the user's username
     * @param username username input when create account
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the user's role (student/admin)
     * @param userRole specific the user role
     */
    public void setUserRole(EnumHelper.UserRole userRole){
        this.userRole = userRole;
    }

    /**
     * Get the user's role (student/admin)
     * @return Enum.UserRole (student/admin)
     */
    public EnumHelper.UserRole getUserRole(){
        return userRole;
    }

    /**
     * Create a new user account by passing in raw username and password (to be encrypted)
     * @param username raw username input by user
     * @param password raw password input by user
     */
    //Constructor
    public Account(String username, String password) {
        this.password = password;
        this.username = username;
    }

    /**
     * Usage: Create new User account and save it into /UserTable/user.txt
     * <p> </p>
     * required inputs: username,password,salt,securePassword,role
     */
    public void addAccount(){

        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // getPassword(): retrieve the user's input password to generate Secure password
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(getPassword(), salt);

        // Print out protected password
//        System.out.println("My secure password: " + mySecurePassword);
//        System.out.println("Salt value: " + salt);

        //Content format: <username>,<password>,<salt>,<securePassword>,<role>
        String content = getUsername() + "," + getPassword() + "," + salt + "," + mySecurePassword + "," + getUserRole() ;

        String path = "C:/Users/USER/Documents/MySTARS" + "/UserTable";
        //String path = System.getProperty("user.dir") + "/UserTable";

        if(Files.notExists(Path.of(path))){
            IOUtills.createDirectory("UserTable");
        }

        IOUtills.ReadFile(path + "/user.txt");
        ArrayList<String> data = IOUtills.getFileInput();
        content = String.join("\r\n",data) + "\r\n" + content;

        new IOUtills("user","txt",content,"UserTable");
        IOUtills.WriteFile();
    }

    /**
     * Retrieve user's account info
      * @param username username used to verify user info
     * @return Login reference
     */
    public Login getAccountInfo(String username){

        String inputFile = "C:/Users/USER/Documents/MySTARS" + "/UserTable/user.txt";
        //String inputFile = System.getProperty("user.dir") + "/UserTable/user.txt";

        //Check against UserTable to find user data
        if(Files.notExists(Path.of(inputFile))){
            return null;
        }

        var singleton_userTable = Singleton_UserTable.getInstance().userDB;
        for(Login userLogin : singleton_userTable){
            if(userLogin.getMyUsername().equals(username)){
                return userLogin;
            }
        }
        return null;
    }

    /**
     * Retrieve student's profile when user is student role
     * @return student reference
     */
    public Student getStudentProfile(){
        Singleton_StudentProfile studentProfile = Singleton_StudentProfile.getInstance();
        Student student = null;

        if(studentProfile.studentProfileDB != null && studentProfile.studentProfileDB.size() > 0)
        {
            for(int i=0; i < studentProfile.studentProfileDB.size(); i++)
            {
                if(studentProfile.studentProfileDB.get(i).getUsername().equals(this.getUsername()) &&
                        studentProfile.studentProfileDB.get(i).getPassword().equals(this.getPassword()))
                {
                    student = studentProfile.studentProfileDB.get(i);
                }
            }
        }
        return student;
    }

    /**
     * Usage: retrieve the Admin profile when the login user is Admin type
     * @return user as admin
     */
    public Admin getAdminProfile(){
        String inputFile = "C:/Users/USER/Documents/MySTARS" + "/AdminProfile/adminProfile.txt";
        //String inputFile = System.getProperty("user.dir") + "/AdminProfile/adminProfile.txt";

        //Check against UserTable to find user data
        if(Files.notExists(Path.of(inputFile))){
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
