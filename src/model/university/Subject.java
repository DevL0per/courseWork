package model.university;

import java.util.Comparator;

public class Subject implements Comparable<Subject> {
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

    @Override
    public int compareTo(Subject subject) {
        if (this.getName().equals(subject.getName())) {
            return 0;
        } else if (this.getName().charAt(0) < (subject.getName().charAt(0))) {
            return -1;
        } else {
            return 1;
        }
    }
}
