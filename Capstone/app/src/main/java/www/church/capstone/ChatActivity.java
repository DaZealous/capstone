package www.church.capstone;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.view.inputmethod.*;
import android.widget.*;
import de.hdodenhof.circleimageview.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import android.graphics.drawable.*;

public class ChatActivity extends Activity
{
	String cUser, fUser;
	String messages[][];
	ListView msgList;
	EditText msgEdit;
	ImageButton sendButton;
	ChatDatabase cDb;
	String message;
	ArrayList<String> uMsgs, fMsgs;
	ArrayAdapter<String> adapter;
	ActionBar bar;
	User user;
	UserDatabase uDb;
	TextView title;
	CircleImageView imgTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);
	msgList = (ListView) findViewById(R.id.chat_list);
		 msgEdit = (EditText) findViewById(R.id.chat_edit_text);
	sendButton = (ImageButton) findViewById(R.id.chat_send);
		cDb = new ChatDatabase(this);
		uDb = new UserDatabase(this);
		cUser = getIntent().getStringExtra("user");
		fUser = getIntent().getStringExtra("userFriend"); 
	LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.action_layout, null);
	title = (TextView) v.findViewById(R.id.chat_title);
	imgTitle = (CircleImageView) v.findViewById(R.id.chat_img);
		user = uDb.findUser(fUser);
	title.setText(user.getUserName());
		Bitmap bitmap = BitmapFactory.decodeByteArray(user.getUserPhoto(), 0, user.getUserPhoto().length);
		imgTitle.setImageBitmap(bitmap);
	 bar = getActionBar();
	//bar.setDisplayShowCustomEnabled(true);
	//bar.setDisplayShowTitleEnabled(true);
	//bar.setDisplayUseLogoEnabled(true);
	bar.setDisplayShowHomeEnabled(true);
	bar.setDisplayHomeAsUpEnabled(true);
		bar.setCustomView(v, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.MATCH_PARENT));
	bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	bar.show();
		
		uMsgs = cDb.getMsg(cUser, fUser);
		//fMsgs = cDb.getMsg(fUser, cUser);
		hideKeyboardFrom(msgEdit);
		if(uMsgs.size() != 0){
	try{
		adapter = new MyAdapter(this, uMsgs);
		msgList.setAdapter(adapter);
	}catch(Exception e){
		Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
	}
		 msgList.post(new Runnable() {
		 @Override
		 public void run() {
	 msgList.setSelection(adapter.getCount() - 1);
		 }
		});
		}
		sendButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1)
		{
	String pat = "\\s+";
	Pattern pattern = Pattern.compile(pat);
	Matcher matcher = pattern.matcher(msgEdit.getText().toString());
	if(TextUtils.isEmpty(msgEdit.getText().toString())){
	msgEdit.setError("Type a message first");
	msgEdit.requestFocus();
	}else if(matcher.matches()){
		msgEdit.setError("Cannot contain ordinary space");
		msgEdit.requestFocus();
	}
	else{
		hideKeyboardFrom(msgEdit);
	message = msgEdit.getText().toString().trim();
			insertMsg(message);
				}
			}
			});
		}
	
	public void insertMsg(String message){
	msgEdit.setText(null);
		cDb.addUserMsg(cUser, fUser, message);
		uMsgs = cDb.getMsg(cUser, fUser);
		//fMsgs = cDb.getMsg(fUser, cUser);
	adapter = new MyAdapter(this, uMsgs);
		msgList.setAdapter(adapter);
		msgList.post(new Runnable() {
				@Override
				public void run() {
				msgList.setSelection(adapter.getCount() - 1);
				}
			});
	}
	
	private void hideKeyboardFrom(View view) {
	 InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
	 imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	 }
	 
	 class MyAdapter extends ArrayAdapter<String>{
		 
		 public MyAdapter(Context context, ArrayList<String> msg){
		super (context, R.layout.chat_layout, msg);
		 }

		 @Override
		 public View getView(int position, View convertView, ViewGroup parent)
		 {
			 View v = null;
			v = LayoutInflater.from(ChatActivity.this).inflate(
		R.layout.chat_layout, parent, false);
			String textMsg = getItem(position);
	//CircleImageView cImg = (CircleImageView) v.findViewById(R.id.user_img);
		CircleImageView fImg = (CircleImageView) v.findViewById(R.id.friend_image);
		TextView userText = (TextView) v.findViewById(R.id.text_user);
	 TextView friendText = (TextView) v.findViewById(R.id.text_user2);
	 try{
	if(cDb.users.size() != 0)
	if(cDb.users.get(position).equals(cUser)){
		/*Bitmap bitmap = BitmapFactory.decodeByteArray(user1.getUserThumbnail(), 0, user1.getUserThumbnail().length);
	*/
		 userText.setVisibility(View.VISIBLE);
	 	userText.setText(textMsg);
		//cImg.setImageBitmap(bitmap);
	}
	if(cDb.users.get(position).equals(fUser)){
	try{
		fImg.setVisibility(View.GONE);
		friendText.setVisibility(View.VISIBLE);
		friendText.setText(textMsg);
		}catch(Exception e){
		Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
	}
		}
		}catch(Exception e){
			 Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
		 }
		 
			 return v;
		 }
	 }
}
