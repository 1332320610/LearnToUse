package cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory implements MethodInterceptor {

    private Object targetObject;//维护一个目标对象

    public CglibProxyFactory(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getProxyInstance() {
        //工具类
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(targetObject.getClass());
        //设置回调函数
        enhancer.setCallback(this);
        //创建子类代理
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始保存");
        Object returnValue = method.invoke(targetObject, objects);
        return null;
    }


}
