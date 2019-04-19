package com.superdjm;

import lombok.Data;

/**
 * session
 *
 * @author jamiedeng
 * @since 2019/4/18
 */
@Data
public class Session {
    // 用户唯一性标识
    private Integer userId;
    private String  userName;
}
