package per.liuchengyin.alipay.service;

import per.liuchengyin.alipay.pojo.PayAsyncVo;
import per.liuchengyin.alipay.pojo.PayVo;

/**
 * @ClassName OrderService
 * @Description
 * @Author 柳成荫
 * @Date 2021/3/23
 */
public interface OrderService {
    PayVo getOrderPay(String orderSn);

    String handlePayResult(PayAsyncVo vo);
}
