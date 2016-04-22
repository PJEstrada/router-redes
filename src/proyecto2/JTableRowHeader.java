/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;


/**
 * JTable redimensionable
 * User: alberto
 * Date: 23/01/13
 * Time: 1:04
 */
public class JTableRowHeader {

private static final String COL_IMAGE = "Image";
private static final int MIN_ROW_HEIGHT = 12;

public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String filename = args[0];
        if (new File(filename).exists()) {
            System.out.println("Provide the path of an image");
        }
        final JTable table = new JTable(new Object[][]{
                                            {filename, filename, 5.5}
                                            , {filename, null, 7}
                                            , {"nothing", null, 12}}
                , new Object[]{"File", COL_IMAGE, "Number"});

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        TableColumn column = table.getColumn(COL_IMAGE);
        column.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value != null) {
                    ImageIcon image = new ImageIcon(value.toString());
                    setIcon(image);
                } else {
                    setIcon(null);
                }
                setHorizontalAlignment(JLabel.CENTER);
                return this;
            }
        });
        table.setRowHeight(0, 200);

        scrollPane.setRowHeaderView(buildRowHeader(table));

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
    }

    private static JList buildRowHeader(final JTable table) {
        final Vector<String> headers = new Vector<String>();
        for (int i = 0; i < table.getRowCount(); i++) {
            headers.add(String.valueOf((char) (i + 65)).toUpperCase());
        }
        ListModel lm = new AbstractListModel() {

            public int getSize() {
                return headers.size();
            }

            public Object getElementAt(int index) {
                return headers.get(index);
            }
        };

        final JList rowHeader = new JList(lm);
        rowHeader.setOpaque(false);
        rowHeader.setFixedCellWidth(50);


        MouseInputAdapter mouseAdapter = new MouseInputAdapter() {
            Cursor oldCursor;
            Cursor RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
            int index = -1;
            int oldY = -1;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                int previ = getLocationToIndex(new Point(e.getX(), e.getY() - 3));
                int nexti = getLocationToIndex(new Point(e.getX(), e.getY() + 3));
                if (previ != -1 && previ != nexti) {
                    if (!isResizeCursor()) {
                        oldCursor = rowHeader.getCursor();
                        rowHeader.setCursor(RESIZE_CURSOR);
                        index = previ;
                    }
                } else if (isResizeCursor()) {
                    rowHeader.setCursor(oldCursor);
                }
            }

            private int getLocationToIndex(Point point) {
                int i = rowHeader.locationToIndex(point);
                if (!rowHeader.getCellBounds(i, i).contains(point)) {
                    i = -1;
                }
                return i;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (isResizeCursor()) {
                    rowHeader.setCursor(oldCursor);
                    index = -1;
                    oldY = -1;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (isResizeCursor() && index != -1) {
                    int y = e.getY();
                    if (oldY != -1) {
                        int inc = y - oldY;
                        int oldRowHeight = table.getRowHeight(index);
                        if (oldRowHeight > 12 || inc > 0) {
                            int rowHeight = Math.max(MIN_ROW_HEIGHT, oldRowHeight + inc);
                            table.setRowHeight(index, rowHeight);
                            if (rowHeader.getModel().getSize() > index + 1) {
                                int rowHeight1 = table.getRowHeight(index + 1) - inc;
                                rowHeight1 = Math.max(12, rowHeight1);
                                table.setRowHeight(index + 1, rowHeight1);
                            }
                        }
                    }
                    oldY = y;
                }
            }

            private boolean isResizeCursor() {
                return rowHeader.getCursor() == RESIZE_CURSOR;
            }
        };
        rowHeader.addMouseListener(mouseAdapter);
        rowHeader.addMouseMotionListener(mouseAdapter);
        rowHeader.addMouseWheelListener(mouseAdapter);

        rowHeader.setCellRenderer(new RowHeaderRenderer(table));
        rowHeader.setBackground(table.getBackground());
        rowHeader.setForeground(table.getForeground());
        return rowHeader;
    }

    static class RowHeaderRenderer extends JLabel implements ListCellRenderer {

        private JTable table;

        RowHeaderRenderer(JTable table) {
            this.table = table;
            JTableHeader header = this.table.getTableHeader();
            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(CENTER);
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont());
            setDoubleBuffered(true);
        }

        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            setText((value == null) ? "" : value.toString());
            setPreferredSize(null);
            setPreferredSize(new Dimension((int) getPreferredSize().getWidth(), table.getRowHeight(index)));
            //trick to force repaint on JList (set updateLayoutStateNeeded = true) on BasicListUI
            list.firePropertyChange("cellRenderer", 0, 1);
            return this;
        }
    }
}