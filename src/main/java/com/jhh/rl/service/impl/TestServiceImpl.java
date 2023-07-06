package com.jhh.rl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhh.rl.controller.DTO.TestDTO;
import com.jhh.rl.controller.TestRequestClass.AddTest;
import com.jhh.rl.controller.TestRequestClass.TestList;
import com.jhh.rl.controller.TestRequestClass.UpdateTestInfo;
import com.jhh.rl.entity.Test;
import com.jhh.rl.mapper.TestMapper;
import com.jhh.rl.service.TestService;
import com.jhh.rl.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper testMapper;
    @Override
    public Result addtest(AddTest addTest) {
        if (addTest == null || addTest.getTest_status() == null || addTest.getAccount() == null
                || addTest.getUsername() == null || addTest.getCreate_name() == null) {
            return Result.fail("输入无效");
        }


        Test test = new Test();
        test.setTestName(addTest.getAccount());
        test.setTestStatus(addTest.getTest_status());
        test.setCreateName(addTest.getCreate_name());
        test.setUserName(addTest.getUsername());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        test.setCreateTime(sdf.format(new Date()));

        int i = testMapper.insert(test);

        if(i==0) return Result.fail("创建实验失败");

        return Result.ok("创建实验成功");


    }

    @Override
    public Result<List<TestDTO>> gettestlist(TestList testList) {
        if(testList==null) return Result.fail("查询实验列表失败");
        IPage<Test> ipage=new Page<>(testList.getPage_no(), testList.getPage_size());
        QueryWrapper<Test>queryWrapper=new QueryWrapper<>();

        String account=testList.getAccount();
        String username= testList.getUsername();
        String test_status=testList.getTest_status();
        String create_name=testList.getCreate_name();


        if(test_status.equals("全部")||test_status.isEmpty())
        {
            queryWrapper.like("test_name",account)
                    .like("user_name",username)
                    .like("create_name",create_name);
        }else
        {
            queryWrapper.like("test_name",account)
                    .like("user_name",username)
                    .like("create_name",create_name)
                    .like("test_status",test_status);
        }

        queryWrapper.orderByDesc("id");

        ipage=testMapper.selectPage(ipage,queryWrapper);
        List<Test>lists=ipage.getRecords();

        if(lists.isEmpty()) return Result.fail("查询实验列表失败");

        List<TestDTO> testDTOS=new ArrayList<>();
        for(Test list:lists){
            TestDTO testList1 = new TestDTO();
            testList1.setTest_status(list.getTestStatus());
            testList1.setUser_name(list.getUserName());
            testList1.setTest_name(list.getTestName());
            testList1.setTest_id(list.getId());
            testList1.setCreate_time(list.getCreateTime());
            System.out.println(testList1);

            testDTOS.add(testList1);

        }

        return Result.ok("查询实验列表成功",testDTOS,testDTOS.size());
    }

    @Override
    public Result updatetestinfo(UpdateTestInfo updateTestInfo) {
        if(updateTestInfo.getTest_id()==0) return Result.fail("无效输入");
        Test test = testMapper.selectById(updateTestInfo.getTest_id());

        test.setTestName(updateTestInfo.getTest_name());
        test.setUserName(updateTestInfo.getUser_name());
        test.setTestStatus(updateTestInfo.getTest_status());

        int i = testMapper.updateById(test);
        if(i==0) return Result.fail("实验信息修改失败");

        return Result.ok("实验信息修改成功");

    }
}
