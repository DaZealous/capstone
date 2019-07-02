package www.church.capstone;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import de.hdodenhof.circleimageview.*;
import java.util.*;

public class RequestActivity extends Activity
{
	RequestDatabase rDb;
	UserDatabase UserDb;
	FriendsDatabase fDb;
	Bundle extras;
	String cUser;
	ListView ReqList;
	ListAdapter adapter;
	String users[];
	ArrayList<String> users2;
	String reqType;
	Requests requests;
	User user;
	ActionBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.request_activity);
		super.onCreate(savedInstanceState);
	ReqList = (ListView) findViewById(R.id.user_request_list);
	rDb = new RequestDatabase(this);
	UserDb = new UserDatabase(this);
	fDb = new FriendsDatabase(this);
		bar = getActionBar();
		bar.setTitle("Friend Requests");
		bar.show();
		reqType = "";
		extras = getIntent().getExtras();
		cUser = extras.getString("user");
	users2 = new ArrayList<String>();
		try{
		users = rDb.getUsers(cUser);
	for(int i = 0; i < users.length; i++){
	reqType = rDb.reqStatus(users[i].toString(), cUser);
	if(reqType.equalsIgnoreCase("Request Received")){
		users2.add(users[i].toString());
			}
		}
		if(users2.size() == 0){
				Toast.makeText(RequestActivity.this,
							   "No Friend Requests", Toast.LENGTH_SHORT).show();
				return;
			}
			adapter = new ReqAdapter(this, users2);
			ReqList.setAdapter(adapter);
	}catch(Exception e){
		Toast.makeText(RequestActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show(); 
	}
		
	}
	class ReqAdapter extends ArrayAdapter<String>{
		public ReqAdapter(Context context, ArrayList<String> users){
			super (context, R.layout.request_layout, users);
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent)
		{
			View v = null;
		try{
		LayoutInflater inflater = LayoutInflater.from(getContext());
	 v = inflater.inflate(R.layout.request_layout, parent, false);
			final String text = getItem(position);
			user = UserDb.findUser(text);
			TextView textDisplay = (TextView) v.findViewById(R.id.request_layout_display);
			TextView textName = (TextView) v.findViewById(R.id.request_layout_name);
			final Button addFriends = (Button) v.findViewById(R.id.request_accept_friend);
			final Button declineFriends = (Button) v.findViewById(R.id.request_decline_friend);
		CircleImageView fImg = (CircleImageView) v.findViewById(R.id.img_request);
		addFriends.setOnClickListener(new OnClickListener(){
			@Override
		public void onClick(View v){
		try{
			fDb.addFriend(cUser, text);
		rDb.update(cUser, text, "Friends");
	addFriends.setEnabled(false);
	declineFriends.setEnabled(false);
			Toast.makeText(RequestActivity.this, "Friends Accepted Successfully", Toast.LENGTH_LONG).show();
		if(true){
			fDb.addFriend(text, cUser);
			rDb.update(text, cUser, "Friends");
		}
	}catch(Exception e){
	Toast.makeText(RequestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
	}
	//onResume();
		}
		});
			declineFriends.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						rDb.delete(text, cUser);
						if(true){
							rDb.delete(cUser, text);
						}
						addFriends.setEnabled(false);
						declineFriends.setEnabled(false);
						Toast.makeText(RequestActivity.this, "Friends Declined Successfully", Toast.LENGTH_LONG).show();
			//onResume();
					}
				});
			Bitmap bitmap = BitmapFactory.decodeByteArray(user.getUserPhoto(), 0, user.getUserPhoto().length);
			fImg.setImageBitmap(bitmap);
		textDisplay.setText(user.getDisplayName());
		textName.setText(user.getUserName());
			}catch(Exception e){
				Toast.makeText(RequestActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show(); 
			}
			return v;
		}
	}
	/*
	@Override
	protected void onResume()
	{
		try{
			users = rDb.getUsers(cUser);
			for(int i = 0; i < users.length; i++){
				reqType = rDb.reqStatus(users[i].toString(), cUser);
				if(reqType.equalsIgnoreCase("Request Received")){
					users2.add(users[i].toString());
				}
			}
			if(users2.size() == 0){
				Toast.makeText(RequestActivity.this,
							   "No Friend Requests", Toast.LENGTH_SHORT).show();
				return;
			}
			adapter = new ReqAdapter(this, users2);
			ReqList.setAdapter(adapter);
		}catch(Exception e){
			Toast.makeText(RequestActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show(); 
		}
		super.onResume();
	}
	*/
}
