

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

    public Boolean validateLogin(){

        //TODO: change providedPassword, securePassword to pull from UserTable

        // User provided password to validate
        String providedPassword = getMyPassword();

        // Encrypted and Base64 encoded password read from database

        //String securePassword = "c96FD/wHxrtidRCDTeuYZE/fWsvDF0nvz6Gr9AGK2wo=";

        try{

            Login currentUser = getUser().getAccountInfo();

            if(currentUser == null){
                System.out.println("Login failed, try again.");
                return false;
            }

            setSalt(currentUser.salt);
            setSecurePassword(currentUser.securePassword);

            boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword,getSecurePassword(),getSalt());
            if(passwordMatch)
            {
                System.out.println("Provided user password <<" + providedPassword + ">> is correct.");
                user.setUserRole(currentUser.getMyRole());
                return true;
            } else {
                System.out.println("Provided password is incorrect");
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

    public void SignOut(){
        System.out.println("User has been signed out!");
    }
}
