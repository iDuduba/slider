package com.laic.slider.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by duduba on 16/5/7.
 */

@Data
public class Page implements Serializable {
    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
}
