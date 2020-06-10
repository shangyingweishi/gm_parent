package com.gm.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gm.eduservice.entity.EduTeacher;
import com.gm.eduservice.mapper.EduTeacherMapper;
import com.gm.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gm.servicebase.entity.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-15
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public List<EduTeacher> getIndexTeachers() {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("id");

        wrapper.last("limit 4");

        List<EduTeacher> teacherList = baseMapper.selectList(wrapper);

        if (teacherList.size() > 0){
            return teacherList;
        }else {
            throw new MyException(20001, "没有数据!请联系管理员");
        }
    }

    @Override
    public IPage<EduTeacher> getTeacherPageList(int current, int limit) {

        Page<EduTeacher> page = new Page<>(current, limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("id");

        IPage<EduTeacher> iPage = baseMapper.selectPage(page, wrapper);

        return iPage;
    }
}
