package com.superdjm.potocol;

import lombok.Data;

/**
 * 协议抽象
 *
 * @author jamiedeng
 * @since 2019/4/16
 */
@Data
public abstract class AbstractProtocol implements Protocol {
    @Override public int getVersion() {
        return DEFAULT_VERSION;
    }
}
