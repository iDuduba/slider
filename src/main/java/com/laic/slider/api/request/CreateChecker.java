package com.laic.slider.api.request;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Created by duduba on 16/5/11.
 */
@GroupSequence({Default.class, CreateChecks.class})
public interface CreateChecker {
}
