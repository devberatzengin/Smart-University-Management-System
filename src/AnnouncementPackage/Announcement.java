package AnnouncementPackage;

import java.time.LocalDate;

public class Announcement {
    private int announcementID;
    private String title;
    private String content;
    private String courseCode; // optional
    private String postedBy;
    private LocalDate postDate;


    public Announcement(String title, String content, String courseCode, String postedBy) {
        this.title = title;
        this.content = content;
        this.courseCode = courseCode;
        this.postedBy = postedBy;
        this.postDate = LocalDate.now();
    }

    public Announcement(String title, String content, String postedBy) {
        this.title = title;
        this.content = content;
        this.courseCode = "For Everyone";
        this.postedBy = postedBy;
        this.postDate = LocalDate.now();
    }











    public int getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(int announcementID) {
        this.announcementID = announcementID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }
}
