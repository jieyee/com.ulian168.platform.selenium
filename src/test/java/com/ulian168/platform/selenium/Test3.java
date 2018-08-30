package com.ulian168.platform.selenium;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class Test3 {
    
    public Class<?> dynamicType() {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader(),          
                      ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        return dynamicType;
    }
    public static void main(String[] args) throws Exception{
        Test3 t3 = new Test3();
        Class<?> dynamicType = t3.dynamicType();
        System.out.println(dynamicType.newInstance().toString());
    }
}
