package com.atguigu.ssyx.acl.utils;

import com.atguigu.ssyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/10 15:42
 */
public class PermissionHelper {

    public static List<Permission> buildPermission(List<Permission> allPermissionList){

        List<Permission> trees = new ArrayList<>();

        //*遍历所有菜单list计喝，得到第一层数据，pid=0
        allPermissionList.forEach(item->{
            if (item.getPid()==0){
                item.setLevel(1);
                //嗲用方法从第一层开始往下找
                trees.add(findChildren(item, allPermissionList));
            }
        });

        return trees;
    }


    /**
     * !递归往下找
     * @param permission 上层节点
     * @param allPermissionList 所有菜单的数据
     * @return 找到的下层的节点
     */
    private static Permission findChildren(Permission permission,
                                           List<Permission> allPermissionList) {
        permission.setChildren(new ArrayList<Permission>());

        allPermissionList.forEach(item->{
            if (item.getPid().equals(permission.getId())){
                int level = permission.getLevel() + 1;
                item.setLevel(level);
                //封装下一层数据
                if (permission.getChildren()==null){
                    permission.setChildren(new ArrayList<>());
                }
                permission.getChildren().add(findChildren(item, allPermissionList));
            }
        });
        return permission;
    }

}
