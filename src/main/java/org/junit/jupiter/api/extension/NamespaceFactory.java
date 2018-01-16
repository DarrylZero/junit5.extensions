package org.junit.jupiter.api.extension;

import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

/**
 *
 * @author Vladimir Bogodukhov 
 **/
public class NamespaceFactory {

    public static Namespace createNameSpace(Object... parts) {
        return Namespace.create(parts);
    }

}
