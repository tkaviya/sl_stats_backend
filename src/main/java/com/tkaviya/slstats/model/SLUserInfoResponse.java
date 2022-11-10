package com.tkaviya.slstats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/***************************************************************************
 * Created:     10 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SLUserInfoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 5926424569005157907L;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
