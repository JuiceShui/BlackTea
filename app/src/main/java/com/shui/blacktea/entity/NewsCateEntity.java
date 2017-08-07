package com.shui.blacktea.entity;

/**
 * Description:
 * Created by Juice_ on 2017/8/7.
 */

public class NewsCateEntity {
    private int type;
    private int icon;
    private String name;
    private boolean isSelect;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
