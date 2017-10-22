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

// TODO: Auto-generated Javadoc
/**
 * The Class Token.
 * 
 * @author acewiczm
 * @author Rafał
 */
@Entity
@Table(name = "Token")
public class Token {

	/**
	 * Instantiates a new token.
	 */
	public Token() {

	}

	/**
	 * Instantiates a new token.
	 *
	 * @param token
	 *            the token
	 * @param user
	 *            the user
	 */
	public Token(String token, User user) {
		this.token = token;
		this.createdOn = new Timestamp(System.currentTimeMillis());
		this.user = user;
		this.active = true;
	}

	/** The token. */
	@Column(name = "TOKEN", nullable = false)
	private String token;

	/** The created on. */
	@Column(name = "CREATEDON", nullable = false)
	private Timestamp createdOn;

	/** The expired on. */
	@Column(name = "EXPIREDON", nullable = true)
	private Timestamp expiredOn;

	/** The user. */
	@ManyToOne()
	@JoinColumn(name = "IDUSER", foreignKey = @ForeignKey(name = "IDUSERTOKEN"))
	private User user;

	/** The active. */
	@Column(name = "ACTIVE", nullable = false)
	private boolean active;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", insertable = false, updatable = false)
	private long id;

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token
	 *            the new token
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * Gets the expired on.
	 *
	 * @return the expired on
	 */
	public Timestamp getExpiredOn() {
		return expiredOn;
	}

	/**
	 * Sets the expired on.
	 *
	 * @param expiredOn
	 *            the new expired on
	 */
	public void setExpiredOn(Timestamp expiredOn) {
		this.expiredOn = expiredOn;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active
	 *            the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

}