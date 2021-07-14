package com.rh.i_mes_plus.service.other;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface WebServiceDemoService {
    /**
     * 用户登录
     * */
    @WebMethod
    public String login(@WebParam(name = "param")String param);
    /**
     * 通过类型号获取入库单号
    * */
    @WebMethod
    public List<String> getReceiveList(@WebParam(name = "dtCode") String dtCode);
    /**
     * 通过类型号获取出库单号
     * */
    @WebMethod
    public List<String> getOutList(@WebParam(name = "dtCode") String dtCode);
    /**
     * pda扫码入库
    * */
    @WebMethod
    public String pdaReceive(@WebParam(name = "param")String param);
    /**
     * pda扫码入库
     * */
    @WebMethod
    public String pdaReceiveCancel(@WebParam(name = "param")String param);
    /**
     * pda扫码出库
     * */
    @WebMethod
    public String pdaOutStock(@WebParam(name = "param")String param);
    /**
     * pda取消扫码出库
     * */
    @WebMethod
    public String pdaCancelOutStock(@WebParam(name = "param")String param);
    /**
     * pda扫码换料
     * */
    @WebMethod
    public String pdaReload(@WebParam(name = "param")String param);
    /**
     * pda扫码调拨
     * */
    @WebMethod
    public String pdaMove(@WebParam(name = "param")String param);
    /**
     * pda扫码拆箱
     * */
    @WebMethod
    public String unboxing(@WebParam(name = "param")String param);
    /**
     * pda扫码合箱
     * */
    @WebMethod
    public String boxing(@WebParam(name = "param")String param);
    /**
     * 成品PDA箱码扫描入库
     * */
    @WebMethod
    public String pdaPdtReceive(@WebParam(name = "param")String param);
    /**
     * 成品PDA箱码扫描出库（dtCode为 22，销售出库 29,返工出库 ）
     * */
    @WebMethod
    public String pdaPdtOutStock(@WebParam(name = "param")String param);
    /**
     * pda根据dtCode查询成品入库单号
     * */
    @WebMethod
    public List<String> getReceiveDocListByDtCode(@WebParam(name = "dtCode") String dtCode);
    /**
     * pda根据dtCode查询成品出库单号（dtCode为 22，销售出库 29,返工出库 ）
     * */
    @WebMethod
    public List<String> getOutStockDocListByDtCode(@WebParam(name = "dtCode") String dtCode);
    /**
     * pda根据类型号查询换料单号
     * */
    @WebMethod
    public List<String> getReloadDocListByDtCode(@WebParam(name = "dtCode") String dtCode);
    /**
     * pda根据类型号查询调拨单号
     * */
    @WebMethod
    public List<String> getMoveDocListByDtCode(@WebParam(name = "dtCode") String dtCode);
    /**
     * pda扫码拆签
     * */
    @WebMethod
    public String spilt(@WebParam(name = "param")String param);
    /**
     * pda扫码合签
     * */
    @WebMethod
    public String combine(@WebParam(name = "param")String param);
    /**
     * pda扫码上架
     * */
    @WebMethod
    public String putAway(@WebParam(name = "param")String param);
    /**
     * pda扫码下架
     * */
    @WebMethod
    public String soldOut(@WebParam(name = "param")String param);
    /**
     * pda扫码根据条码查库位
     * */
    @WebMethod
    public String getLocation(@WebParam(name = "param")String param);

    /**
     * pda扫码根据条码查库位
     */
    @WebMethod
    public String getInfoBySnAndQty(@WebParam(name = "param")String param);

    /**
     * 亮灯控制
     */
    @WebMethod
    public String LightControl(@WebParam(name = "param")String param);

    /**
     * 查询制令单列表
     */
    @WebMethod
    public List<String> getMoNoList(@WebParam(name = "param")String param);
    /**
     * 锡膏回温
     */
    @WebMethod
    public String take(@WebParam(name = "param")String param);
    /**
     * 锡膏领用
     */
    @WebMethod
    public String use(@WebParam(name = "param")String param);
    /**
     * 上锡膏
     */
    @WebMethod
    public String upTin(@WebParam(name = "param")String param);
    /**
     * 退库
     */
    @WebMethod
    public String returnRecord(@WebParam(name = "param")String param);
}
