package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsBoxDTO;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsBoxBarcodeMapper;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsBoxMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsBox;
import com.rh.i_mes_plus.model.pdt.PdtWmsBoxBarcode;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxBarcodeService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Slf4j
@Service
public class PdtWmsBoxServiceImpl extends ServiceImpl<PdtWmsBoxMapper, PdtWmsBox> implements IPdtWmsBoxService {
    @Resource
    private PdtWmsBoxMapper pdtWmsBoxMapper;
    @Autowired
    private IPdtWmsBoxBarcodeService pdtWmsBoxBarcodeService;
    @Resource
    private PdtWmsBoxBarcodeMapper pdtWmsBoxBarcodeMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<Map> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsBoxMapper.findList(pages, params);
    }
    
    @Override
    public Result getBoxNo(Map<String, Object> map) {
        return Result.succeed(getBoxNoByCode(map), "查询成功");
    }
    
    @Override
    public Result saveAll(PdtWmsBoxDTO pdtWmsBoxDTO) {
        PdtWmsBox pdtWmsBox = pdtWmsBoxDTO;
        Map<String, Object> map=new HashMap<>();
        map.put("code","aaaa");
        String boxNo = getBoxNoByCode(map);
        pdtWmsBox.setBoxNo(boxNo);
        saveOrUpdate(pdtWmsBox);
        /*刪除原有条码绑定*/
        pdtWmsBoxBarcodeService.remove(new QueryWrapper<PdtWmsBoxBarcode>()
                .eq("box_no",pdtWmsBox.getBoxNo())
        );
        List<PdtWmsBoxBarcode> pdtWmsBoxBarcode = pdtWmsBoxDTO.getPdtWmsBoxBarcode();
        pdtWmsBoxBarcode.forEach(u->u.setBoxNo(boxNo));
        pdtWmsBoxBarcodeService.saveBatch(pdtWmsBoxBarcode);

        Map<String,Object> result= MapUtil.toCamelCaseMap(pdtWmsBoxBarcodeMapper.selBoxBarcode(pdtWmsBox.getBoxNo()));
        List<PdtWmsBoxBarcode> boxBarcodes = pdtWmsBoxBarcodeMapper.selectList(new QueryWrapper<PdtWmsBoxBarcode>()
                .eq("box_no", pdtWmsBox.getBoxNo()));
        List<String> tos=boxBarcodes.stream().map(u->u.getBarcode()).collect(Collectors.toList());
        result.put("boxBarcodeInfos",tos);
        return Result.succeed( result,"添加成功");
    }

    @Override
    public Result getBoxInfo(Map<String, Object> params) {
        String boxNo = MapUtil.getStr(params, "boxNo");
        Map<String, Object> stringObjectMap = pdtWmsBoxBarcodeMapper.selBoxBarcode(boxNo);
        if (stringObjectMap==null){
         return Result.failed("无此箱号");
        }
        Map<String,Object> result= MapUtil.toCamelCaseMap(stringObjectMap);
        List<PdtWmsBoxBarcode> boxBarcodes = pdtWmsBoxBarcodeMapper.selectList(new QueryWrapper<PdtWmsBoxBarcode>()
                .eq("box_no", boxNo));

        List<String> tos=boxBarcodes.stream().map(u->u.getBarcode()).collect(Collectors.toList());
        result.put("boxBarcodeInfos",tos);
        return Result.succeed(result);
    }
    public String getBoxNoByCode(Map<String, Object> map) {
        String depaCode = MapUtil.getStr(map, "code");
        String prefix=depaCode + DateUtil.format(new Date(), "yyyyMMdd");
        String maxBoxNo=prefix+"00001";
        PdtWmsBox one = getOne(new QueryWrapper<PdtWmsBox>()
                .last("where box_no like '" + prefix + "%' order by box_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getBoxNo(), prefix));
            maxBoxNo=prefix+String.format("%5d",aLong+1).replace(" ","0");
        }
        return maxBoxNo;
    }
}
