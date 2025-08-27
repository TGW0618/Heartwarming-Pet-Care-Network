package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import com.tgwei.demopet.demos.web.mapper.SysOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SysOrderService {

    @Autowired
    private SysOrderMapper sysOrderMapper;

    //获取所有订单信息
    public List<SysOrder> getAllSysOrder() {
        return sysOrderMapper.getAllSysOrder();
    }

    //根据用户id获取订单信息（供客户端用户使用）
    public List<SysOrder> getSysOrderByUserId(Long tokenUserId) {
        return sysOrderMapper.getSysOrderByUserId(tokenUserId);
    }


    //    根据订单id删除订单
    public Boolean deleteSysOrder(Integer id) {
        return sysOrderMapper.deleteSysOrder(id);
    }

    //    用户创建订单
    public Boolean createSysOrder(ServiceOrder serviceOrder) {
        // 生成固定格式的订单号：Pet+服务类型 + YYYYMMDDHHmmss + 8位随机数
        String dateTimeStr = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomStr = String.format("%08d", new java.util.Random().nextInt(100000000));
        String orderNo = "Pet" + serviceOrder.getServiceType() + dateTimeStr + randomStr;

        serviceOrder.setOrderNo(orderNo);
        serviceOrder.setCreateTime(LocalDateTime.now());
        serviceOrder.setUpdateTime(LocalDateTime.now());

        if (serviceOrder.getPaymentStatus() == 0) { // 未支付
            serviceOrder.setStatus("pending"); // 待确认支付
        } else if (serviceOrder.getPaymentStatus() == 1) { // 已支付
            serviceOrder.setStatus("confirmed"); // 已确认订单
            serviceOrder.setPaymentTime(LocalDateTime.now()); // 支付时间
            // 第三方支付流水号：支付方式 + YYYYMMDDHHmmss + 8位随机数
            serviceOrder.setTransactionId(serviceOrder.getPaymentMethod() + dateTimeStr + randomStr);
        }

        return sysOrderMapper.createSysOrder(serviceOrder);
    }


}
