package com.tioclient.controller;

import com.tioclient.Const;
import com.tioclient.HelloClientAioHandler;
import com.tioclient.HelloPacket;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;



@RestController
@RequestMapping("hello")
public class HelloClientController {

    //服务器节点  指定ip地址  端口
    public static Node serverNode = new Node(Const.SERVER, Const.PORT);

    //handler处理器, 包括编码、解码、消息处理
    public static ClientAioHandler tioClientHandler = new HelloClientAioHandler();

    //事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
    public static ClientAioListener aioListener = null;

    //断链后自动连接的，不想自动连接请设为null
    private static ReconnConf reconnConf = new ReconnConf(5000L);

    //一组连接共用的上下文对象
    public static ClientGroupContext clientGroupContext = new ClientGroupContext(tioClientHandler, aioListener, reconnConf);

    public static TioClient tioClient = null;
    public static ClientChannelContext clientChannelContext = null;

    static {
        try {
            //链接对象
            clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);//设置心跳超时
            //客户端
            tioClient = new TioClient(clientGroupContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("connect")
    public String connect() throws Exception {
        //链接指定节点
        clientChannelContext = tioClient.connect(serverNode);
        return "链接成功";
    }

    @GetMapping("send")
    public String send(String message) throws Exception {
        HelloPacket packet = new HelloPacket();
        packet.setBody(message.getBytes(HelloPacket.CHARSET));
        Tio.send(clientChannelContext, packet);
        return "发送成功";
    }

    @GetMapping("close")
    public String close(){
        Tio.close(clientChannelContext,"XXX退出直播间");
        return "退出成功";
    }




}
