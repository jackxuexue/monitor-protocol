package com.jackxue.monitor.entities;

import com.jackxue.monitor.codec.ProtocolMessage;
import com.jackxue.monitor.codec.RegisterCodec;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Data
public class Register  implements RegisterCodec {
    private Long id;
    private Long protocolId;
    private Long regionId;
    private String name;
    private Long address;
    private Long bytes;
    private String type;
    private String unit;
    private Float accuracy;
    private String state;
    private Long minValue;
    private Long maxValue;
    private String description;
    private Date createdTime;
    private Date updatedTime;

    private List<Fault> faults;

    @Override
    public void decode(ByteBuffer buffer, ProtocolMessage message) {
        //获取自己的字节
        if(!buffer.hasRemaining()){
            System.out.println("buffer已经没有数据，返回！");
            return;
        }
        byte[] buf = new byte[bytes.intValue()];
        buffer.get(buf, 0, bytes.intValue());

        if(!StringUtils.isEmpty(type) && type.equals("Bit16")){
            int value = buf[0] << 8 | buf[1] ;
            for (Fault fault : faults) {
                fault.decode(value, message);
            }
        }

        if(!StringUtils.isEmpty(type) && type.equals("U16")) {
            int value = buf[0] << 8 | buf[1] ;
            double value1 = value * accuracy;
            DecimalFormat df = new DecimalFormat("#.00");
            message.getMessages().add(new MessageDto(name, df.format(value1), unit, description));
        }

        if(!StringUtils.isEmpty(type) && type.equals("I16")) {
            short value = (short) ((short)buf[0] << 8 | (short)buf[1]);
            double value1 = value * accuracy;
            DecimalFormat df = new DecimalFormat("#.00");
            message.getMessages().add(new MessageDto(name, df.format(value1), unit, description));
        }

        if(!StringUtils.isEmpty(type) && type.equals("U32")) {
            long value = buf[0] << 24 | buf[1] << 16 | buf[2] << 8 | buf[3];
            double value1 = value * accuracy;
            DecimalFormat df = new DecimalFormat("#.00");
            message.getMessages().add(new MessageDto(name, df.format(value1), unit, description));
        }

        if(!StringUtils.isEmpty(type) && type.equals("U64")) {
            long value = buf[0] << 56 | buf[1] << 48 | buf[2] << 40 | buf[3] << 32 |
                    buf[4] << 24 | buf[5] << 16 | buf[6] << 8 | buf[7];
            double value1 = value * accuracy;
            DecimalFormat df = new DecimalFormat("#.00");
            message.getMessages().add(new MessageDto(name, df.format(value1), unit, description));
        }
    }
}
