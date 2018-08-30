package com.ulian168.platform.selenium.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.ulian168.platform.selenium.web.action.step.WebActionStep;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.model.WebActionStepModel;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    public LogAspect() {
    }
//    @Pointcut("execution(public * *(..))")
//    public void webLog(){
//    }
    
    //@Pointcut("(execution(* execute*(..))) && (within(com.ulian168.platform.selenium.web..*))")
    @Pointcut("execution(* execute*(..)) && target(com.ulian168.platform.selenium.web.action.step.WebActionStep) ")
    private void webLog(){  
    }

    @Before("webLog()")
    public void doBefore(JoinPoint jp) throws Throwable {
        // 接收到请求，记录请求内容
        //logger.info("CLASS_METHOD : " + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        //这里递归死球了.
        //logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        Object arg = jp.getArgs()[0];
        WebContext ctx = (WebContext) arg;
        WebContext actionContext = ctx.getActionContext();
        ExtentReports extentReports = actionContext.getExtentReports();
        ExtentTest extentTestCase = actionContext.getExtentTestCase();
        Object target = jp.getTarget();
        WebActionStep step = (WebActionStep) target;
        WebActionStepModel stepModel = ctx.getWebActionStepModel();
        ExtentTest stepExtentTest = extentReports.startTest(stepModel.getFullDescription(), stepModel.getOpParams());
        actionContext.setExtentTestStep(stepExtentTest);
        extentTestCase.appendChild(stepExtentTest);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint jp, int ret) throws Throwable {
        // 处理完请求，返回内容
        //logger.info("方法的返回值 : " + ret);
        Object arg = jp.getArgs()[0];
        WebContext ctx = (WebContext) arg;
        WebActionStepModel stepModel = ctx.getWebActionStepModel();
        WebContext actionContext = ctx.getActionContext();
        ExtentTest extentTestStep = actionContext.getExtentTestStep();
        String fullDescription = stepModel.getFullDescription();
        if (ret == 0) {
            extentTestStep.log(LogStatus.PASS, fullDescription + "执行成功。");
        } else {
            extentTestStep.log(LogStatus.FAIL, fullDescription + "执行失败。 原因:", actionContext.getReason());
        }
    }

    //后置异常通知
//    @AfterThrowing("webLog()")
//    public void throwss(JoinPoint jp, Throwable e) {
//        logger.info("方法异常时执行.....");
//        Object arg = jp.getArgs()[0];
//        WebContext ctx = (WebContext) arg;
//        WebActionStepModel stepModel = ctx.getWebActionStepModel();
//        WebContext actionContext = ctx.getActionContext();
//        ExtentTest extentTestStep = actionContext.getExtentTestStep();
//        String fullDescription = stepModel.getFullDescription();
//        extentTestStep.log(LogStatus.FAIL, fullDescription + "执行失败。 原因:", e.getMessage());
//    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp){
        logger.info("方法最后执行.....");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        long t1 = System.currentTimeMillis();
        long t2 = 0L;
        try {
            Object result =  pjp.proceed();
            t2 = System.currentTimeMillis();
            logger.info("方法耗时:{}ms，结果是{}:", (t2 - t1), result);
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}