package com.moon.eurekaconsumer.conf;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcessors implements BeanPostProcessor, BeanDefinitionRegistryPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        if (bean instanceof TestService ){
            ProxyFactory factoryBean = new ProxyFactory();
            factoryBean.setInterfaces(bean.getClass().getInterfaces());
            factoryBean.setTarget(bean);
            return factoryBean.getProxy();
        }
        return bean;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(TestService.class).addPropertyValue("name", "111").getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("testService", beanDefinition);
        System.out.println("postProcessBeanDefinitionRegistry.......");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
