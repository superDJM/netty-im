package com.superdjm.potocol;

import lombok.Data;

/**
 * 登录结果
 *
 * @author jamiedeng
 * @since 2019/4/16
 */
@Data
public class LoginResultProtocol extends CommonResponseProtocol {

    private Integer userId;

    private String userName;

    @Override public Byte getOp() {
        return Op.LOGIN_RESULT.getCode();
    }

}
