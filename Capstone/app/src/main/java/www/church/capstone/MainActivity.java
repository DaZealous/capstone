package www.church.capstone;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.media.*;
import de.hdodenhof.circleimageview.*;
import com.squareup.picasso.*;
import android.content.res.*;
import android.graphics.Color;

public class MainActivity extends Activity 
{
	Button prof;
	ProfileActivity profile;
	SharedPreferences preference;
	Boolean isLogIn;
	Boolean isLogout;
	ActionBar bar;
	private static final String MY_TABLE = "TABLE";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		prof = (Button) findViewById(R.id.prof_btn);
		profile = new ProfileActivity();
		bar = getActionBar();
		bar.setTitle("primate");
		bar.show();
		
	try{
		isLogout = false;
	profile.pref = getSharedPreferences(MY_TABLE, MODE_WORLD_READABLE);
		isLogIn = profile.pref.getBoolean("Login", false);
	if(isLogIn){
		Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
		startActivity(intent);
		finish();
	}
		prof.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v){
			Intent intent = new Intent("www.church.capstone.login_activity");
			startActivity(intent);
		}
	});
	findViewById(R.id.reg_btn).setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v){
			Intent intent = new Intent("www.church.capstone.register_activity");
			startActivity(intent);
		}
	});
	}catch(Exception e){
	Toast.makeText(MainActivity.this, isLogIn.toString(), Toast.LENGTH_SHORT).show(); 
	}
		}
}
