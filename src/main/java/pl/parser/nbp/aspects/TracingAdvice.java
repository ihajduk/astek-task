package pl.parser.nbp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingAdvice {

    private final Logger log = LoggerFactory.getLogger(TracingAdvice.class);

    @Before("execution(* pl.parser.nbp.*.*.*(..))")
    public void currencyCodeToUpperCase(JoinPoint joinPoint) {
        log.info("entering " + joinPoint.getStaticPart().getSignature().toString());
    }

}
