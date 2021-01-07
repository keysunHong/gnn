package com.hxtx.udp.client;

import com.hxtx.udp.server.UdpServer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 主线程的客户端
 *
 * @author sunweihong
 * @date 2021/1/7 15:15
 **/
public class MainThreadClient {
    public static void main(String[] args) {
        try {
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(64, 128, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(256), new ThreadPoolExecutor.CallerRunsPolicy());
            List<Map<String, String>> ips = new ArrayList<>();
            Map<String,String> ip1 = new HashMap<String,String>(2){{
                put("address","10.10.2.46");
                put("port","5000");
            }};
            Map<String,String> ip2 = new HashMap<String,String>(2){{
                put("address","10.10.2.46");
                put("port","6000");
            }};
            ips.add(ip1);
            ips.add(ip2);

            Future<String> serverFuture = threadPool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    UdpServer udpServer = new UdpServer();
                    udpServer.start(7000);
                    return "UDP服务启动成功";
                }
            });


            while (true) {
                List<Future> futureList = new ArrayList();
                for (Map<String, String> ip : ips) {
                    Future<String> future = threadPool.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            UdpClient udpClient = new UdpClient();
                            udpClient.connect(ip.get("address"), Integer.valueOf(ip.get("port")));
                            String message = ip.get("address") + ":" + ip.get("port") + "测试发送" + Instant.now().toEpochMilli();
                            udpClient.sendMessage(message);
                            return ip.get("address") + ":" + ip.get("port")+ " OK";
                        }
                    });
                    futureList.add(future);
                }
                for (Future<String> message : futureList) {
                    String messageData = message.get();
                    System.out.println(messageData);
                }
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
