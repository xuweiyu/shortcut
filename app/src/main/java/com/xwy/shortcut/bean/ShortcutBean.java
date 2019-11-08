package com.xwy.shortcut.bean;

import android.content.Intent;
import android.graphics.drawable.Icon;

public class ShortcutBean {
    public String id;
    public Intent intent;
    public String shortLabel;//短名称，如果长名称显示不下则显示短名称
    public String longLabel;//长名称，优先显示长名称
    public Icon icon;//
    public String disableMessage;//跳转目标不可达时给与提示
}
