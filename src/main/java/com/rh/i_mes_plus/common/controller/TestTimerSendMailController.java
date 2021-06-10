package com.rh.i_mes_plus.common.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpUtil;
import com.rh.i_mes_plus.common.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author: hbq
 * @Description: 定时发送邮件测试
 * @Date: 2021/3/26 15:13
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "测试定时发送邮件")
@RequestMapping("ums")
public class TestTimerSendMailController {
    /**
     * 示例
     *每隔5秒执行一次：*\/5****?
     *每隔1分钟执行一次：0*\/1***?
     *每天23点执行一次：0 0 23**?
     *每天凌晨1点执行一次：0 0 1**?
     *每月1号凌晨1点执行一次：0 0 1 1*?
     *每月最后一天23点执行一次：0 0 23 L *?
     *每周星期六凌晨1点实行一次：0 0 1?*L
     *在26分、29 分、33 分执行一次：0 26,29,33***?
     *每天的0点、13 点、18 点、21 点都执行一次：0 0 0,13,18,21**?
     *  [秒] [分] [小时] [日] [月] [周] [年]
     * 序号 	说明 	必填 	允许填写的值 	    允许的通配符
     * 1 	秒 	    是 	    0-59 	        , - * /
     * 2 	分 	    是 	    0-59 	        , - * /
     * 3 	时 	    是 	    0-23 	        , - * /
     * 4 	日 	    是 	    1-31 	        , - * ? / L W
     * 5 	月 	    是 	    1-12/JAN-DEC 	, - * /
     * 6 	周 	    是 	    1-7 or SUN-SAT 	, - * ? / L #
     * 7 	年 	    否 	    1970-2099 	    , - * /
     *
     * */
    @Scheduled(cron ="0 0 0 26-31 5 *")
    @ApiOperation(value = "发送邮件测试")
    @PostMapping("/mail/send")
    public Result send( ){
        ArrayList<String> tos = CollUtil.newArrayList(
                "1847045298@qq.com"
                );
        Date date = DateUtil.parseDate("2021-06-03 00:00:00");
        long day = DateUtil.between(new Date(), date, DateUnit.DAY);
        String oneS ="大哥您好！温馨提醒：你的回爸爸距生日还有"+day+"天;请您及时准备祝福或礼物！" +
                "<br> 此外：你将会在6月3日之前的每天晚上0点收到此邮件(￣３￣)a  爱您！" +
                "<br> <img src='http://img.huijia21.com/blog/1622021097878.png'";
        String s = HttpUtil.get("https://chp.shadiao.app/api.php");
        System.out.println(oneS);
        MailUtil.send(tos, "大哥您好！", oneS, true);
        return Result.succeed("保存成功");
    }
}
