public interface IAdmin extends IUser{
    public void EditStudentAccessPeriod();

    public void AddStudent();

    public void AddCourse(CourseInfo course);

    public void PrintStudentListByIndex(String indexNo);

    public void PrintStudentListByCourse(String courseName);
}
