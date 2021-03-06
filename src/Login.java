import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Handle all the account action for user
 * @author Tan Wen Jun and Mo Naiming
 * @version 1.0
 * @since 2020-11-10
 */
public class Login {
    /**
     * username input
     */
    private String myUsername;
    /**
     * username password
     */
    private String myPassword;
    /**
     * user role
     */
    private EnumHelper.UserRole myRole;
    /**
     * secure password
     */
    private String securePassword;
    /**
     * salt for encryption
     */
    private String salt;
    /**
     * user's account
     */
    private Account user;

    /**
     * Get user's username
     * @return username in string
     */
    public String getMyUsername() {
        return myUsername;
    }

    /**
     * Set user's username
     * @param myUsername
     */
    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    /**
     * Get user's password
     * @return password in string
     */
    public String getMyPassword() {
        return myPassword;
    }

    /**
     * Set user's password
     * @param myPassword
     */
    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    /**
     * Get secure password
     * @return secure password in string
     */
    public String getSecurePassword() {
        return securePassword;
    }

    /**
     * Set secure password
     * @param securePassword
     */
    public void setSecurePassword(String securePassword) {
        this.securePassword = securePassword;
    }

    /**
     * Get salt
     * @return salt in string
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Set salt
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Get user account
     * @return account reference
     */
    public Account getUser() {
        return user;
    }

    /**
     * Set user account
     * @param user
     */
    public void setUser(Account user) {
        this.user = user;
    }

    /**
     * Set user role
     * @param userRole
     */
    public void setMyRole(EnumHelper.UserRole userRole){
        this.myRole = userRole;
    }

    /**
     * Get user role
     * @return Enum.UserRole
     */
    public EnumHelper.UserRole getMyRole(){
        return myRole;
    }

    /**
     * Create a login object to take it user input for username and password
     * @param myUsername
     * @param myPassword
     */
    public Login(String myUsername, String myPassword) {
        this.myUsername = myUsername;
        this.myPassword = myPassword;
        user = new Account(myUsername,myPassword);
    }

    /**
     * Create a login object to take it encryption attributes below
     * @param myUsername
     * @param myPassword
     * @param salt
     * @param securePassword
     * @param role
     */
    public Login(String myUsername, String myPassword,String salt,String securePassword,EnumHelper.UserRole role) {
        this.myUsername = myUsername;
        this.myPassword = myPassword;
        this.salt = salt;
        this.securePassword = securePassword;
        this.setMyRole(role);
        user = new Account(myUsername,myPassword);  // <---
    }

    /**
     * Usage: validate the sign-in user has the access to the system,
     * where the function checks for user's account info, matched password and access period.
     * @return status to indicate the login is permitted
     */
    public Boolean validateLogin(){

        // User provided password to validate
        String providedPassword = getMyPassword();

        // Encrypted and Base64 encoded password read from database

        //String securePassword = "c96FD/wHxrtidRCDTeuYZE/fWsvDF0nvz6Gr9AGK2wo=";

        try{

            //Login currentUser = getUser().getAccountInfo();  //Ori
            Login currentUser = getUser().getAccountInfo(getMyUsername());

            if(currentUser == null){
                System.out.println("Login failed, try again.");
                return false;
            }

//            setSalt(currentUser.salt);
//            setSecurePassword(currentUser.securePassword);

            boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword,
                    currentUser.getSecurePassword(),currentUser.getSalt());

            //<editor-fold desc="<Validate Student Access Period>">
            if(currentUser.getMyRole().equals(EnumHelper.UserRole.STUDENT)){
                for(Student stu : Singleton_StudentProfile.getInstance().studentProfileDB){
                    if(stu.getUsername().equals(getMyUsername())){
                        if(!checkAccessPeriod(stu.getAccessPeriodEnd())){
                            System.out.println("You are not allowed access after the defined period!");
                            return false;
                        }
                        break;
                    }
                }
            }
            //</editor-fold>

            if(passwordMatch)
            {
                user.setUserRole(currentUser.getMyRole());
                return true;
            } else {
                System.out.println("Username/Password is invalid! Please try again");
                return false;
            }
        }
        catch (Exception e){
            System.out.println("Aw, exception: " + e);
            return false;
        }
    }

    /**
     * Get student profile information
     * @return student reference
     */
    public Student getStudentProfile(){
        return getUser().getStudentProfile();
    }

    /**
     * Get admin profile information
     * @return admin reference
     */
    public Admin getAdminProfile(){
        return getUser().getAdminProfile();
    }

    /**
     * Check student's access period
     * @param accessPeriod user's accessPeriod from UserTable
     * @return status to represent the user is good to login (login within the access period)
     * @throws ParseException invalid date
     */
    public static boolean checkAccessPeriod(String accessPeriod) throws ParseException {

        LocalDateTime getTodayDate = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Date _userAccessPeriod = new SimpleDateFormat("dd/MM/yyyy").parse(accessPeriod);
        Date _now = new Date();

        if(_userAccessPeriod.after(_now)){
            return true;
        }
        return false;
    }

    /**
     * To sign out user
     */
    public void SignOut(){
        System.out.println("User has been signed out!");
    }
}
