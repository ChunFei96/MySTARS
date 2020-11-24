import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Course's attributes and functions
 * @author Lee Chun Fei & Mindy Hwang Mei Hui
 * @version 1.0
 * @since 2020-11-13
 */
public class CourseInfo
{
    /**
     * course's code
     */
    private String code;
    /**
     * course's name
     */
    private String name;
    /**
     * course's type
     */
    private String type;

    /**
     * course's class list
     */
    private ArrayList<ClassInfo> classArrayList;

    /**
     * Create a course object with course reference
     * @param courseInfo
     */
    public CourseInfo(CourseInfo courseInfo)
    {
        this.code = courseInfo.code;
        this.name = courseInfo.name;
        this.type = courseInfo.type;

        classArrayList = new ArrayList<ClassInfo>();
    }

    /**
     * Create a course object with string concatenated course information
     * @param courseInfo
     */
    public CourseInfo(String courseInfo)
    {
        String[] courseInfoArr = courseInfo.split(",");
        code = courseInfoArr[0];
        name = courseInfoArr[1];
        type = courseInfoArr[2];

        classArrayList = new ArrayList<ClassInfo>();
    }

    /**
     * Retrieve course information
     * @return course information in string
     */
    public String getCourseInfo()
    {
        return (code + " " + name + " " + type);
    }

    /**
     * Get course's name
     * @return name in string
     */
    public String getName() {return name;}

    /**
     * Get course's code
     * @return code in string
     */
    public String getCode() {return code;}

    /**
     * Get course's type
     * @return type in string
     */
    public String getType() { return type; }

    /**
     * Set course's code
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Set course's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set course's type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Add class into class list
     * @param classInfo class object reference
     */
    public void addClass(ClassInfo classInfo)
    {
        this.classArrayList.add(classInfo);
    }

    /**
     * Retrieve all the classes under this course
     * @return list of course object
     */
    public ArrayList<ClassInfo> getClassList()
    {
        return this.classArrayList;
    }

    /**
     * Print this course information
     */
    public void printCourseInfo(){
        System.out.println("======= Display Course Info =======");
        System.out.println("Course code: " + getCode());
        System.out.println("Course name: " + getName());
        System.out.println("Course type: " + getType());
    }

    /**
     * Edit course information
     * @param editOptions string array of options
     */
    public void EditCourseInfoOptions(String [] editOptions){
        System.out.println("============");
        for(int k = 0; k < editOptions.length;k++){
            System.out.println((k+1) + ". Edit " +  editOptions[k]);
        }
        System.out.println("5. Back");
        System.out.println("============");
        System.out.println("Enter your input: ");
    }

    /**
     * Create a empty list of class object
     */
    public void createClassArrayList() {this.classArrayList = new ArrayList<>();}

    /**
     * Retrieve course information from CourseInfo.txt
     * @param skipByColumnName column name to skip when retrieving
     * @return course information in string
     */
    public String getAllRecordInDB(String skipByColumnName){
        ArrayList<String> d = new ArrayList<String>();
        for(var i :  Singleton_CourseInfo.getInstance().courseInfoDB){
            if(!(i.getCode().equals(skipByColumnName))){
                d.add(getRecordInDB(i));
            }
        }
        return String.join("\r\n",d);
    }

    /**
     * Get course information from CourseInfo.txt
     * @param data course object reference
     * @return course information in string
     */
    public String getRecordInDB(CourseInfo data){
        String [] d = new String[] {data.getCode(),data.getName(),data.getType()};
        return String.join(",",d);
    }

    /**
     * Check if two courses is duplicate
     * @param newCourse new course added
     * @return if duplication is true or false
     */
    public boolean checkDuplicateCourse(CourseInfo newCourse){
        boolean output = true;

        var courses = Singleton_CourseInfo.getInstance().courseInfoDB;
        var getCourseInfo = courses.stream().filter(x -> x.getCode().equals(newCourse.getCode())
                && x.getName().equals(newCourse.getName()) && x.getType().equals(newCourse.getType()))
                .collect(Collectors.toList());

        if(getCourseInfo.size() == 0){
            output = false;
        }

        return output;
    }

}