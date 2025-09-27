package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.ServiceOrderVO;
import com.tgwei.demopet.demos.web.entity.SysNotification;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import com.tgwei.demopet.demos.web.mapper.SysOrderMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SysOrderService {

    @Resource
    private SysOrderMapper sysOrderMapper;
    @Resource
    @Lazy//懒加载(解决循环依赖问题)
    private MedicalRecordService medicalRecordService;
    @Resource
    @Lazy//懒加载
    private FosterRecordService fosterRecordService;

    @Resource
    private SysNotificationService sysNotificationService;


    // 获取订单(根据角色，订单类型（可为空），角色id（可为空）获取订单信息)
    public com.github.pagehelper.PageInfo<ServiceOrderVO> getOrderAll(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("role");

        if (claims == null || tokenUserId == null || userRole == null) {
            return new com.github.pagehelper.PageInfo<>(new java.util.ArrayList<>());
        }

        List<ServiceOrderVO> orderList;
        try {
            com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
            switch (userRole) {
                case "admin":
                    // 管理员获取所有订单
                    orderList = sysOrderMapper.getOrderAll(null, null, null);
                    break;
                case "veterinarian":
                    // 兽医获取医疗相关订单
                    orderList = sysOrderMapper.getOrderAll(null, "medical", tokenUserId);
                    break;
                case "foster_staff":
                    // 饲养员获取寄养相关订单
                    orderList = sysOrderMapper.getOrderAll(null, "foster", null);
                    break;
                case "owner":
                    // 宠物主人获取自己的订单
                    orderList = sysOrderMapper.getOrderAll(tokenUserId, null, null);
                    break;
                default:
                    orderList = new java.util.ArrayList<>();
            }
        } finally {
            com.github.pagehelper.PageHelper.clearPage();
        }

        return new com.github.pagehelper.PageInfo<>(orderList);
    }


    //    根据订单id获取记录相关信息(医疗记录、寄养记录)
    public Result getRecordByOrderId(HttpServletRequest request, Integer orderId) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userRole = (String) request.getAttribute("role");

        if (claims == null || userRole == null) {
            return Result.error(401, "未授权访问");
        }

        String orderType = getOrderTypeById(orderId);

        switch (userRole) {
            case "admin":
                // 管理员可以查看所有类型记录
                if ("medical".equals(orderType)) {
                    return medicalRecordService.getMedicalRecordByOrderId(orderId);
                } else if ("foster".equals(orderType)) {
                    return fosterRecordService.getFosterRecordByOrderId(orderId);
                } else {
                    return Result.error(400, "不支持的订单类型");
                }

            case "veterinarian":
                // 兽医只能查看医疗记录
                if ("medical".equals(orderType)) {
                    return medicalRecordService.getMedicalRecordByOrderId(orderId);
                } else {
                    return Result.error(403, "无权限访问");
                }

            case "foster_staff":
                // 饲养员只能查看寄养记录
                if ("foster".equals(orderType)) {
                    return fosterRecordService.getFosterRecordByOrderId(orderId);
                } else {
                    return Result.error(403, "无权限访问");
                }

            case "owner":
                // 宠物主人可以查看自己的记录
                if ("medical".equals(orderType)) {
                    return medicalRecordService.getMedicalRecordByOrderId(orderId);
                } else if ("foster".equals(orderType)) {
                    return fosterRecordService.getFosterRecordByOrderId(orderId);
                } else {
                    return Result.error(400, "不支持的订单类型");
                }

            default:
                return Result.error(403, "无权限访问");
        }
    }

    //        根据订单ID获取订单类型
    public String getOrderTypeById(Integer orderId) {
        return sysOrderMapper.getOrderTypeById(orderId);
    }


    //获取所有订单信息
    public List<SysOrder> getAllSysOrder() {
        return sysOrderMapper.getAllSysOrder();
    }


    //    获取预约订单（根据订单状态判断）
    public List<ServiceOrderVO> getSysOrderByStatus() {
        return sysOrderMapper.getSysOrderByStatus("confirmed");

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

        /*调用通知接口，根据员工id发送预约成功通知给该员工
         * 设置通知内容
         * 设置通知接收者
         * 发送通知
         * */

        System.out.println("员工id:" + serviceOrder.getEmployeeId());
        SysNotification sysNotification = new SysNotification();
        sysNotification.setUserId(serviceOrder.getEmployeeId());
        sysNotification.setType(SysNotification.Type.ORDER);
        sysNotification.setTitle("订单通知");
        sysNotification.setContent("您有新的订单，请及时处理");

        sysNotificationService.createSysNotification(sysNotification);

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

    //    获取医疗预约订单（根据员工id查询订单后根据订单状态判断）
    public List<ServiceOrderVO> getSysOrderByEmployeeId(Long employeeId) {
        return sysOrderMapper.getSysOrderByEmployeeId(employeeId);
    }


}
