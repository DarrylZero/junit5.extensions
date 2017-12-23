package com.steammachine.org.junit5.extensions.guice.res.normal.shareddata.objects;

/**
 * @author Vladimir Bogodukhov
 *         Created 10/12/15 16:25
 */
public interface RWSingletone {
    public static final RWSingletone NULL_OBJECT = new RWSingletone() {

        @Override
        public String value() {
            return null;
        }

        @Override
        public void setValue(String value) {
            failIfNotOverriden();
        }

        @Override
        public void failIfNotOverriden() {
            throw new IllegalStateException();
        }
    };

    String value();

    void setValue(String value);

    void failIfNotOverriden();

}
