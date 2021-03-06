package com.laic.slider.api.mapper;

import com.laic.slider.api.model.Pressure;
import com.laic.slider.api.model.PressureCriteria;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PressureMapper extends BaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int countByExample(PressureCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int deleteByExample(PressureCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(@Param("time") Date time, @Param("code") String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int insert(Pressure record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int insertSelective(Pressure record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    List<Pressure> selectByExampleWithRowbounds(PressureCriteria example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    List<Pressure> selectByExample(PressureCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    Pressure selectByPrimaryKey(@Param("time") Date time, @Param("code") String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Pressure record, @Param("example") PressureCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Pressure record, @Param("example") PressureCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Pressure record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_SD_BGK4500
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Pressure record);
}