package com.lee.fiftytone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Toast;

import com.lee.fiftytone.db.TradeDataDao;
import com.lee.fiftytone.util.StatusBarUtils;

/**
 * 自定义Activity 用于集中处理一些事件：比如页面刷新等
 */

@SuppressLint("HandlerLeak")
public class BaseActivity extends Activity {
    protected final String TAG = this.getClass().getName();
    public MyApplication app;
    public TradeDataDao tradeDataDao;//本地数据库
    /**
     * 检测新版本服务器返回标识
     */
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtils.setTransparent(this);
        super.onCreate(savedInstanceState);
        initScreen();
        app = MyApplication.getInstance();
        app.addActivity(BaseActivity.this);
        tradeDataDao = new TradeDataDao(this);// 本地数据库
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setBackgroundDrawable(null);//减少gpu渲染次数，优化内存
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initScreen() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtils.setStatusBar(this, ContextCompat.getColor(this, R.color.black));
    }

    @Override
    protected void onPause() {
        if (toast != null) toast.cancel();
        super.onPause();
    }

    /**
     * toast的longshow
     *
     * @param content 显示的文案
     */
    public void longToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(BaseActivity.this, content, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * toast的shortshow
     *
     * @param content 显示的文案
     */
    public void shortToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(BaseActivity.this, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shortToast("permission granted");
                } else {
                    shortToast("permission denied");
                }
                break;
        }
    }
}
