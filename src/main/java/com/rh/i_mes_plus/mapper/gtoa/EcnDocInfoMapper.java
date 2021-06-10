package com.rh.i_mes_plus.mapper.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.gtoa.EcnDocInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 客户文件
 * 
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
@Mapper
public interface EcnDocInfoMapper extends SuperMapper<EcnDocInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    String getNoCust( @Param("ecnNoCust")String ecnNoCust);
    
    Map<String, Object> selCustomerAndBeginDateByEcnNo(@Param("ecnNo") String ecnNo);
    
    List<EcnDocInfo> getEcnDocInfoList(@Param("modelName") String modelName);
    
    EcnDocInfo getMaxEcnNo(@Param("prefix") String prefix);
    
    @Select("select * from ecn_doc_info where ecn_no=#{ecnNo}")
    EcnDocInfo getOneByEcnNo(String ecnNo);
}
