package org.directwebremoting.datasync;

import java.util.List;

import org.directwebremoting.io.Item;
import org.directwebremoting.io.ItemUpdate;
import org.directwebremoting.io.MatchedItems;
import org.directwebremoting.io.RawData;
import org.directwebremoting.io.StoreChangeListener;
import org.directwebremoting.io.StoreRegion;

/**
 * A raw StoreProvider works on a global basis, so all users share the same
 * data. We envisage a number of ways to sub-divide this. For example, one
 * store per HttpSession, one store per ScriptSession, etc. (The PerX versions
 * of the StoreProvider class). This provides an abstract implementation of the
 * PerX behavior.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public abstract class AbstractPerXStoreProvider<T> implements StoreProvider<T>
{
    /**
     * Internal method to find or create a StoreProvider for a given user.
     * @return ...
     */
    protected abstract StoreProvider<T> getStoreProvider();

    public void put(String itemId, RawData rawData)
    {
        getStoreProvider().put(itemId, rawData);
    }

    public void put(String itemId, T value)
    {
        getStoreProvider().put(itemId, value);
    }

    public void unsubscribe(StoreChangeListener<T> listener)
    {
        getStoreProvider().unsubscribe(listener);
    }

    public void update(List<ItemUpdate> changes)
    {
        getStoreProvider().update(changes);
    }

    public Item viewItem(String itemId)
    {
        return getStoreProvider().viewItem(itemId);
    }

    public Item viewItem(String itemId, StoreChangeListener<T> listener)
    {
        return getStoreProvider().viewItem(itemId, listener);
    }

    public MatchedItems viewRegion(StoreRegion region)
    {
        return getStoreProvider().viewRegion(region);
    }

    public MatchedItems viewRegion(StoreRegion region, StoreChangeListener<T> listener)
    {
        return getStoreProvider().viewRegion(region, listener);
    }
}
