package org.junit.jupiter.api.extension;

import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

/**
 * Created 28/09/16 13:58
 *
 * @author Vladimir Bogodukhov 
 **/
public class NamespaceFactory {

    public static Namespace createNameSpace(Object... parts) {
        return Namespace.create(parts);
    }

}
