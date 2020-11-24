
/**
 * Entity Class of User
 *
 */
public class User{

    private String name;
    private String nationality;
    private String email;
    private String username;
    private String password;
    private String contactNo;
    private EnumHelper.UserRole role;
    private EnumHelper.Gender sex;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public EnumHelper.UserRole getUserRole() {
        return role;
    }

    public EnumHelper.Gender getGender(){
        return sex;
    }

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