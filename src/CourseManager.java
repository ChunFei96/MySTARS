public class CourseManager {

    private MailHelper mailHelper;

    public CourseManager()
    {
        mailHelper = new MailHelper();
    }

    //<editor-fold desc="Publlic">
    public int CheckCourseVacancy(String indexId)
    {
        return 0;
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

    //region Admin
    public void EditStudentAccessPeriod()
    {

    }

    public void AddStudent()
    {

    }

    public void AddCourse(CourseInfo course)
    {

    }

    public void PrintStudentListByIndex(String indexNo)
    {

    }

    public void PrintStudentListByCourse(String courseName)
    {

    }
    //</editor-fold>
    //</editor-fold>

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
