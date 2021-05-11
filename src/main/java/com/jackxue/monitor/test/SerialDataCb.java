package com.jackxue.monitor.test;

public interface SerialDataCb {
    void handle(int address, byte[] buf);
}
