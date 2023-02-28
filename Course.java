import com.sun.source.tree.Tree;

import java.util.*;

public abstract class Course {
    String nume_curs;
    Teacher titular;
    Set<Assistant>assistents;
    TreeSet<Grade>grades;
    Strategy strategy;
    Map<String, Group> groups;

    int creditPoints;

    public void setName(String nume_curs) {
        this.nume_curs = nume_curs;
    }

    public String getName() {
        return nume_curs;
    }

    public void setTeacher(Teacher titular) {
        this.titular = titular;
    }

    public Teacher getTeacher() {
        return titular;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void addAssistent(String ID, Assistant assistant) {
        assistents.add(assistant);
    }
    public void addAssistent(Assistant assistant){
        assistents.add(assistant);
    }

    public void addStudent(String ID, Student student){
        for (Map.Entry<String,Group> set : groups.entrySet()){
            String id = set.getKey();
            if(ID.equals(id)){
                set.getValue().addStudent(student);
            }
        }
    }
    public void addGroup(Group group) {
        groups.put(group.getID(),group);
    }
    public void addGroup(String ID, Assistant assistant){
        Group group = new Group(ID, assistant);
        groups.put(ID,group);
    }
    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        Group group = new Group(ID,assistant,comp);
        groups.put(ID,group);
    }

    public Grade getGrade(Student student) {
        for (Grade grade : grades) {
            if (grade.getStudent().equals(student)) {
                return grade;
            }
        }
        return null;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> arr = new ArrayList<Student>();
        for (Grade grade : grades) {
            arr.add(grade.getStudent());
        }
        return arr;
    }

    public HashMap<Student, Grade> gettAllStudentGrades() {
        HashMap<Student, Grade> h = new HashMap<Student, Grade>();

        for (Grade grade : grades) {
            h.put(grade.getStudent(), grade);
        }
        return h;
    }
    public String GradesToString(TreeSet<Grade>grades){
        String ret = "{";
        for(Grade g : grades){
            Student s = g.getStudent();
            ret = ret + s.toString() + " " +g.getTotal() + ", ";
        }
        ret = ret + "}";
        return ret;
    }
    public Student getBestStudent(){
        return strategy.getStudent(grades);
    }
    public abstract ArrayList<Student> getGraduatedStudents();

    private class Snapshot{
        private TreeSet<Grade>coppy_grades = new TreeSet<>();
    }
    private Snapshot snapshot = new Snapshot();

    public void makeBackup(){
        for(Grade g:grades){
            Grade coppy = g.clone();
            snapshot.coppy_grades.add(coppy);
        }
    }

    public void undo(){
        grades = snapshot.coppy_grades;
    }
    public static abstract class CourseBuilder{
        String nume_curs;
        Strategy strategy;
        Teacher titular;
        Set<Assistant>assistents;
        TreeSet<Grade>grades ;
        Map<String, Group> groups;

        public CourseBuilder setNume(String nume_curs){
            this.nume_curs = nume_curs;
            return this;
        }
        public CourseBuilder setTeacher(Teacher teacher){
            this.titular = teacher;
            return this;
        }
        public CourseBuilder setAssistent(){
            this.assistents = new HashSet<>();
            return this;
        }
        public CourseBuilder setMap(){
            this.groups = new HashMap<String, Group>();
            return this;
        }
        public CourseBuilder setGrade(){
            this.grades = new TreeSet<Grade>();
            return this;
        }

        public abstract Course build();



        public CourseBuilder setStrategy(Strategy strategy) {
            if (strategy.getClass().getSimpleName().equals("BestExamScore"))
                strategy = new BestExamScore();
            if (strategy.getClass().getSimpleName().equals("BestPartialScore"))
                strategy = new BestPartialScore();
            if (strategy.getClass().getSimpleName().equals("BestTotalScore"))
                strategy = new BestPartialScore();

            this.strategy = strategy;
            return this;
        }
        }
}


