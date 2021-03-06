**http及https协议原理**

1. http和https的区别

   ```code
   Https是基于Http协议，在传输数据时加入SSL/TLS安全层传输协议，保证数据传输的安全性。
   ```

   

2. 简单说说https是如何保证安全传输的

   ```CODE
   通过公钥在发送端加密，私钥在接收端解密，公钥通过CA机构生成证书，保证了公钥不被篡改。
   ```

   

3. https是不是绝对安全的？有没有办法被破解？

   ```code
   不是绝对安全，证书如果被人为篡改，会引起安全问题.
   ```

4. http无状态协议，怎么理解无状态协议。如何实现有状态的请求

   ```code
   无状态是指，协议中不会保留历史的请求信息，如果记录历史信息，可以通过session或cookie记录用户历史信息。
   ```

   

5. 说说http协议中的302状态码的作用

   ```code
   状态码302表示请求被转发
   ```

   

6. 304缓存原理

   ```code
   response响应中，设置Cache-Control告诉浏览器资源的缓存策略，生成Etag标签(资源状态标签),当浏览器第二次请求资源时，如果服务器上的资源没有变化，返回304
   ```

   

7. http协议1.0和http协议1.1的区别

   ```code
   Http1.1为长连接
   ```

   

8. 如何保证基于http协议的接口的安全性

   ```code
   添加过滤器，过滤非法字符，重要信息加密传输，添加安全认证
   ```

   

9. http协议上传文件，数据如何传输？

   ```code
   在客户端转为二进制数据进行传输
   ```

   

10. 说说http协议的优缺点

    ```code
    优点：连接简单快速，支持任意类型参数
    缺点：明文传输、不保存状态
    ```

    

11. 一次http请求的完整交互流程

    ```code
    第一次握手：建立连接时，客户端发送syn包（syn=j）到服务器，并进入SYN_SENT状态
    第二次握手：服务器收到syn包，确认客户端的SYN（ack=j+1），同时自己也发送一个SYN包（seq=k），即SYN+ACK包，服务器进入SYN_RECV状态；
    第三次握手：客户端收到服务器的SYN+ACK包，向服务器发送确认包ACK(ack=k+1），此包发送完毕，客户端和服务器进入ESTABLISHED（TCP连接成功）状态，完成三次握手。
    ```

    