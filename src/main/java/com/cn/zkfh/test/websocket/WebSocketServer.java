package com.cn.zkfh.test.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.zkfh.log.SysLog;

@ServerEndpoint(value = "/ws/asset/{userId}")
@Component
public class WebSocketServer {
		
	@PostConstruct
    public void init() {
        System.out.println("websocket 加载");
    }
	
    //private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private static int cnt = 0;

    
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session ,@PathParam("userId") String userId) {
    	
    	this.session = session;
        this.userId = userId;
       
        if(webSocketMap.containsKey(userId)){
        	 webSocketMap.remove(userId);
             webSocketMap.put(userId,this);
        }else {
        	webSocketMap.put(userId,this);
        	cnt = OnlineCount.incrementAndGet(); // 在线数加1
        }
       
        
        
        SysLog.info("用户连接:"+userId+",当前在线人数为:" +cnt);
      
        try {
			SendMessage("连接成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
       
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
    	webSocketMap.remove(session);
        int cnt = OnlineCount.decrementAndGet();
        SysLog.info("有连接关闭，当前连接数为：{"+cnt+"}");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     * @throws IOException 
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	SysLog.info("来自客户端的消息：{"+message+"}==用户id"+userId);
        // SendMessage(session, "收到消息，消息内容："+message);
    	if(StringUtils.isNotBlank(message)){
    		 //解析发送的报文
            JSONObject jsonObject = JSON.parseObject(message);
            //追加发送人(防止串改)
            jsonObject.put("fromUserId",this.userId);
            String toUserId=jsonObject.getString("toUserId");
            //传送给对应toUserId用户的websocket
            if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
                webSocketMap.get(toUserId).SendMessage(jsonObject.toJSONString());
            }else{
            	SysLog.error("请求的userId:"+toUserId+"不在该服务器上");
                //否则不在这个服务器上，发送到mysql或者redis
            }

    	}
    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
    	SysLog.error("发生错误：{"+error.getMessage()+"} , Session ID： {"+session.getId()+"}");
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param session
     * @param message
     * @throws IOException 
     */
    public  void SendMessage(String message) throws IOException {
    	

    	this.session.getBasicRemote().sendText(message);
       
      /*  try {
            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)",message,session.getId()));
        } catch (IOException e) {
        	SysLog.error("发送消息出错：{"+e.getMessage()+"}");
            e.printStackTrace();
        }*/
    }
    
    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
    	SysLog.info("发送消息到:"+userId+"，报文:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).SendMessage(message);
        }else{
        	SysLog.error("用户"+userId+",不在线！");
        }
    }


   
}
