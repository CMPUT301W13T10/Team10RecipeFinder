package ca.teamTen.recitopia;

public class ApplicationManager {

	private String userid;
	// private String email;
	
	public ApplicationManager(String user) {
		this.userid = user;
	}
	
	public void setUserID(String user) {
		this.userid = user;
	}
	
	public String getUserID() {
		return this.userid;
	}
}
