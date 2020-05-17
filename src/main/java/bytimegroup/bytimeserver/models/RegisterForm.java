package bytimegroup.bytimeserver.models;

/**
 * todo: insert @JsonCreator spring annotation for smoother work
 */
public class RegisterForm {
	
	// required
	private String username;
	private String email;
	private String password;
	
	// optional
	private String firstname;
	private String lastname;
	
	public RegisterForm() {}
	public RegisterForm(String username, String email, String password) {
		this.username = username.toLowerCase().trim();
		this.email = email.toLowerCase().trim();
		this.password = password;
	}
	public RegisterForm(String username, String email, String password, String firstname, String lastname) {
		this.username = username.toLowerCase().trim();
		this.email = email.toLowerCase().trim();
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() { 
		return password;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	
	public void setUsername(String username) {
		this.username = username.toLowerCase().trim();
	}
	public void setEmail(String email) {
		this.email = email.toLowerCase().trim();
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * email, username, pwd must be present
	 * @return true if so
	 */
	public Boolean isValid() {
		return username != null && username.isEmpty() == false
				&& email != null && email.isEmpty() == false
				&& password != null && password.isEmpty() == false;
	}
	
}
