package tw.brian.hw.general.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Brian Su <memory0318@gmail.com>
 * @description:
 * @date: 2022/12/17
 */
@Aspect
@Component
public class RestControllerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerAspect.class);

    /**
     * Declare pointcut
     */
    @Pointcut("within(tw.brian.hw.bpi.controller..*) || within(tw.brian.hw.currency_mapping.controller..*)")
    public void pointcutControllers() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
        "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
        "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
        "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void pointcutRestFunctions() {}

//    @Before("pointcutControllers() && pointcutRestFunctions()")
//    public void logControllerRequest(JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        GetMapping mapping = signature.getMethod().getAnnotation(GetMapping.class);
//        LOGGER.info("log request");
//    }
//
//    @AfterReturning("pointcutControllers() && pointcutRestFunctions()")
//    public void logResponse(JoinPoint joinPoint) {
//        LOGGER.info("log response");
//    }
}
