package com.example.filehub.commons.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yinfelix
 * @date 2020/4/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResult {
    private int statusCode;
    private String message;
    private Object data;
}
