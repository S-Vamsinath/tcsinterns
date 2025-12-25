package com.tcs.tcsinterns.utility;

import com.tcs.tcsinterns.exception.TcsInternException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Log LOGGER=LogFactory.getLog(LoggingAspect.class);
    @AfterThrowing(
            pointcut = "execution(* com.tcs.tcsinterns.service.ProjectAllocationServiceImpl.*(..))"
              ,throwing = "exception"
)
public void logServiceException(TcsInternException exception){
    LOGGER.error(exception.getMessage(),exception);
    }
}
