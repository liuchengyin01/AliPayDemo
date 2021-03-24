package per.liuchengyin.alipay.service.impl;

import org.springframework.stereotype.Service;
import per.liuchengyin.alipay.pojo.PayAsyncVo;
import per.liuchengyin.alipay.pojo.PayVo;
import per.liuchengyin.alipay.pojo.TransactionStatus;
import per.liuchengyin.alipay.service.OrderService;

/**
 * @ClassName OrderServiceImpl
 * @Description 订单业务类 - 实现类
 * @Author 柳成荫
 * @Date 2021/3/23
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public PayVo getOrderPay(String orderSn) {
        // 模拟一个订单
        PayVo payVo = new PayVo();
        // 订单号
        payVo.setOutTradeNo(orderSn);
        payVo.setSubject("测试商品");
        payVo.setBody("这是测试支付宝沙箱环境专用的测试商品！");
        payVo.setTotalAmount("99.99");
        return payVo;
    }

    /**
     * 处理支付宝支付结果
     * @param vo 支付信息
     * @return
     */
    @Override
    public String handlePayResult(PayAsyncVo vo) {
        // 可以将vo里的相关支付信息保存到数据库....
        // 判断是否支付成功 - 获取状态，只有以下两种状态是支付成功了
        if (TransactionStatus.TRADE_SUCCESS.equals(vo.getTrade_status()) || TransactionStatus.TRADE_FINISHED.equals(vo.getTrade_status())) {
            // 支付成功状态 - 可以根据订单号修改数据库里订单状态为已支付....
            String outTradeNo = vo.getOut_trade_no();
        }
        return "success";
    }
}
