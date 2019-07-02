package www.church.capstone;

public class User
{
	String displayName, userName,
	email, userPassword;
	byte[] userPhoto, userThumbnail;
	int userId;
	public User(String displayName, String userName)
	{
		this.displayName = displayName;
		this.userName = userName;
	}
	
	
	public User(){
		
	}
	public User(String displayName, String userName, String email, String userPassword, byte[] userPhoto, byte[] userThumbnail)
	{
		this.displayName = displayName;
		this.userName = userName;
		this.email = email;
		this.userPassword = userPassword;
		this.userPhoto = userPhoto;
		this.userThumbnail = userThumbnail;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}

	public String getUserPassword()
	{
		return userPassword;
	}
	
	public void setUserPhoto(byte[] userPhoto){
		this.userPhoto = userPhoto;
	}
	public byte[] getUserPhoto(){
		return userPhoto;
	}
	public void setUserThumbnail(byte[] userThumbnail){
		this.userThumbnail = userThumbnail;
	}
	public byte[] getUserThumbnail(){
		return userThumbnail;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public int getUserId()
	{
		return userId;
	}
	}
