package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 烧录文件管理
 *
 * @author hbq
 * @date 2020-12-02 09:14:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("fms_burn_file")
public class FmsBurnFile extends SuperEntity {
    private static final long serialVersionUID=1L;
    
    @Excel(name = "ecn号")
    @ApiModelProperty(value = "ecn号")
    private String ecnNo;
    @Excel(name = "烧录前料号")
    @ApiModelProperty(value = "烧录前料号")
    private String beforeItemCode;
    @Excel(name = "烧录后料号")
    @ApiModelProperty(value = "烧录后料号")
    private String afterItemCode;
    @Excel(name = "上传人")
    @ApiModelProperty(value = "上传人")
    private String createName;
    @Excel(name = "文件路径")
    @ApiModelProperty(value = "文件路径")
    private String filePath;
    @Excel(name = "机型名称")
    @ApiModelProperty(value = "机型名称")
    private String modelName;
    @Excel(name = "ic脚位")
    @ApiModelProperty(value = "ic脚位")
    private String ic;
    @Excel(name = "变更前版本")
    @ApiModelProperty(value = "变更前版本")
    private String verBefore;
    @Excel(name = "变更后版本")
    @ApiModelProperty(value = "变更后版本")
    private String verAfter;
    
}
