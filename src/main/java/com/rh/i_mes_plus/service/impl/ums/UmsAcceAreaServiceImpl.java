package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsAcceAreaMapper;
import com.rh.i_mes_plus.model.ums.UmsAcceArea;
import com.rh.i_mes_plus.service.ums.IUmsAcceAreaService;
import com.rh.i_mes_plus.vo.ChildVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 辅料储位
 *
 * @author hqb
 * @date 2020-09-22 13:34:31
 */
@Slf4j
@Service
public class UmsAcceAreaServiceImpl extends ServiceImpl<UmsAcceAreaMapper, UmsAcceArea> implements IUmsAcceAreaService {
    @Resource
    private UmsAcceAreaMapper umsAcceAreaMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsAcceArea> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsAcceArea> pages = new Page<>(pageNum, pageSize);
        return umsAcceAreaMapper.findList(pages, params);
    }


    @Override
    public List<ChildVO> selectAllTree(String s) {
        List<ChildVO> childVOS = umsAcceAreaMapper.selectAllTree(s);
        if(childVOS!=null){
            for (ChildVO childVO : childVOS) {
                if(childVO!=null&&childVO.getParentCode()!=null){
                    childVO.setChildVOS(selectAllTree(childVO.getCode()));
                }
            }
        }
        return childVOS;
    }
}
