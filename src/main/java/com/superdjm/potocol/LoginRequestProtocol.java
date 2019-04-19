package com.superdjm.potocol;

import lombok.Data;

/**
 * 登录
 *
 * @author jamiedeng
 * @since 2019/4/16
 */
@Data
public class LoginRequestProtocol extends AbstractProtocol {

    private String userName;

    private String password;

    @Override public Byte getOp() {
        return Op.LOGIN_REQUEST.getCode();
    }

}
