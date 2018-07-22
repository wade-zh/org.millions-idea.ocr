/***
 * @pName security
 * @name ValidateCodeException
 * @user HongWei
 * @date 2018/7/20
 * @desc
 */
package com.example.security.validate;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
