
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;

public class App extends JFrame {

    Folder rootFolder = new Folder("OS (C:)", null);
    TreePanel treePanel;
    ListFolderPanel listFolderPanel;

    ImageIcon addIcon = new ImageIcon("assets/add.png");
    Image scaledAdd = addIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    ImageIcon scaledIconAdd = new ImageIcon(scaledAdd);
    ImageIcon deleteIcon = new ImageIcon("assets/delete.png");
    Image scaledDelete = deleteIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    ImageIcon scaledIconDelete = new ImageIcon(scaledDelete);

    JPanel containerButton = new JPanel();
    JButton addButton = new JButton(scaledIconAdd);
    JButton deleteButton = new JButton(scaledIconDelete);

    final int WIDTH = 850;
    final int HEIGHT = 475;

    App() {
        super("File Manager");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.createFolder();

        treePanel = new TreePanel(rootFolder);
        listFolderPanel = new ListFolderPanel(rootFolder);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, listFolderPanel);
        splitPane.setDividerSize(2);
        splitPane.setDividerLocation(200);
        splitPane.setBorder(null);

        addButton.setBackground(new Color(255, 255, 255));
        deleteButton.setBackground(new Color(255, 255, 255));
        addButton.setFocusable(false);
        deleteButton.setFocusable(false);
        // addButton.setBorder(null);
        // deleteButton.setBorder(null);

        containerButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        containerButton.add(addButton);
        containerButton.add(deleteButton);
        containerButton.setBackground(new Color(255, 255, 255));
        containerButton.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        addButton.addActionListener(e -> {
            System.out.println("Add button clicked");
            JFrame inputFrame = new JFrame();
            JTextField textInput = new JTextField();
            JButton done = new JButton("DONE");

            inputFrame.setSize(370, 150);
            inputFrame.setLocationRelativeTo(null);
            textInput.setPreferredSize(new Dimension(300, 25));

            done.addActionListener(e1 -> {
                String folderName = textInput.getText();
                if (folderName.equals("")) {
                    System.out.println("Folder name is empty");
                } else {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePanel.tree
                    .getLastSelectedPathComponent();
                    Folder selectedFolder = rootFolder.searchFolder(rootFolder,
                            selectedNode.getUserObject().toString());
                    if (selectedFolder != null) {
                        selectedFolder.addFolder(folderName, selectedFolder);
                        DefaultMutableTreeNode root = treePanel.makeTree(rootFolder);
                        treePanel.tree.setModel(new javax.swing.tree.DefaultTreeModel(root));
                        listFolderPanel.setCurrentFolder(selectedFolder);
    
                        this.revalidate();
                        this.repaint();
                        inputFrame.dispose();
                    }else{
                        System.out.println("No folder selected");
                    }
                }
            });
            
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            JLabel title = new JLabel("Enter Folder Name");
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setFont(new Font("Nirmala UI", 1, 14));
            title.setOpaque(false);
            title.setForeground(Color.BLACK);
            title.setPreferredSize(new Dimension(100, 25));

            panel.add(title);
            panel.add(textInput);
            panel.add(done);

            inputFrame.add(panel);
            inputFrame.setVisible(true);
        });
        deleteButton.addActionListener(e -> {
            int selectedIndex = this.listFolderPanel.getList().getSelectedIndex();
            if (selectedIndex != -1) {
                String folderName = this.listFolderPanel.getList().getSelectedValue().getText();

                this.listFolderPanel.getListModel().remove(selectedIndex);
                rootFolder.deleteFolder(folderName, rootFolder);
                DefaultMutableTreeNode root = treePanel.makeTree(rootFolder);
                treePanel.tree.setModel(new javax.swing.tree.DefaultTreeModel(root));

                this.revalidate();
                this.repaint();
            } else {
                System.out.println("No folder selected");
            }
        });

        treePanel.tree.addTreeSelectionListener((javax.swing.event.TreeSelectionEvent e) -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePanel.tree
                    .getLastSelectedPathComponent();
            if (selectedNode != null) {
                System.out.println("Selected: " + selectedNode.getUserObject().toString());
                Folder selectedFolder = rootFolder.searchFolder(rootFolder,
                        selectedNode.getUserObject().toString());
                listFolderPanel.setCurrentFolder(selectedFolder);
            }
        });

        this.add(containerButton, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
    }

    void createFolder() {
        rootFolder.addFolder("folder1", rootFolder);
        rootFolder.addFolder("folder2", rootFolder);
        rootFolder.addFolder("folder3", rootFolder);
        rootFolder.addFolder("folder4", rootFolder);

        rootFolder.childFolder.get(2).addFolder("folder1.1", rootFolder.childFolder.get(2));
        rootFolder.childFolder.get(2).addFolder("folder1.2", rootFolder.childFolder.get(2));
        rootFolder.childFolder.get(2).addFolder("folder1.3", rootFolder.childFolder.get(2));

        rootFolder.childFolder.get(2).childFolder.get(0).addFolder("folder1.1.1",
                rootFolder.childFolder.get(2).childFolder.get(0));
        rootFolder.childFolder.get(2).childFolder.get(0).addFolder("folder1.1.2",
                rootFolder.childFolder.get(2).childFolder.get(0));
        rootFolder.childFolder.get(2).childFolder.get(0).addFolder("folder1.1.3",
                rootFolder.childFolder.get(2).childFolder.get(0));

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
