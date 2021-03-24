package per.liuchengyin.alipay.controller;

import com.alipay.api.AlipayApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import per.liuchengyin.alipay.config.AliPayTemplate;
import per.liuchengyin.alipay.pojo.PayVo;
import per.liuchengyin.alipay.service.OrderService;
import per.liuchengyin.alipay.util.SnowflakeIdWorker;

/**
 * @ClassName PayWebController
 * @Description 支付调用的Controller
 * @Author 柳成荫
 * @Date 2021/3/23
 */
@Controller
@Slf4j
public class PayWebController {
    @Autowired
    private AliPayTemplate aliPayTemplate;
    @Autowired
    private OrderService orderService;

    /**
     * 调用该方法会去到支付宝支付
     * @param orderSn 订单号
     * @return 返回的是支付宝的页面 - produces = "text/html"
     * @throws AlipayApiException 支付异常
     */
    @GetMapping(value = "/payOrder", produces = "text/html")
    @ResponseBody
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        // 这里订单号应该是前端传过来的，即orderSn
        // 但是为了Demo方便测试，前端传来的是一个固定的数字(按理说前端应该传的是具体订单号)
        // 支付宝这个订单号又不能重复，所以在这个方法里实际使用了一个雪花算法生成的订单号，即noStr
        String noStr = SnowflakeIdWorker.generateId() + "";
        log.info("订单ID：{}", noStr);
        // 正常的应该是传入前端传来的orderSn
        PayVo payVo = orderService.getOrderPay(noStr);
        return aliPayTemplate.pay(payVo);
    }

}
