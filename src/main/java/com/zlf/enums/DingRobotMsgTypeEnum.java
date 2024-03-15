package com.zlf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 钉钉消息推送类型枚举类
 *
 * @author zlf
 * @date 2014/03/14
 */
@Getter
@AllArgsConstructor
public enum DingRobotMsgTypeEnum {

    /**
     * 文本类型
     */
    TEXT("text"),

    /**
     * MARKDOWN 类型
     */
    MARKDOWN("markdown"),

    /**
     * LINK 链接类型
     */
    LINK("link"),

    /**
     * actionCard 类型
     */
    ACTION_CARD("actionCard"),
    /**
     * feedCard
     */
    FEED_CARD("feedCard");

    private String msgType;

}
