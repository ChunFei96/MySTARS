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

    public User(){

    }

    private int CheckCourseVacancy(int index_ID){
        System.out.println("CheckCourseVacancy");
        return 0;
    }
  
}