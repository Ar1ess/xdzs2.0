package com.softlab.provider;

import com.softlab.common.util.ExecuteResult;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
public class QiniuUtil {

    private static Logger logger = LoggerFactory.getLogger(QiniuUtil.class);

    private static String bucketName = "xdzsnew";
    private static String domain = "http://www.ar1es.cn/";
    private static String ACCESS_KEY = "AdgNN6BnHLoVbZ-mTNKIPoBiNxz93lKMFRhub6Hy";
    private static String SECRET_KEY = "RWEpsIojO1R7MSzLz3sth7bL2yNiHsIX7nIoCm3d";


    /**
     * 通过文件路径上传文件,是本地文件
     */

    public ExecuteResult<String> uploadFile(String localFile) throws AuthException, JSONException {


        File file = new File(localFile);

/**
 * 文件后缀名 文件扩展名
 */

        String filenameExtension = localFile.substring(localFile.lastIndexOf("."), localFile.length());
        //调用下面的方法
        return uploadFile(file, filenameExtension);
    }

    //通过File上传
    public ExecuteResult<String> uploadFile(File file, String filenameExtension) throws AuthException, JSONException {

        ExecuteResult<String> executeResult = new ExecuteResult<String>();
        //获取上传凭证
        String uptoken = getUpToken();

        // 可选的上传选项，具体说明请参见使用手册。
        PutExtra extra = new PutExtra();

        SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd");
        // 上传文件
        PutRet ret = IoApi.putFile(uptoken, time.format(new Date()) + "/" + UUID.randomUUID() + filenameExtension, file.getAbsolutePath(), extra);

        if (ret.ok()) {
            executeResult.setSuccessMessage("上传成功!");
            executeResult.setResult(domain + ret.getKey());
        } else {
            executeResult.addErrorMessage("上传失败");
        }
        return executeResult;
    }


    /**
     * 从 inputstream 中写入七牛
     *
     * @param key     文件名
     * @param content 要写入的内容
     * @return
     * @throws AuthException
     * @throws JSONException
     */

    public boolean uploadFile(String key, String content) throws AuthException, JSONException {
        // 读取的时候按的二进制，所以这里要同一
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

        String uptoken = getUpToken();

        // 可选的上传选项，具体说明请参见使用手册。
        PutExtra extra = new PutExtra();

        // 上传文件
        PutRet ret = IoApi.Put(uptoken, key, inputStream, extra);

        if (ret.ok()) {
            return true;
        } else {
            return false;
        }
    }

    //获得下载地址
    public String getDownloadFileUrl(String filename) throws Exception {
        Mac mac = getMac();
        String baseUrl = URLUtils.makeBaseUrl(domain, filename);
        GetPolicy getPolicy = new GetPolicy();
        String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
        return downloadUrl;
    }

    //删除文件
    public void deleteFile(String filename) {
        Mac mac = getMac();
        RSClient client = new RSClient(mac);
        client.delete(domain, filename);
    }

    //获取凭证
    private String getUpToken() throws AuthException, JSONException, com.qiniu.api.auth.AuthException, org.json.JSONException {
        Mac mac = getMac();
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);
        return uptoken;
    }

    private Mac getMac() {
        Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
        return mac;
    }
}
