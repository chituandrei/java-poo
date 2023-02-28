import com.sun.source.tree.Tree;

import java.io.*;
import java.net.StandardSocketOptions;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("\n\n\nTestarea functiilor : \n\n");
        Comparator<Student> comparator = new Comparator<Student>(){
            @Override
            public int compare(Student s1,Student s2){
                String name_s1 = s1.toString();
                String name_s2 = s2.toString();
                return name_s1.compareTo(name_s2);
            }
        };
        Catalog catalog = Catalog.getInstance();

        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            int NrCursuri = myReader.nextInt();
            for(int i = 0; i < NrCursuri; i ++){

                String TipCurs = myReader.next();
                String Strategie = myReader.next();
                String NumeCurs = myReader.next();
                String teacher_first_name = myReader.next();
                String teacher_last_name = myReader.next();
                Strategy SStrategy = new BestTotalScore();

                if(Strategie.equals("BestExamScore"))
                    SStrategy = new BestExamScore();
                else if (Strategie.equals("BestPartialScore"))
                    SStrategy = new BestPartialScore();
                else if(Strategie.equals("BestTotalScore"))
                    SStrategy = new BestTotalScore();

                Teacher professor = new Teacher(teacher_first_name,teacher_last_name);

                if(TipCurs.equals("FullCourse")) {
                    
                    FullCourse curs = (FullCourse) new FullCourse.FullCourseBuilder().setStrategy(SStrategy).setGrade().setNume(NumeCurs).setMap().setAssistent().setTeacher(professor).build();

                    int nr_Asistenti = myReader.nextInt();
                    for (int j = 0; j < nr_Asistenti; j++) {
                        String asistent_first_name = myReader.next();
                        String asistent_last_name = myReader.next();
                        Assistant assistant = new Assistant(asistent_first_name,asistent_last_name);
                        curs.addAssistent((Assistant) assistant);
                    }
                    int nr_grupe = myReader.nextInt();
                    for(int j = 0; j < nr_grupe; j++){
                        String id_grupa = myReader.next();

                        String asistent_first_name = myReader.next();
                        String asistent_last_name = myReader.next();
                        Assistant assistant = new Assistant(asistent_first_name,asistent_last_name);

                        Group grup = new Group(id_grupa,assistant,comparator);

                        int nr_studenti = myReader.nextInt();

                        for(int t = 0; t < nr_studenti; t++){
                            String first_name_student = myReader.next();
                            String last_name_student = myReader.next();

                            User student = UserFactory.getUser("Student",first_name_student,last_name_student);

                            int nr_parinti = myReader.nextInt();

                            for(int p = 0; p < nr_parinti; p++){
                                String gen = myReader.next();
                                String first_name = myReader.next();
                                String last_name = myReader.next();
                                Parent parent = new Parent(first_name,last_name);

                                if(gen.equals("m")) {
                                    ((Student) student).setMother(parent);
                                    catalog.addObserver(parent);
                                }
                                else {
                                    ((Student) student).setFather(parent);
                                    catalog.addObserver(parent);
                                }
                            }
                            grup.addStudent((Student) student);

                        }
                        curs.addGroup(grup);
                    }
                    catalog.addCourse(curs);
                }

                else if(TipCurs.equals("PartialCourse")) {
                    PartialCourse curs = (PartialCourse) new PartialCourse.PartialCourseBuilder().setStrategy(SStrategy).setGrade().setNume(NumeCurs).setMap().setAssistent().setTeacher(professor).build();
                    int nr_Asistenti = myReader.nextInt();
                    for (int j = 0; j < nr_Asistenti; j++) {
                        String asistent_first_name = myReader.next();
                        String asistent_last_name = myReader.next();
                        User assistant = UserFactory.getUser("Assistant",asistent_first_name,asistent_last_name);
                        curs.addAssistent((Assistant) assistant);
                    }
                    int nr_grupe = myReader.nextInt();
                    for(int j = 0; j < nr_grupe; j++){
                        String id_grupa = myReader.next();

                        String asistent_first_name = myReader.next();
                        String asistent_last_name = myReader.next();
                        Assistant assistant = new Assistant(asistent_first_name,asistent_last_name);

                        Group grup = new Group(id_grupa,assistant,comparator);

                        int nr_studenti = myReader.nextInt();

                        for(int t = 0; t < nr_studenti; t++){
                            String first_name_student = myReader.next();
                            String last_name_student = myReader.next();

                            Student student = new Student(first_name_student,last_name_student);

                            int nr_parinti = myReader.nextInt();

                            for(int p = 0; p < nr_parinti; p++) {
                                String gen = myReader.next();
                                String first_name = myReader.next();
                                String last_name = myReader.next();
                                Parent parent = new Parent(first_name, last_name);

                                if (gen.equals("m")) {
                                    student.setMother(parent);
                                    catalog.addObserver(parent);
                                }
                                else {
                                    student.setFather(parent);
                                    catalog.addObserver(parent);
                                }
                            }
                            grup.addStudent(student);
                        }
                        curs.addGroup(grup);
                    }
                    catalog.addCourse(curs);
                }
            }
            int nr_note = myReader.nextInt();
            /// Scor examen
            for(int t = 0; t < nr_note; t++){
                String nume = myReader.next();
                String prenume = myReader.next();
                String nume_curs = myReader.next();
                Double nota = myReader.nextDouble();
                User student = UserFactory.getUser("Student",nume,prenume);
                Grade grade = new Grade(nume_curs, (Student) student);
                grade.setExamScore(nota);
                Course curs = catalog.getCourse(nume_curs);
                curs.addGrade(grade);
            }
            Course curs = catalog.getCourse("Programare_Orientata_Pe_Obiecte");
            ArrayList<Student>arr;
            arr = curs.getAllStudents();
            System.out.println("Programare_Orientata_Pe_Obiecte : " + arr);
            curs = catalog.getCourse("Paradigme_De_Programare");
            arr = curs.getAllStudents();
            System.out.println("Paradigme_De_Programare : " + arr);


            nr_note = myReader.nextInt();
            for(int t = 0; t < nr_note; t++){
                String nume = myReader.next();
                String prenume = myReader.next();
                String nume_curs = myReader.next();
                Double nota = myReader.nextDouble();
                Student s = new Student(nume,prenume);
                Course c = catalog.getCourse(nume_curs);

                HashMap<Student, Grade> h = c.gettAllStudentGrades();

                Iterator itr = h.entrySet().iterator();

                while(itr.hasNext()) {
                    Map.Entry element = (Map.Entry) itr.next();
                    Grade grade;
                    Student student;
                    grade = (Grade) element.getValue();
                    student = (Student) element.getKey();

                    if(s.toString().equals(student.toString()))
                        grade.setPartialScore(nota);
                }
            }

            Course c = catalog.getCourse("Programare_Orientata_Pe_Obiecte");
            System.out.println("\nProgramare Orientata Pe Obiecte note finale : ");
            HashMap<Student, Grade> h = c.gettAllStudentGrades();

            Student s = c.getBestStudent();
            System.out.println(s.toString() + " are cea mai mare nota in partial");

            Iterator itr = h.entrySet().iterator();

            while(itr.hasNext()) {
                Map.Entry element = (Map.Entry) itr.next();
                Grade grade;
                Student student;
                grade = (Grade) element.getValue();
                student = (Student) element.getKey();
                System.out.println(student.toString() + " " + grade.getTotal() );
            }

            c = catalog.getCourse("Paradigme_De_Programare");
            System.out.println("\n\nParadigme de programare note finale : ");
            h = c.gettAllStudentGrades();

            s = c.getBestStudent();
            System.out.println(s.toString() + " are cea mai mare nota in examen");

            itr = h.entrySet().iterator();

            while(itr.hasNext()) {
                Map.Entry element = (Map.Entry) itr.next();
                Grade grade;
                Student student;
                grade = (Grade) element.getValue();
                student = (Student) element.getKey();
                System.out.println(student.toString() + " " + grade.getTotal() );
            }

            Student sp = new Student("Marinel","Mihai");
            Grade g = new Grade("POO", sp);
            g.setExamScore(2.0);
            g.setPartialScore(1.0);
            Parent mother = new Parent("Cecilia","Mihai");
            Parent father = new Parent("Marian","Mihai");
            catalog.addObserver((Parent) mother);
            catalog.addObserver((Parent) father);
            catalog.notifyObservers(g);
            System.out.println("\n\nObservatorii abonati la catalog sunt:");
            catalog.printObservers();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        /// Testare backup

        PartialCourse c = (PartialCourse) new PartialCourse.PartialCourseBuilder().setGrade().setNume("Test").build();
        User s1 = UserFactory.getUser("Student","Mihai","Andrei");
        User s2 = UserFactory.getUser("Student","Mircea","Andrei");
        User s3 = UserFactory.getUser("Student","Marian","Andrei");
        User s4 = UserFactory.getUser("Student","Cristian","Andrei");
        Grade g1 = new Grade("Test", (Student) s1);
        Grade g2 = new Grade("Test", (Student) s2);
        Grade g3 = new Grade("Test", (Student) s3);
        Grade g4 = new Grade("Test", (Student) s4);
        g1.setCourse("Test");
        g1.setExamScore(2.9);
        g1.setPartialScore(1.3);
        g2.setExamScore(1.5);
        g2.setPartialScore(1.7);
        g3.setExamScore(3.2);
        g3.setPartialScore(4.2);
        g4.setExamScore(3.4);
        g4.setPartialScore(2.9);

        c.addGrade(g1);
        c.addGrade(g2);
        c.addGrade(g3);

        System.out.println("\n-------------------Testare backup si undo--------------------\nNotele adaugate:\n" + c.GradesToString(c.grades));

        c.makeBackup();
        c.addGrade(g4);

        System.out.println("\nAdaug a 4-a nota dupa ce am facut backup:\n" + c.GradesToString(c.grades));

        c.undo();
        System.out.println("\nRevin la primele 3 note:\n" + c.GradesToString(c.grades));


        System.out.println("\n-------------------Testare visit --------------------");
        Course ci = catalog.getCourse("Paradigme_De_Programare");
        Map<Student,Grade> arr = ci.gettAllStudentGrades();

        ArrayList<Grade>g100 = new ArrayList<>();
        for(Map.Entry<Student,Grade> set : arr.entrySet()){
            Grade aux = set.getValue();
            g100.add(aux);
        }
        g100.get(0).setExamScore(0.0);
        Grade coppy_student = g100.get(0);
        System.out.println("Nota studentului inainte sa fie vizitat profesorul : " + coppy_student.getTotal());
        Teacher teacher = ci.getTeacher();
        System.out.println("Profesorul care trebuie sa fie vizitat : "+teacher.toString());
        ScoreVisitor v = new ScoreVisitor();
        v.setExam(teacher,g100);
        v.visit(teacher);
        Student s = coppy_student.getStudent();
        Grade goo = ci.getGrade(s);
        System.out.println("Nota studentului dupa ce a fost vizitat profesorul : " + goo.getExamScore());

        System.out.println("\n\n-----------Verifica Graduated Students------------");
        ArrayList<Student>arr3 = ci.getGraduatedStudents();
        System.out.println(arr3);

        System.out.println("\n\n-----------Verifica grupele-----------");
        System.out.println(ci.groups);

        System.out.println("\n\n------------Verifica asistentii-------");
        System.out.println(ci.assistents);

        System.out.println("\n\n---------Verifica getGrade---------");
        g1 = ci.getGrade(s);
        System.out.println("Am luat nota primului student si afisam to string pentru verificare :" + g1.getStudent().toString());

        System.out.println("\n\n---------Verifica addStudent din curs---------");
        ci.addStudent("312CC", (Student) UserFactory.getUser("Student","Test","Test"));
        System.out.println(ci.groups);


    }
}
