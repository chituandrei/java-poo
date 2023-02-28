import java.util.*;

public class Group extends TreeSet<Student > {
    private String ID;
    private Assistant assistant;

    public String getID(){
        return ID;
    }
    public Group(String ID, Assistant assistant){
        super();
        this.ID = ID;
        this.assistant = assistant;
    }
    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super(comp);
        this.ID = ID;
        this.assistant = assistant;
    }

    public void addStudent(Student student) {
        this.add(student);
    }

    public void removeStudent(Student student) {
        this.remove(student);
    }

}
