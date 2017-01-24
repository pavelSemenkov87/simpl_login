package ru.techmas.simple_login.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.techmas.simple_login.Events.SetFragmentEvent;
import ru.techmas.simple_login.MainActivity;
import ru.techmas.simple_login.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private String mess;


    public MainFragment() {
        // Required empty public constructor
    }

    private void setParametrs(String mess) {
        this.mess = mess;
    }

    public static MainFragment getInstance(String items) {
        MainFragment fragment = new MainFragment();
        fragment.setParametrs(items);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView) view.findViewById(R.id.HelloText);
        textView.setText(mess);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.insideBut:
                EventBus.getDefault().post(new SetFragmentEvent(MainActivity.LOGIN));
                break;
            case R.id.registrBut:
                EventBus.getDefault().post(new SetFragmentEvent(MainActivity.REGISTR));
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetFragmentEvent event){
    }
}
