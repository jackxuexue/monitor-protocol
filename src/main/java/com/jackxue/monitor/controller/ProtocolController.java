package com.jackxue.monitor.controller;

import com.jackxue.monitor.codec.ProtocolMessage;
import com.jackxue.monitor.service.ProtocolService;
import com.jackxue.monitor.test.ContinueRead;
import com.jackxue.monitor.test.SerialDataCb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;


@Controller
@RequestMapping("/protocol")
public class ProtocolController implements SerialDataCb {

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private ContinueRead continueRead;

    private ProtocolMessage message;
    @GetMapping("/list")
    public String listAll(){
        return protocolService.listProtocol().toString();
    }

    @PostConstruct
    public void init(){
        continueRead.startCom("COM3", 9600);
        continueRead.registerCb(this);
    }

    @GetMapping("/decodeTest")
    public String decodeTest(Model model){
        //1.发送获取
        String buf = "01030000003045DE";
        continueRead.send(buf);
        if(message == null){
            message = new ProtocolMessage();
        }
        model.addAttribute("pro_messages", message);
        return "realtime";
    }


    @Override
    public void handle(int address, byte[] buf) {
        message = new ProtocolMessage();
        protocolService.decode(2, address, buf, message);
    }
}
