import java.util.ArrayList;

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
    public ArrayList<StudentCourse> AddCourse(Student student, CourseInfo courseInfo, ArrayList<StudentCourse> studentCourseList)
    {
        student.addCourse(courseInfo);

        // add course in student course DB
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentMatricNo(student.getMatricNo());
        studentCourse.setCourseCode(courseInfo.getCode());
        studentCourse.setCourseIndex(courseInfo.getClassList().get(0).getIndexNo());

        studentCourseList.add(studentCourse);

        // write back to Student Course txt file
        String content = student.getMatricNo() + "," + courseInfo.getCode() + "," + courseInfo.getClassList().get(0).getIndexNo() ;

        IOUtills saveCourse = new IOUtills("StudentCourse","txt",content,"StudentCourseTable");
        IOUtills.WriteFile(true);

        // decrease the course class's vacancy by 1





        mailHelper.setRecipientEmail(student.getEmail());
        mailHelper.setMessage("Congratulation! you have registered " + courseInfo.getName() + " !");
        mailHelper.setEmailSubject("Successfully registered " + courseInfo.getName() + " !");
        //mailHelper.SendEmail();

        return studentCourseList;
    }

    public ArrayList<StudentCourse> DropCourse(Student student, CourseInfo courseInfo, ArrayList<StudentCourse> studentCourseList)
    {
         // remove course in student courseArrayList
         student.dropCourse(courseInfo);

         // remove course in student course DB
         StudentCourse studentCourse = new StudentCourse();
         studentCourse.setStudentMatricNo(student.getMatricNo());
         studentCourse.setCourseCode(courseInfo.getCode());
         studentCourse.setCourseIndex(courseInfo.getClassList().get(0).getIndexNo());

         studentCourseList.remove(studentCourse);

        // write back to Student Course txt file
        if(studentCourseList != null && studentCourseList.size() > 0)
        {
            String content = "";
            for(int i =0; i < studentCourseList.size(); i++)
            {
                content = studentCourseList.get(i).getStudentMatricNo() + "," + studentCourseList.get(i).getCourseCode() + "," + studentCourseList.get(i).getCourseIndex() + "\n" ;
            }
            IOUtills saveCourse = new IOUtills("StudentCourse","txt",content,"StudentCourseTable");
            IOUtills.WriteFile();
        }
        else
        {
            // the db is empty
            IOUtills saveCourse = new IOUtills("StudentCourse","txt","","StudentCourseTable");
            IOUtills.WriteFile();
        }

        // increase the course class's vacancy by 1


         mailHelper.setRecipientEmail(student.getEmail());
         mailHelper.setMessage("We are to inform you that you have dropped " + courseInfo.getName() + ". Please contact your school coordinator if you have not done so.");
         mailHelper.setEmailSubject("Successfully dropped " + courseInfo.getName() + " !");
         //mailHelper.SendEmail();

        return studentCourseList;
    }

    public void RegisteredCourses(Student student)
    {
        if(student.getCourseInfoList() != null && student.getCourseInfoList().size() > 0)
        {
            for(int i=0; i < student.getCourseInfoList().size() ; i++)
            {
                System.out.println(i+1 + ": " + student.getCourseInfoList().get(i).getCourseInfo());
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
//    private Boolean IsCourseDuplicate(Student student, CourseInfo courseInfo)
//    {
//        if(student.getCourseInfoList() != null && student.getCourseInfoList().size() > 0)
//        {
//            for(int i=0; i < student.getCourseInfoList().size() ; i++)
//            {
//                if(student.getCourseInfoList().get(i).getIndexNo() == courseInfo.getIndexNo()){
//                    System.out.println( student.getName() + " already registered " + courseInfo.getIndexNo() );
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    //endregion
}
