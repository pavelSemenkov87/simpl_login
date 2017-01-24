package ru.techmas.simple_login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.techmas.simple_login.Events.LoginSeccsessEvent;
import ru.techmas.simple_login.Events.SetFragmentEvent;
import ru.techmas.simple_login.Fragments.InputFragment;
import ru.techmas.simple_login.Fragments.LoginFragment;
import ru.techmas.simple_login.Fragments.MainFragment;
import ru.techmas.simple_login.Fragments.RegistrFragment;

public class MainActivity extends AppCompatActivity {

    public static final int INPUT = 1, LOGIN = 2, REGISTR = 3, MAIN = 4;
    private SharedPreferences sPref;
    public static final int MODE_PRIVATE = 0x0000;
    public static final String HAS_LOG = "has", LOGIN_DATA = "log", PASS_DATA = "pass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sPref = getPreferences(MODE_PRIVATE);
        if (Boolean.valueOf(sPref.getString(HAS_LOG, "false"))) {
            String log = sPref.getString(LOGIN_DATA, "false"), pass = sPref.getString(PASS_DATA, "false");
            setFragment(MainFragment.getInstance("Привет, " +log+ "!"), true, "Вы вошли как "+log);
        }else {
            setFragment(new InputFragment(), true, "Simple Login");
        }
    }

    private void setFragment(Fragment fragment, boolean first, String title) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (first) {
            transaction.add(R.id.fragment_frame, fragment);
            getSupportActionBar().setTitle(title);
        } else {
            transaction.replace(R.id.fragment_frame, fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            getSupportActionBar().setTitle(title);
        }
        transaction.commit();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetFragmentEvent event){
        switch (event.getTipe()){
            case LOGIN:
                setFragment(new LoginFragment(), false, "Войти");
                break;
            case REGISTR:
                setFragment(new RegistrFragment(), false, "Зарегистрироваться");
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginSeccsessEvent event){
        sPref = getPreferences(MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(HAS_LOG, "true");
        ed.putString(LOGIN_DATA, event.getLogin());
        ed.putString(PASS_DATA, event.getPassword());
        ed.commit();
        setFragment(MainFragment.getInstance("Привет, " +event.getLogin()+ "!"), false, "Вы вошли как "+event.getLogin());
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
