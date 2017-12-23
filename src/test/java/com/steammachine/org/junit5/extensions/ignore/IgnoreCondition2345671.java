package com.steammachine.org.junit5.extensions.ignore;

import com.steammachine.org.junit5.extensions.ignore.DefaultIgnoreCondition;

/**
 * @author Vladimir Bogodukhov.
 */
public class IgnoreCondition2345671 extends DefaultIgnoreCondition {
    public IgnoreCondition2345671() {
        super(true); /* Всегда игнорировать */
    }
}
