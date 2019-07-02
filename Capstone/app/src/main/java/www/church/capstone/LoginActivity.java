package www.church.capstone;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class LoginActivity extends Activity
{
	EditText userLogin, passLogin;
	String LoginText, PassText;
	Button LoginBtn;
	MainActivity main;
	ProfileActivity profile;
	RegisterActivity register;
	private static final String MY_TABLE = "TABLE";
	UserDatabase userDb;
	ActionBar bar;
	User user;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
	userLogin = (EditText) findViewById(R.id.text_login_user);
	passLogin = (EditText) findViewById(R.id.text_login_password);
	LoginBtn = (Button) findViewById(R.id.btn_login);
	userDb = new UserDatabase(this);
	profile = new ProfileActivity();
	register = new RegisterActivity();
	main = new MainActivity();
		bar = getActionBar();
		bar.setTitle("Login");
		bar.show();
		profile.pref = getSharedPreferences(MY_TABLE, MODE_WORLD_READABLE);
		LoginBtn.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v){
	LoginText = userLogin.getText().toString();
	PassText = passLogin.getText().toString();
	user = userDb.findUser(LoginText);
	if(user != null){
		if(PassText.equalsIgnoreCase(String.valueOf(user.getUserPassword()))){
	SharedPreferences.Editor edit = profile.pref.edit();
	edit.putString("userTextName", String.valueOf(user.getUserName()));
	edit.commit();
		Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
intent.putExtra("userName", String.valueOf(user.getUserName()));
	startActivity(intent);
		finish();
	main.finish();
		}else{
	passLogin.setError("password not correct");
	passLogin.requestFocus();
			//Toast.makeText(LoginActivity.this, "password not correct, enter a valid one.", Toast.LENGTH_SHORT).show();
		}
	}else{
	userLogin.setError("username not found");
	userLogin.requestFocus();
		//Toast.makeText(LoginActivity.this, "username not found.", Toast.LENGTH_SHORT).show();
	}
	
	
			}
		});
	}
}
