package com.jackxue.monitor.entities;

import com.jackxue.monitor.codec.FaultCodec;
import com.jackxue.monitor.codec.ProtocolMessage;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

@Data
public class Fault implements FaultCodec {
    private Long id;
    private Long registerId;
    private String name;
    private Integer offset;
    private Integer level;
    private String source;
    private String description;
    private Date createdTime;
    private Date updatedTime;

    @Override
    public void decode(int value, ProtocolMessage message) {
        int value1 = ((int)(value  >> offset) & 0x1);
        if(value1 == 1) {
            message.getErrors().add(new MessageDto(name, "", "",description));
        }
    }
}
