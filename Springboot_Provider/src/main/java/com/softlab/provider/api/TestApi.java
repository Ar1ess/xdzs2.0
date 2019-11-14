package com.softlab.provider.api;

import com.softlab.common.RestData;
import com.softlab.common.service.PaceFzService;
import com.softlab.common.service.PaceService;
import com.softlab.common.util.JsonUtil;
import com.softlab.provider.mapper.RedisMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author LiXiwen
 * @date 2019/11/12 14:12
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class TestApi {
    private static final Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Autowired
    private PaceService paceService;
    @Autowired
    private PaceFzService paceFzService;

    @Autowired
    private RedisMapper redisMapper;


    @GetMapping("/test3")
    public void test3() {
        //logger.info(JsonUtil.getJsonString(redisMapper.zRange1()));
        //logger.info(JsonUtil.getJsonString(redisMapper.zRange("pace_sort", "oWyy-4iI5U726AW4T-1dUcx2Myrs")));
        logger.info(JsonUtil.getJsonString(redisMapper.zScore("oWyy-4jgNbxrNfUqQybLKuEQ8UrY")));
        paceFzService.init1();
    }



    @GetMapping("/test1")
    public RestData test1(@RequestParam(value = "openId") String openId) {
        return paceService.selectPace(openId);
    }

    @GetMapping("/test2")
    public String test2() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet("https://www.nefu.edu.cn/index/tzgg/337.htm");
        request.setHeader("Accept", "text/html, */*; q=0.01");
        request.setHeader("Accept-Encoding", "gzip, deflate,sdch");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.setHeader("Connection", "keep-alive");
        //设置请求头，将爬虫伪装成浏览器
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36)");

        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String s = EntityUtils.toString(httpEntity, "utf-8");
                String[] htmls = s.split("\n");
                System.out.println(s);
                System.out.println("htmls.length : " + htmls.length);
                StringBuffer html = new StringBuffer();
                for (int i = 550; i < 674; i++) {
                    html.append(htmls[i]);
                }
                System.out.println(JsonUtil.getJsonString(html));
                //6.Jsoup解析html
                Document document = Jsoup.parse(String.valueOf(html));
                //像js一样，通过标签获取title
                //System.out.println(document.getElementsByTag("title").first());
                //像js一样，通过id 获取文章列表元素对象
               // Element postList = document.getElementById("post_list");

                Elements lis = document.getElementsByTag("li");

                for (Element e : lis) {
                    String p = e.getElementsByTag("p").text();
                    System.out.println(p);
                    if (p.contains("暖气") || p.contains("停暖") || p.contains("暖气上水") || p.contains("供暖") || p.contains("暖")) {

                    }
                    if (p.contains("停水") || p.contains("停电")) {

                    }
                    if (p.contains("图书馆") && (p.contains("闭馆") || p.contains("开放") || p.contains("开馆") || p.contains("开放"))) {

                    }

                }
                //像js一样，通过class 获取列表下的所有博客
                //Elements postItems = postList.getElementsByClass("post_item");
                //Elements postItems = document.getElementsByClass("list-nr");
                //Elements listnr = document.getElementsByClass("list-nr");
                //Elements uls = listnr.getElementsByTag("ul");

                return String.valueOf(html);
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
                return "error";
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }

}
