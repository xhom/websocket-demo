package com.vz.ws.demo.common;

import lombok.Data;

/**
 * @author visy.wang
 * @description: WebSocket消息封装
 * @date 2023/5/9 21:54
 */
@Data
public class WsMessage {
    /**
     * 消息编码
     */
    private String code;
    /**
     * 来自（保证唯一）
     */
    private String form;
    /**
     * 去自（保证唯一）
     */
    private String to;
    /**
     * 消息内容
     */
    private String content;
}
