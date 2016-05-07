/**
 * duduba
 * Created at 2016年4月30日 下午11:10:02
 */
package com.laic.slider.api.response;

import java.util.Properties;

import lombok.Data;

/**
 * @author duduba
 * 
 */
@Data
public class HelloResponse extends BaseResponse {

    private Properties properties = new Properties();

    public void add(String name, String value) {
        properties.put(name, value);
    }
}
