import java.util.Comparator;

public class Student extends User{
    private Parent father;
    private Parent mother;
    public Student(String firstName, String lastName){
        super(firstName,lastName);
    }
    public void setMother(Parent mother){
        this.mother = mother;
    }
    public void setFather(Parent father){
        this.father = father;
    }
    public boolean eParinte(Observer observer){
        if(observer == father || observer == mother)
            return true;
        return false;
    }

}