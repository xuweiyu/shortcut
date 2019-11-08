package com.xwy.shortcut;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.xwy.shortcut.bean.ShortcutBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ShortcutManager shortcutManager;
    private static final String ID1 = "shortcutBean1";
    private static final String ID2 = "shortcutBean2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager = getSystemService(ShortcutManager.class);
        }
        findViewById(R.id.remove_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortcutManager != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                        removeShortcuts(Arrays.asList(ID1));//移除第一个shortcut
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
        findViewById(R.id.update_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    updateShortcut(ID2, "更新后的第二个");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void removeShortcuts(List<String> ids) {
        if (shortcutManager != null) {
            List<ShortcutInfo> pinningShortcut = shortcutManager.getDynamicShortcuts();
            List<String> pinningedShortcutIds = new ArrayList<>();
            for (ShortcutInfo shortcutInfo : pinningShortcut) {
                if (ids.contains(shortcutInfo.getId())) {
                    pinningedShortcutIds.add(shortcutInfo.getId());
                }
            }
            //让设置为桌面快捷方式的shortcut不起作用
            shortcutManager.disableShortcuts(pinningedShortcutIds);
            //从shortcut中移除
            shortcutManager.removeDynamicShortcuts(ids);
        }
    }

    private void initShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> shortcutInfoList = new ArrayList<>();
            ShortcutBean shortcutBean1 = new ShortcutBean();
            initShortcutBean1(shortcutBean1);
            shortcutInfoList.add(createShortcut(shortcutBean1));
            ShortcutBean shortcutBean2 = new ShortcutBean();
            initShortcutBean2(shortcutBean2);
            shortcutInfoList.add(createShortcut(shortcutBean2));

            shortcutManager.addDynamicShortcuts(shortcutInfoList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void updateShortcut(String id, String label) {
        if (shortcutManager != null) {
            List<ShortcutInfo> shortcuts = shortcutManager.getDynamicShortcuts();
            for (ShortcutInfo shortcutInfo : shortcuts) {
                if (TextUtils.equals(shortcutInfo.getId(), shortcutInfo.getId())) {
                    ShortcutInfo newShortcutInfo = new ShortcutInfo.
                            Builder(this, id).setIntent(shortcutInfo.getIntent()).
                            setShortLabel(label).setLongLabel(label).build();
                    shortcutManager.updateShortcuts(Arrays.asList(newShortcutInfo));
                }
            }
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
