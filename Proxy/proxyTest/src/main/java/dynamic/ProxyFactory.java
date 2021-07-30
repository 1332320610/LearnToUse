package dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    private Object targetObject;

    public ProxyFactory(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getProxyInstance() {
        return (Object) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("开始保存");
                        Object returnValue = method.invoke(proxy, args);
                        System.out.println("保存结束");
                        return null;
                    }
                });
    }
}
