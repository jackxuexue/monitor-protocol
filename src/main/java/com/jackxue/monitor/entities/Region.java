package com.jackxue.monitor.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Region{
    private Long id;
    private Long protocolId;
    private String name;
    private Long startAddr;
    private Long endAddr;
    private Long bytes;
    private Date createdTime;
    private Date updatedTime;
}
