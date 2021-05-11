package com.jackxue.monitor.codec;

import java.nio.ByteBuffer;

public interface RegisterCodec {
    void decode(ByteBuffer buffer, ProtocolMessage message);
}
