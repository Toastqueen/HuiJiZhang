package com.toast.work.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String tempKey;
}
