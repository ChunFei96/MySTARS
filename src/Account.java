
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
    public Account(String username, String password) {
        this.password = password;
        this.username = username;
//        System.out.println("Account username: <<" +  username + ">>");
//        System.out.println("Account password: <<" +  password + ">>");
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

        //new IOUtills(getUsername(),"txt",content,"UserTable");

        String path = "C:/Users/USER/Documents/MySTARS" + "/UserTable";
        if(Files.notExists(Path.of(path))){
            IOUtills.createDirectory("UserTable");
        }

        IOUtills.ReadFile(path + "/user.txt");
        ArrayList<String> data = IOUtills.getFileInput();
        content = String.join("\r\n",data) + "\r\n" + content;

        new IOUtills("chunfei","txt",content,"UserTable");
        IOUtills.WriteFile();
    }

    public Login getAccountInfo(String username){

        String inputFile = "C:/Users/USER/Documents/MySTARS" + "/UserTable/user.txt";

        //Check against UserTable to find user data
        if(Files.notExists(Path.of(inputFile))){
            return null;
        }

//        IOUtills.ReadFile(inputFile);
//        ArrayList<String> output = IOUtills.getFileInput(); //get the file data
//        String[] userData = output.get(0).split(",");
//        if(userData != null){
//
//            Login currentUser = new Login(userData[0],userData[1]);
//            currentUser.setSalt(userData[2]);
//            currentUser.setSecurePassword(userData[3]);
//            currentUser.setMyRole(EnumHelper.UserRole.valueOf(userData[4]));
//
//            return currentUser;
//        }

        var singleton_userTable = Singleton_UserTable.getInstance().userDB;

        for(Login userLogin : singleton_userTable){
            if(userLogin.getMyUsername().equals(username)){
                return userLogin;
            }
        }
        return null;
    }

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

//        String inputFile = System.getProperty("user.dir") + "/StudentProfile/StudentProfile.txt";
//
//        //Check against UserTable to find user data
//        if(Files.notExists(Path.of(inputFile))){
//            //System.out.println("Please try login again!");
//            return null;
//        }
//
//        IOUtills.ReadFile(inputFile);
//        ArrayList<String> output = IOUtills.getFileInput(); //get the file data
//
//        //TODO not always get the first one
//        String[] userData = output.get(0).split(",");

//        if(student != null){
////            Student student = new Student(userData[0], userData[1], userData[2], userData[3],
////                    userData[4], userData[5], EnumHelper.UserRole.valueOf(userData[6]),
////                    EnumHelper.Gender.valueOf(userData[7]), userData[8],userData[9],userData[10]);
//
//            // retrieve all the registered course
//            Singleton_StudentCourse studentCourse = Singleton_StudentCourse.getInstance();
//            Singleton_CourseInfo courseInfo = Singleton_CourseInfo.getInstance();
//
//            ArrayList<StudentCourse> studentCourseArrayList = new ArrayList<>();
//
//            for(int i=0; i < studentCourse.studentCourseDB.size(); i++)
//            {
//                if(studentCourse.studentCourseDB.get(i).getStudentMatricNo().toUpperCase().equals(student.getMatricNo().toUpperCase()))
//                {
//                    studentCourseArrayList.add(studentCourse.studentCourseDB.get(i));
//                }
//            }
//
//            ArrayList<CourseInfo> courseInfoArrayList = new ArrayList<>();
//
//            for(int j=0; j < courseInfo.courseInfoDB.size(); j++)
//            {
//                for(int k=0; k < studentCourseArrayList.size(); k++)
//                {
//                    if(courseInfo.courseInfoDB.get(j).getCode().equals(studentCourseArrayList.get(k).getCourseCode()))
//                    {
//                        CourseInfo courseInfo1 = new CourseInfo(courseInfo.courseInfoDB.get(j));
//
//
//                        for(int z=0; z< courseInfo.courseInfoDB.get(j).getClassList().size() ; z++)
//                        {
//                            if(courseInfo.courseInfoDB.get(j).getClassList().get(z).getIndexNo().equals(studentCourseArrayList.get(k).getClassIndex()))
//                            {
//                                courseInfo1.addClass(courseInfo.courseInfoDB.get(j).getClassList().get(z));
//                            }
//                        }
//                        student.addCourse(courseInfo1);
//                    }
//                }
//            }
//
////            //remove not related class
////            for(int z=0; z < student.getCourseInfoList().size(); z++)
////            {
////                for(int a = 0; a < student.getCourseInfoList().get(z).getClassList().size(); )
////                {
////                    for(int b=0; b < studentCourseArrayList.size(); b++)
////                    {
////                        if(student.getCourseInfoList().get(z).getCode().equals(studentCourseArrayList.get(b).getCourseCode()) &&
////                                !student.getCourseInfoList().get(z).getClassList().get(a).getIndexNo().equals(studentCourseArrayList.get(b).getClassIndex()))
////                        {
////                            student.getCourseInfoList().get(z).getClassList().remove(student.getCourseInfoList().get(z).getClassList().get(a));
////                        }
////                        else
////                            a++;
////                    }
////                }
////
////            }
//
////            for(int z=0; z < courseInfo.courseInfoDB.get(j).getClassList().size(); z++)
////            {
////                if(courseInfo.courseInfoDB.get(j).getClassList().get(z).getIndexNo().equals(studentCourseArrayList.get(k).getClassIndex()))
////                {
////                    courseInfo1.addClass(courseInfo.courseInfoDB.get(j).getClassList().get(z));
////                }
////            }
//
//
//            return student;
//        }
        return student;
    }

    public Admin getAdminProfile(){


        boolean del = true;
        if(del){
            String inputFile = "C:/Users/USER/Documents/MySTARS" + "/AdminProfile/chunfei.txt";

            //Check against UserTable to find user data
            if(Files.notExists(Path.of(inputFile))){
                //IOUtills.createDirectory("AdminProfile");
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
        }

        return null;
    }
}
