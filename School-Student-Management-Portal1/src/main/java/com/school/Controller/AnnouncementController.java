package com.school.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.Entity.Announcement;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
	@Autowired
	private EntityManager entityManager;


	@Autowired
	private NotificationController emailService;

	@PostMapping("/{id}/send-email")
	public void sendEmailForAnnouncement(@PathVariable Long id) throws MessagingException {

		Announcement announcement = entityManager.find(Announcement.class, id);

		List<String> emails = new ArrayList<String>(Arrays.asList("mannamdileep1996@gmail.com", "tejaswararaotavva@gmail.com"));

		// Send email to each target audience member
		for (String email : emails) {
			String subject = "New announcement: " + announcement.getTitle();
			String text = announcement.getMessage() + "\n\n" +
					"Announcement date: " + announcement.getAnnouncementdate() + "\n" +
					"Due date: " + announcement.getDuedate();

			emailService.sendEmail(email, subject, text);
		}
	}

	//	@PostMapping("/{id}/send-email")
	//	  public void sendEmailForAnnouncement(@PathVariable Long id, @RequestParam String standard, @RequestParam(required = false) String section) throws MessagingException {
	//		  Announcement announcement = entityManager.find(Announcement.class, id);
	//		  
	//		  if(standard!=null && section!=null){
	//			  List<String> emails = userRepository.sendEmails(standard, section);
	//			}
	//			else if(section == null){
	//				List<String> emails = userRepository.sendEmails(standard);
	//			}
	//			else{
	//				List<String> emails = userRepository.sendEmails(standard);
	//			}
	//		  
	//		  //List<String> emails = new ArrayList<String>(Arrays.asList("mannamdileep1996@gmail.com", "tejaswararaotavva@gmail.com"));
	//		 
	//		  
	//		    for (String email : emails) {
	//		    	String subject = "New announcement: " + announcement.getTitle();
	//			      String text = announcement.getMessage() + "\n\n" +
	//			                "Announcement date: " + announcement.getAnnouncementdate() + "\n" +
	//			                "Due date: " + announcement.getDuedate();
	//			      
	//		      emailService.sendEmail(email, subject, text);
	//		    }    
	//	  }




	@GetMapping("/getAnnouncement")
	public List<Announcement> getAnnouncements() {
		LocalDate today = LocalDate.now();
		return entityManager.createQuery(
				"SELECT a FROM Announcement a WHERE a.duedate >= :today",
				Announcement.class)
				.setParameter("today", today)
				.getResultList();
	}

	@GetMapping("/getAnnouncementById/{id}")
	public Announcement getAnnouncementById(@PathVariable Integer id) {
		return entityManager.find(Announcement.class, id);
	}


	@PostMapping("/createAnnouncement")
	@Transactional
	public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
		entityManager.persist(announcement);
		return ResponseEntity.status(HttpStatus.CREATED).body(announcement);
	}

	@PutMapping("/updateAnnouncement/{id}")
	@Transactional
	public Announcement updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement announcement) {
		Announcement existingAnnouncement = entityManager.find(Announcement.class, id);
		if (existingAnnouncement != null) {
			existingAnnouncement.setTitle(announcement.getTitle());
			existingAnnouncement.setMessage(announcement.getMessage());
			existingAnnouncement.setCategories(announcement.getCategories());
			existingAnnouncement.setAnnouncementdate(announcement.getAnnouncementdate());
			existingAnnouncement.setDuedate(announcement.getDuedate());
			existingAnnouncement.setTarget(announcement.getTarget());
			entityManager.merge(existingAnnouncement);
		}
		return existingAnnouncement;
	}

	@DeleteMapping("/deleteAnnouncement/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAnnouncement(@PathVariable Integer id) {
		Announcement existingAnnouncement = entityManager.find(Announcement.class, id);
		if (existingAnnouncement != null) {
			entityManager.remove(existingAnnouncement);
		}
	}


	@GetMapping("/target")
	public List<Announcement> getAnnouncementsByTarget(
			@RequestParam(name = "standard", required = false) String standard,
			@RequestParam(name = "section", required = false) String section,
			@RequestParam(name = "usertype", required = false) String usertype) {
		String query = "SELECT a FROM Announcement a WHERE 1=1";
		if (standard != null) {
			query += " AND a.target LIKE '%" + standard + "%'";
		}
		if (section != null) {
			query += " AND a.target LIKE '%" + section + "%'";
		}
		if (usertype != null) {
			query += " AND a.target LIKE '%" + usertype + "%'";
		}
		TypedQuery<Announcement> typedQuery = entityManager.createQuery(query, Announcement.class);
		return typedQuery.getResultList();
	}


}