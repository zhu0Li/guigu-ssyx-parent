package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/9 19:51
 */
public interface AdminService extends IService<Admin> {

    IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo);

    boolean add(Admin admin);

    boolean changeById(Admin admin);
}
