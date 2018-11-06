package com.lee.fiftytone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lee.fiftytone.BaseActivity;
import com.lee.fiftytone.R;
import com.lee.fiftytone.util.PreferencesUtil;
import com.lee.fiftytone.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lee.fiftytone.util.StringUtil.getRandom;

public class FiftyToneActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    public TextView tv_title;

    @BindView(R.id.pgb)
    public ProgressBar pgb;
    @BindView(R.id.im_question)
    public ImageView im_question;
    @BindView(R.id.im_answar1)
    public ImageView im_answar1;
    @BindView(R.id.im_answar2)
    public ImageView im_answar2;

    @BindView(R.id.btn_answar_wrong)
    public Button btn_answar_wrong;
    @BindView(R.id.btn_answar_right)
    public Button btn_answar_right;

    List<int[]> taskList = new ArrayList();
    int currentNum = 0;
    String taskTotal = "";//正在执行任务的总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifty_tone);
        ButterKnife.bind(this);

        taskList = PreferencesUtil.getList(this, PreferencesUtil.TASK_LIST);
        taskTotal = PreferencesUtil.getString(this, PreferencesUtil.TASK_TOTAL, "");
        pgb.setMax(Integer.parseInt(taskTotal));
        setQuestion();
    }

    @OnClick({R.id.btn_answar_right, R.id.btn_answar_wrong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_answar_wrong://查看答案，答案错误
                if ("查看答案".equals(btn_answar_wrong.getText().toString())) {
                    showAnswer();
                } else if ("答案错误".equals(btn_answar_wrong.getText().toString())) {
                    setQuestion();
                }
                break;
            case R.id.btn_answar_right://下一个
                if ("确认答案".equals(btn_answar_right.getText().toString())) {
                    showAnswer();
                } else {
                    taskList.remove(currentNum);
                    PreferencesUtil.saveListOrMap(this, PreferencesUtil.TASK_LIST, taskList);
                    setQuestion();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置问题
     */
    private void setQuestion() {
        if (0 == taskList.size()) {
            shortToast("任务完成！");
            finish();
            return;
        }
        int currentTotal = Integer.parseInt(taskTotal) - taskList.size();
        tv_title.setText("学习进行中 " + currentTotal + "/" + taskTotal);
        pgb.setProgress(currentTotal);

        im_answar1.setVisibility(View.GONE);
        im_answar2.setVisibility(View.GONE);
        currentNum = getRandom(0, taskList.size());
        GlideUtils.LoadImageWithLocation(this, taskList.get(currentNum)[0], im_question);

        btn_answar_wrong.setText("查看答案");
        btn_answar_right.setText("确认答案");
    }

    /**
     * 显示答案
     */
    private void showAnswer() {
        GlideUtils.LoadImageWithLocation(this, taskList.get(currentNum)[1], im_answar1);
        GlideUtils.LoadImageWithLocation(this, taskList.get(currentNum)[2], im_answar2);
        im_answar1.setVisibility(View.VISIBLE);
        im_answar2.setVisibility(View.VISIBLE);
        btn_answar_wrong.setText("答案错误");
        btn_answar_right.setText("答案正确");
    }
}
