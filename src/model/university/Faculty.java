package model.university;

public class Faculty implements Comparable<Faculty> {
    private Integer id;
    private String name;

    public Faculty(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Faculty faculty) {
        if (this.getName().equals(faculty.getName())) {
            return 0;
        } else if (this.getName().charAt(0) < (faculty.getName().charAt(0))) {
            return -1;
        } else {
            return 1;
        }
    }
}
