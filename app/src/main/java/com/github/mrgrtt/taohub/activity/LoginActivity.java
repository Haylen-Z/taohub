package com.github.mrgrtt.taohub.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.TaoApplication;
import com.github.mrgrtt.taohub.dao.UserDao;
import com.github.mrgrtt.taohub.model.User;
import com.github.mrgrtt.taohub.util.AuthUtil;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onRegisterButtonClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClick(View view) {
        TextInputLayout usernameLayout = findViewById(R.id.username);
        TextInputLayout passwordLayout = findViewById(R.id.password);
        usernameLayout.setError("");
        passwordLayout.setError("");
        EditText usernameEdit = usernameLayout.getEditText();
        EditText passwordEdit = passwordLayout.getEditText();
        String username = (usernameEdit == null) ? "" : usernameEdit.getText().toString();
        String password = (passwordEdit == null) ? "" : passwordEdit.getText().toString();
        if (username.trim().isEmpty()) {
            usernameLayout.setError("用户名不能为空");
            return;
        }
        if (password.trim().isEmpty()) {
            passwordLayout.setError("密码不能为空");
            return;
        }
        login(username, password);
    }

    private void login(final String name, final String password) {
        Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> emitter) throws Exception {
                UserDao userDao = ((TaoApplication) getApplication()).getDatabase().userDao();
                emitter.onNext(userDao.listByUsername(name));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        if(users.isEmpty() || !Objects.equals(users.get(0).getPassword(), password)) {
                            loginFail();
                            return;
                        }
                        loginSuccess(users.get(0));
                    }
                });
    }

    private void loginSuccess(User user) {
        AuthUtil.saveLoginState(user, this);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginFail() {
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }
}
