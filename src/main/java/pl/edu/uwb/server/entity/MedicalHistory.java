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
	
	@ManyToOne()
	@JoinColumn(name = "IDUSER", foreignKey = @ForeignKey(name = "IDUSERAPPOINTMENT"))
	private User user;
	
	@Column(name = "SPECIALIZATIONID", nullable = false)
	private int specId;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDAPPOINTMENT")
	@JsonIgnore
	private Set<Appointment> appointmentSet;

	{
		appointmentSet = new HashSet<Appointment>();
	}
	
	
}
