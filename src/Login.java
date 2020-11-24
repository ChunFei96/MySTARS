import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Entity Class : Login
 */
public class Login {

    private String myUsername;
    private String myPassword;
    private EnumHelper.UserRole myRole;
    private String securePassword;
    private String salt;
    private Account user;

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getSecurePassword() {
        return securePassword;
    }

    public void setSecurePassword(String securePassword) {
        this.securePassword = securePassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public void setMyRole(EnumHelper.UserRole userRole){
        this.myRole = userRole;
    }

    public EnumHelper.UserRole getMyRole(){
        return myRole;
    }

    //Constructor
    public Login(String myUsername, String myPassword) {
        this.myUsername = myUsername;
        this.myPassword = myPassword;
        user = new Account(myUsername,myPassword);
    }

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
                return false;
            }
        }
        catch (Exception e){
            System.out.println("Aw, exception: " + e);
            return false;
        }
    }

    public Student getStudentProfile(){
        return getUser().getStudentProfile();
    }

    public Admin getAdminProfile(){
        return getUser().getAdminProfile();
    }

    /**
     *
     * @param accessPeriod user's accessPeriod from UserTable
     * @return status to represent the user is good to login (login within the access period)
     * @throws ParseException
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

    public void SignOut(){
        System.out.println("User has been signed out!");
    }
}
