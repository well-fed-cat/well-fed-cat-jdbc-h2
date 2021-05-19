package xyz.dsemikin.wellfedcat.utils;

public class Utils {

    public static void assertState(final boolean statement, final String commentOnError) {
        assert statement : commentOnError;
        //noinspection ConstantConditions
        if (!statement) {
            throw new IllegalStateException(commentOnError);
        }
    }

    public static void assertState(final boolean statement) {
        assertState(statement, "");
    }
}
