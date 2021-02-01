package com.hr.aclservice.controller;


import com.hr.aclservice.entity.Permission;
import com.hr.aclservice.service.PermissionService;
import com.hr.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    //------------------------------------------------------
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/getAllMenus")
    public R getAllMenus() {
        List<Permission> list =  permissionService.getAllMenuHR();
        return R.ok().data("children",list);
    }

    @ApiOperation(value = "删除菜单及其子菜单")
    @DeleteMapping("/deleteMenus/{id}")
    public R deleteMenus(@PathVariable("id") String id) {
        permissionService.deleteMenus(id);
        return R.ok();
    }


    @ApiOperation(value = "给角色分配菜单")
    @DeleteMapping("/doAssignHR/{id}")
    public R doAssignHR(@RequestParam("roleId") String roleId,
                      @RequestParam("permissionIds") String[] permissionIds) {
        permissionService.saveRolePermissionRealtionShipHR(roleId, permissionIds);
        return R.ok();
    }


    //------------------------------------------------------




    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public R indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return R.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

