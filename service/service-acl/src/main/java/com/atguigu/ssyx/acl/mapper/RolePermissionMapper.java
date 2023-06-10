package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/10 16:29
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    @Select("select * from permission R where R.id in (select RP.role_id from role_permission RP where RP.role_id=#{roleId} and RP.is_deleted=0);")
    List<Permission> selectByAdminId(@Param("roleId") Long adminId);
}
