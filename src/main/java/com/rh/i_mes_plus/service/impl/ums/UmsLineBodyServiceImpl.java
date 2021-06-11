package com.rh.i_mes_plus.service.impl.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsLineBodyMapper;
import com.rh.i_mes_plus.model.ums.UmsLineBody;
import com.rh.i_mes_plus.service.ums.IUmsLineBodyService;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.OneVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 线别管理
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Slf4j
@Service
public class UmsLineBodyServiceImpl extends ServiceImpl<UmsLineBodyMapper, UmsLineBody> implements IUmsLineBodyService {
    @Resource
    private UmsLineBodyMapper umsLineBodyMapper;
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
        return umsLineBodyMapper.findList(pages, params);
    }

    @Override
    public List<OneVO> treeList(Map<String, Object> params) {
        return umsLineBodyMapper.treeList(params);
    }

    @Override
    public List<ChildVO> selectAllTree(String s) {
        List<ChildVO> childVOS = umsLineBodyMapper.selectAllTree(s);
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
