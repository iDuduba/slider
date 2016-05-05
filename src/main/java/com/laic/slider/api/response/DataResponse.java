package com.laic.slider.api.response;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * Created by duduba on 16/5/5.
 */
@Data
public class DataResponse<T> extends BaseResponse {
    private PageInfo<T> pageInfo;
}
