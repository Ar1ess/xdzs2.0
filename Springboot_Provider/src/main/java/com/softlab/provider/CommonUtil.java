package com.softlab.provider;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
public class CommonUtil {
    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */

    public File multipartToFile(MultipartFile multfile) {
        File files = null;
        if (null != multfile) {
            File dfile = null;
            try {
                dfile = File.createTempFile("prefix", "_" + multfile.getOriginalFilename());
                multfile.transferTo(dfile);
                files = dfile;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;


    }
}
