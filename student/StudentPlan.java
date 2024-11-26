package student;

public class StudentPlan {
   private int studentId;
    private int timeAvailable;
    private int subjects;
    private int chapters;
    private String priority;

    // Constructor
    public StudentPlan(int studentId, int timeAvailable, int subjects, int chapters, String priority) {
        this.studentId = studentId;
        this.timeAvailable = timeAvailable;
        this.subjects = subjects;
        this.chapters = chapters;
        this.priority = priority;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTimeAvailable() {
        return timeAvailable;
    }

    public void setTimeAvailable(int timeAvailable) {
        this.timeAvailable = timeAvailable;
    }

    public int getSubjects() {
        return subjects;
    }

    public void setSubjects(int subjects) {
        this.subjects = subjects;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
