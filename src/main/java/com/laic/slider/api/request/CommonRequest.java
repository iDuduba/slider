package com.laic.slider.api.request;


import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * Created by duduba on 16/5/5.
 */
@Data
@ToString
public class CommonRequest extends BaseRequest {
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmm");

    @NotBlank
    @Pattern(regexp = "^([0-9]{4})-?(1[0-2]|0[1-9])-?(3[01]|0[1-9]|[12][0-9])(2[0-3]|[01][0-9]):?([0-5][0-9])$", message = "无效的日期")
    private String from;

    @NotBlank
    @Pattern(regexp = "^([0-9]{4})-?(1[0-2]|0[1-9])-?(3[01]|0[1-9]|[12][0-9])(2[0-3]|[01][0-9]):?([0-5][0-9])$", message = "无效的日期")
    private String to;

    @Min(1)
    private Integer page;

    @Min(1)
    @Max(500)
    private Integer rows;

    public DateTime getBeginTime() {
        return DateTime.parse(from, formatter);
    }
    public DateTime getEndTime() {
        return DateTime.parse(to, formatter);
    }
}
