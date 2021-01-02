package com.hr.test.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    private List<DemoData> demoDataList = new ArrayList<>();

    @Before
    public void getData() {
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("Lucy" + i);
            demoDataList.add(demoData);
        }
    }

    @Test
    public void xie() {
//        //实现excel写的操作
//        //1 设置写入文件夹地址和excel文件名称
//        String fileName = "F:\\stu.xlsx";
//        //2 调用easyexcel里面的方法实现写操作
//        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(demoDataList);


        String fileName = "F:\\stu.xlsx";
        //实现Excel读操作
        EasyExcel.read(fileName,DemoData.class, new ExcelListener()).sheet().doRead();
    }
}
