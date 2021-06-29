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

    public static class WMS_AT_TYPE{
        public static final String SAT="S";
        public static final String AAT="A";
        public static final String PAT="P";
    }
    public static class DT_CODE{
        public static final String CG="11";
        public static final String SC_TL="13";
        public static final String QT="15";
        public static final String TL="99";
    }
    public static class FIX_STATE{
        public static final String ZK="1";
        public static final String JC="2";
        public static final String BF="3";
        public static final String BY="4";
    }
    public static class TYPE_CODE{
        public static final String GCZJ="A";
        public static final String GCSB="B";
        public static final String GZZJ="C";
        public static final String GZBP="D";
        public static final String GW="E";
        public static final String LQ="F";
        public static final String QT="Z";
    }
    public static class FIELD_NAME{
        public static final String PROJECT_ID="工单号";
        public static final String MODEL_CODE="机型代码";
        public static final String BATCH="批次号";
        public static final String VER="版本";
        public static final String SUPPLIER_CODE="供应商";
        public static final String DC="时间";
    }

}
