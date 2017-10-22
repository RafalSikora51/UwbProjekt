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

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 * 
 * @author acewiczm
 */
@Entity
public class User {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable = false, updatable = false)
	private Integer id;

	/** The country id. */
	@Column(name = "PESEL", nullable = false)
	private String countryId;

	/** The first name. */
	@Column(name = "FIRSTNAME", nullable = false)
	private String firstName;

	/** The last name. */
	@Column(name = "LASTNAME", nullable = false)
	private String lastName;

	/** The email. */
	@NaturalId
	@Column(name = "EMAIL", nullable = false)
	private String email;

	/** The created on. */
	@Column(name = "CREATEDON", nullable = false)
	private Timestamp createdOn;

	/** The token set. */
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDUSER")
	@JsonIgnore
	private Set<Token> tokenSet;

	{
		tokenSet = new HashSet<Token>();
	}

	/**
	 * Instantiates a new user.
	 */
	public User() {

	}

	/**
	 * Instantiates a new user.
	 *
	 * @param countryId
	 *            the country id
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param email
	 *            the email
	 */
	public User(String countryId, String firstName, String lastName, String email) {
		this.countryId = countryId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.createdOn = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the country id.
	 *
	 * @return the country id
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * Sets the country id.
	 *
	 * @param countryId
	 *            the new country id
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the new created on
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the token set.
	 *
	 * @return the token set
	 */
	public Set<Token> getTokenSet() {
		return tokenSet;
	}

	/**
	 * Sets the token set.
	 *
	 * @param tokenSet
	 *            the new token set
	 */
	public void setTokenSet(Set<Token> tokenSet) {
		this.tokenSet = tokenSet;
	}
	
	

}
