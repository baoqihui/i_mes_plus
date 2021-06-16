package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 *
 * @author hbq
 * @date 2020-11-06 16:04:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_receive_detail_sub")
public class SmsWmsReceiveDetailSub extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "明细ID")
        @ApiModelProperty(value = "明细ID")
        private Long wrdDid;
        @Excel(name = "收料单号")
        @ApiModelProperty(value = "收料单号")
        private String wrDocNum;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String coItemCode;
        @Excel(name = "物料SN")
        @ApiModelProperty(value = "物料SN")
        private String tblBarcode;
        @Excel(name = "版本号")
        @ApiModelProperty(value = "版本号")
        private String itemVer;
        @Excel(name = "实收数量")
        @ApiModelProperty(value = "实收数量")
        private Long receiveNum;
        @Excel(name = "当前数量")
        @ApiModelProperty(value = "当前数量")
        private Long currentNum;
        @Excel(name = "批次号")
        @ApiModelProperty(value = "批次号")
        private String lotNumber;
        @Excel(name = "货位SN(库位SN)")
        @ApiModelProperty(value = "货位SN(库位SN)")
        private String areaSn;
        @Excel(name = "当前所在区域SN")
        @ApiModelProperty(value = "当前所在区域SN")
        private String currentAreaSn;
        @Excel(name = "标识码")
        @ApiModelProperty(value = "标识码")
        private String wrdsClBcode;
        @Excel(name = "入库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "入库时间")
        private Date wrdsInDate;
        @Excel(name = "制造商批号 LOT")
        @ApiModelProperty(value = "制造商批号 LOT")
        private String tblManufacturerBat;
        @Excel(name = "制造商生产日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "制造商生产日期")
        private Date tblManufacturerDate;
        @Excel(name = "客户传票号")
        @ApiModelProperty(value = "客户传票号")
        private String wrdsTicketNum;
        @Excel(name = "重贴条码标志(Y是N否)，默认否")
        @ApiModelProperty(value = "重贴条码标志(Y是N否)，默认否")
        private String wrdsRepostBar;
        @Excel(name = "领料单物料标志(Y是N否)，默认是")
        @ApiModelProperty(value = "领料单物料标志(Y是N否)，默认是")
        private String wrdsInDoc;
        @Excel(name = "操作人工号")
        @ApiModelProperty(value = "操作人工号")
        private String wrdsEmpNo;
        @Excel(name = "操作人工姓名")
        @ApiModelProperty(value = "操作人工姓名")
        private String wrdsEmpName;
        @Excel(name = "仓库SN")
        @ApiModelProperty(value = "仓库SN")
        private String whCode;
        @Excel(name = "库区SN")
        @ApiModelProperty(value = "库区SN")
        private String reservoirCode;
        @Excel(name = "供应商")
        @ApiModelProperty(value = "供应商")
        private String supplierCode;
        @Excel(name = "退库标识 Y是N否")
        @ApiModelProperty(value = "退库标识 Y是N否")
        private String wrdsReturnFlag;
        @Excel(name = "容器SN")
        @ApiModelProperty(value = "容器SN")
        private String wrdsContainer;
        @Excel(name = "成品下线堆栈批号")
        @ApiModelProperty(value = "成品下线堆栈批号")
        private String wrdsContainerBat;
        @Excel(name = "客户编号")
        @ApiModelProperty(value = "客户编号")
        private String custCode;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "VMI标志")
        @ApiModelProperty(value = "VMI标志")
        private String vmiFlag;
        @Excel(name = "是否回写，Y为是；N/null为否")
        @ApiModelProperty(value = "是否回写，Y为是；N/null为否")
        private String toErp;
        @Excel(name = "栈板SN")
        @ApiModelProperty(value = "栈板SN")
        private String palletSn;
        @Excel(name = "成品完工入库上传标识(0不上传1可以上传)")
        @ApiModelProperty(value = "成品完工入库上传标识(0不上传1可以上传)")
        private String canToErp;
        @Excel(name = "iqc抽检单号")
        @ApiModelProperty(value = "iqc抽检单号")
        private String oqcNo;
        @Excel(name = "是否允收(y/n),挑选时允收才能入库 ")
        @ApiModelProperty(value = "是否允收(y/n),挑选时允收才能入库 ")
        private String osQcResult;
        @Excel(name = "损耗数量 ")
        @ApiModelProperty(value = "损耗数量 ")
        private Long osQtyLoss;
}
