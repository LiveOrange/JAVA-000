package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.HttpHeadRequestFilter;
import io.github.kimmking.gateway.outbound.httpclient.HttpOutboundHandler;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RoundEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.util.ArrayList;
import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpEndpointRouter router;
    private HttpOutboundHandler handler;
    private HttpHeadRequestFilter filter;

    public HttpInboundHandler() {
        this.filter = new HttpHeadRequestFilter();
        this.router = new RoundEndpointRouter();
        this.handler = new HttpOutboundHandler();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            filter.filter(fullRequest, ctx);
            String server = router.route(getProxyServers());
            handler.handle(fullRequest, ctx, server);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private List<String> getProxyServers() {
        List<String> proxyServers = new ArrayList<>();
        proxyServers.add("http://localhost:8088");
        proxyServers.add("http://127.0.0.1:8088");
        return proxyServers;
    }
}
