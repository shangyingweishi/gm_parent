package com.gm.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gm.eduservice.entity.EduSubject;
import com.gm.eduservice.entity.exceldata.ExcelData;
import com.gm.eduservice.entity.subject.OneSubject;
import com.gm.eduservice.entity.subject.TwoSubject;
import com.gm.eduservice.listener.ExcelListener;
import com.gm.eduservice.mapper.EduSubjectMapper;
import com.gm.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-26
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            //文件输入流
            InputStream is = file.getInputStream();
            //调方法进行读取
            EasyExcel.read(is, ExcelData.class, new ExcelListener(eduSubjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getSubjectList() {

        //创建分类对象集合
        List<OneSubject> oneSubjectList = new ArrayList<>();



        //1.先查出一级分类列表
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapperOne);
        for (EduSubject eduSubject : eduSubjects) {
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //下面为上面两行代码的简单写法
            BeanUtils.copyProperties(eduSubject, oneSubject);
            oneSubjectList.add(oneSubject);
        }

        //2.再查二级分类列表
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> eduSubjects1 = baseMapper.selectList(wrapperTwo);
        for (OneSubject subject : oneSubjectList) {
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (EduSubject eduSubject : eduSubjects1) {
                TwoSubject twoSubject = new TwoSubject();
                if (subject.getId().equals(eduSubject.getParentId())) {
//                    twoSubject.setId(eduSubject.getId());
//                    twoSubject.setTitle(eduSubject.getTitle());
                    //同上简写
                    BeanUtils.copyProperties(eduSubject, twoSubject);
                    twoSubjectList.add(twoSubject);

                }
            }
            subject.setChildren(twoSubjectList);

        }

        return oneSubjectList;
    }
}
