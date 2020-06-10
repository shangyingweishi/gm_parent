package com.gm.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gm.eduservice.entity.EduSubject;
import com.gm.eduservice.entity.exceldata.ExcelData;
import com.gm.eduservice.service.EduSubjectService;
import com.gm.servicebase.entity.MyException;


public class ExcelListener extends AnalysisEventListener<ExcelData> {

    //listener无法交给spring管理，需要自己new，不能注入其他对象
    public EduSubjectService eduSubjectService;

    public ExcelListener(){}

    public ExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelData data, AnalysisContext context) {

        if(null == data){
            throw new MyException(20001, "文件为空，读取失败");
        }

        //一级分类不存在则添加
        EduSubject existOne = existOne(data, eduSubjectService);
        if (null == existOne){
            existOne = new EduSubject();
            existOne.setTitle(data.getOneSubjectName());
            existOne.setParentId("0");
            eduSubjectService.save(existOne);
        }

        //二级分类不存在则添加
        String id = existOne.getId();
        if (null == existTow(data, eduSubjectService, id)){
            EduSubject subject2 = new EduSubject();
            subject2.setTitle(data.getTwoSubjectName());
            subject2.setParentId(id);
            eduSubjectService.save(subject2);
        }


    }

    //判断一级分类是否存在
    private EduSubject existOne(ExcelData data,EduSubjectService eduSubjectService){

        String oneSubjectName = data.getOneSubjectName();
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", oneSubjectName);
        wrapper.eq("parent_id", "0");
        EduSubject one = eduSubjectService.getOne(wrapper);


        return one;

    }

    //判断二级分类是否存在
    private EduSubject existTow(ExcelData data, EduSubjectService eduSubjectService, String pId){

        String twoSubjectName = data.getTwoSubjectName();
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", twoSubjectName);
        wrapper.eq("parent_id", pId);
        EduSubject tow = eduSubjectService.getOne(wrapper);

        return tow;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
