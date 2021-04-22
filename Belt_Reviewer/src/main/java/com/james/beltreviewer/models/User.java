package com.james.beltreviewer.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;




@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size(min = 1, message ="first name must be at least 1 character!")
    @Size(max = 25, message ="first name cant be more then 25 characters!")
    private String firstName;
    
    @NotNull
    @Size(min = 1, message ="last name must be at least 1 character!")
    @Size(max = 40, message ="last name cant be more then 40 characters!")
    private String lastName;
    
    @NotNull
    @Size(min = 1, message ="Must use valid email")
    @Pattern(regexp="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-zA-Z0-9.-]+$", message="Invalid email pattern")
    @Email(message ="Must use valid email")
    private String email;
    
    @NotNull(message="Location must not be blank!")
	@Size(min=2, max=60, message="Location must be more than 2 characters!")
	private String location;
    
    @NotNull(message="State must not be blank!")
	private String state;
    
    @NotNull
    @Size(min = 5, message ="password must be at least 5 characters!")
    private String password;
    
    @Transient
    @NotNull(message="Confirm Password must not be blank!")
    private String passwordConfirmation;
    
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    	name = "users_events",
    	joinColumns = @JoinColumn(name = "user_id"),
    	inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> joinedevents;
    
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Event> events;
    
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Message> messages;
    
    public User() {
    }
    public User(String firstName, String lastName, String email, String location, String state, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.location = location;
		this.state = state;
		this.password = password;
	}
    
   
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
	@PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
/////////////// ID /////////////////////
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

///////////////// First Name ////////////////////
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

//////////////// Last Name /////////////////////
	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

/////////////// Email ///////////////////////
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
////////////// Location ///////////////////////
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
//////////////// State //////////////////////////
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
//////////// Password ///////////////////////////////
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}


	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

//////////////// Created at updated at //////////////////////////////
	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
//////////////// EVENTS ////////////////////
	
	public List<Event> getJoinedevents() {
		return joinedevents;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setJoinedevents(List<Event> joinedevents) {
		this.joinedevents = joinedevents;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
