package com.steammachine.org.junit5.extensions.ignore;

import com.steammachine.org.junit5.extensions.ignore.implementation.IgnoreExtensionV6;
import com.steammachine.org.junit5.extensions.types.APILevel;
import com.steammachine.org.junit5.extensions.types.Api;


/**
 * Created 09/09/16 16:02
 *
 * @author Vladimir Bogodukhov
 **/
//@Api(value = APILevel.internal) class IgnoreExtension extends IgnoreExtensionV3 {
@Api(value = APILevel.internal) class IgnoreExtension extends IgnoreExtensionV6 {
}
