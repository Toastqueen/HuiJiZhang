package com.toast.springcloud.filter;
import com.toast.springcloud.GatewayJwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取Request、Response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //获取请求的URI
        String path = request.getURI().getPath();

//        如果是登录等开放的微服务则直接放行
        if (path.startsWith("/user/register")||path.startsWith("/user/login")||path.startsWith("/user/sendCode")||path.startsWith("/user/verifyCode")) {
            //放行
            return chain.filter(exchange);
        }


        // 获取 token 参数
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        System.out.println("token为"+token);

        //如果头文件中没有令牌信息，则从请求参数中获取
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        //如果请求参数中没有令牌信息，则从cookie中获取
        if (StringUtils.isEmpty(token)) {
            HttpCookie httpCookie= request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if(httpCookie!=null){
                token =httpCookie.getValue();
            }
        }
        //如果令牌为空，则输出错误代码
        if (StringUtils.isEmpty(token)) {
            //设置方法不允许被访问，405错误代码
            response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            return response.setComplete();
        }

        //解析令牌数据
        try {
            Claims claims = GatewayJwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败或过期，响应401错误
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {

        return 0;
    }
}
