package Model;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class DiscussionLesson {

    private int disscussionID;
    private Integer parentId;
    private int acccountId;
    private int lessonId;
    private String comment;
    private String name;
    private String avatar;
    private Timestamp createAt;
  private ArrayList<DiscussionLesson> replies;
    public DiscussionLesson() {
    }
    public DiscussionLesson(Integer parentId, int acccountId, int lessonId, String comment, Timestamp createAt) {

        this.parentId = parentId;
        this.acccountId = acccountId;
        this.lessonId = lessonId;
        this.comment = comment;
        this.createAt = createAt;
    }
    
    public DiscussionLesson(int disscussionID, Integer parentId, int acccountId, int lessonId, String comment, String name, String avatar, Timestamp createAt) {
        this.disscussionID = disscussionID;
        this.parentId = parentId;
        this.acccountId = acccountId;
        this.lessonId = lessonId;
        this.comment = comment;
        this.name = name;
        this.avatar = avatar;
        this.createAt = createAt;
    }



    public int getDisscussionID() {
        return disscussionID;
    }

    public void setDisscussionID(int disscussionID) {
        this.disscussionID = disscussionID;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getAcccountId() {
        return acccountId;
    }

    public void setAcccountId(int acccountId) {
        this.acccountId = acccountId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getTimeAgo() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createDatetime = createAt.toLocalDateTime();
        Duration duration = Duration.between(createDatetime, now);

        if (duration.toMinutes() < 1) {
            return " Just now";
        } else if (duration.toHours() < 1) {
            return " "+duration.toMinutes() + " minutes ago";
        } else if (duration.toDays() < 1) {
            return " "+duration.toHours() + " hours ago";
        } else {
            return " " + duration.toDays() + " days ago";
        }
    }

    @Override
    public String toString() {
        return "DiscussionLesson{" + "disscussionID=" + disscussionID + ", parentId=" + parentId + ", acccountId=" + acccountId + ", lessonId=" + lessonId + ", comment=" + comment + ", name=" + name + ", avatar=" + avatar + ", createAt=" + createAt + '}';
    }

}
