package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102100734715";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCacvetIpNnIdJ7iTUluSt0R2JgO6Q84pKvQEzCusJiq48ZyIOR3RR8eW5zqbfFPWqEsO1eKf+u1j9bQfksANcNYkq5alIwRJhne9LPUUCtRHVP2aPvS4tjLsUNtLUTAcx9883NCF51J+4ogJheeKUUpqkmVQxh0CmHOYDxQPHjCI/RZ/b8l5BfPc2K08KSoZkH3xsZIB71G8ivpL+nsqBZwzymycGXYXR3fJdyHfm7lH2ZyOP7eT0J5eIh1Q8gxGWdIZps5jPxJ0djKTH65Js8+jk/8NjRTTAYUGXLkkzG2/Pjd/9MM/tMX4FEQm/Rtav6ETFnhDf5Mg4CBY4fIAvlAgMBAAECggEAdO5Pp2vC71TC9zZU33+Gb51P1I2y9UmQRlBHDTK50lwSljGhssAPL4H75mN6t0CdMWKGA0GGywZ3pw6WqALmzcI50pC1MsfsHoS3gLACaGuo6VBb9eJa4DdbhtnmMUKuYDlB+VW5tbcKbu2U7gkm6jCXZWcEXdVsa21UutIxj3ERoW9rvW54opiMyP9+y4SOSibFETGEe7jLt7DMUf+KRKZxYRc2ph3pIsWTljcdAxats6Dvvyk4Vh8ZOjj0xUzqFzQS05pDJPKeI94Zp1LXcFE9F1YqqwcXo2UzqacioZoNRFvVP++mW4jVmo6YpGxdOt6hudr43RJP9PU3kWtFAQKBgQDPMuRW65dZC+MSPV/FlEo2CqELCFwFJoz6KeXWeH2PmLIK9Lnc0Vt8GdZsFJTVx/m46rIVDcrs3sN/secoKdkUg2eBhMGDs9yy+Qha1D1TkZTnxwdpZhO6kiPRDzPGS9Anpp49231fuu3EdqZ36Cn9Jpu7Cl/UCyubQ7/CzPLbQQKBgQC+04SFqSYeHFUTKUXni5oi63ey2o/t7ISKy8nr1mMHvqIj8qVbJD5eG6pbaXNZwQhToH1Q1ZoCA/Swo3DnCmSS6NroEtfSvCJdsK76N9OxwO2ZiZylYu3eX5eRjEiV52CF4q/8dMJG6jfQ5cE5at/KQgsoXBNgLbf0zZchoaD7pQKBgGxUpnAPo0TqYyGRNzadTqOssQCMsVf8DO9q4oFc5AElC8mQpHCggV2EQMinhh2z/YvCIbPCT1vDKKHmCP7FynrgLUjZeTf73SMx8z1SBHJiBmGcfPKNbTk52UIf2akO0Fk60zdyP73ChTgX9PdySinSes+zH+FEqdr29BbMgxlBAoGAT3kL8haWF2g5TjecM2iCkxR5z3UTCEW+lYd12tB4gds077+4ehLIHq1q75MlRTYNX7axOh8besdm+sG23aIIbN6pfImMdOmBbn+NAAyMD7WHvq//GynuYvRwzV8dQ8EPA/N606tU1e/puwhUxrLp7PRAbVibgSzJNh0BWjJUNzkCgYEAslieJJHX4dZ5vKUj7+UgrGLon2IHs8MAabCzkzvysUSjHlwEJwNugrh4Aiua4sHo0En0i6blt6QvLwgz6oxYOwZlggcRjV56NFc65iMvSUDHG3awo0z+xLqJeNnZz/wI5F8+MsITIeE13PsDjUMJ1gHW+CzjLpr4WoD37G3FTqI=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnAcDISBY9d3PH73gBzAr4xTUIps6NK6gfLmGqIweBqcjoQo8q49m+Fi8GZvywfhm2Mmdy5L8riFmuSEEO01aYIuFSsaqLbOXGQOxei4vnfyNFhmuA7OwvklOG8ZzJ55shEI8P4LOxce1QjYE04GBojA1H+HKVXPjB9Zmw5jJZFpvIOvCYzSfNUekdB0e575oJR96zMxLUdrp85AH+Jb/NAIWKdtzyOl7BRDFpjzvNofl9ZQger9HPwqH2osp+/p4ZsFrBWJIK4UoUTLVyIVfHvpPfqIzmtkHLZUgaP8jKmUZrFc/hi8gi+dbLzqbmnAIhaLUfeD3tse1ovvKworD/QIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	//public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

