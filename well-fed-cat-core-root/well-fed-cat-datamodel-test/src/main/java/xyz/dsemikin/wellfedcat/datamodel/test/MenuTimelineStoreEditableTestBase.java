package xyz.dsemikin.wellfedcat.datamodel.test;

import org.junit.jupiter.api.Test;
import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class MenuTimelineStoreEditableTestBase {

    protected abstract MenuTimelineStoreEditable getMenuTimelineStore();

    protected List<DayMenu> allDayMenus() {
        // TODO
        return new ArrayList<>();
    }

    @Test
    public void test_dummy() {
        assertTrue(true);
    }
}
