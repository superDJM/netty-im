package com.superdjm.potocol;

import lombok.Data;

/**
 * 通用返回
 *
 * @author jamiedeng
 * @since 2019/4/16
 */
@Data
public class CommonResponseProtocol extends AbstractProtocol {

    private Boolean isSuccess;

    private String errMsg;

    public void fillSucc() {
        isSuccess = true;
        errMsg = "ok";
    }

    public void fillFailure(String errMsg) {
        isSuccess = false;
        this.errMsg = errMsg;
    }

    @Override public Byte getOp() {
        return Op.COMMON_RESPONSE.getCode();
    }

}
