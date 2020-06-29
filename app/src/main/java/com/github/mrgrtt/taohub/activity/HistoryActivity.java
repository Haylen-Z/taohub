package com.github.mrgrtt.taohub.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.TaoApplication;
import com.github.mrgrtt.taohub.TaoDatabase;
import com.github.mrgrtt.taohub.adapter.HistoryAdapter;
import com.github.mrgrtt.taohub.dao.HistoryDao;
import com.github.mrgrtt.taohub.dao.ProductDao;
import com.github.mrgrtt.taohub.model.History;
import com.github.mrgrtt.taohub.model.HistoryListItem;
import com.github.mrgrtt.taohub.util.AuthUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HistoryActivity extends AppCompatActivity {
    private HistoryAdapter adapter;
    private HistoryDao historyDao;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TaoDatabase db = ((TaoApplication) getApplication()).getDatabase();
        historyDao = db.historyDao();
        productDao= db.productDao();

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new HistoryAdapter(new ArrayList<HistoryListItem>(), this);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        Observable.create(new ObservableOnSubscribe<List<HistoryListItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryListItem>> emitter) throws Exception {
                List<History> hs = historyDao.getAllByUserId(AuthUtil.getCurrentUserId(HistoryActivity.this));
                List<HistoryListItem> data = new ArrayList<>();
                for (History h: hs) {
                    HistoryListItem item = new HistoryListItem();
                    item.setProduct(productDao.getById(h.getProductId()));
                    item.setTime(h.getTime());
                    data.add(item);
                }
                emitter.onNext(data);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<HistoryListItem>>() {
                    @Override
                    public void accept(List<HistoryListItem> historyListItems) throws Exception {
                        showData(historyListItems);
                    }
                });
    }

    private void showData(List<HistoryListItem> hs) {
        adapter.setData(hs);
        adapter.notifyDataSetChanged();
    }
}
