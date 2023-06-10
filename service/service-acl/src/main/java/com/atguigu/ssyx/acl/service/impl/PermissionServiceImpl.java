package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.mapper.RolePermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.utils.PermissionHelper;
import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/10 14:45
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    PermissionServiceImpl permissionService;

    @Override
    public List<Permission> queryAllPermission() {

        //*查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(null);

        //*转换为要求的格式
        List<Permission> result = PermissionHelper.buildPermission(allPermissionList);

        return result;
    }

    @Override
    public boolean deleteById(Long id) {
        //* 封装所有需要删除的菜单的id
        List<Long> idList = new ArrayList<>();

        //*递归找当前id的菜单的所有子菜单的id
        this.getAllPermissionId(id,idList);

        int i = baseMapper.deleteBatchIds(idList);
        return i>0;
    }

    @Override
    public List<Permission> getRoleByAdminId(Long roleId) {
        //*1 查询所有的权限
        List<Permission> allPermissions = permissionService.queryAllPermission();

        //*2 根据用户id查询用户分配角色列表
//        List<Permission> assignPermissions = rolePermissionMapper.selectByAdminId(roleId);

        Map<String, Object> result = new HashMap<>();
        result.put("allPermissions", allPermissions);
//        result.put("assignPermissions", assignPermissions);
        return allPermissions;
    }

    @Override
    public void saveAdminRole(Long roleId, Long[] permissionId) {

    }


    /**
     * !递归找当前id的菜单的所有子菜单的id
     * @param id 当前菜单的id
     * @param idList 所有子菜单的id
     */
    private void getAllPermissionId(Long id, List<Long> idList) {
        idList.add(id);

        List<Permission> childList = baseMapper.selectList(new LambdaQueryWrapper<Permission>().eq(Permission::getPid, id));
        //递归查询是否还有子菜单
        childList.stream().forEach(item->{
            this.getAllPermissionId(item.getId(),idList);
        });
    }
}
