package com.ebeijia.util.common;


import java.security.MessageDigest;

/*
'============================================================================
'api说明：
'createSHA1Sign创建签名SHA1
'getSha1()Sha1签名
'============================================================================
'*/
public class Sha1Util {


    //获取时间戳
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

 private final int[] abcde = {
            0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476, 0xc3d2e1f0
        };
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    public static void main(String[] args) {
//        String data = "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com";
//        System.out.println(data);
//        String digest = Sha1Util.encode(data);
//        System.out.println(digest);
//
//    }
}
