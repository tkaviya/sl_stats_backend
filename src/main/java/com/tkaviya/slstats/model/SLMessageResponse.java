package com.tkaviya.slstats.model;

import java.io.Serial;
import java.io.Serializable;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public record SLMessageResponse(String message) implements Serializable {
    @Serial
    private static final long serialVersionUID = -8091879091924046844L;
}