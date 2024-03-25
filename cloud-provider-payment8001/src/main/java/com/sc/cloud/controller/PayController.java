package com.sc.cloud.controller;

import com.sc.cloud.entities.Pay;
import com.sc.cloud.entities.PayDTO;
import com.sc.cloud.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class PayController{
    @Resource
    PayService payService;
    @PostMapping(value = "/pay/add")
    public String addPay(@RequestBody Pay pay){
        System.out.println(pay.toString());
        int i = payService.add(pay);
        return "成功插入记录，返回值："+i;
    }
    @DeleteMapping(value = "/pay/del/{id}")
    public Integer deletePay(@PathVariable("id") Integer id) {
        return payService.delete(id);
    }
    @PutMapping(value = "/pay/update")
    public String updatePay(@RequestBody PayDTO payDTO){
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);

        int i = payService.update(pay);
        return "成功修改记录，返回值："+i;
    }
    @GetMapping(value = "/pay/get/{id}")
    public Pay getById(@PathVariable("id") Integer id){
        return payService.getById(id);
    }
    //TODO 全部查询getall作为家庭作业
}