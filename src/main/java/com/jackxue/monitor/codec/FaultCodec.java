package com.jackxue.monitor.codec;

public interface FaultCodec {
    void decode(int value, ProtocolMessage message);
}
