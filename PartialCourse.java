import java.util.*;

public class PartialCourse extends Course{

    public PartialCourse(PartialCourseBuilder builder)
    {
        this.nume_curs= builder.nume_curs;
        this.groups=builder.groups;
        this.titular=builder.titular;
        this.assistents=builder.assistents;
        this.strategy = builder.strategy;
        this.grades = builder.grades;
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> arr = new ArrayList<Student>();
        for (Grade grade : super.grades) {
            if (grade.getTotal() >= 5) {
                arr.add(grade.getStudent());
            }
        }
        return arr;
    }
    static class PartialCourseBuilder extends CourseBuilder{
       @Override
        public Course build(){
           return new PartialCourse(this);
       }
    }
}
