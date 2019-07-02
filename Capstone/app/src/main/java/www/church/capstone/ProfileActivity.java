package www.church.capstone;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.squareup.picasso.*;
import de.hdodenhof.circleimageview.*;
import android.graphics.drawable.*;
import java.io.*;
import java.util.*;

public class ProfileActivity extends Activity
{
	UserDatabase userDb;
	SharedPreferences pref;
	private static final String MY_TABLE = "TABLE";
	View v;
	Button PostB, PageB;
	SharedPreferences.Editor editor;
	Boolean isLogIn;
	CircleImageView imgV;
	User user;
	Uri imgUri;
	Dialog dialog;
	TextView userDisplay, userDisplayName;
	SharedPreferences.Editor edit ;
	ActionBar bar;
	ProfileActivity profile;
	Bitmap imgBitmap;
	ImageView imgView;
	TextView pEmail, pUserCount;
	private static int IMAGE_PICK = 1;
	ArrayList<String> fList;
	FriendsDatabase fDb;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		PostB = (Button) findViewById(R.id.btn_post);
		PageB = (Button) findViewById(R.id.btn_page);
		userDisplay = (TextView) findViewById(R.id.user_display_text);
		userDisplayName = (TextView) findViewById(R.id.user_name_text);
		pEmail = (TextView) findViewById(R.id.profile_user_email);
		pUserCount = (TextView) findViewById(R.id.profile_user_count);
		fDb = new FriendsDatabase(this);
		imgV = (CircleImageView) findViewById(R.id.img_profile);
		Configuration config = getResources().getConfiguration();
		ImageView v = (ImageView) findViewById(1);
		bar = getActionBar();
		bar.setTitle("Your Profile");
		bar.show();
		isLogIn = true;
		Bundle extras = getIntent().getExtras();
		userDb = new UserDatabase(this);
		pref = getSharedPreferences(MY_TABLE, MODE_WORLD_READABLE);
		edit = pref.edit();
		edit.putBoolean("Login", isLogIn);
		edit.commit();
	try{	
		user =  userDb.findUser(pref.getString("userTextName", ""));     
		if (user != null) {      
	fList = fDb.getAllFriends(user.getUserName());
			userDisplay.setText(String.valueOf(user.getDisplayName()));   
			userDisplayName.setText(String.valueOf(user.getUserName()));   
		pEmail.setText("Email: "+String.valueOf(user.getEmail()));
	pUserCount.setText(""+fList.size());
	imgBitmap = BitmapFactory.decodeByteArray(user.getUserPhoto(), 0, user.getUserPhoto().length);
		imgV.setImageBitmap(imgBitmap);
		} else {      
			} 
	
		//Bundle extras = getIntent().getExtras();
		//userDisplay.setText(pref.getString("userText", ""));
	//userDisplayName.setText(pref.getString("userTextName", ""));
			//userDisplay.setText(userDb.SetDisplayText().toString());
		}catch(Exception e){
			Toast.makeText(ProfileActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show(); 
		}
		/*if(config.orientation == Configuration.ORIENTATION_PORTRAIT){
			Toast toast = new Toast(this);
			toast.makeText(ProfileActivity.this, "portrait", Toast.LENGTH_LONG).show();
		}
		img = (CircleImageView) findViewById(R.id.img);
		Picasso.get().load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").placeholder(R.drawable.student).into(img);
		pref = getSharedPreferences(MY_TABLE, MODE_WORLD_READABLE);
		SharedPreferences.Editor edit = pref.edit();
		edit.putBoolean("Login", isLogIn);
		edit.commit();
		int imgR = getResources().getIdentifier("@drawable/ic_launcher", null, this.getPackageName());
		img.setImageResource(imgR);
		*/
		
		imgV.setOnClickListener(new OnClickListener(){
			@Override
		public void onClick(View p1)
			{
try{
		 dialog = new Dialog(ProfileActivity.this);
		dialog.setTitle(userDisplayName.getText().toString());
	dialog.setContentView(R.layout.img_viewer);
	 imgView = (ImageView) dialog.findViewById(R.id.img_view);
	Button btnChange = (Button) dialog.findViewById(R.id.img_change);
		imgView.setImageBitmap(imgBitmap);
	btnChange.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1)
			{
	Intent intent = new Intent();
	intent.setAction(Intent.ACTION_PICK);
	intent.setType("image/*");
	startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_PICK);
			}
	});
		dialog.show();
}catch(Exception e){
	Toast.makeText(ProfileActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show(); 
}
				}
		});
		
		findViewById(R.id.btn_post).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					PostB.setTextColor(Color.parseColor("#ff00ff"));
					PageB.setTextColor(Color.parseColor("#000000"));
					findViewById(R.id.page_v).setVisibility(View.INVISIBLE);
					findViewById(R.id.post_v).setVisibility(View.VISIBLE);
				}
			});

		findViewById(R.id.btn_page).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					PostB.setTextColor(Color.parseColor("#000000"));
					PageB.setTextColor(Color.parseColor("#ff00ff"));
					findViewById(R.id.page_v).setVisibility(View.VISIBLE);
					findViewById(R.id.post_v).setVisibility(View.INVISIBLE);
				}
			});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
	switch(id){
		case R.id.log_out:
			isLogIn = false;
			//Toast.makeText(ProfileActivity.this, isLogIn.toString(), Toast.LENGTH_SHORT).show(); 
			Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
			startActivity(intent);
			edit = pref.edit();
			edit.putBoolean("Login", isLogIn);
			edit.commit();
			finish();
		return true;
	case R.id.users:
	Intent intent2 = new Intent(ProfileActivity.this, AllUsers.class);
	intent2.putExtra("user", userDisplayName.getText().toString());
	startActivity(intent2);
	return true;
	case R.id.requests:
	Intent intent3 = new Intent(ProfileActivity.this, RequestActivity.class);
	intent3.putExtra("user", userDisplayName.getText().toString());
	startActivity(intent3);
		return true;
	case R.id.friends:
	Intent intent4 = new Intent(ProfileActivity.this, FriendsActivity.class);
	intent4.putExtra("user", userDisplayName.getText().toString());
	startActivity(intent4);
	return true;
	case R.id.exit:
			finish();
			System.exit(0);
		return true;
	default:
		return false;
	}
			
	}

	@Override
	public void onBackPressed()
	{
		finish();
	System.exit(0);
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == IMAGE_PICK && resultCode == RESULT_OK){
		try{
			Uri imgUri = data.getData();
		imgView.setImageURI(imgUri);
	
	Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
	byte[] pic = stream.toByteArray();

	Bitmap bitmap2 = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
	ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
	bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, stream2);
	byte[] thumb = stream.toByteArray();
		userDb.updateUser(pic, thumb, userDisplayName.getText().toString());
	user = userDb.findUser(userDisplayName.getText().toString());
	imgBitmap = BitmapFactory.decodeByteArray(user.getUserPhoto(), 0, user.getUserPhoto().length);
	imgV.setImageBitmap(imgBitmap);
	dialog.dismiss();
		}catch(Exception e){
			Toast.makeText(ProfileActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show(); 
		}
		
		super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onResume()
	{
	try{
		//pref = getSharedPreferences(MY_TABLE, MODE_WORLD_READABLE);
		 //user =  userDb.findUser(pref.getString("userTextName", ""));     
		fList = fDb.getAllFriends(user.getUserName());
	pUserCount.setText(""+fList.size());
	}catch(Exception e){
		Toast.makeText(ProfileActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show(); 
	}
		super.onResume();
	}
	
	
}
