package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtBomDetail;
import com.rh.i_mes_plus.model.pdt.PdtReplaceItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 替代料
 * 
 * @author hbq
 * @date 2021-03-30 13:20:00
 */
@Mapper
public interface PdtReplaceItemMapper extends SuperMapper<PdtReplaceItem> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    /**
     * @param ownerSort 自己的排序
     * @param replaceGroup  自己所在分组
     * @return  上一条记录，如果没有返回空
     */
    PdtReplaceItem getBefore(@Param("ownerSort") Long ownerSort, @Param("replaceGroup") String replaceGroup);

    /**
     * @param ownerSort 自己的排序
     * @param replaceGroup  自己所在分组
     * @return  下一条记录，如果没有返回空
     */
    PdtReplaceItem getAfter(@Param("ownerSort") Long ownerSort, @Param("replaceGroup") String replaceGroup);

    /**
     * @param replaceGroup 自己所在分组
     * @return 该分组最小的排序
     */
    Long minSort(@Param("replaceGroup") String replaceGroup);

    /**
     * @param replaceGroup 自己所在分组
     * @return 该分组最大的排序
     */
    Long maxSort(@Param("replaceGroup") String replaceGroup);

    List<PdtReplaceItem> getGroupItemListByItemCode(@Param("itemCode") String itemCode,@Param("modelCode")String modelCode, @Param("isExcludeItself") int isExcludeItself);

    List<PdtBomDetail> getBomDetailList(@Param("replaceGroup") String replaceGroup);
}
