package com.rh.i_mes_plus.util.poiUtil;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PoiReportImgUtil {
    /**
     *
     * @param workbook 工作薄
     * @param patriarch 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
     * @param imgUrl 图片网络地址
     * @param c1 起点列
     * @param r1 起点行
     * @param c2 终点列
     * @param r2 终点行
     * @throws Exception
     */
    public static void reportExeclImg(HSSFWorkbook workbook, HSSFPatriarch patriarch, String imgUrl, short c1, int r1, short c2, int r2) throws Exception {
        byte[] data = {};
        URL url = new URL(imgUrl);
        //打开连接
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        data = readInputStream(inStream);
        //anchor主要用于设置图片的属性
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, c1, r1, c2, r2);
        anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
        //插入图片
        patriarch.createPicture(anchor, workbook.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
