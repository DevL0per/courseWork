package model.university;

public class Subject {
    private Integer numberOfSubject;
    private String name;

    public Subject(Integer numberOfSubject, String name) {
        this.numberOfSubject = numberOfSubject;
        this.name = name;
    }

    public Integer getNumberOfSubject() {
        return numberOfSubject;
    }

    public String getName() {
        return name;
    }

    public void setNumberOfSubject(Integer numberOfSubject) {
        this.numberOfSubject = numberOfSubject;
    }

    public void setName(String name) {
        this.name = name;
    }
}
