package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/10 11:40
 */
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    @Select("select * from Role R where R.id in (select AR.role_id from admin_role AR where AR.admin_id=#{adminId} and AR.is_deleted=0);")
    List<Role> selectByAdminId(@Param("adminId") Long adminId);
}
