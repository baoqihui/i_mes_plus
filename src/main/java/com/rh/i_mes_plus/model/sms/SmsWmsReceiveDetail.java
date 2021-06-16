package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 入库单明细表
 *
 * @author hqb
 * @date 2020-10-08 10:44:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_receive_detail")
public class SmsWmsReceiveDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "入库单号")
        @ApiModelProperty(value = "入库单号")
        private String wrDocNum;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String coItemCode;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料名称")
        private String coItemName;
        @Excel(name = "计划数量")
        @ApiModelProperty(value = "计划数量")
        private Long planNum;
        @Excel(name = "实收数量")
        @ApiModelProperty(value = "实收数量")
        private Long receiveNum;
        @Excel(name = "ERP单据类型")
        @ApiModelProperty(value = "ERP单据类型")
        private String erpDocType;
        @Excel(name = "采购单号")
        @ApiModelProperty(value = "采购单号")
        private String erpPo;
        @Excel(name = "项次")
        @ApiModelProperty(value = "项次")
        private Long erpPoItemNo;
        @Excel(name = "采购交货日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "采购交货日期")
        private Date erpPoDate;
        @Excel(name = "检验状态（0=未检、1=良品、2=不良品、3=特采）")
        @ApiModelProperty(value = "检验状态（0=未检、1=良品、2=不良品、3=特采）")
        private String qcFlag;
        @Excel(name = "仓库代码")
        @ApiModelProperty(value = "仓库代码")
        private String whCode;
        @Excel(name = "",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "")
        private Date lastupdate;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Long didTmp;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "销售订单号")
        @ApiModelProperty(value = "销售订单号")
        private String saleOrder;
        @Excel(name = "赠品数量")
        @ApiModelProperty(value = "赠品数量")
        private Long giftNum;
        @Excel(name = "采购订单量")
        @ApiModelProperty(value = "采购订单量")
        private Long orderNum;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "打印数量")
        @ApiModelProperty(value = "打印数量")
        private Long printNum;
        @Excel(name = "最小包装量")
        @ApiModelProperty(value = "最小包装量")
        private Long itemMinPack;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
        @Excel(name = "制造商批号 LOT")
        @ApiModelProperty(value = "制造商批号 LOT")
        private String tblManufacturerBat;
        @Excel(name = "制造商生产日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "制造商生产日期")
        private Date tblManufacturerDate;
}
