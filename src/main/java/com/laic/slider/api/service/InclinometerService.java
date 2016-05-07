package com.laic.slider.api.service;

import com.github.pagehelper.PageHelper;
import com.laic.slider.api.mapper.InclinometerMapper;
import com.laic.slider.api.model.Inclinometer;
import com.laic.slider.api.model.InclinometerCriteria;
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
public class InclinometerService {

    @Autowired
    private InclinometerMapper inclinometerMapper;

    public List<Inclinometer> getAll(DateTime from, DateTime to, int page, int rows) {
        PageHelper.startPage(page, rows);

        InclinometerCriteria rc = new InclinometerCriteria();
        InclinometerCriteria.Criteria criteria = rc.createCriteria();
        criteria.andTimeBetween(from.toDate(), to.toDate());
        rc.setOrderByClause("time");

        return inclinometerMapper.selectByExample(rc);
    }

}
