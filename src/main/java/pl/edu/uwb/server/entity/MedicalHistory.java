package pl.edu.uwb.server.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MedicalHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable = false, updatable = false)
	private Integer id;

	@Column(name = "SPECIALIZATIONID", nullable = false)
	private int specId;

	@ManyToOne()
	@JoinColumn(name = "IDUSER", foreignKey = @ForeignKey(name = "IDUSERHISTORY"))
	private User user;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDMEDICALHISTORY")
	@JsonIgnore
	private Set<Appointment> appointmentSet;

	{
		appointmentSet = new HashSet<Appointment>();
	}

	public MedicalHistory() {

	}

	public MedicalHistory(User user, int specId) {
		this.user = user;
		this.specId = specId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getSpecId() {
		return specId;
	}

	public void setSpecId(int specId) {
		this.specId = specId;
	}

	public Set<Appointment> getAppointmentSet() {
		return appointmentSet;
	}

	public void setAppointmentSet(Set<Appointment> appointmentSet) {
		this.appointmentSet = appointmentSet;
	}

}
