import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class ObserverTest {
  public static void main(String... args) throws IOException {
    File dir = new File("test");
    DirectoryChangeWatcher watcher = new DirectoryChangeWatcher(dir);
    watcher.addObserver((DirectoryChangeWatcher o, WatchEvent<?> arg) -> {
      System.out.println(arg.context());
    });
    ReactiveTableModel model = new ReactiveTableModel();
    watcher.addObserver(model);

    SwingUtilities.invokeLater(() -> {
      model.init(dir);
      JFrame frame = new JFrame("Files");

      JTable table = new JTable(model);
      frame.add(new JScrollPane(table));

      frame.setSize(800, 600);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setVisible(true);
    });
    watcher.run();
  }
}
