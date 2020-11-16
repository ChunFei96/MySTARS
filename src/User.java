public class User{

    enum gender{
        MALE,
        FEMALE
    }

    enum role{
        STUDENT,
        ADMIN
    }

    enum status{
        ACTIVE,
        DEACTIVED
    }

    private String name;
    private String nationality;
    private String email;
    private String username;
    private String password;
    private int contactNo;

    public User(String name, String nationality, String email, String username, String password, int contactNo) {
        this.name = name;
        this.nationality = nationality;
        this.email = email;
        this.username = username;
        this.password = password;
        this.contactNo = contactNo;
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

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }



}