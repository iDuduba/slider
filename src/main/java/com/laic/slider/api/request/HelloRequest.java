package com.laic.slider.api.request;


import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * Created by duduba on 16/5/5.
 */
@Data
@ToString
public class HelloRequest extends BaseRequest {

    private String from;

    @NotBlank(groups = CreateChecks.class)
    @Pattern(regexp = "^(00)|(01)$")
    private String to;

    @Min(1)
    private Integer page;

    @Min(1)
    @Max(500)
    private Integer rows;

}
