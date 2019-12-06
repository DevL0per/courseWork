package model;

import javax.validation.constraints.NotNull;

public class Student extends AbstractUser {

    private Integer studentNumber;
    private Integer numberOfGroup;
    private Role role;
    private Double scholarship;
    private String formOfTraining;

    public Student(String name, String surname, String patronymic,
                   String phoneNumber, String email, String password,
                   Integer accountCode, Integer group, Integer studentNumber,
                   Double scholarship, String formOfTraining, Role role) {
        super(name, surname, patronymic, phoneNumber, email, password, accountCode);
        this.numberOfGroup = group;
        this.studentNumber = studentNumber;
        this.scholarship = scholarship;
        this.formOfTraining = formOfTraining;
        this.role = role;
    }

    public void setScholarship(Double scholarship) {
        this.scholarship = scholarship;
    }

    public void setFormOfTraining(String formOfTraining) {
        this.formOfTraining = formOfTraining;
    }

    public Double getScholarship() {
        return scholarship;
    }

    public String getFormOfTraining() {
        return formOfTraining;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setNumberOfGroup(Integer numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getNumberOfGroup() {
        return numberOfGroup;
    }

    public Role getRole() {
        return role;
    }
}
