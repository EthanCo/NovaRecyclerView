package com.ethanco.sample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.ethanco.nova.AdapterWrap;
import com.ethanco.nova.NovaSupervisor;
import com.ethanco.sample.R;
import com.ethanco.sample.adapter.DataAdapter;
import com.ethanco.sample.bean.ItemModel;
import com.ethanco.sample.databinding.ActivityCommonBinding;
import com.ethanco.sample.widget.SampleHeader;

import java.util.ArrayList;
import java.util.List;

public class CommonActivity extends AppCompatActivity {
    private ActivityCommonBinding binding;

    private AdapterWrap<ItemModel> adapterWrap;
    private NovaSupervisor supervisor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_common);

        adapterWrap = new AdapterWrap(new DataAdapter(this));
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(adapterWrap);

        supervisor = new NovaSupervisor(binding.list);
        supervisor.setHeaderView(new SampleHeader(this));

        List<ItemModel> data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add(generationItemModel(i));
        }
        adapterWrap.getAdapter().setNewData(data);
        //supervisor.openLoadMore();
        //supervisor.setFooterViewState(LoadingFooter.State.Loading);
    }

    @NonNull
    private ItemModel generationItemModel(Integer i) {
        ItemModel itemModel = new ItemModel();
        itemModel.id = i;
        itemModel.title = "title" + i;
        itemModel.imgRes = R.mipmap.ic_launcher;
        return itemModel;
    }
}
