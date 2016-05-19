package com.laic.slider.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laic.slider.config.I18n;
import lombok.Data;

/**
 * Created by duduba on 16/5/20.
 */
@Data
@I18n
public class Foo {
    @I18n
    private String name;
    @JsonIgnore
    private String jpName;
    @JsonIgnore
    private String cnName;

    private Integer age;

}
