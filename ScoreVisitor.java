import com.sun.source.tree.Tree;

import java.util.*;
public class ScoreVisitor implements Visitor{
    private class Tuple{
        Student student;
        String nume_curs;
        Double nota;
        Tuple(Student student, String nume_curs, Double nota){
            this.student = student;
            this.nume_curs = nume_curs;
            this.nota = nota;
        }
    }
    Map<Teacher, ArrayList<Tuple>>examScores = new HashMap<>();
    Map<Assistant, ArrayList<Tuple>>partialScores = new HashMap<>();

    public void setExam(Teacher t,ArrayList<Grade>grade){
        ArrayList<Tuple> temp = new ArrayList<>();
        for(Grade g : grade) {
            Tuple tuple = new Tuple(g.getStudent(),g.getCourse(),g.getExamScore());
            temp.add(tuple);
        }
        examScores.put(t,temp);
    }
    public void setPartial(Assistant a, TreeSet<Grade>grade){
        ArrayList<Tuple> temp = new ArrayList<>();
        for(Grade g : grade) {
            Tuple tuple = new Tuple(g.getStudent(),g.getCourse(),g.getPartialScore());
            temp.add(tuple);
        }
        partialScores.put(a,temp);
    }
    @Override
    public void visit(Assistant assistant) {
        Catalog cat = Catalog.getInstance();

        for(Map.Entry<Assistant,ArrayList<Tuple>> set : partialScores.entrySet()){
            Assistant a = set.getKey();
            ArrayList<Tuple>t = set.getValue();
            if(a.equals(assistant)){
                Course c = cat.getCourse(t.get(1).nume_curs);
                for(Grade g : c.grades){
                    for(Tuple ti : t){
                        if(g.getStudent().equals(ti.student)){
                            g.setPartialScore(ti.nota);
                            for(Observer observer: cat.observers)
                                if(ti.student.equals(observer))
                                    notifyObservers(observer);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void visit(Teacher teacher) {
        Catalog cat = Catalog.getInstance();

        for(Map.Entry<Teacher,ArrayList<Tuple>> set : examScores.entrySet()){
            Teacher teacher1 = set.getKey();
            ArrayList<Tuple>t = set.getValue();
            if(teacher1.equals(teacher)){
                Course c = cat.getCourse(t.get(1).nume_curs);
                for(Grade g : c.grades){
                    for(Tuple ti : t){
                        if(g.getStudent().equals(ti.student)){
                            g.setExamScore(ti.nota);
                            for(Observer observer: cat.observers)
                                if(ti.student.equals(observer))
                                    notifyObservers(observer);
                        }
                    }
                }
            }
        }
    }


    public void notifyObservers(Observer observer){
        Notification notification = new Notification("copilul a primit nota","incerta");
        observer.update(notification);
    }
}
