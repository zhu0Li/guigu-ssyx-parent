package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminRoleMapper;
import com.atguigu.ssyx.acl.mapper.RoleMapper;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author ：zhuo
 * @description：角色管理实现类
 * @date ：2023/6/9 17:12
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * !角色列表，条件分页查询
     * @param pagePrarm 分页参数，当前页和每页记录数
     * @param roleQueryVo 查询条件
     * @return 查询结果
     */
    @Override
    public IPage<Role> selectRolePage(Page<Role> pagePrarm, RoleQueryVo roleQueryVo) {
        //*获取条件值
        String name = roleQueryVo.getRoleName();

        //*判断条件值是否为空
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like(Role::getRoleName,name);
        }

        //*调用方法实现条件分页查询
        IPage<Role> rolePage = baseMapper.selectPage(pagePrarm, queryWrapper);

        //*返回分页对象
        return rolePage;
    }

    /**
     * !获取所有角色，和根据用户id查询用户分配角色列表
     * @param adminId 用户id
     * @return 所有角色，和根据用户id的用户分配角色列表
     */
    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        //*1 查询所有的角色
        List<Role> allRolesList = baseMapper.selectList(null);

        //*2 根据用户id查询用户分配角色列表
        List<Role> assignRoles = adminRoleMapper.selectByAdminId(adminId);

        Map<String, Object> result = new HashMap<>();
        result.put("allRolesList", allRolesList);
        result.put("assignRoles", assignRoles);
        return result;
    }

    /**
     * !为用户分配角色
     * @param adminId 用户id
     * @param roleId 角色们的id
     */
    @Override
    public void saveAdminRole(Long adminId, Long[] roleId) {
        adminRoleService.remove(new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId, adminId));

        List<AdminRole> list = new ArrayList<>();
        for (Long id: roleId){
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(id);
            list.add(adminRole);
        }

        adminRoleService.saveBatch(list);
    }
}
