package com.jackxue.monitor.codec;

import com.jackxue.monitor.entities.MessageDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProtocolMessage {
    private String protocolName;
    private String deviceSN;
    private String deviceID;
    private Long timeStamp;
    private Object source;
    private Long messageCount;
    private Long errorCount;

    private List<MessageDto> messages = new ArrayList<>();
    private List<MessageDto> errors = new ArrayList<>();
}
