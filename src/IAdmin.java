public interface IAdmin extends IUser{
    public void EditStudentAccessPeriod(Student student);

    public void AddStudent(Student student);

    public void AddCourse(CourseInfo course);

    public void PrintStudentListByIndex(String indexNo);

    public void PrintStudentListByCourse(CourseInfo courseInfo);
}
