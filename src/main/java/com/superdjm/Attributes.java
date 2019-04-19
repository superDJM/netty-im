package com.superdjm;

import io.netty.util.AttributeKey;

/**
 * @author jamiedeng
 * @since 2019/4/18
 */

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
