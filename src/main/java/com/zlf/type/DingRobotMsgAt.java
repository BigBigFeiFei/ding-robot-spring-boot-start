package com.zlf.type;

import lombok.Data;

import java.util.List;

@Data
public class DingRobotMsgAt {

    private Boolean isAtAll = Boolean.TRUE;
    /**
     * @用户手机号 说明 (非必填)
     * 消息内容content中要带上"@手机号"，跟atMobiles参数结合使用，才有@效果，如上示例。
     */
    private List<String> atMobiles;

    /**
     * 被@人的用户userid (非必填)
     */
    private List<String> atUserIds;

}
