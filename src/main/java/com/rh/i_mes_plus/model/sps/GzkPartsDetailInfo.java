package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工装备品详情信息表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("gzk_parts_detail_info")
public class GzkPartsDetailInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "KGT编号（内部）")
        @ApiModelProperty(value = "KGT编号（内部）")
        private String kgtNo;
        @Excel(name = "编号（外部）")
        @ApiModelProperty(value = "编号（外部）")
        private String sn;
        @Excel(name = "治具大类代码")
        @ApiModelProperty(value = "治具大类代码")
        private String typeCode;
        @Excel(name = "备品类别")
        @ApiModelProperty(value = "备品类别")
        private String itemTypeCode;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String itemCode;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long qty;
        @Excel(name = "存储位置")
        @ApiModelProperty(value = "存储位置")
        private String pos;
        @Excel(name = "库区（量产库/实验库/报废库/生产库）",replace = {"量产库_1", "实验库_2","报废库_3","生产库_4" })
        @ApiModelProperty(value = "库区（量产库/实验库/报废库/生产库）")
        private String loc;
        @Excel(name = "状态（在库/已借出/报废）",replace = {"在库_1", "借出_2","报废_3"})
        @ApiModelProperty(value = "状态（在库/已借出/报废）")
        private String state;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createUser;
}
