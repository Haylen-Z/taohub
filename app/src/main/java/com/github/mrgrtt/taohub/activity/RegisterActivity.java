package com.github.mrgrtt.taohub.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.TaoApplication;
import com.github.mrgrtt.taohub.dao.UserDao;
import com.github.mrgrtt.taohub.model.User;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDao = ((TaoApplication) getApplication()).getDatabase().userDao();
    }

    public void onRegisterButtonClick(View view) {
        TextInputLayout usernameLayout = findViewById(R.id.username);
        TextInputLayout passwordLayout = findViewById(R.id.password);
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
        register(username, password);
    }

    private void register(final String name, final String password) {
        final User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                List<User> users = userDao.listByUsername(name);
                if (users.isEmpty()) {
                    userDao.addUser(user);
                    emitter.onComplete();
                    return;
                }
                emitter.onError(new RuntimeException("该用户名已被注册"));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object o) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        registerSuccess();
                    }
                });
    }


    private void registerSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
