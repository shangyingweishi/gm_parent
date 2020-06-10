package com.gm.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gm.commonutils.R;
import com.gm.eduservice.entity.EduTeacher;
import com.gm.eduservice.query.TeacherQuery;
import com.gm.eduservice.service.EduTeacherService;
import com.gm.servicebase.entity.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-15
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //rest风格
    @GetMapping("/findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAllTeachers() {

        List<EduTeacher> teacherList = eduTeacherService.list(null);

//        try {
//            int i = 10/0;
//        } catch (Exception e) {
//            throw new MyException(55555, "自定义异常处理");
//        }


        return R.ok().data("items", teacherList);

    }

    //逻辑删除讲师
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id删除讲师")
    public R deleteTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {


        boolean b = eduTeacherService.removeById(id);

        if (b) {
            return R.ok();
        }

        return R.error();

    }

    //分页查询讲师
    @GetMapping("/pageList/{current}/{limit}")
    @ApiOperation(value = "讲师分页列表")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {

        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        //调用方法实现分页
        IPage<EduTeacher> page = eduTeacherService.page(teacherPage, null);


        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());


    }

    //条件查询
    @PostMapping("/pageListCondition/{current}/{limit}")
    @ApiOperation(value = "条件组合查询讲师分页列表")
    public R pageListConditionTeacher(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();


        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name", teacherQuery.getName());
        }
        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level", teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_Create", teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_Modified", teacherQuery.getEnd());
        }

        wrapper.orderByDesc("gmt_Create");

        IPage<EduTeacher> page = eduTeacherService.page(teacherPage, wrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());

    }

    @PostMapping("/addTeacher")
    @ApiOperation(value = "添加讲师方法")
    public R addTeacher(@RequestBody(required = false) EduTeacher eduTeacher) {


        boolean flag = eduTeacherService.save(eduTeacher);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据id查询讲师
    @GetMapping("/getTeacherById/{id}")
    @ApiOperation(value = "根据id查询讲师")
    public R updateTeacher(@PathVariable String id) {


        EduTeacher teacher = eduTeacherService.getById(id);

        return R.ok().data("teacher", teacher);

    }

    @PostMapping("/updateTeacher")
    @ApiOperation(value = "根据id修改讲师信息")
    public R updateTeacher(@RequestBody(required = false) EduTeacher eduTeacher) {

        boolean save = eduTeacherService.updateById(eduTeacher);

        if (save) {
            return R.ok();
        } else {
            return R.error();
        }

    }

}

