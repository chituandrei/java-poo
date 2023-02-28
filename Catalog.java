import java.util.*;
public class Catalog implements Subject {

    ArrayList<Course> arrayList;
    ArrayList<Observer> observers = new ArrayList<>();

    public Catalog() {
        arrayList = new ArrayList<Course>();
    }

    static private Catalog catalog = null;

    public static Catalog getInstance() {
        if (catalog == null)
            catalog = new Catalog();
        return catalog;
    }

    @Override
    public String toString() {
        return "Catalog{" + "arrayList=" + arrayList + '}';
    }

    public Course getCourse(String nume) {

        Enumeration<Course> e = Collections.enumeration(arrayList);
        while (e.hasMoreElements()) {
            Course curs = e.nextElement();
            if (curs.getName().equals(nume))
                return curs;
        }
        return null;
    }

    public void addCourse(Course course) {
        arrayList.add(course);
    }

    public void removeCourse(Course course) {
        arrayList.remove(course);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        for (Observer observer : observers) {
            Student s = grade.getStudent();
            if (s.eParinte(observer)) {
                if (grade.getTotal() >= 5)
                    observer.update(new Notification(grade.getStudent().toString(), "trecut"));
                else if(grade.getTotal() < 5)
                    observer.update(new Notification(grade.getStudent().toString(), "trecut"));
            }

        }
    }
    public void printObservers(){
        for(Observer observer : observers){
            System.out.println(observer.toString());
        }
    }
}