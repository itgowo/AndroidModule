package com.itgowo.module.chat;

import java.io.Serializable;

public class ItemData<ItemType> implements Serializable {
    private int type;
    private ItemType data;

    public int getType() {
        return type;
    }

    public ItemData setType(int type) {
        this.type = type;
        return this;
    }

    public ItemType getData() {
        return data;
    }

    public ItemData setData(ItemType data) {
        this.data = data;
        return this;
    }
}
