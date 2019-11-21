package model.university;

public class Group {
    private Integer numberOfGroup;
    private Integer course;
    private Integer specialtyId;

    public Group(Integer numberOfGroup, Integer course, Integer specialtyId) {
        this.numberOfGroup = numberOfGroup;
        this.course = course;
        this.specialtyId = specialtyId;
    }

    public void setNumberOfGroup(Integer numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public Integer getNumberOfGroup() {
        return numberOfGroup;
    }

    public Integer getCourse() {
        return course;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }
}
