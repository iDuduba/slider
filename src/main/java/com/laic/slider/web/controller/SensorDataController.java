package com.laic.slider.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.laic.slider.api.enums.CodeEnum;
import com.laic.slider.api.model.Raingauge;
import com.laic.slider.api.request.CommonRequest;
import com.laic.slider.api.response.CommonResponse;
import com.laic.slider.api.response.DataResponse;
import com.laic.slider.api.service.RaingaugeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by duduba on 16/5/5.
 */

@Slf4j
@RestController
@CacheConfig(cacheNames = "foo")
public class SensorDataController {

    @Autowired
    private RaingaugeService raingaugeService;

    @Cacheable
    @RequestMapping(value = "/rains")
    public DataResponse<Raingauge> rain(@Value("#{request.getAttribute('data')}") String data) {

        CommonRequest request = JSONObject.parseObject(data, CommonRequest.class);

        List<Raingauge> rs = raingaugeService.getAll(
                request.getBeginTime(),
                request.getEndTime(),
                request.getPage(),
                request.getRows());

        DataResponse<Raingauge> response = new DataResponse<>();
        response.setPageInfo(new PageInfo<Raingauge>(rs));
        response.setResponse(CodeEnum.SUCCESS);
        return response;
    }

    @RequestMapping(value = "/hello")
    public CommonResponse hello(@Value("#{request.getAttribute('data')}") String data) {

        CommonResponse response = new CommonResponse(CodeEnum.SUCCESS);
        return response;
    }


    @CacheEvict(value = {"foo","bar"}, allEntries = true)
    @RequestMapping(value = "/cleanCache")
    public CommonResponse helcleanCachelo(@Value("#{request.getAttribute('data')}") String data) {
        return new CommonResponse(CodeEnum.SUCCESS);
    }

}
