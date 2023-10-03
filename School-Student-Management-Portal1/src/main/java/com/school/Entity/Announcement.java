   package com.school.Entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;





@Entity
@Table(name="announcement")
public class Announcement {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(name="title")
    private String title;
    
    @Column(name="message")
    private String message;
    
    @ElementCollection
    @CollectionTable(name = "announcement_categories",
            joinColumns = {@JoinColumn(name = "announcement_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "category_name")
    @Column(name = "category")
    private Map<String, String> categories = new HashMap<>();
    
    
    @Column(name="announcementdate")
    private LocalDate announcementdate;
    
    @Column(name="duedate")
    private LocalDate duedate;
    
    @Column(name="target")
    private String target;
    
    public Announcement() {
        
    }
    
    
    
    public Announcement(String title, String message, Map<String, String> categories, LocalDate announcementdate,
            LocalDate duedate, String target) {
        super();
        this.title = title;
        this.message = message;
        this.categories = categories;
        this.announcementdate = announcementdate;
        this.duedate = duedate;
        this.target = target;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public Map<String, String> getCategories() {
        return categories;
    }
    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }
    public LocalDate getAnnouncementdate() {
        return announcementdate;
    }
    public void setAnnouncementdate(LocalDate announcementdate) {
        this.announcementdate = announcementdate;
    }
    public LocalDate getDuedate() {
        return duedate;
    }
    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }



	public String getTarget() {
		return target;
	}



	public void setTarget(String target) {
		this.target = target;
	}
    
    
	
    


}
