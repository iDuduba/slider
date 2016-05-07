package com.laic.slider.api.response;

import com.github.pagehelper.PageInfo;
import com.laic.slider.api.model.Page;
import lombok.Data;

import java.util.List;

/**
 * Created by duduba on 16/5/5.
 */
@Data
public class DataResponse<T> extends BaseResponse {
    private Page page;
    private List<T> rows;
}
