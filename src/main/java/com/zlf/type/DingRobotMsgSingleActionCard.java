package com.zlf.type;

import com.zlf.enums.DingRobotMsgTypeEnum;
import lombok.Data;

@Data
public class DingRobotMsgSingleActionCard extends DingRobotMsg {

    /**
     * 首屏会话透出的展示内容(必填)
     */
    private String title;

    /**
     * markdown格式的消息内容(必填)
     */
    private String text;

    /**
     * 单个按钮的标题(必填)
     */
    private String singleTitle;

    /**
     * 单个按钮的跳转链接(必填)
     */
    private String singleURL;


    @Override
    public DingRobotMsgTypeEnum getMsgType() {
        return DingRobotMsgTypeEnum.ACTION_CARD;
    }


}
