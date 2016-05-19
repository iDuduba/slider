/**
 * duduba
 * Created at 2016年4月30日 下午11:10:02
 */
package com.laic.slider.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laic.slider.config.I18n;
import lombok.Data;

import java.util.Date;
import java.util.Properties;

/**
 * @author duduba
 * 
 */
@Data
public class HelloResponse extends BaseResponse {

    @I18n
    private String name;

    @JsonIgnore
    private String jpName;

    @JsonIgnore
    private String cnName;

    @I18n
    private Foo foo;

    private Date go;

    private Properties properties = new Properties();

    public void add(String name, String value) {
        properties.put(name, value);
    }
}
