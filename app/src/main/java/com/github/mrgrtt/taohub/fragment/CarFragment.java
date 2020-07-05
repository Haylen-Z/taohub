package com.github.mrgrtt.taohub.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.TaoApplication;
import com.github.mrgrtt.taohub.TaoDatabase;
import com.github.mrgrtt.taohub.adapter.CartItemAdapter;
import com.github.mrgrtt.taohub.dao.CartItemDao;
import com.github.mrgrtt.taohub.dao.ProductDao;
import com.github.mrgrtt.taohub.model.CartItem;
import com.github.mrgrtt.taohub.model.CartListItem;
import com.github.mrgrtt.taohub.util.AuthUtil;
import com.github.mrgrtt.taohub.util.PriceUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CarFragment extends Fragment implements CartItemAdapter.OnDelete {
    private CartItemDao cartItemDao;
    private ProductDao productDao;
    private CartItemAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        TaoDatabase db = ((TaoApplication) getActivity().getApplication()).getDatabase();
        cartItemDao = db.cartItemDao();
        productDao = db.productDao();

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartItemAdapter(new ArrayList<CartListItem>(), getContext(), this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        loadCartItem();
        super.onStart();
    }

    private void loadCartItem() {
        Observable.create(new ObservableOnSubscribe<List<CartListItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CartListItem>> emitter) throws Exception {
                List<CartItem> cs = cartItemDao.listByUserId(AuthUtil.getCurrentUserId(getContext()));
                List<CartListItem> data = new ArrayList<>();
                for (CartItem item: cs) {
                    CartListItem c = new CartListItem();
                    c.setCount(item.getCount());
                    c.setPrice(item.getSumPrice());
                    c.setProduct(productDao.getById(item.getProductId()));
                    c.setId(item.getId());
                    data.add(c);
                }
                emitter.onNext(data);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CartListItem>>() {
                    @Override
                    public void accept(List<CartListItem> cartListItems) throws Exception {
                        showCartItem(cartListItems);
                    }
                });
    }

    private void showCartItem(List<CartListItem> cs) {
        adapter.setData(cs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void delete(final CartListItem item) {
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                cartItemDao.delete(item.getId());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
