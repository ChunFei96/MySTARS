import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CourseManager {

    private MailHelper mailHelper;

    public CourseManager()
    {
        mailHelper = new MailHelper();
    }

    //<editor-fold desc="Student">
    public void AddCourse(Student student, CourseInfo courseInfo)
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();
        Singleton_StudentCourse singleton_studentCourse = Singleton_StudentCourse.getInstance();

        student.addCourse(courseInfo);

        // add course in student course DB
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentMatricNo(student.getMatricNo());
        studentCourse.setCourseCode(courseInfo.getCode());
        studentCourse.setClassIndex(courseInfo.getClassList().get(0).getIndexNo());

        singleton_studentCourse.studentCourseDB.add(studentCourse);

        // write back to Student Course txt file
        String content = student.getMatricNo() + "," + courseInfo.getCode() + "," + courseInfo.getClassList().get(0).getIndexNo() ;

            UpdateDB("StudentCourse","txt",content,"StudentCourseTable",true);

        // decrease the course class's vacancy by 1
        if(singleton_courseInfo.courseInfoDB != null && singleton_courseInfo.courseInfoDB.size() > 0)
        {
            String classContent = "";
            for(int j=0; j < singleton_courseInfo.courseInfoDB.size(); j++)
            {
                CourseInfo courseInfo1 = singleton_courseInfo.courseInfoDB.get(j);
                if(courseInfo1.getCode() == courseInfo.getCode())
                {
                    for(int k=0; k < singleton_courseInfo.courseInfoDB.get(j).getClassList().size(); k++)
                    {
                        ClassInfo classInfo = singleton_courseInfo.courseInfoDB.get(j).getClassList().get(k);
                        if(classInfo.getIndexNo() == courseInfo.getClassList().get(0).getIndexNo())
                        {
                            if(classInfo.getVacancy() > 0)
                                classInfo.setVacancy(courseInfo.getClassList().get(0).getVacancy() - 1);
                            else
                                classInfo.setQueue(courseInfo.getClassList().get(0).getQueue() + 1);
                        }
                        classContent += classInfo.getIndexNo() + "," + classInfo.getCourseCodeReference() + "," + classInfo.getGroupNo() + "," + classInfo.getDay() + "," +
                                classInfo.getTime() + "," + classInfo.getVenue() + "," + classInfo.getRemark() + "," + classInfo.getVacancy() + "," + classInfo.getQueue() + "\n";
                    }
                }
            }
            UpdateDB(courseInfo.getCode()+ "_Class","txt",classContent,"ClassTable",false);
        }

        mailHelper.setRecipientEmail(student.getEmail());
        mailHelper.setMessage("Congratulation! you have registered " + courseInfo.getName() + " !");
        mailHelper.setEmailSubject("Successfully registered " + courseInfo.getName() + " !");
        //mailHelper.SendEmail();

    }

    public void DropCourse(Student student, CourseInfo courseInfo)
    {
        Singleton_CourseInfo singleton_courseInfo = Singleton_CourseInfo.getInstance();
        Singleton_StudentCourse singleton_studentCourse = Singleton_StudentCourse.getInstance();

         // remove course in student courseArrayList
         student.dropCourse(courseInfo);

         // remove course in student course DB
        if(singleton_studentCourse.studentCourseDB != null && singleton_studentCourse.studentCourseDB.size() > 0)
        {
            String content = "";
            for(int i=0; i < singleton_studentCourse.studentCourseDB.size();)
            {
                StudentCourse removedstudentCourse = singleton_studentCourse.studentCourseDB.get(i);
                if(removedstudentCourse.getStudentMatricNo() == student.getMatricNo() && removedstudentCourse.getCourseCode() == courseInfo.getCode() &&
                        removedstudentCourse.getClassIndex() == courseInfo.getClassList().get(0).getIndexNo())
                    singleton_studentCourse.studentCourseDB.remove(removedstudentCourse);
                else{
                    content += singleton_studentCourse.studentCourseDB.get(i).getStudentMatricNo() + "," + singleton_studentCourse.studentCourseDB.get(i).getCourseCode() + "," + singleton_studentCourse.studentCourseDB.get(i).getClassIndex() + "\n" ;
                    i++;
                }


            }
            UpdateDB("StudentCourse","txt",content,"StudentCourseTable",false);
        }
        else
        {
            // the db is empty
            UpdateDB("StudentCourse","txt","","StudentCourseTable",false);

        }

        // increase the course class's vacancy by 1
        if(singleton_courseInfo.courseInfoDB != null && singleton_courseInfo.courseInfoDB.size() > 0)
        {
            String content = "";
            for(int j=0; j < singleton_courseInfo.courseInfoDB.size(); j++)
            {
                CourseInfo courseInfo1 = singleton_courseInfo.courseInfoDB.get(j);
                if(courseInfo1.getCode() == courseInfo.getCode())
                {
                    for(int k=0; k < singleton_courseInfo.courseInfoDB.get(j).getClassList().size(); k++)
                    {
                        ClassInfo classInfo = singleton_courseInfo.courseInfoDB.get(j).getClassList().get(k);
                        if(classInfo.getIndexNo() == courseInfo.getClassList().get(0).getIndexNo())
                        {
                            if(classInfo.getQueue() <=0)
                                classInfo.setVacancy(courseInfo.getClassList().get(0).getVacancy() + 1);
                            else
                                classInfo.setQueue(courseInfo.getClassList().get(0).getQueue() - 1);
                        }
                        content += classInfo.getIndexNo() + "," + classInfo.getCourseCodeReference() + "," + classInfo.getGroupNo() + "," + classInfo.getDay() + "," +
                                    classInfo.getTime() + "," + classInfo.getVenue() + "," + classInfo.getRemark() + "," + classInfo.getVacancy() + "," + classInfo.getQueue() + "\n";
                    }
                }
            }

            UpdateDB(courseInfo.getCode()+ "_Class","txt",content,"ClassTable",false);
        }


         mailHelper.setRecipientEmail(student.getEmail());
         mailHelper.setMessage("We are to inform you that you have dropped " + courseInfo.getName() + ". Please contact your school coordinator if you have not done so.");
         mailHelper.setEmailSubject("Successfully dropped " + courseInfo.getName() + " !");
         //mailHelper.SendEmail();

    }

    public void RegisteredCourses(Student student)
    {
        String menuTitle = student.getName() + " registered courses";
        System.out.println("\n=========================================== " +  menuTitle + " ================================================\n");
        if(student.getCourseInfoList() != null && student.getCourseInfoList().size() > 0)
        {
            for(int i=0; i < student.getCourseInfoList().size() ; i++)
            {
                System.out.println( i+1 + ": " + student.getCourseInfoList().get(i).getCourseInfo());
                System.out.println("   " + student.getCourseInfoList().get(i).getClassList().get(0).getClassInfo());
            }
        }
        else
            System.out.println("There is no registered course(s) for " + student.getName() + "!");
    }

