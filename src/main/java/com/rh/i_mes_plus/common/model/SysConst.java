package com.rh.i_mes_plus.common.model;

public class SysConst {

    public static final String USER_IFO = "userInfo";
    public static final String SESSION_USER_PERMISSION = "userPermission";

    public static final int YES = 1;
    public static final int NO = 0;
    public static final Boolean BYES = true;
    public static final Boolean BNO = false;
    public static final String DEFAULT_PWD="000000"; //默认密码
    public static class EXESTATE {
        /**
         * -2.已导入(快速关结) -1.弃用
         * 0.新建 1.草稿  2.ECR审核（已提交未审核）
         * 3.ECR审核（已通过，时间未完善） 4.ECR审核（未通过）
         * 5.子任务执行中 6. QA验收（未验收）
         * 7.QA验收(已通过)
         * 8.FAI 9.已导入(关结)
         * */

        public static final Integer QUICK_CLOSE=-2;
        public static final Integer ABANDON=-1;
        public static final Integer NEW=0;          //1
        public static final Integer DRAFT=1;
        public static final Integer SUBMITTED=2;
        public static final Integer APPROVED=3;     //1
        public static final Integer NOT_APPROVED=4;
        public static final Integer SUB_EXECUTION=5;    //1
        public static final Integer QA_ACCEPTING=6;
        public static final Integer QA_ACCEPTED=7;
        public static final Integer FAI=8;
        public static final Integer HAS_DEL=9;
    }
}
