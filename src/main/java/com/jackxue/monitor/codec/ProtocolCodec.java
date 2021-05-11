package com.jackxue.monitor.codec;

public interface ProtocolCodec {
    void decode(long startAddr, byte[] content, ProtocolMessage message);
}
