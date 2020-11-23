package com.github.xxscloud5722.mud;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MuSDK {
    private String token;
    private static final String URL = "http://api.mudu.tv";
    private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

    public MuSDK(String token) {
        this.token = token;
    }


    /**
     * 创建频道.
     *
     * @param name      频道名称
     * @param startTime 开始时间 (选填).
     * @param adminId   管理员ID (选填).
     * @param mode      直播模式 (选填).
     */
    public Activities createActivities(String name, Date startTime, String adminId, Boolean mode) throws IOException {
        final String url = URL + "/v1/activities";
        final JSONObject request = new JSONObject();
        request.put("name", name);
        request.put("startTime", startTime);
        request.put("adminId", adminId);
        request.put("mode", mode != null && mode ? "camera" : "no_camera");
        return post(url, request, Activities.class);
    }


    /**
     * 修改频道名称.
     *
     * @param id   ID.
     * @param name 名称.
     * @return 频道信息.
     */
    public Activities updateActivities(String id, String name) throws IOException {
        final String url = URL + "/v1/activities/" + id;
        final JSONObject request = new JSONObject();
        request.put("name", name);
        return put(url, request, Activities.class);
    }

    /**
     * 修改频道名称
     *
     * @param id         ID.
     * @param startTime  开始时间.
     * @param pcLogo     PC_Logo 地址.
     * @param mobileLogo 手机端Logo 地址.
     * @param banner     Banner 地址.
     * @param coverImage 封面地址.
     * @param liveImages 直播图片地址.
     * @param footer     版权.
     * @param color      背景颜色.
     * @param qrCode     是否需要二维码.
     * @param theme      样式.
     * @return 频道信息.
     * @throws IOException 处理异常.
     */
    public Activities updateActivities(String id, Date startTime, String pcLogo, String mobileLogo,
                                       String banner, String coverImage, String liveImages, String footer,
                                       String color, Boolean qrCode, String theme) throws IOException {
        final String url = URL + "/v1/activities/" + id + "/page";
        final JSONObject request = new JSONObject();
        request.put("start_time", startTime);
        request.put("pc_logo", pcLogo);
        request.put("mobile_logo", mobileLogo);
        request.put("banner", banner);
        request.put("cover_image", coverImage);
        request.put("live_img", liveImages);
        request.put("footer", footer);
        request.put("bg_color", color);
        request.put("show_qrcode", qrCode);
        request.put("theme", theme);
        return put(url, request, Activities.class);
    }


    /**
     * 删除频道.
     *
     * @param id 频道ID.
     * @return 是否成功.
     * @throws IOException 处理异常.
     */
    public boolean deleteActivities(String id) throws IOException {
        final String url = URL + "/v1/activities/" + id;
        return Objects.equals(delete(url).getString("result"), "success");
    }

    /**
     * 获取频道报表数据.
     *
     * @param id 频道ID.
     * @return 是否成功.
     * @throws IOException 处理异常.
     */
    public ActivitiesReport getActivitiesReport(String id) throws IOException {
        final String url = URL + "/v1/activities/" + id + "/report";
        return get(url, ActivitiesReport.class);
    }

    /**
     * 获取频道访客报表.
     *
     * @param id           频道ID.
     * @param currentIndex 页面.
     * @return 报表数据.
     * @throws IOException 处理异常.
     */
    public List<ActivitiesVisitors> getActivitiesVisitors(String id, Integer currentIndex) throws IOException {
        final String url = URL + "/v1/activities/" + id + "/visitors";
        final JSONObject request = new JSONObject();
        request.put("filter", "1");
        request.put("perPage", "100");
        request.put("p", currentIndex);
        request.put("direction", "desc");
        return post(url, request, ActivitiesVisitors.class, true);
    }

    /**
     * 获取频道访客报表.
     *
     * @param id           频道ID.
     * @param currentIndex 页面.
     * @return 报表数据.
     * @throws IOException 处理异常.
     */
    public List<ActivitiesVisitors> getRecordList(String id, int currentIndex, boolean watch, Date startTime, Date endTime) throws IOException {
        final String url = URL + "/v2/activities/" + id + "/userRecords";
        final JSONObject request = new JSONObject();
        request.put("start_time", startTime);
        request.put("end_time", endTime);
        request.put("perPage", "100");
        request.put("p", currentIndex);
        request.put("filter_watch_time", watch ? "1" : "0");
        return post(url, request, ActivitiesVisitors.class, true);
    }


    private JSONObject get(String url) throws IOException {
        return get(url, JSONObject.class);
    }

    private <T> T get(String url, Class<T> clazz) throws IOException {
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", "Bearer " + token);

        log.info("[目睹SDK] 请求地址: " + url);
        final HttpResponse response = HTTP_CLIENT.execute(httpGet);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("response code : " + response.getStatusLine().getStatusCode());
        }
        final String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());
        log.info("[目睹SDK] 响应: " + responseString);
        return JSONObject.parseObject(responseString, clazz);
    }

    private JSONObject post(String url, Object body) throws IOException {
        return post(url, body, JSONObject.class);
    }


    private <T> T post(String url, Object body, Class<T> clazz) throws IOException {
        final HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + token);

        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(body), StandardCharsets.UTF_8));
        log.info("[目睹SDK] 请求地址: " + url);
        final HttpResponse response = HTTP_CLIENT.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("response code : " + response.getStatusLine().getStatusCode());
        }
        final String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());
        log.info("[目睹SDK] 响应: " + responseString);
        return JSONObject.parseObject(responseString, clazz);
    }

    private <T> List<T> post(String url, Object body, Class<T> clazz, Object s) throws IOException {
        final HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + token);

        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(body), StandardCharsets.UTF_8));
        log.info("[目睹SDK] 请求地址: " + url);
        final HttpResponse response = HTTP_CLIENT.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("response code : " + response.getStatusLine().getStatusCode());
        }
        final String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());
        log.info("[目睹SDK] 响应: " + responseString);
        return JSONObject.parseArray(responseString, clazz);
    }

    private JSONObject put(String url, Object body) throws IOException {
        return put(url, body, JSONObject.class);
    }

    private <T> T put(String url, Object body, Class<T> clazz) throws IOException {
        final HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-Type", "application/json");
        httpPut.setHeader("Authorization", "Bearer " + token);

        httpPut.setEntity(new StringEntity(JSONObject.toJSONString(body), StandardCharsets.UTF_8));
        log.info("[目睹SDK] 请求地址: " + url);
        final HttpResponse response = HTTP_CLIENT.execute(httpPut);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("response code : " + response.getStatusLine().getStatusCode());
        }
        final String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());
        log.info("[目睹SDK] 响应: " + responseString);
        return JSONObject.parseObject(responseString, clazz);
    }


    private JSONObject delete(String url) throws IOException {
        return put(url, JSONObject.class);
    }

    private <T> T delete(String url, Class<T> clazz) throws IOException {
        final HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Content-Type", "application/json");
        httpDelete.setHeader("Authorization", "Bearer " + token);
        log.info("[目睹SDK] 请求地址: " + url);
        final HttpResponse response = HTTP_CLIENT.execute(httpDelete);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("response code : " + response.getStatusLine().getStatusCode());
        }
        final String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());
        log.info("[目睹SDK] 响应: " + responseString);
        return JSONObject.parseObject(responseString, clazz);
    }
}
