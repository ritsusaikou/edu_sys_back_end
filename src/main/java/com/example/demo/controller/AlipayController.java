package com.example.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.demo.entity.vo.Result;
import com.example.demo.service.impl.ScoreServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
public class AlipayController {

    private final AlipayClient alipayClient;
    private final ScoreServiceImpl scoreServiceImpl;

    // 注入容器里面已经配置好的客户端
    public AlipayController(AlipayClient alipayClient, ScoreServiceImpl scoreServiceImpl) {
        this.alipayClient = alipayClient;
        this.scoreServiceImpl = scoreServiceImpl;
    }

    /**
     * 发起沙箱网页支付
     * 访问地址：http://127.0.0.1:9999/goPay
     */
    @GetMapping("/goPay")
    public String goPay(HttpServletResponse response, @RequestParam Long scoreId) throws AlipayApiException, IOException {
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();        // UUID生成唯一订单号，不能固定为xxx，重复订单号会报错
        String orderNo = UUID.randomUUID().toString().replace("-", "");
        String bizJson = String.format("" +  // 业务请求体，product_code固定 FAST_INSTANT_TRADE_PAY 电脑网页支付
                "{" +
                "\"out_trade_no\":\"%s\"," +
                "\"total_amount\":\"0.01\"," +
                "\"subject\":\"沙箱测试订单\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"" +
                "}", orderNo);
        payRequest.setBizContent(bizJson);
        // ====== 回调说明 ======
        // 127.0.0.1本地地址支付宝外网服务器无法访问，仅本地调试表单可以正常弹出支付页面
        // 后续想要接收支付回调通知，必须使用ngrok内网穿透公网地址
        Long studentId = StpUtil.getLoginIdAsLong();
        payRequest.setReturnUrl("http://localhost:9999/pay/return?scoreId=" + scoreId);
        String body = alipayClient.pageExecute(payRequest).getBody();
        return body;
    }

    //支付成功之后 页面同步回调（用户浏览器跳转回来）
    @GetMapping("/pay/return")
    public void payReturn(@RequestParam Long scoreId, HttpServletResponse resp) throws IOException {
        scoreServiceImpl.updateScoreToMax(scoreId);
        resp.sendRedirect("http://localhost:5173/score-query");
    }

    //支付宝服务器异步回调地址（真正修改订单状态的接口）
    @GetMapping("/pay/notify")
    public String payNotify() {
        // 此处编写验签、修改订单状态逻辑
        return "success";
    }

}