package com.rh.i_mes_plus.util;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class ToolNote {
	
	public static String getNote(String tel){
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FtpgZZ1fCtoM8SBXew2", "oFx9gFmFtEFN6IEjUE7AeqXxL1qZ51");
        IAcsClient client = new DefaultAcsClient(profile);

        String[] codees={"0","1","2","3","4","5","6","7","8","9"};
        String code="";
        for(int i=0;i<6;i++){
        	int j=(int)(Math.random()*10);
        	code+=codees[j];
        }

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", tel);
        request.putQueryParameter("SignName", "IDSE");
        request.putQueryParameter("TemplateCode", "SMS_175540528");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if(response.getData()!=null){
                System.out.println(code);
            	return code;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
	}
}
