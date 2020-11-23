package com.github.xxscloud5722.mud;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ActivitiesRecord {
    @JSONField(name = "record_id")
    private String recordId;
    @JSONField(name = "user_id")
    private String userId;
    @JSONField(name = "assign_id")
    private String assignId;
    private String nick;
    @JSONField(name = "addr")
    private String address;
    @JSONField(name = "online_time")
    private String onlineTime;
    @JSONField(name = "watch_time")
    private String watchTime;
    private String phone;
    @JSONField(name = "first_login_time")
    private String firstLoginTime;
    @JSONField(name = "last_online_time")
    private String lastOnlineTime;
    private String device;
    private String browser;
    private String origin;
}
