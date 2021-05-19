package xyz.dsemikin.wellfedcat.datastore.file.simple;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreException;
import xyz.dsemikin.wellfedcat.datastore.inmemory.DishStoreInMemory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class DishStoreSimpleFile implements DishStoreEditable {

    final DishStoreInMemory inMemoryStore;
    final Path filePath;

    public DishStoreSimpleFile(final Path filePath) {
        this.filePath = filePath;
        if (Files.exists(filePath)) {
            inMemoryStore = readDishStoreFromFile(filePath);
        } else {
            inMemoryStore = new DishStoreInMemory();
        }
    }

    private static DishStoreInMemory readDishStoreFromFile(final Path filePath) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (DishStoreInMemory) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DishStoreException("Failed to read dishStore file", e);
        }
    }

    public static void writeDishStoreToFile(
            final Path filePath,
            final DishStoreInMemory dishStore
    ) throws IOException {
        try (
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream(filePath.toFile()))
        ) {
            objectOutputStream.writeObject(dishStore);
        }
    }

    @Override
    public List<Dish> all() {
        return inMemoryStore.all();
    }

    @Override
    public Optional<Dish> getByName(String name) {
        return inMemoryStore.getByName(name);
    }

    @Override
    public Optional<Dish> getById(String publicId) {
        return inMemoryStore.getById(publicId);
    }

    @Override
    public boolean add(Dish dish) {
        try {
            final boolean result = inMemoryStore.add(dish);
            writeDishStoreToFile(filePath, inMemoryStore);
            return result;
        } catch (IOException e) {
            throw new DishStoreException("Failed to write dishes store file.", e);
        }
    }

    @Override
    public RemoveStatus removeByName(String name) {
        try {
            final RemoveStatus status = inMemoryStore.removeByName(name);
            writeDishStoreToFile(filePath, inMemoryStore);
            return status;
        } catch (IOException e) {
            throw new DishStoreException("Failed to write dishes store file.", e);
        }
    }

    @Override
    public RemoveStatus removeById(String publicId) {
        Optional<Dish> maybeDish = getById(publicId);
        if (maybeDish.isPresent()) {
            return removeByName(maybeDish.get().name());
        } else {
            return RemoveStatus.DOES_NOT_EXIST;
        }
    }
}
