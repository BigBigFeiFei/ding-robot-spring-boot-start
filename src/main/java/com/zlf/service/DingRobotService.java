package com.zlf.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.zlf.config.DingRobotConfig;
import com.zlf.config.RobotProperties;
import com.zlf.constants.ZlfDingRobotConstant;
import com.zlf.dto.DingRobotMsgDto;
import com.zlf.enums.DingRobotMsgTypeEnum;
import com.zlf.type.DingRobotMsg;
import com.zlf.type.DingRobotMsgActionCardButton;
import com.zlf.type.DingRobotMsgAt;
import com.zlf.type.DingRobotMsgFeedCard;
import com.zlf.type.DingRobotMsgFeedCardLink;
import com.zlf.type.DingRobotMsgLink;
import com.zlf.type.DingRobotMsgMarkdown;
import com.zlf.type.DingRobotMsgMoreActionCard;
import com.zlf.type.DingRobotMsgSingleActionCard;
import com.zlf.type.DingRobotMsgText;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class DingRobotService {

    private DingRobotConfig dingRobotConfig;

    public DingRobotService(DingRobotConfig dingRobotConfig) {
        this.dingRobotConfig = dingRobotConfig;
    }

    /**
     * 根据一个map构建一个markdown的字符串
     * Map<String, Map<String, String>> map
     * Map<String,String> map
     *
     * @param map
     * @return
     */
    public String buildMarkDownContent(Map<String, Object> map) {
        DingRobotMsgDto dto = new DingRobotMsgDto(map);
        String mdContent = dto.buildMarkdownMsgContent();
        return mdContent;
    }

    public void sendMsgByIndex0(DingRobotMsg msg) {
        String msgStr = this.buildMsg1(msg);
        this.send(0, msgStr);
    }

    public void sendMsgByIndex0(DingRobotMsg msg, DingRobotMsgAt at) {
        String msgStr = this.buildMsg1(msg, at);
        this.send(0, msgStr);
    }

    public void sendMsgByIndex(int index, DingRobotMsg msg) {
        String msgStr = this.buildMsg1(msg);
        this.send(index, msgStr);
    }

    public void sendMsgByIndex(int index, DingRobotMsg msg, DingRobotMsgAt at) {
        String msgStr = this.buildMsg0(msg, at);
        this.send(index, msgStr);
    }

    public String buildMsg1(DingRobotMsg msg) {
        return this.buildMsg0(msg, null);
    }

    public String buildMsg1(DingRobotMsg msg, DingRobotMsgAt at) {
        return this.buildMsg0(msg, at);
    }

    public String buildMsg0(DingRobotMsg msg, DingRobotMsgAt at) {
        this.checkMsg(msg, at);
        HashMap<String, Object> param = new HashMap<>(3);
        DingRobotMsgTypeEnum msgTypeEnum = msg.getMsgType();
        String msgType = msgTypeEnum.getMsgType();
        param.put("msgtype", msgType);
        param.put(msgType, msg);
        //是否@所有人
        if (msg instanceof DingRobotMsgText) {
            if (Objects.isNull(at)) {
                param.put("at", new DingRobotMsgAt());
            } else {
                param.put("at", at);
            }
        } else if (msg instanceof DingRobotMsgMarkdown) {
            if (Objects.isNull(at)) {
                param.put("at", new DingRobotMsgAt());
            } else {
                param.put("at", at);
            }
        }
        return JSON.toJSONString(param);
    }

    /**
     * 检查消息参数
     *
     * @param msg
     * @param at
     */
    private void checkMsg(DingRobotMsg msg, DingRobotMsgAt at) {
        if (Objects.isNull(msg)) {
            throw new RuntimeException("DingRobotMsg参数不为空");
        }
        if (msg instanceof DingRobotMsgText) {
            DingRobotMsgText msgText = (DingRobotMsgText) msg;
            if (StringUtils.isEmpty(msgText.getContent())) {
                throw new RuntimeException("发送" + msgText.getMsgType().getMsgType() + "类型消息的content不为空");
            }
            this.doContent(msg, at);
        } else if (msg instanceof DingRobotMsgMarkdown) {
            DingRobotMsgMarkdown msgMarkdown = (DingRobotMsgMarkdown) msg;
            if (StringUtils.isEmpty(msgMarkdown.getTitle())) {
                throw new RuntimeException("发送" + msgMarkdown.getMsgType().getMsgType() + "类型消息的title不为空");
            }
            if (StringUtils.isEmpty(msgMarkdown.getText())) {
                throw new RuntimeException("发送" + msgMarkdown.getMsgType().getMsgType() + "类型消息的text不为空");
            }
            this.doContent(msg, at);
        } else if (msg instanceof DingRobotMsgLink) {
            DingRobotMsgLink msgLink = (DingRobotMsgLink) msg;
            if (StringUtils.isEmpty(msgLink.getTitle())) {
                throw new RuntimeException("发送" + msgLink.getMsgType().getMsgType() + "类型消息的title不为空");
            }
            if (StringUtils.isEmpty(msgLink.getText())) {
                throw new RuntimeException("发送" + msgLink.getMsgType().getMsgType() + "类型消息的text不为空");
            }
        } else if (msg instanceof DingRobotMsgSingleActionCard) {
            DingRobotMsgSingleActionCard singleActionCard = (DingRobotMsgSingleActionCard) msg;
            if (StringUtils.isEmpty(singleActionCard.getTitle())) {
                throw new RuntimeException("发送" + singleActionCard.getMsgType().getMsgType() + "类型消息的title不为空");
            }
            if (StringUtils.isEmpty(singleActionCard.getText())) {
                throw new RuntimeException("发送" + singleActionCard.getMsgType().getMsgType() + "类型消息的text不为空");
            }
            if (StringUtils.isEmpty(singleActionCard.getSingleTitle())) {
                throw new RuntimeException("发送" + singleActionCard.getMsgType().getMsgType() + "类型消息的singleTitle不为空");
            }
            if (StringUtils.isEmpty(singleActionCard.getSingleURL())) {
                throw new RuntimeException("发送" + singleActionCard.getMsgType().getMsgType() + "类型消息的singleURL不为空");
            }
        } else if (msg instanceof DingRobotMsgMoreActionCard) {
            DingRobotMsgMoreActionCard moreActionCard = (DingRobotMsgMoreActionCard) msg;
            if (StringUtils.isEmpty(moreActionCard.getTitle())) {
                throw new RuntimeException("发送" + moreActionCard.getMsgType().getMsgType() + "类型消息的title不为空");
            }
            if (StringUtils.isEmpty(moreActionCard.getText())) {
                throw new RuntimeException("发送" + moreActionCard.getMsgType().getMsgType() + "类型消息的text不为空");
            }
            if (CollectionUtil.isEmpty(moreActionCard.getBtns())) {
                throw new RuntimeException("发送" + moreActionCard.getMsgType().getMsgType() + "类型消息的btns不为空");
            }
            //校验btns
            List<DingRobotMsgActionCardButton> btns = moreActionCard.getBtns();
            for (DingRobotMsgActionCardButton btn : btns) {
                if (StringUtils.isEmpty(btn.getTitle())) {
                    throw new RuntimeException("发送" + moreActionCard.getMsgType().getMsgType() + "类型消息的btns中title不为空");
                }
                if (StringUtils.isEmpty(btn.getActionURL())) {
                    throw new RuntimeException("发送" + moreActionCard.getMsgType().getMsgType() + "类型消息的btns中actionURL不为空");
                }
            }
        } else if (msg instanceof DingRobotMsgFeedCard) {
            DingRobotMsgFeedCard feedCard = (DingRobotMsgFeedCard) msg;
            if (CollectionUtil.isEmpty(feedCard.getLinks())) {
                throw new RuntimeException("发送" + feedCard.getMsgType().getMsgType() + "类型消息的links不为空");
            }
            //校验links
            List<DingRobotMsgFeedCardLink> links = feedCard.getLinks();
            for (DingRobotMsgFeedCardLink link : links) {
                if (StringUtils.isEmpty(link.getTitle())) {
                    throw new RuntimeException("发送" + feedCard.getMsgType().getMsgType() + "类型消息的links中title不为空");
                }
            }
        }
    }

    private void doContent(DingRobotMsg msg, DingRobotMsgAt at) {
        if (Objects.nonNull(at) && CollectionUtil.isNotEmpty(at.getAtUserIds())) {
            //userIds去重
            List<String> atUserIds = at.getAtUserIds();
            log.info("doContent.atUserIds:{}", JSON.toJSONString(atUserIds));
            Set<String> atUserIdsSet = new HashSet<>(atUserIds);
            List<String> atUserIdsNew = new ArrayList<>(atUserIdsSet);
            log.info("doContent.atUserIdsNew:{}", JSON.toJSONString(atUserIdsNew));
            at.setAtUserIds(atUserIdsNew);
            StringBuffer sb = new StringBuffer();
            atUserIdsNew.forEach(e -> {
                        sb.append("@" + e);
                    }
            );
            String userIdsNewStr = sb.toString();
            log.info("DingRobotService.doContent.userIdsNewStr:{}", userIdsNewStr);
            if (msg instanceof DingRobotMsgText) {
                DingRobotMsgText msgText = (DingRobotMsgText) msg;
                msgText.setContent(msgText.getContent() + userIdsNewStr);
                log.info("DingRobotService.doContent.atUserIds.text-content:{}", msgText.getContent());
            } else if (msg instanceof DingRobotMsgMarkdown) {
                DingRobotMsgMarkdown msgMarkdown = (DingRobotMsgMarkdown) msg;
                msgMarkdown.setText(msgMarkdown.getText() + userIdsNewStr);
                log.info("DingRobotService.doContent.atUserIds.markdown-text:{}", msgMarkdown.getText());
            }
        }
        if (Objects.nonNull(at) && CollectionUtil.isNotEmpty(at.getAtMobiles())) {
            List<String> atMobiles = at.getAtMobiles();
            log.info("DingRobotService.doContent.atMobiles:{}", JSON.toJSONString(atMobiles));
            //手机号去重
            Set<String> atMobilesSet = new HashSet<>(atMobiles);
            StringBuffer sb = new StringBuffer();
            atMobilesSet.forEach(e -> {
                sb.append("@" + e);
            });
            String atMobileStr = sb.toString();
            log.info("DingRobotService.doContent.atMobileStr:{}", atMobileStr);
            if (msg instanceof DingRobotMsgText) {
                DingRobotMsgText msgText = (DingRobotMsgText) msg;
                msgText.setContent(msgText.getContent() + atMobileStr);
                log.info("DingRobotService.doContent.atMobiles.text-content:{}", msgText.getContent());
            } else if (msg instanceof DingRobotMsgMarkdown) {
                DingRobotMsgMarkdown msgMarkdown = (DingRobotMsgMarkdown) msg;
                msgMarkdown.setText(msgMarkdown.getText() + atMobileStr);
                log.info("DingRobotService.doContent.atMobiles.markdown-text:{}", msgMarkdown.getText());
            }
        }
        if (Objects.nonNull(at) && (CollectionUtil.isNotEmpty(at.getAtMobiles()) || CollectionUtil.isNotEmpty(at.getAtUserIds()))) {
            at.setIsAtAll(Boolean.FALSE);
        }
    }

    private RobotProperties getRobotProperties(int index) {
        List<RobotProperties> rbs = dingRobotConfig.getRbs();
        if (CollectionUtil.isEmpty(rbs)) {
            throw new RuntimeException("根据index获取RobotProperties为空");
        }
        return rbs.get(index);
    }

    private void send(int index, String msg) {
        try {
            RobotProperties p = this.getRobotProperties(index);
            Long timestamp = System.currentTimeMillis();
            String sign = getSign(p.getSecret(), timestamp);
            if (StringUtils.isEmpty(sign)) {
                log.error("【发送钉钉群消息】sign不为空,签名异常");
            }
            String url = ZlfDingRobotConstant.DING_URL + p.getAccessToken() + "&timestamp=" + timestamp + "&sign=" + sign;
            ThreadPoolService.getInstance().execute(
                    () -> {
                        log.info("【发送钉钉群消息】请求参数：url = {}, msg = {}", url, msg);
                        String res = HttpUtil.post(url, msg);
                        log.info("【发送钉钉群消息】消息响应结果：" + res);
                    });
        } catch (Exception e) {
            log.error("【发送钉钉群消息】请求钉钉接口异常，msg = {}", e);
        }
    }

    private String getSign(String secret, Long timestamp) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            log.info("【发送钉钉群消息】获取到签名sign = {}", sign);
            return sign;
        } catch (Exception e) {
            log.error("【发送钉钉群消息】计算签名异常，errMsg = {}", e);
            return null;
        }
    }

}
