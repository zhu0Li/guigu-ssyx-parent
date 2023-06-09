package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminMapper;
import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.common.utils.MD5;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/9 19:52
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String name = adminQueryVo.getName();
        String username = adminQueryVo.getUsername();

        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like(Admin::getName,name);
        }
        if (!StringUtils.isEmpty(username)){
            queryWrapper.like(Admin::getUsername,username);
        }

        IPage<Admin> adminPage = baseMapper.selectPage(pageParam, queryWrapper);
        return adminPage;
    }

    @Override
    public boolean add(Admin admin) {
        //*获取输入的密码
        String password = admin.getPassword();

        //*对密码进行md5加密
        String passwordMD5 = MD5.encrypt(password);

        admin.setPassword(passwordMD5);

        //*调用方法添加
        int insert = baseMapper.insert(admin);
        return insert > 0;
    }

    @Override
    public boolean changeById(Admin admin) {
        //*获取输入的密码
        String password = admin.getPassword();

        //*对密码进行md5加密
        if (!StringUtils.isEmpty(password)){
            String passwordMD5 = MD5.encrypt(password);
            admin.setPassword(passwordMD5);
        }

        //*调用方法添加
        int insert = baseMapper.updateById(admin);
        return insert > 0;
    }
}
