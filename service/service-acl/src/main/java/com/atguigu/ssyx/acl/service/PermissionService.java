package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/10 14:44
 */
public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllPermission();

    boolean deleteById(Long id);

    List<Permission> getRoleByAdminId(Long roleId);

    void saveAdminRole(Long roleId, Long[] permissionId);
}
