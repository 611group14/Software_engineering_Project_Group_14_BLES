package group_14.software_engineering_project_group_14_bles;

import android.app.Application;

public class MyApplication extends Application{
    private static final String INITIAL_VALUE = "NULL";

    private String value;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(INITIAL_VALUE);
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public boolean isLogin(){if(INITIAL_VALUE==value) return false; else return true;}
}
