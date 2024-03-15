package com.zlf.type;

import com.zlf.enums.DingRobotMsgTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class DingRobotMsgMoreActionCard extends DingRobotMsg {

    /**
     * 首屏会话透出的展示内容(必填)
     */
    private String title;

    /**
     * markdown格式的消息内容(必填)
     */
    private String text;

    /**
     * 按钮排列顺序。(非必填)
     * 0：按钮竖直排列
     * 1：按钮横向排列
     */
    private String btnOrientation;

    /**
     * 按钮列表(必填)
     */
    private List<DingRobotMsgActionCardButton> btns;

    @Override
    public DingRobotMsgTypeEnum getMsgType() {
        return DingRobotMsgTypeEnum.ACTION_CARD;
    }


}
