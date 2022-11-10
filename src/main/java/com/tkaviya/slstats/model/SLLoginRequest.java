package com.tkaviya.slstats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SLLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 5926468583005150707L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
