public class Grade implements Comparable<Grade>, Cloneable {
    private Double partialScore;
    private Double examScore;
    private Student student;
    private String course;

    public Grade(String course, Student student) {
        partialScore = 0.0;
        examScore = 0.0;
        this.course = course;
        this.student = student;
    }

    public Double getTotal() {
        return partialScore + examScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public int compareTo(Grade o) {
        return this.getTotal().compareTo(o.getTotal());
    }

    @Override
    public Grade clone() {
        Grade grade = new Grade(course,student);
        grade.setPartialScore(partialScore);
        grade.setExamScore(examScore);
        return grade;
    }
}
