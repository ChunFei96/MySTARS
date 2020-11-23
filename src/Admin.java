import java.io.File;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Admin extends User implements IAdmin{

    public Admin(String name, String nationality, String email, String username, String password, String contactNo,
                 EnumHelper.UserRole role, EnumHelper.Gender sex) {
        super(name, nationality, email, username, password, contactNo, EnumHelper.UserRole.ADMIN, sex);
    }

    public void EditStudentAccessPeriod() throws Exception {
        new CourseManager().EditStudentAccessPeriod();
    }

    public void AddStudent(){
        new CourseManager().AddStudent();
    }

    public void AddCourse(){
        new CourseManager().AddCourse();
    }

    public void UpdateCourse(){
        new CourseManager().UpdateCourse();
    }

    public void PrintStudentListByIndex() {
        new CourseManager().PrintStudentListByIndex();
    }

    public void PrintStudentListByCourse() {
        new CourseManager().PrintStudentListByCourse();
    }

    public int CheckCourseVacancy(){
        int result = new CourseManager().CheckCourseVacancy();
        return result;
    }
}
