package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 物料信息（sap获取）
 *
 * @author hqb
 * @date 2020-09-25 10:19:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_item_sap")
public class UmsItemSap extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String coItemCode;
        @Excel(name = "品名")
        @ApiModelProperty(value = "品名")
        private String coItemName;
        @Excel(name = "物料等级")
        @ApiModelProperty(value = "物料等级")
        private String coItemSpec;
        @Excel(name = "物料类型（等同于ERP材料类型）")
        @ApiModelProperty(value = "物料类型（等同于ERP材料类型）")
        private String itemSourcecode;
        @Excel(name = "物料类型（a smt料 b 其他） 默认a(不用) ")
        @ApiModelProperty(value = "物料类型（a smt料 b 其他） 默认a(不用) ")
        private String coItemType;
        @Excel(name = "物料来源类型(外购/自制)(平板）")
        @ApiModelProperty(value = "物料来源类型(外购/自制)(平板）")
        private String itemSrctype;
        @Excel(name = "客户编号")
        @ApiModelProperty(value = "客户编号")
        private String custCode;
        @Excel(name = "供应商编号")
        @ApiModelProperty(value = "供应商编号")
        private String supplierCode;
        @Excel(name = "单位DID")
        @ApiModelProperty(value = "单位DID")
        private Long cuDid;
        @Excel(name = "单位组代码")
        @ApiModelProperty(value = "单位组代码")
        private Long pmCode;
        @Excel(name = "批次规则代码")
        @ApiModelProperty(value = "批次规则代码")
        private String trCode;
        @Excel(name = "休积")
        @ApiModelProperty(value = "休积")
        private Long ioItemBulk;
        @Excel(name = "体积单位DID")
        @ApiModelProperty(value = "体积单位DID")
        private Long ioItemBulkUnit;
        @Excel(name = "重量")
        @ApiModelProperty(value = "重量")
        private Long ioItemWeigh;
        @Excel(name = "重量单位DID")
        @ApiModelProperty(value = "重量单位DID")
        private Long ioItemWeighUnit;
        @Excel(name = "安全库存")
        @ApiModelProperty(value = "安全库存")
        private Long ioItemMinStock;
        @Excel(name = "最大安全库存")
        @ApiModelProperty(value = "最大安全库存")
        private Long ioItemMaxStock;
        @Excel(name = "存货基本单价")
        @ApiModelProperty(value = "存货基本单价")
        private Long ioItemBasePrice;
        @Excel(name = "有效期")
        @ApiModelProperty(value = "有效期")
        private Long ioItemAvail;
        @Excel(name = "有效期单位")
        @ApiModelProperty(value = "有效期单位")
        private Long ioItemAvailUnit;
        @Excel(name = "最小包装量")
        @ApiModelProperty(value = "最小包装量")
        private Long ioItemMinPack;
        @Excel(name = "连板数")
        @ApiModelProperty(value = "连板数")
        private Long connectPlankNum;
        @Excel(name = "建档人")
        @ApiModelProperty(value = "建档人")
        private String createName;
        @Excel(name = "建档时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "建档时间")
        private Date createDate;
        @Excel(name = "修改人")
        @ApiModelProperty(value = "修改人")
        private String modifyName;
        @Excel(name = "修改时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "修改时间")
        private Date modifyDate;
        @Excel(name = "最后存放库位")
        @ApiModelProperty(value = "最后存放库位")
        private String ioItemStockBitLast;
        @Excel(name = "传票号管控(Y/N)")
        @ApiModelProperty(value = "传票号管控(Y/N)")
        private String ioItemCLot;
        @Excel(name = "超领管控(Y/N)")
        @ApiModelProperty(value = "超领管控(Y/N)")
        private String ioItemMoreControl;
        @Excel(name = "阻容上限值")
        @ApiModelProperty(value = "阻容上限值")
        private Long itemValueB;
        @Excel(name = "阻容下限值")
        @ApiModelProperty(value = "阻容下限值")
        private Long itemValueT;
        @Excel(name = "阻容单位")
        @ApiModelProperty(value = "阻容单位")
        private String itemValueUnit;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String ioItemCBcode;
        @Excel(name = "金额")
        @ApiModelProperty(value = "金额")
        private String ioItemAmount;
        @Excel(name = "物料占比")
        @ApiModelProperty(value = "物料占比")
        private String ioItemMaterialRatio;
        @Excel(name = "SMT占比")
        @ApiModelProperty(value = "SMT占比")
        private String ioItemSmtRatio;
        @Excel(name = "PTH占比")
        @ApiModelProperty(value = "PTH占比")
        private String ioItemPthRatio;
        @Excel(name = "物料条码规则")
        @ApiModelProperty(value = "物料条码规则")
        private String ioLotRule;
        @Excel(name = "大板条码规则")
        @ApiModelProperty(value = "大板条码规则")
        private String ioSingleRule;
        @Excel(name = "物料来源(1:自采、2:客供、3：自产、4：自用、5：特殊)")
        @ApiModelProperty(value = "物料来源(1:自采、2:客供、3：自产、4：自用、5：特殊)")
        private Long ioItemSource;
        @Excel(name = "栈板容量")
        @ApiModelProperty(value = "栈板容量")
        private Long loPalletCapacity;
        @Excel(name = "条码拼数")
        @ApiModelProperty(value = "条码拼数")
        private Long itemJigsawNumber;
        @Excel(name = "包装规则")
        @ApiModelProperty(value = "包装规则")
        private Long itemPackagingRule;
        @Excel(name = "物料备注")
        @ApiModelProperty(value = "物料备注")
        private String itemRemark;
        @Excel(name = "标签简码")
        @ApiModelProperty(value = "标签简码")
        private String ioLabelAbbreviation;
        @Excel(name = "WAVE 焊点数")
        @ApiModelProperty(value = "WAVE 焊点数")
        private Long waveSolderJoints;
        @Excel(name = "H/S 焊点数")
        @ApiModelProperty(value = "H/S 焊点数")
        private Long hsSolderJoints;
        @Excel(name = "物料大类1:PCB 2:PCBA 3:原材料")
        @ApiModelProperty(value = "物料大类1:PCB 2:PCBA 3:原材料")
        private Long ioMajorCategories;
        @Excel(name = "小板条码规则")
        @ApiModelProperty(value = "小板条码规则")
        private Long ioSmallSingleRule;
        @Excel(name = "烧入程序名")
        @ApiModelProperty(value = "烧入程序名")
        private String ioBurninName;
        @Excel(name = "产品标准重量(含箱)")
        @ApiModelProperty(value = "产品标准重量(含箱)")
        private Long ioStandardWeights;
        @Excel(name = "满箱重量(含箱)")
        @ApiModelProperty(value = "满箱重量(含箱)")
        private Long ioFullWeights;
        @Excel(name = "高温时间(小时)")
        @ApiModelProperty(value = "高温时间(小时)")
        private Long coAgingTime;
        @Excel(name = "客户料号")
        @ApiModelProperty(value = "客户料号")
        private String coCustomerItem;
        @Excel(name = "库存周期(天)")
        @ApiModelProperty(value = "库存周期(天)")
        private Long coStockCycle;
        @Excel(name = "周期提醒提前天")
        @ApiModelProperty(value = "周期提醒提前天")
        private Long coCycleRemind;
        @Excel(name = "FQC自动送检(Y/N)")
        @ApiModelProperty(value = "FQC自动送检(Y/N)")
        private String ioAutoFqc;
        @Excel(name = "送检批量")
        @ApiModelProperty(value = "送检批量")
        private Long ioFqcQty;
        @Excel(name = "有效期")
        @ApiModelProperty(value = "有效期")
        private Long coNameplate;
        @Excel(name = "最大回流次数")
        @ApiModelProperty(value = "最大回流次数")
        private Long coReviewTime;
        @Excel(name = "不良直接报废标志Y/N")
        @ApiModelProperty(value = "不良直接报废标志Y/N")
        private String ioItemScrapFlag;
        @Excel(name = "物料描述（平板）")
        @ApiModelProperty(value = "物料描述（平板）")
        private String coItemDesc;
        @Excel(name = "单位（平板）")
        @ApiModelProperty(value = "单位（平板）")
        private String coItemUnit;
        @Excel(name = "键盘类型（shopfloor接口用）")
        @ApiModelProperty(value = "键盘类型（shopfloor接口用）")
        private String keyboardType;
        @Excel(name = "封样测试标志(Y/N)")
        @ApiModelProperty(value = "封样测试标志(Y/N)")
        private String itemValueFlag;
        @Excel(name = "容阻类型(0:电容1：电阻 2 电感)")
        @ApiModelProperty(value = "容阻类型(0:电容1：电阻 2 电感)")
        private String itemValueType;
        @Excel(name = "阻容值")
        @ApiModelProperty(value = "阻容值")
        private Long itemValue;
        @Excel(name = "工质(东贝)")
        @ApiModelProperty(value = "工质(东贝)")
        private String coWorkingFluid;
        @Excel(name = "免检标志(Y是N否 )")
        @ApiModelProperty(value = "免检标志(Y是N否 )")
        private String qcFlag;
        @Excel(name = "基本计量单位()")
        @ApiModelProperty(value = "基本计量单位()")
        private String coUnit;
        @Excel(name = "默认仓库")
        @ApiModelProperty(value = "默认仓库")
        private String defaultWarehouse;
        @Excel(name = "默认库位")
        @ApiModelProperty(value = "默认库位")
        private String defaultArea;
        @Excel(name = "检验组")
        @ApiModelProperty(value = "检验组")
        private String ogName;
        @Excel(name = "仓库组")
        @ApiModelProperty(value = "仓库组")
        private String ggName;
        @Excel(name = "分选标志(Y是N否 )")
        @ApiModelProperty(value = "分选标志(Y是N否 )")
        private String sortFlag;
        @Excel(name = "恒温时间(小时)")
        @ApiModelProperty(value = "恒温时间(小时)")
        private Long thermostaticHours;
        @Excel(name = "标识色有效期(天 )")
        @ApiModelProperty(value = "标识色有效期(天 )")
        private Long colorValidDays;
        @Excel(name = "禁用标识(Y是N否)")
        @ApiModelProperty(value = "禁用标识(Y是N否)")
        private String forbidFlag;
        @Excel(name = "VMI标志")
        @ApiModelProperty(value = "VMI标志")
        private String skVmi;
        @Excel(name = "管理方式")
        @ApiModelProperty(value = "管理方式")
        private String mgType;
        @Excel(name = "IQC二检天数")
        @ApiModelProperty(value = "IQC二检天数")
        private Long secondIqcDays;
        @Excel(name = "MSD标识(Y是N否)")
        @ApiModelProperty(value = "MSD标识(Y是N否)")
        private String coIsMsd;
        @Excel(name = "湿敏等级")
        @ApiModelProperty(value = "湿敏等级")
        private String mcrMsdCode;
        @Excel(name = "顶级包装容量")
        @ApiModelProperty(value = "顶级包装容量")
        private Long topPackQty;
        @Excel(name = "上级包装容量")
        @ApiModelProperty(value = "上级包装容量")
        private Long upperPackQty;
        @Excel(name = "物料检验分类")
        @ApiModelProperty(value = "物料检验分类")
        private String iqcItemSourcecode;
        @Excel(name = "仓管员工号")
        @ApiModelProperty(value = "仓管员工号")
        private String stockManagerNo;
        @Excel(name = "ERP标准人数")
        @ApiModelProperty(value = "ERP标准人数")
        private String erpStandPersons;
        @Excel(name = "ERP标准产能")
        @ApiModelProperty(value = "ERP标准产能")
        private String erpStandUphNumber;
        @Excel(name = "ERP标准工时")
        @ApiModelProperty(value = "ERP标准工时")
        private String erpStandHours;
        @Excel(name = "是否同步到标准工时表-")
        @ApiModelProperty(value = "是否同步到标准工时表-")
        private String itemToSlt;
        @Excel(name = "OSP管控小时数   0小时该物料为不进行OSP管控")
        @ApiModelProperty(value = "OSP管控小时数   0小时该物料为不进行OSP管控")
        private String ospItemCode;
        @Excel(name = "单位")
        @ApiModelProperty(value = "单位")
        private String cuName;
        @Excel(name = "贵重物料(y-是 n-否)")
        @ApiModelProperty(value = "贵重物料(y-是 n-否)")
        private String batchFlag;
        @Excel(name = "mpn")
        @ApiModelProperty(value = "mpn")
        private String mpn;
        @Excel(name = "制造商")
        @ApiModelProperty(value = "制造商")
        private String manufacturerCode;
        @Excel(name = "项目号")
        @ApiModelProperty(value = "项目号")
        private String projectCode;
        @Excel(name = "检验周期（天）")
        @ApiModelProperty(value = "检验周期（天）")
        private String shLife;
        @Excel(name = "PCB工艺")
        @ApiModelProperty(value = "PCB工艺")
        private String alternativeItem;
        @Excel(name = "最小订单量")
        @ApiModelProperty(value = "最小订单量")
        private Long ioItemMinOrder;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String ioItemLevel;
        @Excel(name = "物料组")
        @ApiModelProperty(value = "物料组")
        private String ownedGroup;
        @Excel(name = "SAP物料代码")
        @ApiModelProperty(value = "SAP物料代码")
        private String sapItemCode;
        @Excel(name = "是否烧录物料")
        @ApiModelProperty(value = "是否烧录物料")
        private String isBurning;
        @Excel(name = "货币单位")
        @ApiModelProperty(value = "货币单位")
        private String currency;
        @Excel(name = "价格")
        @ApiModelProperty(value = "价格")
        private String price;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
