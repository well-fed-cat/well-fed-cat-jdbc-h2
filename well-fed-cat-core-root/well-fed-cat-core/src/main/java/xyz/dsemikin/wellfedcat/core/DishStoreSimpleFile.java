package xyz.dsemikin.wellfedcat.core;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class DishStoreSimpleFile implements DishStore {

    final DishStoreInMemory inMemoryStore;
    final Path filePath;

    public DishStoreSimpleFile(final Path filePath) {
        this.filePath = filePath;
        if (Files.exists(filePath)) {
            try {
                inMemoryStore = readDishStoreFromFile(filePath);
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalStateException("Failed to read dishes store from file.", e);
            }
        } else {
            inMemoryStore = new DishStoreInMemory();
        }
    }

    private static DishStoreInMemory readDishStoreFromFile(final Path filePath)
            throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            return (DishStoreInMemory) objectInputStream.readObject();
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
    public List<Dish> allDishes() {
        return inMemoryStore.allDishes();
    }

    @Override
    public Optional<Dish> dish(String name) {
        return inMemoryStore.dish(name);
    }

    @Override
    public void addDish(Dish dish) {
        inMemoryStore.addDish(dish);
        try {
            writeDishStoreToFile(filePath, inMemoryStore);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write dishes store file.", e);
        }
    }

    @Override
    public void removeDish(String name) {
        inMemoryStore.removeDish(name);
        try {
            writeDishStoreToFile(filePath, inMemoryStore);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write dishes store file.", e);
        }
    }
}
