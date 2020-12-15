package io.kimmking.rpcfx.api;

public abstract class RpcInvoker {
    public abstract Object invoke(Object instance, String method, Object[] params);
}
