package model.university;

public class StudentProgress {
    private Integer numberOfGrade;
    private Integer grade;
    private Integer numberOfSemester;
    private Integer numberOfSubject;
    private Integer numberOfStudent;

    public StudentProgress(Integer numberOfGrade, Integer grade, Integer numberOfSubject,
            Integer numberOfStudent, Integer numberOfSemester) {
        this.numberOfGrade = numberOfGrade;
        this.grade = grade;
        this.numberOfSemester = numberOfSemester;
        this.numberOfSubject = numberOfSubject;
        this.numberOfStudent = numberOfStudent;
    }

    public void setNumberOfStudent(Integer numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }

    public Integer getNumberOfStudent() {
        return numberOfStudent;
    }

    public Integer getNumberOfGrade() {
        return numberOfGrade;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getNumberOfSubject() {
        return numberOfSubject;
    }

    public void setNumberOfGrade(Integer numberOfGrade) {
        this.numberOfGrade = numberOfGrade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setNumberOfSubject(Integer numberOfSubject) {
        this.numberOfSubject = numberOfSubject;
    }
}
