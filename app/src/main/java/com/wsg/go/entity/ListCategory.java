package com.wsg.go.entity;

import java.util.List;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-05-06 10:20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ListCategory extends BaseData {

    private List<CategoryData> list;

    public List<CategoryData> getList() {
        return list;
    }

    public void setList(List<CategoryData> list) {
        this.list = list;
    }
}
