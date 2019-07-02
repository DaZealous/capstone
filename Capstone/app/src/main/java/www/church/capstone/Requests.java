package www.church.capstone;

public class Requests
{
	String currentUser, requestUser;

	public Requests(){
		
	}
	public Requests(String currentUser, String requestUser)
	{
		this.currentUser = currentUser;
		this.requestUser = requestUser;
	}

	public void setCurrentUser(String currentUser)
	{
		this.currentUser = currentUser;
	}

	public String getCurrentUser()
	{
		return currentUser;
	}

	public void setRequestUser(String requestUser)
	{
		this.requestUser = requestUser;
	}

	public String getRequestUser()
	{
		return requestUser;
	}
		}
