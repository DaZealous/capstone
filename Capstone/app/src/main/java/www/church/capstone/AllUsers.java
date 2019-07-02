package www.church.capstone;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.util.*;
import android.view.*;
import android.widget.AdapterView.*;
import de.hdodenhof.circleimageview.*;
import android.graphics.*;

public class AllUsers extends Activity
{
	ListView listV;
	ArrayList<String> list;
	ListAdapter adapter;
		UserDatabase userDb;
		User user;
	Context context;
	Bundle extras;
	String cUser;
	Requests request;
	RequestDatabase rDb;
	String reqType = "not friends";
	String reqType2 = "not friends";
	ActionBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.all_users);
		super.onCreate(savedInstanceState);
		userDb = new UserDatabase(this);
	user = new User();
	rDb = new RequestDatabase(this);
	
		extras = getIntent().getExtras();
		cUser = extras.getString("user");
	listV = (ListView) findViewById(R.id.user_list_id);
		list = new ArrayList<String>();
		bar = getActionBar();
		bar.setTitle("Users");
		bar.show();
	String userName[] = userDb.getUsers(cUser);
	if(userName.length == 0){
		listV.setVisibility(View.GONE);
		Toast.makeText(AllUsers.this, "no users", Toast.LENGTH_SHORT).show();
		return;
	}
	/*ArrayList<String> list2 = new ArrayList<String>();
	list2.add("good");*/
	try{
		//list.addAll(userDb.getUsers());
		//adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	adapter = new MyAdapter(this, userName);
		listV.setAdapter(adapter);
		}catch(Exception e){
	Toast.makeText(AllUsers.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	listV.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> v, View p, int a, long b){
			String s = (String) v.getItemAtPosition(a);
	Toast.makeText(AllUsers.this, s, Toast.LENGTH_SHORT).show();
		}
	});
		
	}
	class MyAdapter extends ArrayAdapter<String>{
		public MyAdapter(Context context, String[] userName){
			super(context, R.layout.user_layout, userName);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
	View v;
			LayoutInflater inflater = LayoutInflater.from(getContext());
		v = inflater.inflate(R.layout.user_layout, parent, false);
	final String text = getItem(position);
	String[] reqSent = rDb.getUsers(cUser);
	String[] userReq = rDb.getUsers(text);
	TextView textDisplay = (TextView) v.findViewById(R.id.user_layout_display);
	TextView textName = (TextView) v.findViewById(R.id.user_layout_name);
	CircleImageView imgReq = (CircleImageView) v.findViewById(R.id.img_request);
	final TextView textStatus = (TextView) v.findViewById(R.id.request_status);
	final Button addFriends = (Button) v.findViewById(R.id.add_friend);
	final Button cancleFriends = (Button) v.findViewById(R.id.cancle_friend);
	cancleFriends.setEnabled(false);
		reqType = rDb.reqStatus(cUser, text);
	if(reqType.equalsIgnoreCase("Friends")){
		addFriends.setEnabled(false);
		cancleFriends.setEnabled(false);
		textStatus.setText(reqType);
	}
		if(reqType.equalsIgnoreCase("request sent")){
			addFriends.setEnabled(false);
			cancleFriends.setEnabled(true);
		textStatus.setText(reqType);
		}
	if(reqType.equalsIgnoreCase("request received")){
		addFriends.setEnabled(false);
		cancleFriends.setEnabled(true);
		textStatus.setText(reqType);
	}
			reqType2 = rDb.reqStatus(text, cUser);
			if(reqType2.equalsIgnoreCase("Friends")){
				addFriends.setEnabled(false);
				cancleFriends.setEnabled(false);
				textStatus.setText(reqType);
			}
	if(reqType2.equalsIgnoreCase("request sent")){
		addFriends.setEnabled(false);
		cancleFriends.setEnabled(true);
		textStatus.setText(reqType2);
	}
	if(reqType2.equalsIgnoreCase("request received")){
		addFriends.setEnabled(false);
		cancleFriends.setEnabled(false);
		textStatus.setText(reqType2);
	}
	
		cancleFriends.setOnClickListener(new OnClickListener(){
			@Override
		public void onClick(View v)
			{
	textStatus.setText("Not Friends");
		addFriends.setEnabled(true);
			rDb.delete(text, cUser);
		if(true){
			rDb.delete(cUser, text);
		}
		cancleFriends.setEnabled(false);
				}
	});
	
	addFriends.setOnClickListener(new OnClickListener(){
		@Override
	public void onClick(View view){
		addFriends.setEnabled(false);
		cancleFriends.setEnabled(true);
	if(textStatus.getText().toString().equalsIgnoreCase("Not Friends")){
	reqType = "Request Sent";
		textStatus.setText(reqType);
	performRequests(text, reqType);
	if(true){
	reqType2 = "Request Received";
		rDb.addUserReq(cUser, text, reqType2);
	}
		}else{
		reqType = "Not Friends";
			textStatus.setText(reqType);
		}
	}
	});
	
	user = userDb.findUser(text);
	//textName.setText(text);
	Bitmap bitmap = BitmapFactory.decodeByteArray(user.getUserPhoto(), 0, user.getUserPhoto().length);
		textDisplay.setText(user.getDisplayName());
		textName.setText(user.getUserName());
		imgReq.setImageBitmap(bitmap);
			return v;
		}
		
	/*
		@Override
		public void remove(String object)
		{
			// TODO: Implement this method
			super.remove(object);
		}*/
	}
	public void performRequests(String userText, String textStatus){
		try{
		rDb.addUserReq(userText, cUser, textStatus);
			Toast.makeText(AllUsers.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
			}catch(Exception e){
	Toast.makeText(AllUsers.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	}
