package com.github.xxscloud5722.mud;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Activities {
    private Integer id;
    private String name;
    @JSONField(name = "create_at")
    private Date createTime;
    @JSONField(name = "live_status")
    private Integer liveStatus;
    @JSONField(name = "watch_url")
    private Watch watchUrl;
    @JSONField(name = "embed_url")
    private String embedUrl;
    private Page page;
    @JSONField(name = "rtmp_publish_addr")
    private String rtmpPublishAddress;
    @JSONField(name = "hls_play_addr")
    private String hlsPlayAddress;
    @JSONField(name = "rtmp_play_addr")
    private String rtmpPlayAddress;
    private Long manager;
    @JSONField(name = "manager_username")
    private String managerUsername;
    @JSONField(name = "last_push_stream_at")
    private Date lastPushStreamTime;
    @JSONField(name = "web_live_tool")
    private LiveTool webLiveTool;

    @Data
    public static class Watch {
        private String pc;
        private String mobile;
    }


    @Data
    public static class Page {
        @JSONField(name = "start_time")
        private String startTime;
        private String logo;
        @JSONField(name = "pc_logo")
        private String pcLogo;
        @JSONField(name = "mobile_logo")
        private String mobileLogo;
        private String banner;
        @JSONField(name = "cover_image")
        private String coverImage;
        @JSONField(name = "live_img")
        private String liveImg;
        private String footer;
    }

    public static class LiveTool {
        @JSONField(name = "host_login_addr")
        private String host_login_addr;
        @JSONField(name = "host_login_token")
        private String host_login_token;
        @JSONField(name = "guest_login_addr")
        private String guest_login_addr;
        @JSONField(name = "guest_login_token")
        private String guest_login_token;
    }
}
