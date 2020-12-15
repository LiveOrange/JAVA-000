package io.kimmking.rpcfx.server;

import io.kimmking.rpcfx.api.RpcInvoker;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.cmmon.InvokerFactory;

public class RpcfxInvoker {
    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();
        try {
            Object service = resolver.resolve(serviceClass);
            RpcInvoker rpcInvoker = InvokerFactory.getInvoker(service.getClass());
            Object result = rpcInvoker.invoke(service, request.getMethod(), request.getParams());
            response.setResult(result);
            response.setStatus(true);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }
}
