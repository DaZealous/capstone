package www.church.capstone;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import java.util.*;
import java.lang.reflect.*;
public class UserDatabase extends SQLiteOpenHelper
{

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "user_database";
	private static final String DB_TABLE = "user_details";
	private static final String USER_DISPLAY = "display_name";
	private static final String USER_NAME = "user_name";
	private static final String USER_EMAIL = "user_email";
	private static final String USER_PASSWORD = "user_password";
	private static final String USER_ID = "_id";
	private static final String USER_PHOTO = "user_photo";
	private static final String USER_THUMBNAIL = "user_thumbnail";
	
	public UserDatabase(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +      
			DB_TABLE + "("          
			+ USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ USER_DISPLAY + " TEXT NOT NULL, " 
			+ USER_NAME + " TEXT NOT NULL, " 
			+ USER_EMAIL + " TEXT NOT NULL, "
			+ USER_PASSWORD +" TEXT NOT NULL, " 
			+ USER_PHOTO +" BLOB NOT NULL, "
			+ USER_THUMBNAIL+" BLOB NOT NULL"+")";
		try{
			db.execSQL(CREATE_PRODUCTS_TABLE);
		}catch(Exception e){
			e.printStackTrace();
		}
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		String TABLE_UPGRADE = "DROP TABLE IF EXISTS";
		db.execSQL(TABLE_UPGRADE);
		onCreate(db);
			}
		
		public boolean addUsers(User user){
		boolean result = false;
			ContentValues cv = new ContentValues();
		cv.put(USER_DISPLAY, user.getDisplayName());
		cv.put(USER_NAME, user.getUserName());
		cv.put(USER_EMAIL, user.getEmail());
		cv.put(USER_PASSWORD, user.getUserPassword());
		cv.put(USER_PHOTO, user.getUserPhoto());
		cv.put(USER_THUMBNAIL, user.getUserThumbnail());
	SQLiteDatabase db = this.getWritableDatabase();
	String query = "SELECT * FROM " + DB_TABLE + " WHERE " + USER_NAME + " =  \"" + user.getUserName() + "\"";   
	Cursor cursor = db.rawQuery(query, null);
		if(cursor.getCount() <= 0){
		db.insert(DB_TABLE, null, cv);
		db.close();
		cursor.close();
		result = false;
			}else{
			db.close();
			cursor.close();
				result = true;
			}
			return result;
		}
		
	public User findUser(String username){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + DB_TABLE + " WHERE " + USER_NAME + " =  \"" + username + "\"";   
	User user = new User();
		Cursor cursor = db.rawQuery(query, null);
	if(cursor.moveToFirst()){
		cursor.moveToFirst();
		user.setDisplayName(cursor.getString(1));
		user.setUserName(cursor.getString(2));
		user.setEmail(cursor.getString(3));
		user.setUserPassword(cursor.getString(4));
		user.setUserPhoto(cursor.getBlob(5));
		user.setUserThumbnail(cursor.getBlob(6));
		cursor.close();
	}else{
		user = null;
	}
	db.close();
	return user;
	}
	
	public String[] getUsers(String userName){
		int count = 0;
	String[] lists;
		User user = new User();
		//ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = getReadableDatabase();   
		Cursor cursor = db.query(DB_TABLE, 
		new String[]{
		USER_NAME},    
		null, null, null, null,    
		USER_NAME);   
	lists = new String[cursor.getCount()-1];
		if (cursor.moveToFirst()) {     
		do {  
		if(cursor.getString(0).equals(userName)){}else{
	lists[count] = cursor.getString(0);
		++count;
		}
		} while(cursor.moveToNext());    
		}   
		return lists;
	}
	
	public void updateUser(byte[] pic, byte[] thumb, String username){
		SQLiteDatabase db = this.getReadableDatabase();
	ContentValues values = new ContentValues();
	values.put(USER_PHOTO, pic);
	values.put(USER_THUMBNAIL, thumb);
	Cursor cursor = db.query(DB_TABLE,
	new String[]{
		USER_ID,
		USER_NAME
	}, null, null, null, null, null);
	if(cursor.moveToFirst()){
	do{
	if(cursor.getString(1).equals(username)){
	db = this.getWritableDatabase();
	db.update(DB_TABLE, values, USER_ID+" =? ", new String[]{String.valueOf(cursor.getString(0))});
				}
		}while(cursor.moveToNext());
			}
	cursor.close();
	db.close();
		}
}
