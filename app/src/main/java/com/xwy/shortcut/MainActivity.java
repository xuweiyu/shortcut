package com.xwy.shortcut;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.xwy.shortcut.bean.ShortcutBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ShortcutManager shortcutManager;
    private static final String ID1 = "shortcutBean1";
    private static final String ID2 = "shortcutBean2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initShortcut();
        findViewById(R.id.remove_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortcutManager != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                        shortcutManager.removeAllDynamicShortcuts();
                    }
                }
            }
        });
        findViewById(R.id.add_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShortcut();
            }
        });
    }

    private void initShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> shortcutInfoList = new ArrayList<>();
            shortcutManager = getSystemService(ShortcutManager.class);
            ShortcutBean shortcutBean1 = new ShortcutBean();
            initShortcutBean1(shortcutBean1);
            shortcutInfoList.add(createShortcut(shortcutBean1));
            ShortcutBean shortcutBean2 = new ShortcutBean();
            initShortcutBean2(shortcutBean2);
            shortcutInfoList.add(createShortcut(shortcutBean2));

            shortcutManager.addDynamicShortcuts(shortcutInfoList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initShortcutBean2(ShortcutBean shortcutBean) {
        shortcutBean.id = ID2;
        shortcutBean.icon = Icon.createWithResource(this, R.drawable.icon_unselect_user);
        shortcutBean.longLabel = "长点击第二个";
        shortcutBean.shortLabel = "短点击第二个";
        Intent intent = new Intent(Intent.ACTION_VIEW);//必须设定action
        intent.setClass(this, ActivityB.class);
        shortcutBean.intent = intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initShortcutBean1(ShortcutBean shortcutBean) {
        shortcutBean.id = ID1;
        shortcutBean.icon = Icon.createWithResource(this, R.drawable.icon_select_user);
        shortcutBean.longLabel = "长点击第一个";
        shortcutBean.shortLabel = "短点击第一个";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, ActivityA.class);
        shortcutBean.intent = intent;
    }


    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private ShortcutInfo createShortcut(ShortcutBean shortcutBean) {
        ShortcutInfo shortcutInfo = null;
        shortcutInfo = new ShortcutInfo.Builder(this, shortcutBean.id).
                setShortLabel(shortcutBean.shortLabel).
                setIcon(shortcutBean.icon).
                setIntent(shortcutBean.intent).build();
        return shortcutInfo;
    }
}
