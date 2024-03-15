package com.zlf.type;

import com.zlf.enums.DingRobotMsgTypeEnum;
import lombok.Data;

/**
 * 钉钉推送消息 text类型
 *
 * @author zlf
 * @date 2024/03/14
 */
@Data
public class DingRobotMsgText extends DingRobotMsg {

    /**
     * 标题(必填)
     */
    private String content;

    /**
     * 获取消息类型
     *
     * @return
     */
    @Override
    public DingRobotMsgTypeEnum getMsgType() {
        return DingRobotMsgTypeEnum.TEXT;
    }


}
