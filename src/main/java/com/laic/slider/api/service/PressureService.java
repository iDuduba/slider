package com.laic.slider.api.service;

import com.github.pagehelper.PageHelper;
import com.laic.slider.api.mapper.PressureMapper;
import com.laic.slider.api.model.Pressure;
import com.laic.slider.api.model.PressureCriteria;
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
public class PressureService {

    @Autowired
    private PressureMapper pressureMapper;

    public List<Pressure> getAll(DateTime from, DateTime to, int page, int rows) {
        PageHelper.startPage(page, rows);

        PressureCriteria rc = new PressureCriteria();
        PressureCriteria.Criteria criteria = rc.createCriteria();
        criteria.andTimeBetween(from.toDate(), to.toDate());
        rc.setOrderByClause("time");

        return pressureMapper.selectByExample(rc);
    }

}
