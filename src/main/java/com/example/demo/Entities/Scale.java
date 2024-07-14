package com.example.demo.Entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;


@Entity(name = "scales")
public class Scale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int totalPage;

    private boolean isDeleted;

    private LocalDateTime createdDate;

    private LocalDateTime expiryDate;

    private int adminId;

    private Long previousId;

    private String eGroupBgColor;

    private String cGroupBgColor;

    private String eGroupMusic;

    private String cGroupMusic; 

    @OneToMany(mappedBy = "scale")
    private List<Question> questions;

    public Scale() {
    }

    public Scale(String title) {
        this.title = title;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Long getPreviousId() {
        return previousId;
    }

    public void setPreviousId(Long previousId) {
        this.previousId = previousId;
    }

    public String getEGroupBgColor() {
        return eGroupBgColor;
    }

    public void setEGroupBgColor(String eGroupBgColor) {
        this.eGroupBgColor = eGroupBgColor;
    }

    public String getCGroupBgColor() {
        return cGroupBgColor;
    }

    public void setCGroupBgColor(String cGroupBgColor) {
        this.cGroupBgColor = cGroupBgColor;
    }

    public String getEGroupMusic() {
        return eGroupMusic;
    }

    public void setEGroupMusic(String eGroupMusic) {
        this.eGroupMusic = eGroupMusic;
    }

    public String getCGroupMusic() {
        return cGroupMusic;
    }

    public void setCGroupMusic(String cGroupMusic) {
        this.cGroupMusic = cGroupMusic;
    }
    
}