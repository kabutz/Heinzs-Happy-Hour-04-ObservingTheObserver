import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingInfinity extends JFrame {
    private final JButton go = new JButton("Go Crazy!");
    private final JButton stop = new JButton("Stop Madness!");
    private final JButton button1 = new JButton("Button1");
    private final JButton button2 = new JButton("Button2");

    public SwingInfinity() throws HeadlessException {
        go.addActionListener(createGoActionListener(button1));
        final FocusListener button1Focus = createFocusListener(button2);
        button1.addFocusListener(button1Focus);
        button2.addFocusListener(createFocusListener(button1));
        stop.addActionListener(createStopAction(button1, button1Focus, go));

        JPanel panel = new JPanel();
        panel.add(go);
        panel.add(stop);
        panel.add(button1);
        panel.add(button2);
        add(panel);
    }

    private static ActionListener createStopAction(final JButton button1,
                                                   final FocusListener button1Focus,
                                                   final JButton go) {
        return e -> {
            button1.removeFocusListener(button1Focus);
            go.grabFocus();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    button1.addFocusListener(button1Focus);
                }
            });
        };
    }

    private static FocusListener createFocusListener(final JComponent component) {
        return new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                component.grabFocus();
            }
        };
    }

    private static ActionListener createGoActionListener(final JComponent component) {
        return e -> component.grabFocus();
    }

    public static void main(String... args) {
        SwingInfinity si = new SwingInfinity();
        si.setDefaultCloseOperation(EXIT_ON_CLOSE);
        si.setSize(400, 400);
        si.setVisible(true);
    }
}