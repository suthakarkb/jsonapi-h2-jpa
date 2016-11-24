package com.sc.csl.retail.crm.helper;

import com.sc.csl.retail.crm.annotation.Audited;
import org.springframework.stereotype.Component;

@Component
public class AuditTrailTestHelper {

    @Audited
    public void invokeSimpleArgument(String param1) {
        System.out.println("i'm here. " + param1);
    }
}
