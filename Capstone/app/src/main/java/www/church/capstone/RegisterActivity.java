package www.church.capstone;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import de.hdodenhof.circleimageview.*;
import java.net.*;
import android.graphics.*;
import android.graphics.drawable.*;
import java.io.*;

public class RegisterActivity extends Activity
{
	Button registerUser;
	private static final int IMAGE_PICK = 1;
	Uri imageUri;
	CircleImageView img;
	User user;
	UserDatabase userDatabase;
	ProfileActivity profile;
	MainActivity main;
	EditText displayName, userName, email, userPassword;
	private static final String MY_TABLE = "TABLE";
	SharedPreferences.Editor edit;
	ActionBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
	registerUser = (Button) findViewById(R.id.btn_register);
	displayName = (EditText) findViewById(R.id.text_display);
	userName = (EditText) findViewById(R.id.text_username);
	email = (EditText) findViewById(R.id.text_email);
	userPassword = (EditText) findViewById(R.id.text_password);
	img = (CircleImageView) findViewById(R.id.img);
		bar = getActionBar();
		bar.setTitle("Register");
		bar.show();
	profile = new ProfileActivity();
	imageUri = null;
	main = new MainActivity();
		//Toast.makeText(RegisterActivity.this, "upload image first "+imageUri.toString(), Toast.LENGTH_LONG).show();
		
		userDatabase = new UserDatabase(this);
		try{
			profile.pref = getSharedPreferences(MY_TABLE, MODE_WORLD_READABLE);
		edit = profile.pref.edit();
		}catch(Exception e){
			Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		}
		registerUser.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				checkDetails();
			}
		});
		
		findViewById(R.id.btn).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					/*Picasso.get().load("https://www.gstatic.com/webp/gallery/1.jpg").networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.businessman).into(img, new Callback(){
					 @Override
					 public void onSuccess()
					 {
					 // TODO: Implement this method
					 }

					 @Override
					 public void onError(Exception p1)
					 {
					 Picasso.get().load("https://www.gstatic.com/webp/gallery/1.jpg").placeholder(R.drawable.businessman).into(img);

					 }


					 });
					 */
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE"), IMAGE_PICK);
				}
			});
	}
	
		
	public void checkDetails(){
		if(TextUtils.isEmpty(displayName.getText().toString())
		|| TextUtils.isEmpty(userName.getText().toString())
		|| TextUtils.isEmpty(email.getText().toString())
		|| TextUtils.isEmpty(userPassword.getText().toString())){
	Toast.makeText(RegisterActivity.this, "Either of fields is empty", Toast.LENGTH_SHORT).show();
		return;
		}
		if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
			email.setError("Invalid Email Address");
		email.requestFocus();
			return;
		}
	if(userName.getText().toString().contains(" ")){
		userName.setError("space not allowed");
		userName.requestFocus();
	return;
	}
		handleRegister();
	}
	
		public void handleRegister(){
	if(imageUri == null){
		Toast.makeText(RegisterActivity.this, "upload image first ", Toast.LENGTH_LONG).show();
	return;
	}
	Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
		byte[] pic = stream.toByteArray();
			Bitmap bitmap2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
			ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
			bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, stream2);
			byte[] thumb = stream2.toByteArray();
			
			String display,
			username,
			emailAddress,
			password;
		display = displayName.getText().toString();
		username = userName.getText().toString().trim();
		emailAddress = email.getText().toString();
		password = userPassword.getText().toString();
			user = new User(
			display, 
			username,
			emailAddress,
			password,
			pic,
			thumb);
		try{
	if(userDatabase.addUsers(user) == false){
			Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
		edit.putString("userText", display);
		edit.putString("userTextName", username);
		edit.commit();
		Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
		intent.putExtra("userDisplay", display);
		intent.putExtra("userName", username);
			startActivity(intent);
			finish();
			main.finish();
			}else{
		Toast.makeText(RegisterActivity.this, "Username Already Exist, Choose Another One", Toast.LENGTH_SHORT).show();
			}
				/*userDatabase.addUsers(user);
					Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
				profile.pref = getSharedPreferences(MY_TABLE, MODE_WORLD_READABLE);
				SharedPreferences.Editor edit = profile.pref.edit();
				edit.putString("userText", display);
				edit.putString("userTextName", username);
				edit.commit();
					Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
			//intent.putExtra("userDisplay", display);
			//intent.putExtra("userName", username);
					startActivity(intent);
					finish();*/
			}catch(Exception e){
		Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
			}
		}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{	super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == IMAGE_PICK && resultCode == RESULT_OK){
			imageUri = data.getData();
			//Toast.makeText(RegisterActivity.this, data.getDataString(), Toast.LENGTH_LONG).show();
			img.setImageURI(imageUri);
		}
	}
}