//    public CourseInfo RegisteredCourses(Student student, int indexNo)
//    {
//        for(int i=0; i < student.getCourseInfoList().size() ; i++)
//        {
//            if(student.getCourseInfoList().get(i).getIndexNo().toUpperCase() == String.valueOf(indexNo).toUpperCase())
//            {
//                System.out.println(i+1 + ": " + student.getCourseInfoList().get(i).getCourseInfo());
//                return student.getCourseInfoList().get(i);
//            }
//        }
//        System.out.println("There is no course exists for this " + indexNo + " index number!");
//        return null;
//    }

    public void ChangeCourseIndexNumber(Student student, CourseInfo oldClass, CourseInfo newClass)
    {
        if(student.getCourseInfoList().contains(oldClass))
        {
            student.getCourseInfoList().add(newClass);
            student.getCourseInfoList().add(oldClass);
        }
        else
            System.out.println("This is not registered course(s) for " + student.getName() + "!");

    }

    public void SwapIndexNumber(int index_ID, int student_ID)
    {
        
    }
    //endregion
    //</editor-fold>

    //region Admin
    public void EditStudentAccessPeriod()
    {
        Scanner sr = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Student Matrix No: ");
        String studentMatrixNo = sr.nextLine();

        Student student = null;

        for(Student j : Singleton_StudentProfile.getInstance().studentProfileDB){
            if(j.getMatricNo().equals(studentMatrixNo)){
                student = j;  //Assign the student obj

                String [] info = new String[]{
                        j.getName(),j.getMatricNo(),j.getAccessPeriodStart(),j.getAccessPeriodEnd()
                };

                System.out.println("Retrieving Student Info: ");
                System.out.println(String.join(" | ",info));
                break;
            }
        }

        boolean valid = false;
        do{
            try{
                System.out.println("Enter New Access Period (dd/MM/yyyy): ");
                String _accessPeriod = sr.nextLine();

                new SimpleDateFormat("dd/MM/yyyy").parse(_accessPeriod);
                student.setAccessPeriodEnd(_accessPeriod);
                valid = true;
            }
            catch(Exception e){
                System.out.println("Invalid access period, please try again!");
            }
        }
        while(!valid);
        System.out.println("New Access Period: " + student.getAccessPeriodEnd());
    }

    public void AddStudent()
    {
        String filepath = System.getProperty("user.dir") + "/StudentProfile";
        IOUtills.ReadFile(filepath + "/user.txt");

        ArrayList<String> studentList = IOUtills.getFileInput();

        //<editor-fold desc="<User inputs>">
        Scanner sr = new Scanner(System.in);

        System.out.println("Enter name: ");
        String name = sr.nextLine();
        System.out.println("Student name: " + name);

        System.out.println("Enter nationality: ");
        String nationality = sr.nextLine();
        System.out.println("Student nationality: " + nationality);

        System.out.println("Enter email: ");
        String email = sr.nextLine();
        System.out.println("Student email: " + email);

        System.out.println("Enter contactNo: ");
        String contactNo = sr.nextLine();
        System.out.println("Student contactNo: " + contactNo);

        System.out.println("Enter sex (M/F): ");
        String sex = sr.nextLine();
        System.out.println("Student sex: " + sex);

        System.out.println("Enter matricNo: ");
        String matricNo = sr.nextLine();
        System.out.println("Student matricNo: " + matricNo);
        //</editor-fold>

        String username = name;  //Set username as name
        String password = PasswordUtils.generatePassword(8).toString(); //Generate Random password
        Enum gender = sex.equals("M") ? EnumHelper.Gender.MALE : EnumHelper.Gender.FEMALE;

        //<editor-fold desc="<Get Current Date>">
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String getTodayDate = myDateObj.format(myFormatObj);
        //</editor-fold>

        //TODO: set Period End - today + 3months
        String accessPeriodEnd = myDateObj.plusMonths(3).format(myFormatObj);
        Student newStudent = new Student(name,nationality,email,username,
                password,contactNo, EnumHelper.UserRole.STUDENT, (EnumHelper.Gender) gender,
                matricNo,getTodayDate,accessPeriodEnd);

        String formatStudent = newStudent.ToStr();
        formatStudent = String.join("\r\n",studentList) + "\r\n" + formatStudent;

        //<editor-fold desc="<Add into StudentProfile>">
        new IOUtills("chunfei","txt",formatStudent,filepath);
        IOUtills.WriteFile();
        //</editor-fold>

        //<editor-fold desc="<Add into UserTable>">
        Account user_2 = new Account(username,password);
        user_2.setUserRole(EnumHelper.UserRole.STUDENT);
        user_2.addAccount();
        //</editor-fold>
    }

    public void AddCourse()
    {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter Course code: ");
        String code = myObj.nextLine();

        System.out.println("Enter Course name: ");
        String name = myObj.nextLine();

        System.out.println("Enter Course type: ");
        String type = myObj.nextLine();

        String [] setCourseInfo = new String[]{code,name,type};
        CourseInfo courseInfo = new CourseInfo(String.join(",",setCourseInfo));

        System.out.println("Enter Total Number of Classes: ");
        int classNum = myObj.nextInt();
        myObj.nextLine();

        for(int n = 0;n < classNum;n++){
            System.out.println("======= Add New Class =======");
            ClassInfo classInfo = addClassInfo(myObj);
            courseInfo.addClass(classInfo);
            System.out.println("==============");
            System.out.println((n+1) + ". Added a new class.");
            System.out.println("==============");
        }

        Singleton_CourseInfo.getInstance().courseInfoDB.add(courseInfo);
    }

    public void UpdateCourse(){
        Scanner myObj = new Scanner(System.in);
        boolean valid = false;
        int counter = 0;

        ArrayList<CourseInfo> CourseInfo = Singleton_CourseInfo.getInstance().courseInfoDB;
        for(CourseInfo Course : CourseInfo){
            System.out.println((counter + 1) + ". " + Course.getCode() + " " + Course.getName());
            counter++;
        }

        int choice = 0;
        do{

            System.out.println("Select an option for update: ");
            choice = myObj.nextInt();

            if(!(choice > 0 && choice < CourseInfo.size())){
                System.out.println("Please enter a valid option!");
            }
            else{
                valid = true;
            }

        }while(!valid);

        CourseInfo courseInfo = CourseInfo.get((choice+1));
        var k = 0;

    }

    private ClassInfo addClassInfo(Scanner myObj){
        System.out.println("Enter Class Index No: ");
        String indexNo = myObj.nextLine();

        System.out.println("Enter Class Code: ");
        String courseCodeReference = myObj.nextLine();

        boolean validGroupNo = false;
        String groupNo = "0";
        do{
            try{
                System.out.println("Enter Class Group No: ");
                groupNo = myObj.nextLine();

                Integer.parseInt(groupNo);
                validGroupNo = true;
            }
            catch(Exception e){
                System.out.println("Please enter number only");
            }
        }while(!validGroupNo);

        System.out.println("Enter Class Day: ");
        String day = myObj.nextLine();

        System.out.println("Enter Class Time: ");
        String time = myObj.nextLine();

        System.out.println("Enter Class Venue: ");
        String venue = myObj.nextLine();

        boolean _validCourseType = false;
        int remark = 0;
        do{
            System.out.println("Select Class Type: ");
            int CourseTypeCounter = 1;
            for(var type : EnumHelper.CourseType.values()){
                System.out.println(CourseTypeCounter + " ." + type);
                CourseTypeCounter++;
            }
            remark = myObj.nextInt();

            if(remark > 0 && remark <= EnumHelper.CourseType.values().length){
                _validCourseType = true;
            }
            else {
                System.out.println("Please select a valid option!");
            }
        }while(!_validCourseType);

        String setRemark = EnumHelper.CourseType.ONLINE.toString();
        switch(remark){
            case 1:
                setRemark = EnumHelper.CourseType.ONLINE.toString();
                break;
            case 2:
                setRemark = EnumHelper.CourseType.LEC.toString();
                break;
            case 3:
                setRemark = EnumHelper.CourseType.LEC_TUT.toString();
                break;
            case 4:
                setRemark = EnumHelper.CourseType.LEC_TUT_LAB.toString();
                break;
        }

        myObj.nextLine();

        System.out.println("Enter Class Vacancy");
        String vacancy = myObj.nextLine();
        String queue = "0";

        String [] _classInfo = new String[] {indexNo,courseCodeReference,groupNo,day,time,venue,setRemark,vacancy,queue};
        ClassInfo newClass = new ClassInfo(String.join(",",_classInfo));
        return newClass;
    }

    public void PrintStudentListByIndex()
    {
        Scanner myObj = new Scanner(System.in);
        System.out.println("===== Print Student List by Index ID =====");

        System.out.println("Enter an Index No: ");
        String indexNo = myObj.nextLine();

        ArrayList<StudentCourse> data = Singleton_StudentCourse.getInstance().studentCourseDB;
        List<StudentCourse> _StudentProfile = data.stream().filter(c -> c.getClassIndex().equals(indexNo)).collect(Collectors.toList());
        ArrayList<Student> students = Singleton_StudentProfile.getInstance().studentProfileDB;
        ArrayList<String> output = new ArrayList<>();

        int counter = 0;
        if(_StudentProfile.size() > 0){
            System.out.println("\r\n===============");
            System.out.println("Print Student List by Index ID: " + indexNo);
            System.out.println("===============");
            for(var j : _StudentProfile){

                List<Student> stds = students.stream().filter(m -> m.getMatricNo().
                        equals(j.getStudentMatricNo()))
                        .collect(Collectors.toList());

                if(stds.stream().count() > 0){
                    Student studentInfo = stds.get(0);
                    String name = studentInfo.getName();
                    String nationality = studentInfo.getNationality();
                    String sex = studentInfo.getGender().toString();
                    String [] reportContent = new String[] {name,nationality,sex};
                    String print = (counter+1) + ". " + String.join(" | ",reportContent);
                    System.out.println(print);

                    output.add(print);
                    counter++;
                }
            }
            System.out.println("=====END=====");

            Scanner sr = new Scanner(System.in);
            boolean valid = false;
            do{
                System.out.println("Do you want to output the report? (Y/N)");
                String generateTxt = sr.nextLine().toUpperCase();

                if(generateTxt.equals("Y")){
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = formatter.format(currentTime);
                    IOUtills.setFilename("StudentList_" + indexNo + "_" + strDate);
                    IOUtills.setDirectoryName(System.getProperty("user.dir"));
                    IOUtills.setFiletype("txt");
                    IOUtills.setContent(String.join("\r\n",output));
                    IOUtills.WriteFile();
                    valid = true;
                }
                else if(generateTxt.equals("N")){
                    valid = true;
                    System.out.println("Pending message for NOT printing the report.");
                }
            }while(!valid);
        }
        else{
            System.out.println("Index No not found, please try again!");
        }
    }

    public void PrintStudentListByCourse(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("===== Print StudentList By Course =====");
        System.out.println("Enter a Course Code: ");
        String courseCode = myObj.nextLine();

        ArrayList<StudentCourse> data = Singleton_StudentCourse.getInstance().studentCourseDB;
        List<StudentCourse> _StudentProfile = data.stream().filter(c -> c.getCourseCode().equals(courseCode)).collect(Collectors.toList());

        ArrayList<Student> students = Singleton_StudentProfile.getInstance().studentProfileDB;
        ArrayList<String> output = new ArrayList<>();

        int counter = 0;
        if(_StudentProfile.size() > 0){
            System.out.println("\r\n===============");
            System.out.println("Print Student List by Course Name: " + _StudentProfile.get(0).getCourseCode());
            System.out.println("===============");

            for(var j : _StudentProfile){

                List<Student> stds = students.stream().filter(m -> m.getMatricNo().
                        equals(j.getStudentMatricNo()))
                        .collect(Collectors.toList());

                if(stds.stream().count() > 0){
                    Student studentInfo = stds.get(0);
                    String name = studentInfo.getName();
                    String nationality = studentInfo.getNationality();
                    String sex = studentInfo.getGender().toString();
                    String [] reportContent = new String[] {name,nationality,sex};

                    String print = (counter+1) + ". " + String.join(" | ",reportContent);
                    System.out.println(print);

                    output.add(print);
                    counter++;
                }
            }
            System.out.println("=====END=====");

            Scanner sr = new Scanner(System.in);
            boolean valid = false;
            do{
                System.out.println("Do you want to output the report? (Y/N)");
                String generateTxt = sr.nextLine().toUpperCase();

                if(generateTxt.equals("Y")){
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = formatter.format(currentTime);
                    IOUtills.setFilename("StudentList_" + _StudentProfile.get(0).getCourseCode() + "_" + strDate);
                    IOUtills.setDirectoryName(System.getProperty("user.dir"));
                    IOUtills.setFiletype("txt");
                    IOUtills.setContent(String.join("\r\n",output));
                    IOUtills.WriteFile();
                    valid = true;
                }
                else if(generateTxt.equals("N")){
                    valid = true;
                    System.out.println("Pending message for NOT printing the report.");
                }
            }while(!valid);
        }
        else{
            System.out.println("Course Code not found, please try again!");
        }
    }

    public int CheckCourseVacancy(){
        int counter = 0;
        int c = 0;
        boolean isCourseValid = false;
        boolean isClassValid = false;
        Scanner sr = new Scanner(System.in);

        ArrayList<CourseInfo> CourseInfo = Singleton_CourseInfo.getInstance().courseInfoDB;
        for(CourseInfo Course : CourseInfo){
            System.out.println((counter + 1) + ". " + Course.getCode() + " " + Course.getName());
            counter++;
        }

        do{
            System.out.println("Select A Course: " );
            int courseOption = sr.nextInt();

            if(courseOption > 0 && courseOption <= CourseInfo.size()){
                CourseInfo selectedCourse = CourseInfo.get(courseOption-1);

                for(var classInfo : selectedCourse.getClassList()){
                    System.out.println((c+1)  + ". " + classInfo.getCourseCodeReference() + "'s index number: " + classInfo.getIndexNo());
                    c++;
                }

                do{
                    System.out.println("Select A Class Index: ");
                    int classOption = sr.nextInt();

                    if(classOption > 0 && classOption <= selectedCourse.getClassList().size()){
                        var selectedClassIndex = selectedCourse.getClassList().get(classOption-1);
                        System.out.println("Total available vacancies: " + selectedClassIndex.getVacancy());
                        isClassValid = true;
                    }
                }while(!isClassValid);

                if(isClassValid){
                    isCourseValid = true;
                }
            }
        }while(!isCourseValid);
        return 0;
    }


    //<editor-fold desc="Private">
    private void UpdateDB(String filename,String filetype,String content,String directoryName, Boolean append)
    {
        IOUtills saveCourse = new IOUtills(filename,filetype,content,directoryName);
        if(!append)
            IOUtills.WriteFile();
        else
            IOUtills.WriteFile(true);
    }
    //</editor-fold>
}
