package com.jackxue.monitor.service;

import com.jackxue.monitor.codec.ProtocolMessage;
import com.jackxue.monitor.entities.Protocol;

import java.util.List;

public interface ProtocolService {
    List<Protocol> listProtocol();
    void decode(int protocolId, int address, byte[] buffer, ProtocolMessage message);
}
