package org.junit.platform.launcher;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.TestTag;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Created 21/12/16 14:09
 *
 * @author Vladimir Bogodukhov
 **/
public class TestIdentifierHelper {

    /**
     * Возвращает новый объект с задаными данными
     *
     * @param original -
     * @param source   -
     * @return новый объект с задаными данными
     */
    public static TestIdentifier updateIdentifierSource(TestIdentifier original, TestSource source) {
        Objects.requireNonNull(original);
        return new TestIdentifier(
                original.getUniqueId(),
                original.getDisplayName(),
                source,
                original.getTags(),
                original.getType(),
                original.getParentId().orElse(null), original.getLegacyReportingName());
    }

}
