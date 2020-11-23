import java.util.ArrayList;

public class CourseManager {

    private MailHelper mailHelper;

    public CourseManager()
    {
        mailHelper = new MailHelper();
    }

    //<editor-fold desc="Publlic">
    public int CheckCourseVacancy()
    {
        return 0;
    }

    //<editor-fold desc="Student">
    public void AddCourse(Student student, CourseInfo courseInfo, Boolean isSwapIndex)
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
        // bypass this add vacancy when student swaps index with another student, vacancy should remain no change
        if(singleton_courseInfo.courseInfoDB != null && singleton_courseInfo.courseInfoDB.size() > 0 && !isSwapIndex)
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
                            else{

                                classInfo.setQueue(courseInfo.getClassList().get(0).getQueue() + 1);
                                // create a queue txt file for the class with matric number line by line
                                String queueContent = student.getMatricNo();
                                UpdateDB(courseInfo.getCode() + "_" + courseInfo.getClassList().get(0).getIndexNo() + "_Queue", "txt", queueContent, "ClassQueueTable",true);

                                System.out.println("You have been added to the waitlist for course index " + classInfo.getIndexNo() + ". Your queue number is " + classInfo.getQueue() +". An email notification will be sent to you.");

                                mailHelper.setRecipientEmail(student.getEmail());
                                mailHelper.setMessage("You have been added to the waitlist for course index " + classInfo.getIndexNo() + ". Your queue number is " + classInfo.getQueue() +".");
                                mailHelper.setEmailSubject("Added to the waitlist for course index " + classInfo.getIndexNo());
                                mailHelper.SendEmail();
                            }

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

    public void DropCourse(Student student, CourseInfo courseInfo, Boolean isSwapIndex)
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
                if(removedstudentCourse.getStudentMatricNo().equals(student.getMatricNo()) && removedstudentCourse.getCourseCode().equals(courseInfo.getCode()) &&
                        removedstudentCourse.getClassIndex().equals(courseInfo.getClassList().get(0).getIndexNo()))
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
        // bypass this add vacancy when student swaps index with another student, vacancy should remain no change
        if(singleton_courseInfo.courseInfoDB != null && singleton_courseInfo.courseInfoDB.size() > 0 && !isSwapIndex)
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
                            else{
                                classInfo.setQueue(courseInfo.getClassList().get(0).getQueue() - 1);
                                // help the first queue student to register to this class
                                String class_filename = singleton_courseInfo.courseInfoDB.get(j).getCode() + "_" + classInfo.getIndexNo() + "_Queue.txt";
                                String class_filepath = System.getProperty("user.dir") + "/ClassQueueTable/" + class_filename;
                                IOUtills.ReadFile(class_filepath);

                                ArrayList<String> queueList =  IOUtills.getFileInput();

                                String matricNumber = queueList.get(0);

                                String firstQueueStudentContent = matricNumber + "," + singleton_courseInfo.courseInfoDB.get(j).getCode() + "," +  classInfo.getIndexNo();

                                UpdateDB("StudentCourse","txt",firstQueueStudentContent,"StudentCourseTable",true);

                                // remove the first student from queue
                                queueList.remove(0);
                                String queueContent = "";
                                if(queueList != null && queueList.size() > 0)
                                {
                                    for(int z = 0; z <queueList.size(); z++)
                                    {
                                        queueContent += queueList.get(z) + "\n";
                                    }

                                    UpdateDB(singleton_courseInfo.courseInfoDB.get(j).getCode() + "_" + classInfo.getIndexNo() + "_Queue" ,"txt",queueContent,"ClassQueueTable",false);
                                }
                                else
                                    UpdateDB(singleton_courseInfo.courseInfoDB.get(j).getCode() + "_" + classInfo.getIndexNo() + "_Queue" ,"txt","","ClassQueueTable",false);

                                // send email to notify their queue number

                                System.out.println("sending email...");
                                Singleton_StudentProfile singleton_studentProfile = Singleton_StudentProfile.getInstance();
                                String studentEmail = "";
                                // send for first queue student
                                for(int a = 0; a < singleton_studentProfile.studentProfileDB.size() ; a++)
                                {
                                    if(singleton_studentProfile.studentProfileDB.get(a).getMatricNo().equals(matricNumber))
                                    {
                                        mailHelper.setRecipientEmail(singleton_studentProfile.studentProfileDB.get(a).getEmail());
                                        mailHelper.setMessage("You have successfully register for course index " + classInfo.getIndexNo());
                                        mailHelper.setEmailSubject("Successfully register for course index " + classInfo.getIndexNo());
                                        mailHelper.SendEmail();
                                    }

                                }
                                // send for second or below queue student
                                for(int b = 0; b < singleton_studentProfile.studentProfileDB.size() ; b++)
                                {
                                    for(int c =0; c<queueList.size(); c++)
                                    {
                                        if(singleton_studentProfile.studentProfileDB.get(b).getMatricNo().equals(queueList.get(c)))
                                        {
                                            int latestQueue = c+1;
                                            mailHelper.setRecipientEmail(singleton_studentProfile.studentProfileDB.get(b).getEmail());
                                            mailHelper.setMessage("Your queue number for course index " + classInfo.getIndexNo() + " is " + latestQueue);
                                            mailHelper.setEmailSubject("Queue number for course index " + classInfo.getIndexNo());
                                            mailHelper.SendEmail();
                                        }
                                    }
                                }
                                System.out.println("Done!");
                            }

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
        System.out.println("\n================================================================== " +  menuTitle + " =========================================================================================\n");
        if(student.getCourseInfoList() != null && student.getCourseInfoList().size() > 0)
        {
            String leftAlign = "| %-11s | %-43s |%-9s | %-11s | %-7s |%-9s | %-12s | %-10s | %-14s | %-8s |%-14s |%n";

            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
            System.out.format("| Course Code |                  Course Name                |   Type   | Class Index |  Group  |    Day   |    Period    |    Venue   |     Remark     |  Vacancy | Waiting list  |%n");
            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");

            for (int i = 0; i < student.getCourseInfoList().size(); i++) {
                System.out.format(leftAlign, i+1 + ": " + student.getCourseInfoList().get(i).getCode(), student.getCourseInfoList().get(i).getName(), student.getCourseInfoList().get(i).getType(),student.getCourseInfoList().get(i).getClassList().get(0).getIndexNo(),student.getCourseInfoList().get(i).getClassList().get(0).getGroupNo(),student.getCourseInfoList().get(i).getClassList().get(0).getDay(),student.getCourseInfoList().get(i).getClassList().get(0).getTime(),student.getCourseInfoList().get(i).getClassList().get(0).getVenue(),
                        student.getCourseInfoList().get(i).getClassList().get(0).getRemark(),student.getCourseInfoList().get(i).getClassList().get(0).getVacancy(),student.getCourseInfoList().get(i).getClassList().get(0).getQueue());
            }
            System.out.format("+-------------+---------------------------------------------+----------+-------------+---------+----------+--------------+------------+----------------+----------+---------------+%n");
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
