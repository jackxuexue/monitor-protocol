package com.jackxue.monitor.test;

import com.jackxue.monitor.utils.ByteUtils;
import gnu.io.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

@Component
public class ContinueRead implements SerialPortEventListener {
    private CommPortIdentifier portId; // 串口通信管理类
    private SerialPort serialPort; // 串口的引用
    private InputStream inputStream;
    private OutputStream outputStream;
    private SerialDataCb serialDataCb;
    private int lastSendAddr;
    private byte[] buf = new byte[1024];


    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        System.out.println("serialEvent:" + serialPortEvent.getEventType());
        switch (serialPortEvent.getEventType()){
            case SerialPortEvent.BI: // 通讯中断
            case SerialPortEvent.OE: // 溢位错误
            case SerialPortEvent.FE: // 帧错误
            case SerialPortEvent.PE: // 奇偶校验错误
            case SerialPortEvent.CD: // 载波检测
            case SerialPortEvent.CTS: // 清除发送
            case SerialPortEvent.DSR: // 数据设备准备好
            case SerialPortEvent.RI: // 响铃侦测
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
                break;
            case SerialPortEvent.DATA_AVAILABLE: //数据可读
                int read = 0;
                int totalRead = 0;
                try {
                    while ((read = inputStream.read(buf, totalRead, buf.length-totalRead)) > 0){
                        totalRead += read;
                    }
                    System.out.println("读取了:" + totalRead);
                    System.out.println("接受到的数据");
                    for (int i = 0; i < totalRead; i++) {
                        System.out.printf("0x%02x ", buf[i]);
                    }
                    byte[] newBuffer = new byte[totalRead];
                    System.arraycopy(buf, 0, newBuffer, 0, totalRead);
                    if(serialDataCb != null){
                        serialDataCb.handle(lastSendAddr, newBuffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public void startCom(String com, int baudRate){
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        while (portIdentifiers.hasMoreElements()){
            portId  = (CommPortIdentifier) portIdentifiers.nextElement();
            System.out.println("设备类型：--->" + portId.getPortType());
            System.out.println("设备名称：---->" + portId.getName());
            if(portId.getName().equals(com) && portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
                try {
                    serialPort = (SerialPort) portId.open(com, 200);
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    serialPort.addEventListener(this);
                    serialPort.notifyOnDataAvailable(true);
                    serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                } catch (PortInUseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void registerCb(SerialDataCb cb){
        this.serialDataCb = cb;
    }

    public void send(String buf){
        byte[] buffer = ByteUtils.hexStringToBytes(buf);
        lastSendAddr = buffer[2] << 8 | buffer[3];
        try {
            outputStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
