package com.example.carfixsystem.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

public class MessageUtils {
    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public static String sendMessage(String mobile,String apiId,String apiKey) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

        //随机验证码
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);

        //短信内容：注意短信的内容格式 如果是白嫖，那么内容格式不能修改！短信模板
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        // 您的验证码是：【变量】。请不要把验证码泄露给其他人

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", apiId), //查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", apiKey),  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", mobile), //接收验证码的手机号
                new NameValuePair("content", content),
        };

        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult = method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);

            if ("2".equals(code)) {
                System.out.println("短信提交成功");
                return String.valueOf(mobile_code);
            }

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
