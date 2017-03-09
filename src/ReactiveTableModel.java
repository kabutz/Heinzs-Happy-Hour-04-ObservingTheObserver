import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ReactiveTableModel extends AbstractTableModel implements WatchObserver {
  private String[] files = {};

  public void init(File dir) {
    files = dir.list();
  }

  public String getColumnName(int column) {
    return "Files";
  }

  public int getRowCount() {
    return files.length;
  }

  public int getColumnCount() {
    return 1;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    return files[rowIndex];
  }

  public void update(DirectoryChangeWatcher o, WatchEvent<?> event) {
    SwingUtilities.invokeLater(() -> {
      if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE ||
          event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
        Path path = (Path) event.context();
        Set<String> names = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Collections.addAll(names, files);
        names.add(path.toFile().getName());
        files = names.toArray(new String[0]);
      } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
        Path path = (Path) event.context();
        Set<String> names = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Collections.addAll(names, files);
        names.remove(path.toFile().getName());
        files = names.toArray(new String[0]);
      }
      fireTableDataChanged();
    });
  }
}
