package www.church.capstone;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;
import java.util.*;

public class ChatDatabase extends SQLiteOpenHelper
{
	public static final String CHAT_DB = "chat_database";
	public static final String CHAT_TABLE = "chat_table";
	public static final int CHAT_DB_VERSION = 1;
	public static final String FRIENDS_COL = "user_friends_chat";
	public static final String USER_COL = "user_col";
	public static final String MSG_COL = "message_col";
	public static final String ROW_ID = "_id";
	
	ArrayList<String> users;
	String lastMsg;
	public ChatDatabase(Context context){
		super (context, CHAT_DB, null, CHAT_DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String CHATS = "CREATE TABLE "+
			CHAT_TABLE+"("+
			ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
			USER_COL+" TEXT NOT NULL, "+
			FRIENDS_COL+" TEXT NOT NULL, "+
			MSG_COL+" TEXT NOT NULL"+
			")";
		db.execSQL(CHATS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		String Table = "DROP TABLE IF EXISTS "+CHAT_TABLE;
		db.execSQL(Table);
		onCreate(db);	
		}
	
	
	public void addUserMsg(String user, String user2, String message){
	try{
		
	SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(USER_COL, user);
		cv.put(FRIENDS_COL, user2);
		cv.put(MSG_COL, message);
		db.insert(CHAT_TABLE, null, cv);
		db.close();
		}catch(Exception e){
			Log.e("DB ERROR", e.getMessage());
		}
	}
	
	
	public ArrayList<String> getMsg(String user, String user2){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<String> list= new ArrayList<String>();
	lastMsg = "";
	this.users = new ArrayList<String>();
	Cursor cursor = db.query(CHAT_TABLE,
		 new String[]{
		 ROW_ID,
		 USER_COL,
			 FRIENDS_COL,
			 MSG_COL
				 },
			 null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
		if(cursor.getString(1).equals(user)&&
		cursor.getString(2).equals(user2)){
		list.add(cursor.getString(3));
	users.add(cursor.getString(1));
			}
		if(cursor.getString(1).equals(user2)&&
		cursor.getString(2).equals(user)){
		list.add(cursor.getString(3));
		users.add(cursor.getString(1));
	}
			}while(cursor.moveToNext());
		}
	for(int i = 0; i < list.size(); i++){
		if(i == list.size() - 1){
			lastMsg = list.get(i).toString();
		}
	}
		cursor.close();
		db.close();
		return list;
			}
		}
