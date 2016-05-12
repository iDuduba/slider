package com.laic.slider.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.laic.slider.api.enums.CodeEnum;
import com.laic.slider.api.model.*;
import com.laic.slider.api.request.CommonRequest;
import com.laic.slider.api.request.CreateChecker;
import com.laic.slider.api.request.CreateChecks;
import com.laic.slider.api.request.HelloRequest;
import com.laic.slider.api.response.CommonResponse;
import com.laic.slider.api.response.DataResponse;
import com.laic.slider.api.response.HelloResponse;
import com.laic.slider.api.service.HumidistatService;
import com.laic.slider.api.service.InclinometerService;
import com.laic.slider.api.service.PressureService;
import com.laic.slider.api.service.RaingaugeService;
import com.laic.slider.config.VersionConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;
import java.util.Date;
import java.util.List;

/**
 * Created by duduba on 16/5/5.
 */

@Slf4j
@RestController
@CacheConfig(cacheNames = "foo")
public class SensorDataController extends BaseController{

    @Autowired
    private VersionConfig versionConfig;

    @Autowired
    private RaingaugeService raingaugeService;

    @Autowired
    private PressureService pressureService;

    @Autowired
    private HumidistatService humidistatService;

    @Autowired
    private InclinometerService inclinometerService;

//    @Cacheable
    @RequestMapping(value = "/rains")
    public DataResponse<Raingauge> rain(@Value("#{request.getAttribute('data')}") String data) {

        CommonRequest request = JSONObject.parseObject(data, CommonRequest.class);
        validate(request);

        List<Raingauge> rs = raingaugeService.getAll(
                request.getBeginTime(),
                request.getEndTime(),
                request.getPage(),
                request.getPageSize());

        DataResponse<Raingauge> response = new DataResponse<>();
        PageInfo pageInfo = new PageInfo<Raingauge>(rs);
        Page page = new Page();
        BeanUtils.copyProperties(pageInfo, page);
        response.setPage(page);
        response.setRows(rs);
        //response.setPageInfo(new PageInfo<Raingauge>(rs));
        response.setResponse(CodeEnum.SUCCESS);
        return response;
    }

    @RequestMapping(value = "/pressures")
    public DataResponse<Pressure> pressure(@Value("#{request.getAttribute('data')}") String data) {

        CommonRequest request = JSONObject.parseObject(data, CommonRequest.class);
        validate(request);

        List<Pressure> rs = pressureService.getAll(
                request.getBeginTime(),
                request.getEndTime(),
                request.getPage(),
                request.getPageSize());

        DataResponse<Pressure> response = new DataResponse<>();
        PageInfo pageInfo = new PageInfo<Pressure>(rs);
        Page page = new Page();
        BeanUtils.copyProperties(pageInfo, page);
        response.setPage(page);
        response.setRows(rs);

        response.setResponse(CodeEnum.SUCCESS);
        return response;
    }

    @RequestMapping(value = "/humidistats")
    public DataResponse<Humidistat> humidistat(@Value("#{request.getAttribute('data')}") String data) {

        CommonRequest request = JSONObject.parseObject(data, CommonRequest.class);
        validate(request);

        List<Humidistat> rs = humidistatService.getAll(
                request.getBeginTime(),
                request.getEndTime(),
                request.getPage(),
                request.getPageSize());

        DataResponse<Humidistat> response = new DataResponse<>();
//        response.setPageInfo(new PageInfo<Humidistat>(rs));
        PageInfo pageInfo = new PageInfo<Humidistat>(rs);
        Page page = new Page();
        BeanUtils.copyProperties(pageInfo, page);
        response.setPage(page);
        response.setRows(rs);

        response.setResponse(CodeEnum.SUCCESS);
        return response;
    }

    @RequestMapping(value = "/inclinometers")
    public DataResponse<Inclinometer> inclinometer(@Value("#{request.getAttribute('data')}") String data) {

        CommonRequest request = JSONObject.parseObject(data, CommonRequest.class);
        validate(request);

        List<Inclinometer> rs = inclinometerService.getAll(
                request.getBeginTime(),
                request.getEndTime(),
                request.getPage(),
                request.getPageSize());

        DataResponse<Inclinometer> response = new DataResponse<>();
//        response.setPageInfo(new PageInfo<Inclinometer>(rs));
        PageInfo pageInfo = new PageInfo<Inclinometer>(rs);
        Page page = new Page();
        BeanUtils.copyProperties(pageInfo, page);
        response.setPage(page);
        response.setRows(rs);

        response.setResponse(CodeEnum.SUCCESS);
        return response;
    }

    @RequestMapping(value = "/hello")
    public HelloResponse hello(@Value("#{request.getAttribute('data')}") String data) {

        HelloRequest request = JSONObject.parseObject(data, HelloRequest.class);
        if(request.getFrom() == null) {
            validate(request, CreateChecker.class);
        } else {
            validate(request);
        }

        HelloResponse response = new HelloResponse();
        response.setResponse(CodeEnum.SUCCESS);

        response.setJpName("bageyalu");
        response.setGo(new Date());
        response.add("serverApiVersion", versionConfig.getVersion());
        response.add("scmRevision", versionConfig.getRevision());
        response.add("scmTime", versionConfig.getTimestamp());
        response.add("active", ""+versionConfig.getFoo().isActive());

        return response;
    }


    @CacheEvict(value = {"foo","bar"}, allEntries = true)
    @RequestMapping(value = "/cleanCache")
    public CommonResponse helcleanCachelo(@Value("#{request.getAttribute('data')}") String data) {
        return new CommonResponse(CodeEnum.SUCCESS);
    }

}
