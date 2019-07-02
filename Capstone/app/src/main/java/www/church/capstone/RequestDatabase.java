package www.church.capstone;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import java.util.*;
import android.util.*;

public class RequestDatabase extends SQLiteOpenHelper
{
	public static final String REQ_DB = "friend_requests";
	public static final String REQ_TABLE = "requests_table";
	public static final int REQ_DB_VERSION = 1;
	public static final String USER_REQUESTS = "user_requests";
	public static final String CURRENT_USER = "current_user";
	public static final String ROW_ID = "_id";
	public static final String TEXT_STATUS = "request_type";
	
	public RequestDatabase(Context context){
		super (context, REQ_DB, null, REQ_DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String ReqTable = "CREATE TABLE "+
		REQ_TABLE+ "( "+
			ROW_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
		USER_REQUESTS+" TEXT NOT NULL, "+
		CURRENT_USER+" TEXT NOT NULL, "+
		TEXT_STATUS+" TEXT NOT NULL "+")";
	db.execSQL(ReqTable);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldNum, int newNum)
	{
		String TABLE_UPGRADE = "DROP TABLE IF EXISTS";
		db.execSQL(TABLE_UPGRADE);
		onCreate(db);
	}
	
	public void addUserReq(String userText, String cUser, String textStatus){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
	values.put(USER_REQUESTS, userText);
	values.put(CURRENT_USER, cUser);
	values.put(TEXT_STATUS, textStatus);
	db.insert(REQ_TABLE, null, values);
	db.close();
	}
	
	
	
	public String[] getUsers(String cUsers){
		SQLiteDatabase db = this.getReadableDatabase();
	String[] lists = null;
	String list2[];
	List<String> list3 = new ArrayList<String>();
	int count = 0;
	//try{
		Cursor cursor = db.query(REQ_TABLE,
		new String[]{
			USER_REQUESTS,
			CURRENT_USER,
			TEXT_STATUS
		},
		null, null, null, null,
		null);
	list2 = new String[cursor.getCount()];
	if(cursor.moveToFirst()){
		do{
	if(cursor.getString(0).equals(cUsers)){
	//list2[count] = cursor.getString(1);
	list3.add(cursor.getString(1));
		count += 1;
		}
		}while(cursor.moveToNext());
	}
	db.close();
	cursor.close();
	lists = new String[count];
		for(int i = 0; i < count; i++){
		//lists[i] = list2[i];
		lists[i] = list3.get(i).toString();
		}
		/*}catch(Exception e){
		Log.e("DB ERROR", e.toString());
	e.printStackTrace();
		}*/
		return lists;
	}
	
	public String reqStatus(String user, String userList){
		String status = "";
	SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(REQ_TABLE,
		new String[]{
			USER_REQUESTS,
			CURRENT_USER,
			TEXT_STATUS
		},
		null, null, null, null, null);
	if(cursor.moveToFirst()){
	do{
		if(cursor.getString(0).equals(user)){
	if(cursor.getString(1).equals(userList)){
		status = cursor.getString(2);
	}
		}
	}while(cursor.moveToNext());
	}
		return status;
	}
	
	public void delete(String user, String user2){
		SQLiteDatabase db = this.getReadableDatabase();
	try{
		Cursor cursor = db.query(REQ_TABLE,
								 new String[]{
									 ROW_ID,
									 USER_REQUESTS,
									 CURRENT_USER,
									 TEXT_STATUS
								 },
								 null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				if(cursor.getString(1).equals(user)){
					if(cursor.getString(2).equals(user2)){
			db = this.getWritableDatabase();
			db.delete(REQ_TABLE, ROW_ID+"=?", new String[]{cursor.getString(0).toString()});
						}
				}
			}while(cursor.moveToNext());
		}
		}catch(Exception e){
		Log.e("DB ERROR", e.getMessage().toString());
	}
	db.close();
	}
	
	public void update(String user, String user2, String status){
		SQLiteDatabase db = this.getWritableDatabase();
	ContentValues cv = new ContentValues();
		cv.put(TEXT_STATUS, status);
		try{
			Cursor cursor = db.query(REQ_TABLE,
									 new String[]{
										 ROW_ID,
										 USER_REQUESTS,
										 CURRENT_USER,
										 TEXT_STATUS
									 },
									 null, null, null, null, null);
			if(cursor.moveToFirst()){
				do{
					if(cursor.getString(1).equals(user)){
						if(cursor.getString(2).equals(user2)){
							db.update(REQ_TABLE, cv, ROW_ID+" =? ", new String[]{String.valueOf(cursor.getString(0))});
								}
					}
				}while(cursor.moveToNext());
			}
		}catch(Exception e){
			Log.e("DB ERROR", e.getMessage().toString());
		}
	db.close();
		}
}
