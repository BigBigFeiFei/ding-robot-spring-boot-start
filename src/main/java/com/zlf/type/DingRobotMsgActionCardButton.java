package com.zlf.type;

import lombok.Data;


@Data
public class DingRobotMsgActionCardButton {

    /**
     * 按钮标题(必填)
     */
    private String title;

    /**
     * 点击按钮触发的URL(必填)
     */
    private String actionURL;

    /**
     * 按钮排列顺序。(非必填)
     * 0：按钮竖直排列
     * 1：按钮横向排列
     */
    private String btnOrientation;

}
