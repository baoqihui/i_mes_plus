package com.rh.i_mes_plus.service.impl.ums;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.dto.UserRoleDTO;
import com.rh.i_mes_plus.mapper.ums.UmsUserMapper;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.model.ums.UmsUserRole;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.service.ums.IUmsPermissionService;
import com.rh.i_mes_plus.service.ums.IUmsUserRoleService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import com.rh.i_mes_plus.vo.PermissionTreeVO;
import com.rh.i_mes_plus.vo.UmsPermissionVO;
import com.rh.i_mes_plus.vo.UmsUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {
    @Resource
    private UmsUserMapper umsUserMapper;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IUmsPermissionService umsPermissionService;
    @Autowired
    private IUmsUserRoleService umsUserRoleService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsUser> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsUser> pages = new Page<>(pageNum, pageSize);
        return umsUserMapper.findList(pages, params);
    }

    @Override
    public Map<String, Object> getUser(String userAccount, String password) {
        UmsUser existUmsUser = umsUserService.getOne(new LambdaQueryWrapper<UmsUser>()
                .eq(UmsUser::getUserAccount, userAccount)
                .eq(UmsUser::getUserPwd, password)
                .eq(UmsUser::getState,1)
        );
        return BeanUtil.beanToMap(existUmsUser);
    }

    @Override
    public Result authLogin(Map<String, Object> map) {
        String userAccount = MapUtil.getStr(map, "userAccount");
        String userPwd = MapUtil.getStr(map, "userPwd");
        userPwd=SecureUtil.md5(userPwd);
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userAccount, userPwd);
        try {
            currentUser.login(token);
            return Result.succeed("登录成功");
        }  catch (UnknownAccountException e){
            return Result.failed("用户名或密码错误");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return Result.failed("密码错误");
        } catch (AuthenticationException e) {
            return Result.failed("登录失败");
        }
    }

    @Override
    public Result getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        Map map =(Map)session.getAttribute(SysConst.USER_IFO);
        String userAccount = MapUtil.getStr(map,"userAccount");
        UmsUser user = umsUserService.getOne(new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getUserAccount, userAccount));
        Map<String, Object> userMap = BeanUtil.beanToMap(user);
        List<PermissionTreeVO> userAllPermission = umsPermissionService.getUserPermission(userAccount,0L);
        List<String> userButtonPermission = umsPermissionService.getUserButtonPermission(userAccount);
        /**
         * 因为同一用户可能有多个角色，角色的按钮权限可能相同 应去重
         * */
        userButtonPermission= userButtonPermission.stream().distinct().collect(Collectors.toList());

        userMap.put("userAllPermission",userAllPermission);
        userMap.put("userButtonPermission",userButtonPermission);
        session.setAttribute(SysConst.SESSION_USER_PERMISSION, userMap);
        return Result.succeed(userMap,"成功");
    }


    @Override
    public Result logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return Result.succeed("退出成功");
    }

    @Override
    public Result saveOrUpdateUser(UserRoleDTO userRoleDTO) {
        UmsUser umsUser = new UmsUser();
        BeanUtil.copyProperties(userRoleDTO,umsUser);
        String depaCode = umsUser.getDepaCode();
        String depaName = umsDepaService.getDepaNameByCode(depaCode);
        umsUser.setDepaName(depaName);

        List<Long> roleIds = userRoleDTO.getRoleIds();

        String userAccount = umsUser.getUserAccount();
        String userPwd = umsUser.getUserPwd();
        UmsUser existUmsUser = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", userAccount));
        if (existUmsUser != null) {
            //修改角色信息
            if (roleIds != null&&roleIds.size()>0) {
                UmsUserRole umsUserRole = new UmsUserRole();
                umsUserRole.setUserId(existUmsUser.getId());
                umsUserRoleService.remove(new QueryWrapper<UmsUserRole>().eq("user_id",existUmsUser.getId()));
                for (Long roleId : roleIds) {
                    umsUserRole.setRoleId(roleId);
                    umsUserRoleService.save(umsUserRole);
                }
            }
            //修改用户部门信息
            existUmsUser.setDepaCode(depaCode);
            existUmsUser.setDepaName(depaName);
            //修改用户信息
            existUmsUser.setUserName(umsUser.getUserName());
            existUmsUser.setIsExter(umsUser.getIsExter());
            existUmsUser.setState(umsUser.getState());
            if (StrUtil.isNotEmpty(userPwd)){
                existUmsUser.setUserPwd(SecureUtil.md5(userPwd));
            }
            umsUserService.updateById(existUmsUser);
            return Result.succeed("修改成功");
        }else {
            umsUser.setUserPwd(SecureUtil.md5(StrUtil.isEmpty(userPwd)? SysConst.DEFAULT_PWD:userPwd));
            umsUserService.save(umsUser);
            //保存角色信息
            if (roleIds != null&&roleIds.size()>0) {
                UmsUserRole umsUserRole = new UmsUserRole();
                umsUserRole.setUserId(umsUser.getId());
                for (Long roleId : roleIds) {
                    umsUserRole.setRoleId(roleId);
                    umsUserRoleService.save(umsUserRole);
                }
            }
            return Result.succeed("保存成功");
        }
    }
    @Override
    public List<UmsUser> getManager(Map<String, Object> params) {
        return umsUserMapper.getManager(params);
    }

    @Override
    public Result pdaLogin(UmsUser umsUser) {
        String userAccount = umsUser.getUserAccount();
        String userPwd = umsUser.getUserPwd();
        UmsUser existUmsUser = umsUserService.getOne(new QueryWrapper<UmsUser>()
                .eq("user_account", userAccount)
                .eq("user_pwd", userPwd)
        );
        if (existUmsUser!=null){
            UmsUserVO umsUserVO=new UmsUserVO();
            BeanUtil.copyProperties(existUmsUser,umsUserVO);
            umsUserVO.setToken(SecureUtil.md5(existUmsUser.getUserAccount()));
            Long id = existUmsUser.getId();
            List<UmsPermissionVO> umsPermissionVOS=umsUserMapper.selectPermissionOfUser(id);
            if (umsPermissionVOS.size()<=0){
                return Result.failed(umsUserVO,"无用户权限，请联系管理员分配角色");
            }
            List list=(List) umsPermissionVOS.stream().distinct().collect(Collectors.toList());
            umsUserVO.setUmsPermissionVOS(list);
            log.info("用户：{} 登录成功",userAccount);
            return Result.succeed(umsUserVO,"登录成功");
        }
        return Result.failed("登录失败，账号或密码错误");
    }
}
