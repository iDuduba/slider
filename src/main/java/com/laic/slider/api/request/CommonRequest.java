package com.laic.slider.api.request;


import lombok.Data;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by duduba on 16/5/5.
 */
@Data
@ToString
public class CommonRequest extends BaseRequest {
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmm");

    private String from;
    private String to;
    private Integer page;
    private Integer rows;

    public DateTime getBeginTime() {
        return DateTime.parse(from, formatter);
    }
    public DateTime getEndTime() {
        return DateTime.parse(to, formatter);
    }
}
