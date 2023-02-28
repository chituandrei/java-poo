import java.util.TreeSet;

public interface Strategy {
    Student getStudent(TreeSet<Grade> grades);
}
