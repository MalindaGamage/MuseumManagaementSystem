package model;

import java.util.Date;
import java.util.UUID;

public class Exhibition {
    private UUID exhibitionId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;
    private boolean isActive;

    // Constructor
    public Exhibition(UUID exhibitionId, String title, Date startDate, Date endDate, String description, boolean isActive) {
        this.exhibitionId = exhibitionId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.isActive = isActive;
    }

    // Getters
    public UUID getExhibitionId() {
        return exhibitionId;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setExhibitionId(UUID exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
