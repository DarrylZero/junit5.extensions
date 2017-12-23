package com.steammachine.org.junit5.extensions.ignore;

import com.steammachine.org.junit5.extensions.ignore.DefaultIgnoreCondition;
import com.steammachine.org.junit5.extensions.ignore.IgnoreJ5;
import org.junit.jupiter.api.Test;

/**
 * Created 25/10/16 12:58
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
    @IgnoreJ5(condition = ContentIgnoreCondition.class)
    void test8() {
    }

}
