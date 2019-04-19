package com.superdjm.potocol;

import lombok.Data;

/**
 * 单聊
 *
 * @author jamiedeng
 * @since 2019/4/16
 */
@Data
public class SingleMessageProtocol extends AbstractProtocol {

    private Object msg;

    private Class<?> msgClazzType;

    private Integer toUserId;

    private Integer fromUserId;

    @Override public Byte getOp() {
        return Op.SINGLE_MESSAGE.getCode();
    }

}
