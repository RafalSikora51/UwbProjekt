package pl.edu.uwb.server.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Token")
public class Token {

	public Token() {

	}

	public Token(String token, User user) {
		this.token = token;
		this.createdOn = new Timestamp(System.currentTimeMillis());
		this.user = user;
		this.active = true;
	}

	public Token(String token, Doctor doctor) {
		this.token = token;
		this.createdOn = new Timestamp(System.currentTimeMillis());
		this.doctor = doctor;
		this.active = true;
	}
	
	@Column(name = "TOKEN", nullable = false)
	private String token;

	@Column(name = "CREATEDON", nullable = false)
	private Timestamp createdOn;

	@Column(name = "EXPIREDON", nullable = true)
	private Timestamp expiredOn;

	@ManyToOne()
	@JoinColumn(name = "IDUSER", foreignKey = @ForeignKey(name = "IDUSERTOKEN"))
	private User user;

	@ManyToOne()
	@JoinColumn(name = "IDDOCTOR", foreignKey = @ForeignKey(name = "IDDOCTORTOKEN"))
	private Doctor doctor;
	
	@Column(name = "ACTIVE", nullable = false)
	private boolean active;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable = false, updatable = false)
	private long id;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getExpiredOn() {
		return expiredOn;
	}

	public void setExpiredOn(Timestamp expiredOn) {
		this.expiredOn = expiredOn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	

}