package ru.techmas.simple_login.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.techmas.simple_login.Events.LoginSeccsessEvent;
import ru.techmas.simple_login.Events.SetFragmentEvent;
import ru.techmas.simple_login.R;

public class RegistrFragment extends Fragment{

    private TextView logCheck, passCheck;
    private EditText inLog, inPass;
    private boolean logC = false, passC = false;
    private Button insideB;


    public RegistrFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registr, container, false);
        logCheck = (TextView) view.findViewById(R.id.infoLogRView);
        passCheck = (TextView) view.findViewById(R.id.infoPassRView);
        inLog = (EditText) view.findViewById(R.id.editLogRText);
        insideB = (Button) view.findViewById(R.id.inputRegBut);
        insideB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logC && passC){
                    EventBus.getDefault().post(new LoginSeccsessEvent(inLog.getText().toString(), inPass.getText().toString()));
                }
            }
        });
        inLog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<4){
                    logCheck.setText("Логин должен содержать больше 4-х симвалов");
                }else {
                    logCheck.setText("");
                    logC = true;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        inPass = (EditText) view.findViewById(R.id.editPassRText);
        inPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<4){
                    passCheck.setText("Пароль должен содержать больше 4-х симвалов");
                }else {
                    passCheck.setText("");
                    passC = true;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetFragmentEvent event){
    }
}
