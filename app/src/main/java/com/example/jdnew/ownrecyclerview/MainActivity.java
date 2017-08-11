package com.example.jdnew.ownrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshRecyclerView pullToRefreshRecyclerview;
    private MyAdapter myAdapter;
    private List<String> dataList;
    private Handler handler = new Handler(
            new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if(msg.what == 1){
                        if(dataList.size() > 0){
                            dataList.clear();
                        }else {
                            for (int i = 0; i < 10; i++) {
                                dataList.add(i + "");
                            }
                        }

                        myAdapter.updateData(dataList);
                        pullToRefreshRecyclerview.updateDataComplete();
                        return true;
                    }else if(msg.what == 2){
                        if(dataList.size() == 20){

                        }else {
                            for (int i = 10; i < 20; i++) {
                                dataList.add(i + "");
                            }

                        }
                        myAdapter.updateData(dataList);
                        pullToRefreshRecyclerview.updateDataComplete();

                    }
                    return false;
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pullToRefreshRecyclerview = (PullToRefreshRecyclerView) findViewById(R.id.pullToRefreshRecyclerview);

        initData();
        myAdapter = new MyAdapter(this, R.layout.item_string, dataList);
        pullToRefreshRecyclerview.setAdapter(myAdapter);
        pullToRefreshRecyclerview.setEmptyLayout(R.layout.custom_empty_view);


        pullToRefreshRecyclerview.setOnRefreshOrLoadListener(new PullToRefreshRecyclerView.OnRefreshOrLoadListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                          handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onLoadMore() {
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            handler.sendEmptyMessage(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}).start();
            }
        });

    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(i + "");
        }
    }
}
