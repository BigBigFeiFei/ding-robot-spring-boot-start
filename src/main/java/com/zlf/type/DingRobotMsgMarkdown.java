package com.zlf.type;

import com.zlf.enums.DingRobotMsgTypeEnum;
import lombok.Data;

/**
 * 钉钉推送消息 markdown类型
 * 目前只支持Markdown语法的子集，支持的元素如下：
 * 标题
 * # 一级标题
 * ## 二级标题
 * ### 三级标题
 * #### 四级标题
 * ##### 五级标题
 * ###### 六级标题
 * <p>
 * 引用
 * > A man who stands for nothing will fall for anything.
 * <p>
 * 文字加粗、斜体
 * **bold**
 * *italic*
 * <p>
 * 链接
 * [this is a link](https://www.dingtalk.com/)
 * <p>
 * 图片
 * ![](http://name.com/pic.jpg)
 *
 * @author zlf
 * @date 2014/03/14
 */
@Data
public class DingRobotMsgMarkdown extends DingRobotMsg {

    /**
     * 标题(必填)
     * 首屏会话透出的展示内容。
     */
    private String title;

    /**
     * Markdown格式的消息内容(必填)
     */
    private String text;

    /**
     * 获取消息类型
     *
     * @return
     */
    @Override
    public DingRobotMsgTypeEnum getMsgType() {
        return DingRobotMsgTypeEnum.MARKDOWN;
    }

}
