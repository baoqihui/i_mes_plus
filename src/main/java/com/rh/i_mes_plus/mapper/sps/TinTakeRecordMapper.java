package com.rh.i_mes_plus.mapper.sps;

import com.rh.i_mes_plus.model.sps.TinTakeRecord;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.vo.TinTakeRecordVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Mapper
public interface TinTakeRecordMapper extends SuperMapper<TinTakeRecord> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<TinTakeRecordVO> findList(Page<TinTakeRecordVO> pages, @Param("p") Map<String, Object> params);

    TinTakeRecordVO getTakeStateByTinSn(@Param("tinSn") String tinSn);
}
