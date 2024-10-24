import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

class ListFolderPanel extends JPanel {

    private Folder currentFolder;
    private DefaultListModel<JLabel> listModel;
    private JList<JLabel> list;

    ListFolderPanel(Folder currentFolder) {
        super();
        this.currentFolder = currentFolder;
        this.setLayout(new BorderLayout());
        // get the name of child folder
        this.listModel = new DefaultListModel<>();
        for (Folder child : this.currentFolder.childFolder) {
            JLabel label = new JLabel(child.nama);
            this.listModel.addElement(label);
        }

        this.list = new JList<>(listModel);
        this.list.setLayoutOrientation(JList.VERTICAL_WRAP);
        this.renderList();
        
        this.add(this.list, BorderLayout.CENTER);
    }
    
    void setCurrentFolder(Folder current) {
        this.listModel.clear();
        this.removeAll();
        if (current.childFolder.isEmpty()) {
            JLabel label = new JLabel("This folder is empty");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.NORTH);
            label.setBackground(Color.decode("#ffffff"));
            label.setOpaque(true);
            label.setBorder(new MatteBorder(10, 0, 0, 0, label.getBackground()));
            this.add(label, BorderLayout.CENTER);
        } else {
            for (Folder child : current.childFolder) {
                this.listModel.addElement(new JLabel(child.nama));
            }
            this.add(this.list, BorderLayout.CENTER);
        }

        this.revalidate();
        this.repaint();
    }

    void renderList() {
        this.list.setCellRenderer(new ListCellRenderer<JLabel>() {
            @Override
            public JLabel getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                // Set the text and icon for the JLabel

                ImageIcon folderIcon = new ImageIcon("assets/folder.png");
                Image scaledFolder = folderIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledIconFolder = new ImageIcon(scaledFolder);

                JLabel label = new JLabel(value.getText(), scaledIconFolder, JLabel.LEFT);

                // Customize appearance
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                    label.setOpaque(true);
                } else {
                    label.setBackground(list.getBackground());
                    label.setForeground(list.getForeground());
                    label.setOpaque(true);
                }

                // Set the font and alignment
                label.setPreferredSize(new Dimension(150, 50));
                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new MatteBorder(0, 5, 0, 0, label.getBackground()));
                return label;
            }
        });
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public DefaultListModel<JLabel> getListModel() {
        return listModel;
    }

    public JList<JLabel> getList() {
        return list;
    }
}
