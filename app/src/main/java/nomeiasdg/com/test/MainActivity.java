package nomeiasdg.com.test;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton mFloationActionButton;

    private TestAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activiity_test1);
        ButterKnife.bind(this);

        initRecyclerView();
        initListener();
        reflushData();
    }
    private void initListener() {
        mFloationActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();
            }
        });
    }

    private void initRecyclerView() {

        mAdapter = new TestAdapter(MainActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void reflushData() {
        mDatas = Arrays.asList(Images.imageUrls);
        mAdapter.addAll(mDatas);
    }
}
