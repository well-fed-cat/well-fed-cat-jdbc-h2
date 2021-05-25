package xyz.dsemikin.wellfedcat.datamodel;

import java.time.LocalDate;

public interface MenuTimelineStoreEditable extends MenuTimelineStore {

    void acceptMenu(final Menu menu, final OnAlreadyDefined onAlreadyDefined);
    void acceptMenu(final DayMenu dayMenu, final OnAlreadyDefined onAlreadyDefined);

    /** @return  `true` if menu was removed, `false` if date did not have assigned menu. */
    boolean removeMenu(final LocalDate date);

    enum OnAlreadyDefined {
        OVERWRITE,
        SKIP,
        FAIL
    }
}
