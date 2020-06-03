package bytimegroup.bytimeserver.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * todo: insert @AliasFor spring annotation for smoother interaction with mongo
 */
@Document
public class Users {
	
	@Id private ObjectId _id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private Boolean verified;
	// these list contain ids
	private List<String> subscriptions;
	private List<String> subscribers;
	
	public Users() {}
	public Users(ObjectId _id, String username, String password, String email, 
					String firstname, String lastname, Boolean verified, List<String> subscriptions,
						List<String> subscribers) {
		this._id = _id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.verified = verified;
		this.subscribers = subscribers;
		this.subscriptions = subscriptions;
	}
	public Users(RegisterForm registration) {
		this.username = registration.getUsername();
		this.email = registration.getEmail();
		this.password = registration.getPassword();
		this.firstname = registration.getFirstname();
		this.lastname = registration.getLastname();
		this.verified = false;
		this.subscribers = new ArrayList<String>();
		this.subscriptions = new ArrayList<String>();
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getVerified() {
		return verified;
	}
	public void verify() {
		verified = true;
	}
	
	public String getID() {
		return _id.toHexString();
	}
	public void set_id(ObjectId _id) { this._id = _id; }
	
	public List<String> getSubscriptions() {
		return subscriptions;
	}
	public List<String> getSubscribers() {
		return subscribers;
	}
	
	public void setSubscriptions(List<String> subscriptions) {
		this.subscriptions = subscriptions;
	}
	public void setSubscribers(List<String> subscribers) {
		this.subscribers = subscribers;
	}
	
}
