package pl.edu.uwb.server.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable = false, updatable = false)
	private Integer id;

	@Column(name = "PESEL", nullable = false, unique = true)
	private String countryId;

	@Column(name = "FIRSTNAME", nullable = false)
	private String firstName;

	@Column(name = "LASTNAME", nullable = false)
	private String lastName;

	@NaturalId
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;

	@Column(name = "CREATEDON", nullable = false)
	private Timestamp createdOn;

	@Column(name = "ADMIN", nullable = false)
	private boolean isAdmin;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDUSER")
	@JsonIgnore
	private Set<Token> tokenSet;

	{
		tokenSet = new HashSet<Token>();
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDUSER")
	@JsonIgnore
	private Set<Appointment> appointmentSet;

	{
		appointmentSet = new HashSet<Appointment>();
	}

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDUSER")
	@JsonIgnore
	private Set<MedicalHistory> medicalHistorySet;

	{
		medicalHistorySet = new HashSet<MedicalHistory>();
	}

	public User() {

	}

	public User(String countryId, String firstName, String lastName, String email) {
		this.countryId = countryId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isAdmin = false;
		this.createdOn = new Timestamp(System.currentTimeMillis());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Set<Token> getTokenSet() {
		return tokenSet;
	}

	public void setTokenSet(Set<Token> tokenSet) {
		this.tokenSet = tokenSet;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<Appointment> getAppointmentSet() {
		return appointmentSet;
	}

	public void setAppointmentSet(Set<Appointment> appointmentSet) {
		this.appointmentSet = appointmentSet;
	}

	public Set<MedicalHistory> getMedicalHistorySet() {
		return medicalHistorySet;
	}

	public void setMedicalHistorySet(Set<MedicalHistory> medicalHistorySet) {
		this.medicalHistorySet = medicalHistorySet;
	}

}
