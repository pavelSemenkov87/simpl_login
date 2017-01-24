package ru.techmas.simple_login.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.techmas.simple_login.Events.SetFragmentEvent;
import ru.techmas.simple_login.MainActivity;
import ru.techmas.simple_login.R;

public class InputFragment extends Fragment implements View.OnClickListener {

    private Button inButton, regButton;


    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        inButton = (Button) view.findViewById(R.id.insideBut);
        inButton.setOnClickListener(this);
        regButton = (Button) view.findViewById(R.id.registrBut);
        regButton.setOnClickListener(this);
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
