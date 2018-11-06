package com.lee.fiftytone;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lee.fiftytone.activity.FiftyToneActivity;
import com.lee.fiftytone.util.PreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.btn_fifty_tone)
    public Button btn_fifty_tone;

    Resources mResources;
    TypedArray icons_fifty_tone_imgs_1, icons_fifty_tone_imgs_2, icons_fifty_tone_imgs_3;
    List<int[]> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        taskList = PreferencesUtil.getList(this, PreferencesUtil.TASK_LIST);
        if (null != taskList && taskList.size() > 0) return;

        taskList = new ArrayList();
        mResources = this.getResources();
        icons_fifty_tone_imgs_1 = mResources.obtainTypedArray(R.array.fifty_tone_imgs_1);
        icons_fifty_tone_imgs_2 = mResources.obtainTypedArray(R.array.fifty_tone_imgs_2);
        icons_fifty_tone_imgs_3 = mResources.obtainTypedArray(R.array.fifty_tone_imgs_3);
        for (int i = 0; i < icons_fifty_tone_imgs_1.length(); i++) {
            taskList.add(new int[]{icons_fifty_tone_imgs_1.getResourceId(i, -1), icons_fifty_tone_imgs_2.getResourceId(i, -1), icons_fifty_tone_imgs_3.getResourceId(i, -1)});
        }
        for (int i = 0; i < icons_fifty_tone_imgs_2.length(); i++) {
            taskList.add(new int[]{icons_fifty_tone_imgs_2.getResourceId(i, -1), icons_fifty_tone_imgs_1.getResourceId(i, -1), icons_fifty_tone_imgs_3.getResourceId(i, -1)});
        }
        for (int i = 0; i < icons_fifty_tone_imgs_3.length(); i++) {
            taskList.add(new int[]{icons_fifty_tone_imgs_3.getResourceId(i, -1), icons_fifty_tone_imgs_1.getResourceId(i, -1), icons_fifty_tone_imgs_2.getResourceId(i, -1)});
        }

        PreferencesUtil.saveListOrMap(this, PreferencesUtil.TASK_LIST, taskList);
        PreferencesUtil.saveString(this, PreferencesUtil.TASK_TOTAL, taskList.size() + "");
    }

    @OnClick({R.id.btn_fifty_tone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fifty_tone:
                startActivity(new Intent(this, FiftyToneActivity.class));
                break;
            default:
                break;
        }
    }
}
