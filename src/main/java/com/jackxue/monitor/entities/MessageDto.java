package com.jackxue.monitor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MessageDto {
    //名称
    private String name;
    //值
    private String value;
    //单位
    private String unit;
    //详细描述
    private String description;
}
