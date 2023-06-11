package com.atguigu.ssyx.product.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.ssyx.product.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/11 11:28
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {


    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    @Value("${aliyun.endpoint}")
    private String endpoint;
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    @Value("${aliyun.keyid}")
    private String accessKeyId;
    @Value("${aliyun.keysecret}")
    private String accessKeySecret;
    // 填写Bucket名称，例如examplebucket。
    @Value("${aliyun.bucketname}")
    private String bucketName;

    /**
     *! 上传文件
     * @param file 本地上传的文件
     * @return 返回图片的url
     */
    @Override
    public String fileUpload(MultipartFile file) {
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath= "D:\\localpath\\examplefile.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 得到文件的输入流
            InputStream inputStream = file.getInputStream();
            // 创建PutObjectRequest对象。
            String objectName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            objectName =uuid+objectName;

            //* 根据当前年月日对文件进行分组
            String currentDateTime = new DateTime().toString("yyyy/MM/dd");
            objectName = currentDateTime+"/"+objectName;

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 创建PutObject请求。
            putObjectRequest.setProcess("true");
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            System.out.println(result.getResponse().getStatusCode());
            System.out.println(result.getResponse().getUri());
            System.out.println(result.getResponse().getErrorResponseAsString());

            String url = result.getResponse().getUri();
            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
