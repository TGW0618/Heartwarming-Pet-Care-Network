package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.ServiceOrderVO;
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

    //根据用户id获取订单信息
    public List<ServiceOrderVO> getSysOrderByUserId(Integer userId, String status, String paymentStatus) {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        return sysOrderMapper.getSysOrderByUserId(userId, status, paymentStatus);
    }

    // 根据订单id删除订单
    public Boolean deleteSysOrder(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("订单ID不能为空");
        }
        return sysOrderMapper.deleteSysOrder(id);
    }

    // 用户创建订单
    public Boolean createSysOrder(ServiceOrder serviceOrder) {
        if (serviceOrder == null) {
            throw new IllegalArgumentException("订单信息不能为空");
        }

        // 生成固定格式的订单号：Pet+服务类型 + YYYYMMDDHHmmss + 8位随机数
        String dateTimeStr = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomStr = String.format("%08d", new java.util.Random().nextInt(100000000));
        String orderNo = "Pet" + serviceOrder.getServiceType() + dateTimeStr + randomStr;

        serviceOrder.setOrderNo(orderNo);
        serviceOrder.setCreateTime(LocalDateTime.now());
        serviceOrder.setUpdateTime(LocalDateTime.now());

        if (serviceOrder.getPaymentStatus() == null) {
            serviceOrder.setPaymentStatus(0); // 默认未支付
        }

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

    // 根据订单id获取订单相关信息（连表查询）
    public ServiceOrderVO getSysOrderById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("订单ID不能为空");
        }
        return sysOrderMapper.getSysOrderById(id);
    }

    //    根据订单id修改订单信息
    public boolean updateSysOrder(ServiceOrder serviceOrder) {
        if (serviceOrder == null || serviceOrder.getId() == null) {
            throw new IllegalArgumentException("订单信息或订单ID不能为空");
        }
        // 设置更新时间
        serviceOrder.setUpdateTime(LocalDateTime.now());
        return sysOrderMapper.updateSysOrder(serviceOrder);
    }

// 模糊搜索订单
public List<ServiceOrderVO> searchOrders(Integer userId, String keyword) {
    if (userId == null) {
        throw new IllegalArgumentException("用户ID不能为空");
    }
    if (keyword == null || keyword.trim().isEmpty()) {
        throw new IllegalArgumentException("搜索关键词不能为空");
    }
    return sysOrderMapper.searchOrders(userId, keyword.trim());
}

}
