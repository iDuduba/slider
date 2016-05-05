package com.laic.slider.api.service;

import com.github.pagehelper.PageHelper;
import com.laic.slider.api.mapper.RaingaugeMapper;
import com.laic.slider.api.model.Raingauge;
import com.laic.slider.api.model.RaingaugeCriteria;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by duduba on 16/5/5.
 */

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RaingaugeService {

    @Autowired
    private RaingaugeMapper raingaugeMapper;

    public List<Raingauge> getAll(DateTime from, DateTime to, int page, int rows) {
        PageHelper.startPage(page, rows);

        RaingaugeCriteria rc = new  RaingaugeCriteria();
        RaingaugeCriteria.Criteria criteria = rc.createCriteria();
        criteria.andTimeBetween(from.toDate(), to.toDate());

        return raingaugeMapper.selectByExample(rc);
    }

}
