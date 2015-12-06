package group_14.software_engineering_project_group_14_bles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class RegisterActivity extends Activity {

    EditText USER_NAME,USER_PASS,CON_PASS;
    RadioButton radio_restaurant,radio_cafe,radio_retail;
    String user_name,user_pass,con_pass,user_type;
    Button REG;
    Context context = this;


    int registerStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        USER_NAME = (EditText) findViewById(R.id.reg_user);
        USER_PASS = (EditText) findViewById(R.id.reg_pass) ;
        CON_PASS = (EditText) findViewById(R.id.con_pass);

        radio_restaurant = (RadioButton)findViewById(R.id.radio_restaurant);
        radio_cafe = (RadioButton)findViewById(R.id.radio_cafe);
        radio_retail = (RadioButton)findViewById(R.id.radio_retail);

        REG = (Button) findViewById(R.id.user_reg);

        REG.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_name = USER_NAME.getText().toString();
                user_pass = USER_PASS.getText().toString();
                con_pass = CON_PASS.getText().toString();
                DataOperation dataOperation = new DataOperation();
                ArrayList<ArrayList<String>> userInfo = new ArrayList<ArrayList<String>>();
                userInfo = dataOperation.getAllUserInfo(context);

                if(!(user_pass.equals(con_pass)))
                {
                    Toast.makeText(getBaseContext(), "Passwords are not matching", Toast.LENGTH_LONG).show();
                    USER_NAME.setText("");
                    USER_PASS.setText("");
                    CON_PASS.setText("");
                    registerStatus = 1;
                }

                for (int i = 0;i < userInfo.size()-1;i++)
                {
                    if((user_name.equals(userInfo.get(i).get(1))))
                    {
                        Toast.makeText(getBaseContext(), "The same User Name existed!", Toast.LENGTH_LONG).show();
                        USER_NAME.setText("");
                        USER_PASS.setText("");
                        CON_PASS.setText("");
                        registerStatus = 1;
                        break;
                    }

                }

                if(registerStatus == 0)
                {
                    Random r = new Random();
                    String rr = r.nextInt(99999)+"";

                    Calendar now = Calendar.getInstance();
                    Integer year = now.get(Calendar.YEAR);
                    Integer month = now.get(Calendar.MONTH);
                    Integer date = now.get(Calendar.DATE);
                    String userID = year.toString()+month.toString()+date.toString()+rr;

                    UserDbHelper userDbHelper = new UserDbHelper(context);
                    userDbHelper.addUserInformations(userDbHelper, userID, user_name, user_pass, user_type);
                    MyApplication app = (MyApplication) getApplication();
                    app.setValue(user_name);
                    Toast.makeText(getBaseContext(), "Registration success", Toast.LENGTH_LONG).show();

                    finish();

                }

            }
        });



    }

    public void onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_restaurant:
                if (checked)
                    user_type = radio_restaurant.getText().toString();
                    break;
            case R.id.radio_cafe:
                if (checked)
                    user_type = radio_cafe.getText().toString();
                    break;
            case R.id.radio_retail:
                if (checked)
                    user_type = radio_retail.getText().toString();
                    break;
        }
    }


}
