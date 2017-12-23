package com.steammachine.org.junit5.extensions.types;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created 28/09/16 08:51
 *
 * @author Vladimir Bogodukhov
 **/
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.SOURCE)
public @interface Api {

    APILevel value();

}