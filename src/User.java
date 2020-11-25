
/**
 * Entity Class of User
 * @author Lee Chun Fei and Tan Wen Jun
 * @version 1.0
 * @since 2020-11-16
 */
public class User{
    /**
     * user's name
     */
    private String name;
    /**
     * user's nationality
     */
    private String nationality;
    /**
     * user's email
     */
    private String email;
    /**
     * user's username
     */
    private String username;
    /**
     * user's password
     */
    private String password;
    /**
     * user's contact number
     */
    private String contactNo;
    /**
     * user's role (student/admin)
     */
    private EnumHelper.UserRole role;
    /**
     * user's gender
     */
    private EnumHelper.Gender sex;

    /**
     * Create user with attributes below
     * @param name
     * @param nationality
     * @param email
     * @param username
     * @param password
     * @param contactNo
     * @param role
     * @param sex
     */
    public User(String name, String nationality, String email, String username, String password, String contactNo, EnumHelper.UserRole role, EnumHelper.Gender sex) {
        this.name = name;
        this.nationality = nationality;
        this.email = email;
        this.username = username;
        this.password = password;
        this.contactNo = contactNo;
        this.role = role;
        this.sex = sex;
    }

    /**
     * Get user's name
     * @return user's name in string
     */
    public String getName() {
        return name;
    }

    /**
     * Set user's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user's nationality
     * @return nationality in string
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Set user's nationality
     * @param nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Get user's email
     * @return email in string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user's email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user's username
     * @return username in string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set user's username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get user's password
     * @return password in string
     */
    public String getPassword() {
        return password;
    }

    /**
     *  Set user's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user's contact number
     * @return contact number in string
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Set user's contact number
     * @param contactNo
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * Get user's role
     * @return role in Enum
     */
    public EnumHelper.UserRole getUserRole() {
        return role;
    }

    /**
     * Ger user's gender
     * @return gender in Enum
     */
    public EnumHelper.Gender getGender(){
        return sex;
    }

    /**
     * Set user's gender
     * @param sex
     */
    public void setGender(EnumHelper.Gender sex) { this.sex = sex;}

    /**
     * Example output = "name,nationality,email,...sex"
     * @return User object in string with comma separator for all the attributes
     */
    public String ToStr(){
        String[] raw = new String[]{name,nationality,email,username,
                password,contactNo, String.valueOf(role), String.valueOf(sex)};

        return String.join(",",raw);
    }

}