package xyz.dsemikin.wellfedcat.datastore.file.simple;

import xyz.dsemikin.wellfedcat.datamodel.DishStoreException;
import xyz.dsemikin.wellfedcat.datastore.inmemory.DishStoreInMemory;
import xyz.dsemikin.wellfedcat.datastore.inmemory.MenuTimelineStoreInMemory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class StoreObjectProviderSimpleFile {

    private final StoreObjects storeObjects;
    private final Path filePath;

    public StoreObjectProviderSimpleFile(final Path filePath) {
        this.filePath = filePath;
        if (Files.exists(filePath)) {
            storeObjects = readStoreObjectsFromFile(filePath);
        } else {
            storeObjects = new StoreObjects();
        }
    }

    public DishStoreSimpleFile getDishStore() {
        return new DishStoreSimpleFile(this);
    }

    public MenuTimelineStoreSimpleFile getMenuTimelineStoreSimpleFile() {
        return new MenuTimelineStoreSimpleFile(this);
    }

    /** Only to be used by `DishStoreSimpleFile`. */
    /*package*/ DishStoreInMemory getDishStoreInMemory() {
        return storeObjects.dishStore;
    }

    /** Only to be used by `MenuTimelineStoreSimpleFile`. */
    /*package*/ MenuTimelineStoreInMemory getMenuTimelineStoreInMemory() {
        return storeObjects.menuTimelineStore;
    }

    /** Only to be used by `MenuTimelineStoreSimpleFile` and `DishStoreSimpleFile`. */
    /*package*/ void writeStoreObjectsToFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            objectOutputStream.writeObject(storeObjects);
        } catch (Exception e) {
            throw new DishStoreException("Failed to write store objects to file.", e);
        }
    }

    private static StoreObjects readStoreObjectsFromFile(final Path filePath) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (StoreObjects) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DishStoreException("Failed to read store objects file", e);
        }
    }

    private static class StoreObjects implements Serializable {
        private DishStoreInMemory dishStore = new DishStoreInMemory();
        private MenuTimelineStoreInMemory menuTimelineStore = new MenuTimelineStoreInMemory();
    }
}
