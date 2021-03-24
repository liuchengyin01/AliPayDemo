package per.liuchengyin.alipay.pojo;

/**
 * @ClassName TransactionStatus
 * @Description 交易状态(决定是否触发异步通知) - 支付宝官方文档：https://opendocs.alipay.com/open/270/105902
 * @Author 柳成荫
 * @Date 2021/3/24
 */
public interface TransactionStatus {
    /**
     * 触发通知的条件
     * TRADE_FINISHED - false（不触发通知）
     * TRADE_SUCCESS - true（触发通知）
     * WAIT_BUYER_PAY - false（不触发通知）
     * TRADE_CLOSED - false（不触发通知）
     */

    /** 交易创建，等待买家付款 */
    String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    /** 未付款交易超时关闭，或支付完成后全额退款 */
    String TRADE_CLOSED = "TRADE_CLOSED";
    /** 交易支付成功 */
    String TRADE_SUCCESS = "TRADE_SUCCESS";
    /** 交易结束，不可退款 */
    String TRADE_FINISHED = "TRADE_FINISHED";


}
