package com.zlf.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Slf4j
@Data
public class DingRobotMsgDto {

    private final String pattern1 = "> **%s**  \n  ";

    private final String pattern2 = "- %-8s %-10s  \n  ";

    private final String pattern3 = "> **%s**: %s  \n  ";

    private Map<String, Object> map;

    private final String title = "title";

    public DingRobotMsgDto(Map<String, Object> map) {
        this.map = map;
    }

    public String buildMarkdownMsgContent() {
        if (CollectionUtil.isEmpty(map)) {
            throw new RuntimeException("所需map参数不为空");
        }
        Boolean vIsString = Boolean.FALSE;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj instanceof String) {
                vIsString = Boolean.TRUE;
            }
        }
        if (vIsString) {
            Set<String> ks = map.keySet();
            if (ks.contains(title)) {
                sb.append(String.format(pattern1, map.get("title")));
                Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Object> next = iterator.next();
                    if (title.equals(next.getKey())) {
                        iterator.remove();
                    }
                }
                log.info("buildMarkdownMsgContent.map中存在title,移除完成,sb:{}", sb);
            }
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object obj = entry.getValue();
                if (obj instanceof Map) {
                    sb.append(String.format(pattern1, key));
                    Map<String, String> valueMap = (Map) entry.getValue();
                    log.info("buildMarkdownMsgContent.key:{},valueMap1:{}", key, JSON.toJSONString(valueMap));
                    for (Map.Entry<String, String> valueEntry : valueMap.entrySet()) {
                        String vk = valueEntry.getKey();
                        String vv = valueEntry.getValue();
                        log.info("buildMarkdownMsgContent.vk1:{},vv1:{}", vk, vv);
                        sb.append(String.format(pattern2, vk, vv));
                    }
                } else if (obj instanceof String) {
                    sb.append(String.format(pattern3, entry.getKey(), entry.getValue()));
                }
            }
        } else {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object obj = entry.getValue();
                if (obj instanceof Map) {
                    sb.append(String.format(pattern1, key));
                    Map<String, String> valueMap = (Map) entry.getValue();
                    log.info("buildMarkdownMsgContent.key:{},valueMap2:{}", key, JSON.toJSONString(valueMap));
                    for (Map.Entry<String, String> valueEntry : valueMap.entrySet()) {
                        String vk = valueEntry.getKey();
                        String vv = valueEntry.getValue();
                        log.info("buildMarkdownMsgContent.vk2:{},vv2:{}", vk, vv);
                        sb.append(String.format(pattern2, vk, vv));
                    }
                }
            }
        }
        log.info("buildMarkdownMsgContent.sb:{}", sb);
        return sb.toString();
    }

}
