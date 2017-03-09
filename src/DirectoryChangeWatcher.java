import com.sun.nio.file.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DirectoryChangeWatcher extends
    Observable<DirectoryChangeWatcher, WatchObserver, WatchEvent<?>>
    implements Runnable {
  private final WatchService watcher;
  public DirectoryChangeWatcher(File dir) throws IOException {
    if (!dir.isDirectory()) throw new IllegalArgumentException("not a dir: " + dir);

    watcher = FileSystems.getDefault().newWatchService();
    dir.toPath().register(watcher,
        new WatchEvent.Kind<?>[] {
        StandardWatchEventKinds.ENTRY_MODIFY,
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_DELETE},
        SensitivityWatchEventModifier.HIGH);
  }

  public void run() {
    while(true) {
      try {
        WatchKey key = watcher.take();
        for (WatchEvent<?> watchEvent : key.pollEvents()) {
          setChanged();
          notifyObservers(watchEvent);
        }
        key.reset();
      } catch (InterruptedException cancelled) {
        return;
      }
    }
  }
}
