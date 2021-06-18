package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsBoxBarcodeMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsBox;
import com.rh.i_mes_plus.model.pdt.PdtWmsBoxBarcode;
import com.rh.i_mes_plus.model.pdt.PdtWmsStockInfo;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxBarcodeService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsStockInfoService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Slf4j
@Service
public class PdtWmsBoxBarcodeServiceImpl extends ServiceImpl<PdtWmsBoxBarcodeMapper, PdtWmsBoxBarcode> implements IPdtWmsBoxBarcodeService {
    @Resource
    @Lazy
    private PdtWmsBoxBarcodeMapper pdtWmsBoxBarcodeMapper;
    @Autowired
    @Lazy
    private IPdtWmsStockInfoService pdtWmsStockInfoService;
    @Autowired
    @Lazy
    private IPdtWmsBoxService pdtWmsBoxService;
    @Autowired
    @Lazy
    private IUmsUserService umsUserService;
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
        return pdtWmsBoxBarcodeMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result unboxing(Map<String,Object> map) {
        try{
            String boxNo = MapUtil.getStr(map, "boxNo");
            String barcode = MapUtil.getStr(map, "barcode");
            String empNo = MapUtil.getStr(map, "empNo");
            PdtWmsBoxBarcode boxBarcode = getOne(new QueryWrapper<PdtWmsBoxBarcode>()
                    .eq("box_no", boxNo)
                    .eq("barcode", barcode));
            if (boxBarcode == null) {
                return Result.failed("条码信息不符");
            }
            PdtWmsStockInfo stockInfo = pdtWmsStockInfoService.getOne(new QueryWrapper<PdtWmsStockInfo>()
                    .eq("barcode", barcode)
            );
            if (stockInfo != null){
                //存在库存信息不允许拆箱
                return Result.failed("该条码仍在库中，无法拆箱");
            }
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            /* 1.删除绑定关系 */
            /*pdaMesLogService.save(PdaMesLog.builder()
                    .name("拆箱")
                    .context("箱码:" + boxNo + " 与QN号:" + barcode+ " 解除绑定"+" 操作人："+user.getUserName())
                    .build()
            );*/
            removeById(boxBarcode);
            /* 2.修改箱中数量 */
            int boxQty = count(new QueryWrapper<PdtWmsBoxBarcode>().eq("box_no", boxNo));

            PdtWmsBox box = pdtWmsBoxService.getOne(new QueryWrapper<PdtWmsBox>().eq("box_no", boxNo));
            //Long boxQty = box.getBoxQty() - 1;
            box.setBoxQty(Convert.toLong(boxQty));
            pdtWmsBoxService.updateById(box);

            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "出库失败");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result boxing(Map<String, Object> map) {
        try{
            String boxNo = MapUtil.getStr(map, "boxNo");
            String barcode = MapUtil.getStr(map, "barcode");
            String empNo = MapUtil.getStr(map, "empNo");
            PdtWmsBox box = pdtWmsBoxService.getOne(new QueryWrapper<PdtWmsBox>().eq("box_no", boxNo));
            if (box == null) {
                return Result.failed("无此箱号");
            }
            if (box.getBoxQty()>=box.getMaxQty()){
                return Result.failed("箱已包满");
            }
            String boxMoNo = box.getMoNo();
            PdtWmsBoxBarcode boxBarcode = getOne(new QueryWrapper<PdtWmsBoxBarcode>()
                    .eq("barcode", barcode)
            );
            if (boxBarcode!=null&&boxNo.equals(boxBarcode.getBoxNo())){
                return Result.failed("该条码已在此箱，无需合箱");
            }
            PdtWmsStockInfo stockInfo = pdtWmsStockInfoService.getOne(new QueryWrapper<PdtWmsStockInfo>()
                    .eq("barcode", barcode)
            );
            if (stockInfo != null){
                //存在库存信息不允许拆箱
                return Result.failed("该条码仍在库中，无法合箱");
            }
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }

            if (boxBarcode != null) {
                String barcodeMoNo = boxBarcode.getMoNo();
                if (!boxMoNo.equals(barcodeMoNo)){
                    return Result.failed("条码与箱非同批次");
                }
                //如果该条码原来已在某箱中，需要先拆箱
                String existBoxNo = boxBarcode.getBoxNo();
                /* 1.删除绑定关系 */
                /*pdaMesLogService.save(PdaMesLog.builder()
                        .name("拆箱")
                        .context("箱码:" + existBoxNo + " 与QN号:" + barcode+ " 解除绑定"+" 操作人："+user.getUserName())
                        .build()
                );*/
                removeById(boxBarcode);
                /* 2.修改箱中数量 */
                int boxQty = count(new QueryWrapper<PdtWmsBoxBarcode>().eq("box_no", existBoxNo));
                PdtWmsBox existBox = pdtWmsBoxService.getOne(new QueryWrapper<PdtWmsBox>().eq("box_no", existBoxNo));
                //Long boxQty = existBox.getBoxQty() - 1;
                existBox.setBoxQty(Convert.toLong(boxQty));
                pdtWmsBoxService.updateById(existBox);
            }
            PdtWmsBoxBarcode newBoxBarcode=new PdtWmsBoxBarcode();
            newBoxBarcode.setBoxNo(boxNo);
            newBoxBarcode.setBarcode(barcode);
            newBoxBarcode.setMoNo(boxMoNo);
            /* 1.添加绑定关系 */
            /*pdaMesLogService.save(PdaMesLog.builder()
                    .name("合箱")
                    .context("箱码:" + boxNo + " 与QN号:" + barcode+ " 添加绑定"+" 操作人："+user.getUserName())
                    .build()
            );*/
            save(newBoxBarcode);
            /* 2.修改箱中数量 */
            int boxQty = count(new QueryWrapper<PdtWmsBoxBarcode>().eq("box_no", boxNo));
            box.setBoxQty(Convert.toLong(boxQty));
            pdtWmsBoxService.updateById(box);
           return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "合箱失败");
        }
    }

    @Override
    public List<Map<String, Object>> listByBoxNo(String boxNo) {
        return pdtWmsBoxBarcodeMapper.listByBoxNo(boxNo);
    }

}
