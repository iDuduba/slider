/**
 * duduba
 * Created at 2016年4月30日 下午11:10:02
 */
package com.laic.slider.api.response;

import lombok.Data;

import java.util.Properties;

/**
 * @author duduba
 * 
 */
@Data
public class InvalidParameterResponse extends BaseResponse {

    private Properties errors = new Properties();

    public void add(String name, String value) {
        errors.put(name, value);
    }
}
