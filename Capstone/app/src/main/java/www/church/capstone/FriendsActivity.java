package www.church.capstone;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;
import de.hdodenhof.circleimageview.*;

public class FriendsActivity extends Activity
{
	FriendsDatabase fDb;
	UserDatabase uDb;
	ArrayAdapter<String> adapter;
	ArrayList<String> list, AllMsg;
	String lastMsg;
	User user;
	String cUser;
	ListView fList;
	ActionBar bar;
	ChatDatabase cDb;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	setContentView(R.layout.friends_activity);
	fList = (ListView) findViewById(R.id.friends_list);
	fDb = new FriendsDatabase(this);
	uDb = new UserDatabase(this);
	cDb = new ChatDatabase(this);
	cUser = getIntent().getStringExtra("user");
	list = fDb.getAllFriends(cUser);
		bar = getActionBar();
		bar.setTitle(cUser+"'s "+"friends");
		bar.setBackgroundDrawable(getDrawable(R.drawable.bar_back));
		bar.show();
		adapter = new MyAdapter(this, list);
	fList.setAdapter(adapter);
		try{
	fList.setOnItemClickListener(new OnItemClickListener(){
		@Override
			public void onItemClick(AdapterView<?> p1, View v, int position, long p4)
			{
		try{
		String friend = (String) p1.getItemAtPosition(position);
		Intent intent = new Intent(FriendsActivity.this, ChatActivity.class);
	intent.putExtra("userFriend", friend);
	intent.putExtra("user", cUser);
	startActivity(intent);
	//Toast.makeText(FriendsActivity.this, friend, Toast.LENGTH_LONG).show();
	}catch(Exception e){
		Toast.makeText(FriendsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
	}
			}
		});
		}catch(Exception e){
			Toast.makeText(FriendsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	class MyAdapter extends ArrayAdapter<String>{
		public MyAdapter(Context context, ArrayList<String> list){
			super (context, R.layout.friends_layout, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
	View v;
		LayoutInflater inflater = LayoutInflater.from(FriendsActivity.this);
	v = inflater.inflate(R.layout.friends_layout, parent, false);
	TextView Fdisplay = (TextView) v.findViewById(R.id.friends_display);
	TextView Fusername = (TextView) v.findViewById(R.id.friends_username);
	CircleImageView fImg = (CircleImageView) v.findViewById(R.id.img_friends);
		try{
			String text = getItem(position);
	user = uDb.findUser(text);
	AllMsg = cDb.getMsg(cUser, text);
		Fdisplay.setText(String.valueOf(user.getDisplayName()));
			Bitmap bitmap = BitmapFactory.decodeByteArray(user.getUserPhoto(), 0, user.getUserPhoto().length);
			fImg.setImageBitmap(bitmap);
	//Fusername.setText(String.valueOf(user.getUserName()));
	if(cDb.lastMsg.toString().equals("")){
	Fusername.setVisibility(View.GONE);
	}else{
	Fusername.setText(cDb.lastMsg.toString());
	}
	}catch(Exception e){
		Toast.makeText(FriendsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
	}
			return v;
		}
	
	}

	@Override
	protected void onResume()
	{
		list = fDb.getAllFriends(cUser);
		adapter = new MyAdapter(this, list);
		fList.setAdapter(adapter);
		super.onResume();
	}
	
}
