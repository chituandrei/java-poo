import java.util.TreeSet;

public class BestPartialScore implements Strategy{
    double max = 0;
    Student studRET;
    @Override
    public Student getStudent(TreeSet<Grade>grades){
        for (Grade grade : grades){
            double score = grade.getPartialScore();
            if(score > max) {
                max = score;
                studRET = grade.getStudent();
            }
        }
        return studRET;
    }
}
