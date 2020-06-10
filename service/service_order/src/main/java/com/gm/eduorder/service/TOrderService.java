package com.gm.eduorder.service;

import com.gm.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-04
 */
public interface TOrderService extends IService<TOrder> {

    TOrder createOrder(String courseId, String memberId);

}
