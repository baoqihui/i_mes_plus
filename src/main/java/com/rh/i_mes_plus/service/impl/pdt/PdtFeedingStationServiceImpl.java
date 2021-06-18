package com.rh.i_mes_plus.service.impl.pdt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtFeedingStationDTO;
import com.rh.i_mes_plus.mapper.pdt.PdtFeedingStationMapper;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStation;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStationDetail;
import com.rh.i_mes_plus.service.pdt.IPdtFeedingStationDetailService;
import com.rh.i_mes_plus.service.pdt.IPdtFeedingStationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 料站表主表
 *
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
@Slf4j
@Service
public class PdtFeedingStationServiceImpl extends ServiceImpl<PdtFeedingStationMapper, PdtFeedingStation> implements IPdtFeedingStationService {
    @Resource
    private PdtFeedingStationMapper pdtFeedingStationMapper;
    @Autowired
    private IPdtFeedingStationDetailService pdtFeedingStationDetailService;
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
        return pdtFeedingStationMapper.findList(pages, params);
    }

    @Override
    public Result saveAll(PdtFeedingStationDTO pdtFeedingStationDTO) {
        PdtFeedingStation pdtFeedingStation=pdtFeedingStationDTO;
        log.info("新建料站表：{}",pdtFeedingStationDTO);
        //删除原有docNo的详情
        pdtFeedingStationDetailService.remove(new LambdaQueryWrapper<PdtFeedingStationDetail>().eq(PdtFeedingStationDetail::getFsSn,pdtFeedingStation.getFsSn()));
        List<PdtFeedingStationDetail> pdtFeedingStationDetails = pdtFeedingStationDTO.getPdtFeedingStationDetails();
        if (pdtFeedingStationDetails == null||pdtFeedingStationDetails.isEmpty()) {
            return Result.failed(  "详情列表不能为空");
        }
        pdtFeedingStationDetails.forEach(u->{
            u.setFsSn(pdtFeedingStation.getFsSn());
        });
        saveOrUpdate(pdtFeedingStation);
        pdtFeedingStationDetailService.saveBatch(pdtFeedingStationDetails);
        return Result.succeed("保存成功");
    }
}
