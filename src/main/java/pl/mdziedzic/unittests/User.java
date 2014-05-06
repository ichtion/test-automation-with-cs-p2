package pl.mdziedzic.unittests;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_users")
@Access(AccessType.FIELD)
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private long id;

	@Column(name = "login", nullable = false)
	private String login;

	@SuppressWarnings("unused")
	private User() {
	}

	public User(String login) {
		this.login = login;
	}

	public long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String toString() {
		return String.format("User [%s]", login);
	}

}
