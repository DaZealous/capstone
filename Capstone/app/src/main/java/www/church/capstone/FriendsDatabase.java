package www.church.capstone;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import java.util.*;

public class FriendsDatabase extends SQLiteOpenHelper
{
	public static final String FRIENDS_DB = "friend_database";
	public static final String FRIENDS_TABLE = "friends_table";
	public static final int FRIENDS_DB_VERSION = 1;
	public static final String FRIENDS_COL = "user_friends";
	public static final String USER_COL = "user_col";
	public static final String ROW_ID = "_id";
	
	public FriendsDatabase(Context context){
		super (context, FRIENDS_DB, null, FRIENDS_DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String F_TABLE = "CREATE TABLE "+
		FRIENDS_TABLE+"("+
			ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
		USER_COL+" TEXT NOT NULL, "+
		FRIENDS_COL+" TEXT NOT NULL"+")";
	db.execSQL(F_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		String Table = "DROP TABLE IF EXISTS "+FRIENDS_TABLE;
		db.execSQL(Table);
		onCreate(db);
	}

	public void addFriend(String cUser, String user2){
		SQLiteDatabase db = this.getWritableDatabase();
	ContentValues cv = new ContentValues();
	cv.put(USER_COL, cUser);
	cv.put(FRIENDS_COL, user2);
		db.insert(FRIENDS_TABLE, null, cv);
	db.close();
	}
	
	public ArrayList<String> getAllFriends(String cUser){
		ArrayList<String> list = new ArrayList<String>();
	SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(FRIENDS_TABLE,
		new String[]{
			ROW_ID,
			USER_COL,
			FRIENDS_COL
		}, 
null, null, null, null, null);
	if(cursor.moveToLast()){
		do{
	if(cursor.getString(1).equals(cUser)){
	list.add(cursor.getString(2));
	}
		}while(cursor.moveToPrevious());
	}
		return list;
	}
}
