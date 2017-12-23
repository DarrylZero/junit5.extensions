package com.steammachine.org.junit5.extensions.testresult.implementation;

import org.junit.Assert;
import org.junit.Test;
import ru.socialquantum.common.utils.enumerations.EnumComparisonUtils;
import com.steammachine.org.junit5.extensions.testresult.ReflectionsCall;

/**
 * @author Vladimir Bogodukhov.
 */
public class ReflectionsCallCheck {

    enum InvolvedMethodTemplate {
        success,
        failure,
        skipped,
        aborted,
        successful,
        failed,
        containerSuccess,
        containerFailure,
    }

    @Test
    public void testName() {
        Assert.assertEquals("ReflectionsCall",
                ReflectionsCall.class.getName());
    }

    @Test
    public void testName2() {
        Assert.assertEquals("ReflectionsCall$InvolvedMethod",
                ReflectionsCall.InvolvedMethod.class.getName());
    }

    @Test
    public void testInvolvedMethodNameIntegrity() {
        EnumComparisonUtils.checkIfEnumsEqual(InvolvedMethodTemplate.class, ReflectionsCall.InvolvedMethod.class);
    }


}