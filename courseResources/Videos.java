package courseResources;

public class Videos extends Resources {

    private String videoUrl;    // URL of the video
    private String videoDescription;  // Description of the video (optional)

    // Constructor
    public Videos(String title, String description, String courseName, String videoUrl, String videoDescription) {
        super(title, description, courseName);
        this.videoUrl = videoUrl;
        this.videoDescription = videoDescription;
    }

    // Getter and Setter methods for videoUrl and videoDescription
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    // Override the getResourceType method from Resources
    @Override
    public String getResourceType() {
        return "Video";
    }

    // Override toString to include video-specific details
    @Override
    public String toString() {
        return super.toString() + ", Video URL: " + videoUrl + ", Video Description: " + videoDescription;
    }
}