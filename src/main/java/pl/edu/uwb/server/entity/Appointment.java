package pl.edu.uwb.server.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

	@Column(name = "APPOINTMENTDAY", nullable = false)
	private String localDate;

	@Column(name = "APPOINTMENTHOURID", nullable = false)
	private int appHourId;

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
	String appointmentProcess;

	public Appointment() {
	}

	public Appointment(User user, Doctor doctor, MedicalHistory medicalHistory, int appHourId, int year, int month,
			int dayOfMonth) {
		this.user = user;
		this.doctor = doctor;
		this.medicalHistory = medicalHistory;
		this.localDate = LocalDate.of(year, month, dayOfMonth).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.appHourId = appHourId;
		this.appointmentProcess = "";
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

	public int getAppHourId() {
		return appHourId;
	}

	public void setAppHourId(int appHourId) {
		this.appHourId = appHourId;
	}

	public String getLocalDate() {
		return localDate;
	}

	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}

}
