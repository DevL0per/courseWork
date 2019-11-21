package model.university;

public class Specialty {
    private Integer id;
    private String name;
    private Integer facultyId;

    public Specialty(Integer id, String name, Integer facultyId) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }
}
