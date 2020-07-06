package com.github.mrgrtt.taohub.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.TaoApplication;
import com.github.mrgrtt.taohub.TaoDatabase;
import com.github.mrgrtt.taohub.dao.CartItemDao;
import com.github.mrgrtt.taohub.dao.HistoryDao;
import com.github.mrgrtt.taohub.model.CartItem;
import com.github.mrgrtt.taohub.model.History;
import com.github.mrgrtt.taohub.model.Product;
import com.github.mrgrtt.taohub.util.AuthUtil;
import com.github.mrgrtt.taohub.util.PriceUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {
    public static final String DATA_KEY = "com.github.mrgrtt.taohub.detail.data.key";
    private Product p;
    private CartItemDao cartItemDao;
    private HistoryDao historyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        TaoDatabase db = ((TaoApplication) getApplication()).getDatabase();
        cartItemDao = db.cartItemDao();
        historyDao = db.historyDao();

        p = (Product) getIntent().getSerializableExtra(DATA_KEY);
        if (p == null) {
            finish();
        }
        showProduct(p);
        addHistory();
    }

    private void showProduct(Product product) {
        ImageView image = findViewById(R.id.image);
        TextView name = findViewById(R.id.name);
        TextView price = findViewById(R.id.price);
        TextView detail = findViewById(R.id.detail);

        Glide.with(this).load(product.getPicture()).into(image);
        name.setText(product.getName());
        price.setText("￥" + PriceUtil.convert(product.getPrice()));
        detail.setText(product.getDetail());
    }

    public void buttonClick(View view) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_count, null);
        final TextInputEditText editText = dialogView.findViewById(R.id.count);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("请输入购买数量");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (editText.getText().toString().isEmpty()) {
                   Toast.makeText(DetailActivity.this, "请输入购买数量", Toast.LENGTH_SHORT).show();
                   return;
                }
                int num = Integer.valueOf(editText.getText().toString());
                if (num < 1) {
                    Toast.makeText(DetailActivity.this, "最少购买一个", Toast.LENGTH_SHORT).show();
                    return;
                }
                addCartItem(num);
                dialogInterface.dismiss();
            }
        });

        builder.setView(dialogView);
        builder.create().show();
    }

    private void addCartItem(int count) {
        final CartItem carItem = new CartItem();
        carItem.setCount(count);
        carItem.setUserId(AuthUtil.getCurrentUserId(this));
        carItem.setProductId(p.getId());
        carItem.setSumPrice(count * p.getPrice());
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                cartItemDao.insert(carItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(DetailActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addHistory() {
        final History history = new History();
        history.setProductId(p.getId());
        history.setTime(System.currentTimeMillis());
        history.setUserId(AuthUtil.getCurrentUserId(this));

        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                historyDao.addHistory(history);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
