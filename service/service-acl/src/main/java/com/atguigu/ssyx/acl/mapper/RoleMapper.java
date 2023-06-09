package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ：zhuo
 * @description：角色管理mapper
 * @date ：2023/6/9 17:14
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
