package cn.tuling.rpc.client.rpc;

import cn.tuling.rpc.remote.vo.RegisterServiceVo;
import org.springframework.stereotype.Service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *@author Mark老师
 *类说明：rpc框架的客户端代理部分
 */
@Service
public class RpcClientFrame {

    /*远程服务的代理对象，参数为客户端要调用的的服务*/
    public static<T> T getRemoteProxyObject(final Class<?> serviceInterface) throws Exception {
        /*获得远程服务的一个网络地址*/
        InetSocketAddress addr = //new InetSocketAddress("127.0.0.1",8778);
           getService(serviceInterface.getName());

        /*拿到一个代理对象，由这个代理对象通过网络进行实际的服务调用*/
        return (T)Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface},
                new DynProxy(serviceInterface,addr));
    }


    /*动态代理，实现对远程服务的访问*/
    private static class DynProxy implements InvocationHandler{
        private Class<?> serviceInterface;
        private InetSocketAddress addr;

        public DynProxy(Class<?> serviceInterface, InetSocketAddress addr) {
            this.serviceInterface = serviceInterface;
            this.addr = addr;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            Socket socket = null;
            ObjectInputStream inputStream = null;
            ObjectOutputStream outputStream = null;
            try{
                socket = new Socket();
                socket.connect(addr);
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                //方法所在类名接口名
                outputStream.writeUTF(serviceInterface.getName());
                //方法的名字
                outputStream.writeUTF(method.getName());
                //方法的入参类型
                outputStream.writeObject(method.getParameterTypes());
                //方法入参的值
                outputStream.writeObject(args);

                outputStream.flush();

                inputStream = new ObjectInputStream(socket.getInputStream());
                /*接受服务器的输出*/
                System.out.println(serviceInterface+" remote exec success!");
                return inputStream.readObject();

            }finally {
                if(socket!=null) socket.close();
                if(outputStream!=null) outputStream.close();
                if(inputStream!=null) inputStream.close();

            }
        }
    }



    /*----------------以下和动态获得服务提供者有关------------------------------*/

    private static Random r = new Random();

    /*获得远程服务的地址*/
    private static InetSocketAddress getService(String serviceName)
            throws Exception {
        //获得服务提供者的地址列表
        List<InetSocketAddress> serviceVoList = getServiceList(serviceName);
        InetSocketAddress addr
                = serviceVoList.get(r.nextInt(serviceVoList.size()));
        System.out.println("本次选择了服务器："+addr);
        return addr;
    }

    /*获得服务提供者的地址*/
    private static List<InetSocketAddress> getServiceList(String serviceName)
            throws Exception {
        Socket socket = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1",9999));

            output = new ObjectOutputStream(socket.getOutputStream());
            //需要获得服务提供者
            output.writeBoolean(true);
            //告诉注册中心服务名
            output.writeUTF(serviceName);
            output.flush();

            input = new ObjectInputStream(socket.getInputStream());
            Set<RegisterServiceVo> result
                    = (Set<RegisterServiceVo>)input.readObject();
            List<InetSocketAddress> services = new ArrayList<>();
            for(RegisterServiceVo serviceVo : result){
                String host = serviceVo.getHost();//获得服务提供者的IP
                int port = serviceVo.getPort();//获得服务提供者的端口号
                InetSocketAddress serviceAddr = new InetSocketAddress(host,port);
                services.add(serviceAddr);
            }
            System.out.println("获得服务["+serviceName
                    +"]提供者的地址列表["+services+"]，准备调用.");
            return services;
        }finally{
            if (socket!=null) socket.close();
            if (output!=null) output.close();
            if (input!=null) input.close();
        }

    }

}
