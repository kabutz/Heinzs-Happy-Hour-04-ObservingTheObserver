import java.nio.file.*;
import java.util.*;

public interface WatchObserver extends
    Observer<DirectoryChangeWatcher, WatchObserver, WatchEvent<?>> {
}
