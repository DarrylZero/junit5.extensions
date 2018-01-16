package com.steammachine.org.junit5.extensions.ignore;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Vladimir Bogodukhov
 **/
public class IgnoredConditionExample2 {

    private class ContentIgnoreCondition extends DefaultIgnoreCondition {
        @Override
        public boolean evaluate() throws Exception {


            return false;
        }
    }

    @Test
    @Ignore(condition = ContentIgnoreCondition.class)
    void test8() {
    }

}
