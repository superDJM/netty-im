package com.superdjm.util;

import com.superdjm.Attributes;
import com.superdjm.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jamiedeng
 * @since 2019/4/18
 */

public class SessionUtil {

    // userId -> channel 的映射
    private static final Map<Integer, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(Integer userId) {

        return userIdChannelMap.get(userId);
    }
}
