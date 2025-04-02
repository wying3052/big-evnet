package com.wuying.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;

/**
 * AliOSSUtil类用于与阿里云OSS进行交互，提供文件上传功能
 */
public class AliOSSUtil {
    // 定义常量：存储桶名称
    @Value("${aliyun.oss.bucket-name}")
    private static String BUCKET_NAME;
    // 定义常量：OSS服务Endpoint
    @Value("${aliyun.oss.endpoint}")
    private static String ENDPOINT;
    // 定义常量：阿里云Access Key ID
    @Value("${aliyun.oss.access-key-id}")
    private static String ACCESS_KEY_ID;
    // 定义常量：阿里云Access Key Secret
    @Value("${aliyun.oss.access-key-secret}")
    private static String ACCESS_KEY_SECRET;
    // 创建并初始化凭证提供者
    private static DefaultCredentialProvider CREDENTIAL_PROVIDER =
            CredentialsProviderFactory.newDefaultCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    // 定义常量：OSS服务所在区域
    @Value("${aliyun.oss.region}")
    private static String REGION;

    /**
     * 上传文件到阿里云OSS
     *
     * @param fileName    文件名，用于在OSS中标识文件
     * @param inputStream 文件输入流，用于读取文件内容进行上传
     * @return 返回上传成功后的文件访问URL
     * @throws Exception 如果上传过程中发生错误则抛出异常
     */
    public static String upload(String fileName, InputStream inputStream) throws Exception {
        OSS ossClient = null;
        try {
            // 创建OSSClient实例
            ClientBuilderConfiguration config = new ClientBuilderConfiguration();
            config.setSignatureVersion(SignVersion.V4);

            ossClient = OSSClientBuilder.create()
                    .endpoint(ENDPOINT)
                    .credentialsProvider(CREDENTIAL_PROVIDER)
                    .clientConfiguration(config)
                    .region(REGION)
                    .build();

            // 上传文件流
            ossClient.putObject(BUCKET_NAME, fileName, inputStream);

            // 生成访问URL
            return "https://" + BUCKET_NAME + "." + ENDPOINT.substring(ENDPOINT.lastIndexOf("/") + 1) + "/" + fileName;
        } catch (OSSException oe) {
            // 处理OSS服务异常
            throw new RuntimeException("OSS服务异常: " + oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            // 处理OSS客户端异常
            throw new RuntimeException("OSS客户端异常: " + ce.getMessage(), ce);
        } finally {
            // 释放资源
            if (ossClient != null) {
                ossClient.shutdown();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}