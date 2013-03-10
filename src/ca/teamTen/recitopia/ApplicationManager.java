package ca.teamTen.recitopia;

public class ApplicationManager {

	private String userid;
	// private String email;
	private static ApplicationManager appMgr;
	
	public ApplicationManager(String user) {
		this.userid = user;
	}
	
	public void setUserID(String user) {
		this.userid = user;
	}
	
	public String getUserID() {
		return this.userid;
	}
	
	public static ApplicationManager getInstance(){
	    if (appMgr == null){
	        appMgr = new ApplicationManager("test@test.com");
	    }
	    else {
	        return appMgr;
	    }
        return appMgr;
	}
}
