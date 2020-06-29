package com.github.mrgrtt.taohub.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.TaoApplication;
import com.github.mrgrtt.taohub.TaoDatabase;
import com.github.mrgrtt.taohub.adapter.ProductAdapter;
import com.github.mrgrtt.taohub.dao.CategoryDao;
import com.github.mrgrtt.taohub.dao.ProductDao;
import com.github.mrgrtt.taohub.model.Category;
import com.github.mrgrtt.taohub.model.Product;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private List<Category> categories;
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private ProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TaoDatabase db = ((TaoApplication) getActivity().getApplication()).getDatabase();
        categoryDao = db.categoryDao();
        productDao = db.productDao();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadProductByCategoryId(categories.get(tab.getPosition()).getId());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        loadCategory();
        if (tabLayout.getTabCount() > 0) {
            tabLayout.getTabAt(0).select();
        }

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter(new ArrayList<Product>(), getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        return view;
    }

    private void loadCategory() {
        Observable.create(new ObservableOnSubscribe<List<Category>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Category>> emitter) throws Exception {
                List<Category> cs = categoryDao.getAll();
                emitter.onNext(cs);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> o) throws Exception {
                        showTabs(o);
                    }
                });
    }

    private void showTabs(List<Category> cs) {
        this.categories = cs;
        for (Category c: cs) {
            tabLayout.addTab(tabLayout.newTab().setText(c.getName()));
        }
    }

    private void loadProductByCategoryId(final Long cId) {
        Observable.create(new ObservableOnSubscribe<List<Product>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Product>> emitter) throws Exception {
                List<Product> products = productDao.getByCategoryId(cId);
                emitter.onNext(products);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) throws Exception {
                        showProductList(products);
                    }
                });
    }

    private void showProductList(List<Product> ps) {
        adapter.setData(ps);
        adapter.notifyDataSetChanged();
    }
}
