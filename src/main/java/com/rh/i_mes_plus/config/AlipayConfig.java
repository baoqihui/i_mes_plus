package com.rh.i_mes_plus.config;
/**
 * @author hbq
 * @createTime 2020/3/19 23:29
 */
public class AlipayConfig {
    // 商户appid
    public static String APPID = "2016101500690876";
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRMwTrh5BHBgicgFLsuKakLU9roz+tEv+rFTyvkaeuRKRDYzRQ/NBpS8SuQDsn66wLYctfNz3Jza+rpFl95G8jAW/maXR00dbCDIwgAc1i8JjzOm/OlU2JzonXGF//39XNrKt1JMxbcoG9TbBGVlBc9IqF9NKlwzvDOMrp/AH4KDryGXcl/KeiB9DBgFdJJ9y2dsIcQOLZ9ev11DhKbhRNMn7i9YiptqRynX8x9HMymGuCQYXZjoZWWTi1eaSY8e7Hh37+uT0IfoYNHgfc8ppJbAQaRWvn3Pcsmfyl2aFX2gj4o1EO4ghXyzUGsz2yLcOCMG/168E+yor8044brxslAgMBAAECggEBAIbzQzxhJ8BLJE/ImMn5KLBoiGtDNBh5EvUAbQHEv3MhywXyuxoWjuF+kVNe0VxeFu4RO4LIhf381KwTuTVfzcdcO5OMKbe1nAOIw+evpSOy+IT/qBD6BTHyq3wl5Hudn0pklIrw45qOx3SZMx98c36hremozOtRlEbyju1/0pi/7MWHIKof+pMICK11JKqKKImW4Kfa9SIxmJ0HUHM1hJLfy6hPEph0ux7AZ/P7cVa6+6h10zO4aGRAYj8ZTPCCZVU9vytObQwFC7faQtyZeyVizhTETcMpkzBfNB/6alVF+Zfw6Uxx67spxaJKtO/f68AYemuSHM56bRxM6QaAOeECgYEAx79WKVfMOincatkqSCzRzlAbVUTKAverruuIBO0wDn/8Qe6uwer/peYnauySiH4UMj0pde4TJlsQLgejWV/3lKIVevKLQUPQNvjAQdGQ+92A4gQoZC+CJjty+w2DwbFV8JdPGXXNfRWXsAr7x6f198Qw9FX2WqxhKK6BqDFR6ZkCgYEAuhcSG0tioLTTpd5kmbJofmx6m/tjRK4q1vinhb39+e5cx72KJgNWb4CmJgFkKfuKBrY/5OBKl4lM+TbTqJKsf8e3mtzPm16pEsCR5Pcmy/j+8YUfKsylC6XOYOo+GdpV/H2LeJkJjG9fFCURLJrvdk53RT1dpp70yTWx/8ps7W0CgYEAvCvxFHOEjoqe/f1lSqcGY3Ctvy9Vi2Qc1WVHNzTm39IMJ2SeN0qV2YivO5hwFquHhSh/JXzJh4mj/WjupqS0jM4ApFlXfH1Q6mh+XcL57XvnDYTTNEZZOoEouX8qz06rYkJ4nsiOc7bitlIyjQ0at3WYlKVitlrMENIMjREhVIkCgYBla4l8wrtAJw1qyyg89BUrQegY9wuFNP84JlD2/TNcceKzIcQXL55+Krli4UM79lGlktKD5RICQNxovs7ggv/srGaOymWDojW3eKiLB4Bnjo80Bn+UDfCtxnISM9ggsvRGJg5LbezcUx6YlhMkIlT8P4uVevp9gwiaWN9oL2TqiQKBgAiaYeJc3gy4oU6aH1d8MKLzNOJKZDVOyvbZ6cYu21gJvgX4VBM/2yRweqVqcmVP1DPtfRpupkH0d5YvM6QYTR+MTYggthwHmkxpLASJRGiJ92d22XPzA/VJBDWWR7MrEJK+Poj6H88N7cACNvkFAHs/Ym+373e4pUZzrbWwLFtI";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.idse.top";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://www.idse.top";
    // 请求网关地址,沙箱是:https://openapi.alipaydev.com/gateway.do
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥(在应用中可以获取)
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnxoj8ib3HmsoTYZvuT/dppTY1ecDZupos+n432cleQ4ndphYt4ZKzn+Y97mTRtCu+7qtyls1/cJ0xHn1WH4+qse2LckJkJWp+G+qsWim7E39ITgldmFGM+6hS/3B+Em0cWhC5WPAr0SklkRrRCkaU/m7nK+QhAzvjl+8vEz2R8vPgy4kTbSTPwa5CC++hUOqu31yZliJibxbIDqgXHNJDniGrzQdh2Apf08Qo67PHwk+zkVatuJHEPrSKgySFQTULnnUliaUHSoC79jTcaSDbHTcxhdyAavm6trd0tV9V7IbgD+1B2XXCtVomUVds32KfTbuGuTBPi+BpVFK2YRb+wIDAQAB";
    // RSA2
    public static String SIGNTYPE = "RSA2";

}
