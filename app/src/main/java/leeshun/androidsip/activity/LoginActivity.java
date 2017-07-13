package leeshun.androidsip.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import leeshun.androidsip.R;
import leeshun.androidsip.domain.Interaction;
import leeshun.androidsip.handler.Listener;
import leeshun.androidsip.manager.ActionHolder;
import leeshun.androidsip.manager.SipProfile;
import leeshun.androidsip.services.SipService;
import leeshun.androidsip.state.Action;

public class LoginActivity extends FragmentActivity implements Listener.OnLoginListener,Listener.OnRegisterListener{

    private EditText username;
    private EditText password;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Listener.onLoginListener=this;
        Listener.onRegisterListener=this;
        handler = new Handler();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new SipService(LoginActivity.this,"aa","10.128.194.130",5060);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).start();
    }

    public void login(View view) {
        SipProfile.getInstance().setSipUserName(username.getText().toString());
        System.err.println("login");
        ActionHolder.getInstance().addAction(new Interaction(Action.LOGIN,"",password.getText().toString()));
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void register(View view) {
        SipProfile.getInstance().setSipUserName(username.getText().toString());
        System.err.println("register");
        ActionHolder.getInstance().addAction(new Interaction(Action.REGISTER,"",password.getText().toString()));
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void OnLogin(boolean result) {
        if(result) {
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            System.err.println("login err here");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void OnRegister(boolean result) {
        if(result) {
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
