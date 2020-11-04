package io.github.kimmking.gateway;


import io.github.kimmking.gateway.inbound.HttpInboundServer;

public class NettyServerApplication {
    
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    
    public static void main(String[] args) {
        String proxyPort = System.getProperty("proxyPort","8888");

          //  http://localhost:8888/api/hello  ==> gateway API
          //  http://localhost:8088/api/hello  ==> backend service
          //  http://127.0.0.1:8088/api/hello  ==> backend service
    
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(port);
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
