package student;

public class Student {
    private short id;
    private String name;
    private byte semester;
    private float averageScore;
    private String branch;

    // Default Constructor
    public Student() {
        super();
    }

    // Parameterized Constructor
    public Student(short id, String name, byte semester, float averageScore, String branch) {
        super();
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.averageScore = averageScore;
        this.branch = branch;
    }

    // Getters and Setters
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getSemester() {
        return semester;
    }

    public void setSemester(byte semester) {
        this.semester = semester;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    // toString Method
    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", semester=" + semester +
               ", averageScore=" + averageScore + ", branch=" + branch + "]";
    }
}
