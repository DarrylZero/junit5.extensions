package com.steammachine.org.junit5.extensions.ignore.predefined.implementation;

import com.steammachine.org.junit5.extensions.ignore.Cached;
import com.steammachine.org.junit5.extensions.ignore.IgnoreCondition;

/**
 * Created 24/11/16 12:36
 *
 * @author Vladimir Bogodukhov
 **/
@Cached(false)
public class MinVersionConditionVer1 implements IgnoreCondition {

    public static final int VERSION = 1;

    private int minversion;
    private final int currentVersion;

    public MinVersionConditionVer1(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    public MinVersionConditionVer1() {
        this(0);
    }

    public void setMinversion(int minversion) {
        this.minversion = minversion;
    }

    @Override
    public boolean evaluate() throws Exception {
        return currentVersion < minversion;
    }
}

