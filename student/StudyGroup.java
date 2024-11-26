package student;

public class StudyGroup {
    private int id;
    private String groupName;

    // Constructor
    public StudyGroup(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

            @Override
        public String toString() {
            return groupName + " (ID: " + id + ")";
        }

}

