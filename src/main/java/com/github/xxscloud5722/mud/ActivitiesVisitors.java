package com.github.xxscloud5722.mud;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class ActivitiesVisitors {
    private String id;
    private String uin;
    private String name;
    @JSONField(name = "addr")
    private String address;
    @JSONField(name = "visitor_id")
    private String visitorId;
    @JSONField(name = "nick")
    private String nickName;
    @JSONField(name = "watch_time")
    private String watchTime;
    private String phone;
    private String refer;
    @JSONField(name = "visit_times")
    private Long visitTimes;
    @JSONField(name = "first_login_time")
    private Date firstLoginTime;
    @JSONField(name = "last_online_time")
    private Date lastOnlineTime;
    private String device;
    private String browser;
    @JSONField(name = "last_ip")
    private String lastIp;
    @JSONField(name = "share_times")
    private Long shareTimes;
    @JSONField(name = "comment_count")
    private Long commentCount;
}
