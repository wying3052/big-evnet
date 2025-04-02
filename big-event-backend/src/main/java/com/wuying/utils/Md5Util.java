package com.wuying.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    /**
     * 默认的密码字符串组合，用来将字节转换成16进制表示的字符。
     * Apache校验下载文件的正确性用的就是默认的这个组合。
     */
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messagedigest = null;

    /**
     * 静态初始化块，用于初始化MessageDigest实例。
     * 如果MD5算法不可用，将打印错误信息并抛出异常。
     */
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(Md5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }

    /**
     * 生成字符串的MD5校验值。
     *
     * @param s 需要生成MD5值的字符串
     * @return 返回字符串的MD5校验值，以16进制字符串形式表示
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    /**
     * 判断字符串的MD5校验码是否与一个已知的MD5码相匹配。
     *
     * @param password  需要校验的字符串
     * @param md5PwdStr 已知的MD5校验码
     * @return 如果字符串的MD5值与已知的MD5码匹配，则返回true；否则返回false
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }


    /**
     * 生成字节数组的MD5校验值。
     *
     * @param bytes 需要生成MD5值的字节数组
     * @return 返回字节数组的MD5校验值，以16进制字符串形式表示
     */
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }


    /**
     * 将字节数组转换为16进制字符串。
     *
     * @param bytes 需要转换的字节数组
     * @return 返回字节数组的16进制字符串表示
     */
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }


    /**
     * 将字节数组的指定部分转换为16进制字符串。
     *
     * @param bytes 需要转换的字节数组
     * @param m     起始位置
     * @param n     转换的字节长度
     * @return 返回指定部分的16进制字符串表示
     */
    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * 将单个字节转换为16进制字符并追加到字符串缓冲区中。
     *
     * @param bt           需要转换的字节
     * @param stringbuffer 用于存储16进制字符的字符串缓冲区
     */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

}
