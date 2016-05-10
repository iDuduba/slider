/**
 * duduba
 * Created at 2016年4月30日 下午11:10:02
 */
package com.laic.slider.api.response;

import java.util.Date;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laic.slider.config.I18n;
import lombok.Data;

/**
 * @author duduba
 * 
 */
@I18n
@Data
public class HelloResponse extends BaseResponse {

    @I18n(codeType = "China", code = "jpName")
    private String name;

    @JsonIgnore
    private String jpName;

    private Date go;

    private Properties properties = new Properties();

    public void add(String name, String value) {
        properties.put(name, value);
    }
}
