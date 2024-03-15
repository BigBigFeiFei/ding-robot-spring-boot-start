package com.zlf.type;

import com.zlf.enums.DingRobotMsgTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class DingRobotMsgFeedCard extends DingRobotMsg {

    /**
     * 列表(必填)
     */
    private List<DingRobotMsgFeedCardLink> links;

    @Override
    public DingRobotMsgTypeEnum getMsgType() {
        return DingRobotMsgTypeEnum.FEED_CARD;
    }

}
