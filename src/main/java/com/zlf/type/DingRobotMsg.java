package com.zlf.type;


import com.zlf.enums.DingRobotMsgTypeEnum;

/**
 * 钉钉推送消息基类
 *
 * @author zlf
 * @date 2014/03/14
 */
public abstract class DingRobotMsg {

    /**
     * 获取消息类型
     *
     * @return
     */
    public abstract DingRobotMsgTypeEnum getMsgType();


}
