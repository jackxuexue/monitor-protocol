package com.jackxue.monitor.service.impl;

import com.jackxue.monitor.codec.ProtocolMessage;
import com.jackxue.monitor.entities.Protocol;
import com.jackxue.monitor.mapper.ProtocolMapper;
import com.jackxue.monitor.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ProtocolServiceImpl implements ProtocolService {

    @Autowired
    private ProtocolMapper protocolMapper;

    private List<Protocol> protocolList;

    @Override
    public List<Protocol> listProtocol() {
        return protocolMapper.list();
    }

    @PostConstruct
    void init(){
        protocolList = protocolMapper.list();
    }

    @Override
    public void decode(int protocolId, int address, byte[] buffer, ProtocolMessage message){
        for (Protocol protocol : protocolList) {
            if(protocol.getId().equals(new Long(protocolId))){
                message.setProtocolName(protocol.getName());
                protocol.decode(address, buffer, message);
            }
        }
        System.out.println(message);
    }
}
