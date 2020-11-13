package top.zhouy.util.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

import java.io.InputStream;

/**
 * @description: 阿里Oss文件上传
 * @author: zhouy
 * @create: 2020-11-12 18:20:00
 */
public class OssUtils {

    private static String endpoint = "oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAI4GJxbx8zBLxtNxa86YYq";
    private static String accessKeySecret = "8m7grgPIuXL6VjnzhKhSrZUcnCLNrv";
    private static String bucketName = "zhouyoss";

    /**
     * 上传文件
     * @param inputStream
     * @param fileName
     * @return
     */
    public static String uploadFile(InputStream inputStream, String fileName){
        String url = "";
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            String fileKey = fileName;
            ossClient.putObject(bucketName, fileKey, inputStream);
            url = "https://zhouyoss.oss-cn-shanghai.aliyuncs.com/" + fileKey;
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return url;
    }

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public static Boolean delete(String fileName){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            String fileKey = fileName;
            ossClient.deleteObject(bucketName, fileKey);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return true;
    }

    /**
     * 查找文件是否存在
     * @param fileName
     * @return
     */
    public static Boolean isExist(String fileName){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        boolean flag = false;
        try {
            String fileKey = fileName;
            flag = ossClient.doesObjectExist(bucketName, fileKey);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return flag;
    }

}
