



public class Login {

    private String username;
    private String password;


    public Login(String username,String password){
        this.username = username;
        this.password = password;
    }

    /*
        https://www.baeldung.com/java-password-hashing
        https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
        http://www.mindrot.org/projects/jBCrypt/

     */
    private String HashPassword(String password){
        System.out.println("HashPassword");
        return "test";
    }

    private Boolean AuthenticateAccount(String username,String hash){
        System.out.println("AuthenticateAccount");
        return true;
    }
    
}
