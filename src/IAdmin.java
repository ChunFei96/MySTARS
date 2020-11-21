public interface IAdmin extends IUser{
    public void EditStudentAccessPeriod(Student student, String accessPeriod);

    public void AddStudent();

    public void AddCourse(CourseInfo course);

    public void PrintStudentListByIndex(String indexNo);

    public void PrintStudentListByCourse(CourseInfo courseInfo);
}
