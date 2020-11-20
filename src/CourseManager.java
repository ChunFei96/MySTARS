public class CourseManager {

    private MailHelper mailHelper;

    public CourseManager()
    {
        mailHelper = new MailHelper();
    }

    //region Public
    public int CheckCourseVacancy(int indexId)
    {
        return 0;
    } 
    
    //region Student 
    public void AddCourse(Student student, CourseInfo courseInfo)
    {
        if(!IsCourseDuplicate(student, courseInfo)){
            student.getCourseInfoList().add(courseInfo);

            String content = student.getName() + "," + courseInfo.getIndexNo();

            IOUtills userCredentials = new IOUtills(student.getUsername(),"txt",content,"StudentCourseTable");
            IOUtills.WriteFile();



            mailHelper.setRecipientEmail(student.getEmail());
            mailHelper.setMessage("Congratulation! you have registered " + courseInfo.getName() + " !");
            mailHelper.setEmailSubject("Successfully registered " + courseInfo.getName() + " !");
            //mailHelper.SendEmail();
        }

    }

    public void DropCourse(Student student, CourseInfo courseInfo)
    {
            student.getCourseInfoList().remove(courseInfo);
            mailHelper.setRecipientEmail(student.getEmail());
            mailHelper.setMessage("We are to inform you that you have dropped " + courseInfo.getName() + ". Please contact your school coordinator if you have not done so.");
            mailHelper.setEmailSubject("Successfully dropped " + courseInfo.getName() + " !");
            //mailHelper.SendEmail();
    }

    public void RegisteredCourses(Student student)
    {
        if(student.getCourseInfoList() != null && student.getCourseInfoList().size() > 0)
        {
            for(int i=0; i < student.getCourseInfoList().size() ; i++)
            {
                System.out.println(i+1 + ": " + student.getCourseInfoList().get(i).GetCourseInfo());
            }
        }
        else
            System.out.println("There is no registered course(s) for " + student.getName() + "!");
    }

    public void ChangeCourseIndexNumber(int course_Code)
    {

    }

    public void SwapIndexNumber(int index_ID, int student_ID)
    {
        
    }
    //endregion

    //region Admin
    public void EditStudentAccessPeriod(Student student)
    {

    }

    public void AddStudent(Student student)
    {

    }

    public void AddCourse(CourseInfo course)
    {

    }

    public void PrintStudentListByIndex(String indexNo)
    {

    }

    public void PrintStudentListByCourse(CourseInfo courseInfo)
    {

    }
    //endregion
    //endregion
    
    //region Private 
    private Boolean IsCourseDuplicate(Student student, CourseInfo courseInfo)
    {
        if(student.getCourseInfoList() != null && student.getCourseInfoList().size() > 0)
        {
            for(int i=0; i < student.getCourseInfoList().size() ; i++)
            {
                if(student.getCourseInfoList().get(i).getIndexNo() == courseInfo.getIndexNo()){
                    System.out.println( student.getName() + " already registered " + courseInfo.getIndexNo() );
                    return true;
                }
            }
        }
        return false;
    }
    //endregion
}
