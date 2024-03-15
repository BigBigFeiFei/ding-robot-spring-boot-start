# ding-robot-spring-boot-start

ding-robot-spring-boot-start启动器<br>
1. 项目中引入依赖如下：<br>
```
<dependency>
    <groupId>org.zlf</groupId>
    <artifactId>ding-robot-spring-boot-start</artifactId>
    <version>1.0-SNAPSHOT</version> 
</dependency>
```        
2. nacos配置如下：<br>
```
zlf:
  robot:
    rbs:
      - accessToken: xxxxxxx
        secret: xxxxxxx
```
3. 启动类上加入如下注解：<br>
   启动类上加@EnableDingRobot注解
4. 使用TestDemo<br>
```
package xxxx.controller;

import com.zlf.service.DingRobotService;
import com.zlf.type.DingRobotMsgActionCardButton;
import com.zlf.type.DingRobotMsgAt;
import com.zlf.type.DingRobotMsgLink;
import com.zlf.type.DingRobotMsgMarkdown;
import com.zlf.type.DingRobotMsgMoreActionCard;
import com.zlf.type.DingRobotMsgSingleActionCard;
import com.zlf.type.DingRobotMsgText;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("ding")
public class DingDingController {

    @Autowired
    private DingRobotService dingRobotService;

    @GetMapping("/sendMsg1")
    public String sendMsg1(@RequestParam(value = "msg") String msg) {
        DingRobotMsgText dingRobotMsgText = new DingRobotMsgText();
        dingRobotMsgText.setContent(msg);
        DingRobotMsgAt at = new DingRobotMsgAt();
       /* List<String> mobiles = new ArrayList<>();
        mobiles.add("xxxx");
        mobiles.add("xxxxxx");
        at.setAtMobiles(mobiles);*/
        //xxxxxx
        List<String> userIds = new ArrayList<>();
        userIds.add("xxxxx");
        userIds.add("xxxxx");
        at.setAtUserIds(userIds);
        dingRobotService.sendMsgByIndex0(dingRobotMsgText, at);
        return "ok";
    }

    @GetMapping("/sendMsg2")
    public String sendMsg2(@RequestParam(value = "msg") String msg) {
        DingRobotMsgMarkdown msgMarkdown = new DingRobotMsgMarkdown();
        msgMarkdown.setTitle("测试");
        msgMarkdown.setText(msg);
        dingRobotService.sendMsgByIndex0(msgMarkdown);
        return "ok";
    }

    @GetMapping("/sendMsg3")
    public String sendMsg() {
        DingRobotMsgMarkdown msgMarkdown = new DingRobotMsgMarkdown();
        msgMarkdown.setTitle("测试");
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("title", "=====我是标题=====");
        /*map.put("年龄", "28");
        map.put("兴趣", "打球");*/
        Map<String, String> map1 = new HashMap<>();
        map1.put("姓名", "张三");
        map1.put("年龄", "28");
        Map<String, String> map2 = new HashMap<>();
        map2.put("兴趣", "打球");
        map.put("item1", map1);
        map.put("item2", map2);
        map.put("我是张三", "我是张三");
        map.put("我是李四", "我是李四");
        String msg = dingRobotService.buildMarkDownContent(map);
        msgMarkdown.setTitle("我是markdown的title");
        //真正的标题定义,上面那个是个map里面的title是个伪标题
        msgMarkdown.setText("# 我是大标题   \n  " + msg);
        DingRobotMsgAt at = new DingRobotMsgAt();
        dingRobotService.sendMsgByIndex0(msgMarkdown,at);
        return "ok";
    }

    @GetMapping("/sendMsg4")
    public String sendMsg4() {
        DingRobotMsgLink link = new DingRobotMsgLink();
        link.setTitle("这是Link消息");
        link.setText("这是一个Link消息");
        link.setPicUrl("https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png");
        link.setMessageUrl("https://open.dingtalk.com/document/");
        dingRobotService.sendMsgByIndex0(link);
        return "ok";
    }

    @GetMapping("/sendMsg5")
    public String sendMsg5() {
        DingRobotMsgSingleActionCard singleActionCard = new DingRobotMsgSingleActionCard();
        singleActionCard.setTitle("打造一间咖啡厅");
        singleActionCard.setText("![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png) \\n #### 乔布斯 20 年前想打造的苹果咖啡厅 \\n\\n Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划");
        singleActionCard.setSingleTitle("阅读全文");
        singleActionCard.setSingleURL("https://www.dingtalk.com/");
        dingRobotService.sendMsgByIndex0(singleActionCard);
        return "ok";
    }


    @GetMapping("/sendMsg6")
    public String sendMsg6() {
        DingRobotMsgMoreActionCard moreActionCard = new DingRobotMsgMoreActionCard();
        moreActionCard.setTitle("打造一间咖啡厅");
        moreActionCard.setText("![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png) \\n #### 乔布斯 20 年前想打造的苹果咖啡厅 \\n\\n Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划");
        List<DingRobotMsgActionCardButton> btns = new ArrayList<>();
        DingRobotMsgActionCardButton bt1 = new DingRobotMsgActionCardButton();
        bt1.setTitle("内容不错");
        bt1.setActionURL("https://www.dingtalk.com/");
        DingRobotMsgActionCardButton bt2 = new DingRobotMsgActionCardButton();
        bt2.setTitle("不感兴趣");
        bt2.setActionURL("https://www.dingtalk.com/");
        btns.add(bt1);
        btns.add(bt2);
        moreActionCard.setBtns(btns);
        dingRobotService.sendMsgByIndex0(moreActionCard);
        return "ok";
    }
    
    @GetMapping("/sendMsg7")
    public String sendMsg7() {
        DingRobotMsgFeedCard feedCard = new DingRobotMsgFeedCard();
        List<DingRobotMsgFeedCardLink> links = new ArrayList<>();
        DingRobotMsgFeedCardLink link = new DingRobotMsgFeedCardLink();
        link.setTitle("时代的火车向前开1");
        link.setMessageURL("https://www.dingtalk.com/");
        link.setPicURL("https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png");

        DingRobotMsgFeedCardLink link2 = new DingRobotMsgFeedCardLink();
        link2.setTitle("时代的火车向前开2");
        link2.setMessageURL("https://www.dingtalk.com/");
        link2.setPicURL("https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png");

        links.add(link);
        links.add(link2);
        feedCard.setLinks(links);
        dingRobotService.sendMsgByIndex0(feedCard);
    }
}

```