package com.zlf.type;

import com.zlf.enums.DingRobotMsgTypeEnum;
import lombok.Data;

@Data
public class DingRobotMsgFeedCardLink extends DingRobotMsg {

    /**
     * 消息标题(必填)
     */
    private String title;


    /**
     * 点击消息跳转的URL(非必填)
     */
    private String messageURL;

    /**
     * 图片URL(非必填)
     */
    private String picURL;


    @Override
    public DingRobotMsgTypeEnum getMsgType() {
        return DingRobotMsgTypeEnum.FEED_CARD;
    }


}
