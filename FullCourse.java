import java.util.*;

public class FullCourse extends Course {

    public FullCourse(FullCourseBuilder builder)
    {
        this.nume_curs= builder.nume_curs;
        this.groups=builder.groups;
        this.titular=builder.titular;
        this.assistents=builder.assistents;
        this.strategy = builder.strategy;
        this.grades = builder.grades;
    }

    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> arr = new ArrayList<Student>();
        for (Grade grade : super.grades) {
            if (grade.getPartialScore() >= 3 && grade.getExamScore() >= 2) {
                arr.add(grade.getStudent());
            }
        }
        return arr;
    }
    static class FullCourseBuilder extends CourseBuilder{
        @Override
        public Course build(){
            return new FullCourse(this);
        }
    }
}
