package com.zlf.type;

import com.zlf.enums.DingRobotMsgTypeEnum;
import lombok.Data;

@Data
public class DingRobotMsgLink extends DingRobotMsg {

    /**
     * 消息标题(必填)
     */
    private String title;

    /**
     * 消息内容:如果太长只会部分展示
     */
    private String text;

    /**
     * 点击消息跳转的URL(非必填)
     */
    private String messageUrl;

    /**
     * 图片URL(非必填)
     */
    private String picUrl;


    @Override
    public DingRobotMsgTypeEnum getMsgType() {
        return DingRobotMsgTypeEnum.LINK;
    }

}
