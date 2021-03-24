package per.liuchengyin.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import per.liuchengyin.alipay.config.AliPayTemplate;
import per.liuchengyin.alipay.pojo.PayAsyncVo;
import per.liuchengyin.alipay.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName OrderPayedListener
 * @Description 支付宝异步回调Controller - 监听器
 * @Author 柳成荫
 * @Date 2021/3/23
 */
@RestController
@Slf4j
public class OrderPayedListener {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AliPayTemplate alipayTemplate;

    /**
     * 异步结果通知回调方法
     * @param vo 支付宝异步通知返回的结果
     * @param request request
     * @return 是否验签通过
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @PostMapping("/payed/notify")
    public String handleAlipayed(PayAsyncVo vo, HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        // 验证签名
        boolean signVerified = checkV1(request);
        if(signVerified){
            // 签名验证通过
            log.info("签名验证成功！");
            // 处理支付结果
            // 只要收到了支付宝给我们异步的通知，告诉我们订单支付成功，返回success，支付宝就不会再通知
            return orderService.handlePayResult(vo);
        }else{
            // 只要回复的不是success，就会一直通知
            log.info("签名验证失败！");
            return "error";
        }
    }

    /**
     * 校验签名
     * @param request request
     * @return 是否验证通过
     * @throws AlipayApiException 支付异常
     */
    private boolean checkV1(HttpServletRequest request) throws AlipayApiException {
        /*
         * 支付宝验证签名
         * 获取支付宝POST过来反馈信息
         */
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 调用SDK验证签名
        return AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipayPublicKey(), alipayTemplate.getCharset(), alipayTemplate.getSignType());
    }
}
