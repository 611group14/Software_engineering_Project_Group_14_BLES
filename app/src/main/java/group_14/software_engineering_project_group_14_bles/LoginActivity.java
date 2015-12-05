package group_14.software_engineering_project_group_14_bles;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
    Button Login;
    AutoCompleteTextView USERNAME;
    EditText USERPASS;
//    EditText USERNAME,USERPASS;

    String username,userpass;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * essential button initialization
         */

        Login = (Button)findViewById(R.id.b_login);
//        USERNAME = (EditText) findViewById(R.id.user_name);
        USERNAME =  (AutoCompleteTextView)findViewById(R.id.user_name);
        USERPASS = (EditText) findViewById(R.id.user_pass);

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String NAME;
                boolean loginStatus = false;

                Toast.makeText(getBaseContext(), "Please wait...", Toast.LENGTH_LONG).show();

                username = USERNAME.getText().toString();
                userpass = USERPASS.getText().toString();

                DataOperation dataOperation = new DataOperation();
                loginStatus = dataOperation.loginAuthentication(context,username,userpass);

                NAME = username;

                if(loginStatus)
                {
                    Toast.makeText(getBaseContext(), "Login Success----\n Welcome "+NAME, Toast.LENGTH_LONG).show();
                    finish();

                    MyApplication app = (MyApplication) getApplication();
                    app.setValue(NAME);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Login Failed---- ", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });


    }
}
