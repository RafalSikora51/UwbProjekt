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

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable = false, updatable = false)
	private Integer id;

	@Column(name = "APPOINTMENTDATE", nullable = false)
	private Timestamp date;

	@ManyToOne()
	@JoinColumn(name = "IDUSER", foreignKey = @ForeignKey(name = "IDUSERAPPOINTMENT"))
	private User user;

	@ManyToOne()
	@JoinColumn(name = "IDDOCTOR", foreignKey = @ForeignKey(name = "IDDOCTORAPPOINTMENT"))
	private Doctor doctor;

	@ManyToOne()
	@JoinColumn(name = "IDAPPOINTMENT", foreignKey = @ForeignKey(name = "IDHISTORYAPPOINTMENT"))
	private MedicalHistory medicalHistory;

	@Column(name = "APPOINTMENTPROCESS")
	String appointmentProcess; // tutaj zapisane bedzie po co byla wizyta/ co sie na niej dzialo. Moze zrobic
								// jako obiekt nowy?

	public Appointment() {
		this.date = new Timestamp(System.currentTimeMillis());
	}

	public Appointment(User user, Doctor doctor, MedicalHistory medicalHistory) {
		this.user = user;
		this.doctor = doctor;
		this.medicalHistory = medicalHistory;
		this.date = new Timestamp(System.currentTimeMillis());
		this.appointmentProcess = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getAppointmentProcess() {
		return appointmentProcess;
	}

	public void setAppointmentProcess(String appointmentProcess) {
		this.appointmentProcess = appointmentProcess;
	}

	public MedicalHistory getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(MedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

}
