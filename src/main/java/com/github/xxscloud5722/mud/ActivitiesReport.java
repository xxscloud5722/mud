package com.github.xxscloud5722.mud;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;

@Data
public class ActivitiesReport {
    private String id;
    private String name;
    private Long cost;
    @JSONField(name = "watch_seconds")
    private Long watchSeconds;
    @JSONField(name = "watch_times")
    private Long watchTimes;

    @JSONField(name = "watch_times_detail")
    private HashMap<String, String> watchTimesDetail;


    @JSONField(name = "online_peak")
    private String onlinePeak;
    @JSONField(name = "comments_count")
    private Integer commentsCount;
    @JSONField(name = "publish_seconds")
    private Integer publishSeconds;
    @JSONField(name = "online_detail")
    private HashMap<String, String> onlineDetail;
    private String uv;
    private String pv;


}
