package dbps.dbps.util;

import javafx.concurrent.Task;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AsyncTaskUtil {
    public static <T> Task<T> createTask(Supplier<T> supplier) {
        return new Task<>() {
            @Override
            protected T call() throws Exception {
                return supplier.get();
            }
        };
    }

    public static <T> CompletableFuture<T> createCompletableFuture(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public static void runOnUIThread(Runnable runnable) {
        javafx.application.Platform.runLater(runnable);
    }
} 