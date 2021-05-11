package com.jackxue.monitor.entities;

import com.jackxue.monitor.codec.ProtocolCodec;
import com.jackxue.monitor.codec.ProtocolMessage;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

@Data
public class Protocol implements ProtocolCodec {

    private Long id;
    private String name;
    private String version;
    private Date createdTime;
    private Date updatedTime;

    private List<Register> registers;

    @Override
    public void decode(long startAddr, byte[] content, ProtocolMessage message) {
        if(content[0] != 0x1 || content[1] != 0x3 || content[2] %2 != 0){
            System.out.println("数据格式不对，返回");
            return;
        }
        int offset = 3;
        int len = content[2];
        ByteBuffer buf = ByteBuffer.wrap(content, offset, len);


        //校验消息
        for (Register register : registers) {
            if(register.getAddress() >= startAddr){
                register.decode(buf, message);
            }
        }
    }
}
