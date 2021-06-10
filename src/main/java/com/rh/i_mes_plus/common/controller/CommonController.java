package com.rh.i_mes_plus.common.controller;

import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.util.BufferImage;
import com.rh.i_mes_plus.util.OssUploadImage;
import com.rh.i_mes_plus.util.ToolNote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@Api(tags = "公共方法")
public class CommonController {
    @Autowired
    private OssUploadImage ossUploadImage;


    /**
     * 上传图片到 oss
     * @param imgFile
     * @return
     */
    @ApiOperation(value = "上传图片到oss")
    @PostMapping(value="/uploadImgToOSS")
    public String uploadImgToOSS(MultipartFile imgFile) {
        return ossUploadImage.uploadImg(imgFile);
    }
    /**
     * 发送短信
     * @param tel   手机号
     * @param session   设置验证码过期时间
     * @return
     */
    @ApiOperation(value = "发送短信")
    @PostMapping(value="/sendMessage")
    public Result sendMessage(String tel, HttpSession session){
        System.out.println(tel);
        if(tel==null){
            return Result.failed("请输入手机号");
        }else if(tel.equals("")||tel.length()!=11){
            return  Result.failed("手机号有误");
        }
        Object ytel=session.getAttribute(tel);
        if(tel.equals(ytel)){
            return  Result.failed( "60s内重复发送");
        }
        String code= ToolNote.getNote(tel);
        if(code!=null){
            session.setAttribute("tel", tel);
            session.setMaxInactiveInterval(60);
            session.setAttribute("code", code);
            return  Result.succeed(code,"发送成功");
        }
        return Result.failed("获取出错");
    }
    /**
     * 图片验证码
     * @param req
     * @param res
     * @throws IOException
     */
    @ApiOperation(value = "图片验证码")
    @GetMapping(value = "/BufferImage")
    protected void createImg(HttpServletRequest req, HttpServletResponse res) throws IOException {
        //1.生成随机的验证码及图片
        Object[] objs = BufferImage.createImage();
        //2.将验证码存入session
        String imgcode = (String) objs[0];
        HttpSession session = req.getSession();
        session.setAttribute("checkcode", imgcode);
        //3.将图片输出给浏览器
        BufferedImage img = (BufferedImage) objs[1];
        res.setContentType("image/png");
        //服务器自动创建输出流，目标指向浏览器
        OutputStream os = res.getOutputStream();
        ImageIO.write(img, "png", os);
        os.close();
    }
    @PostMapping(value="/userLogout")
    @ResponseBody
    public Result userLogout(HttpSession session){
        session.removeAttribute("user");
        return Result.succeed("退出成功");
    }
}
