import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
public class App extends JFrame {
	Folder rootFolder = new Folder("OS (C:)", null);
    TreePanel treePanel;
    ListFolderPanel listFolderPanel;
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

        treePanel.tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePanel.tree.getLastSelectedPathComponent();
				if (selectedNode != null) {
					System.out.println("Selected: " + selectedNode.getUserObject().toString());
                    Folder selectedFolder = rootFolder.searchFolder(rootFolder, selectedNode.getUserObject().toString());
                    listFolderPanel.setCurrentFolder(selectedFolder);
                }
			}
		});

        this.add(splitPane, BorderLayout.CENTER);
    }

    void createFolder() {
        rootFolder.addFolder("Brida", rootFolder);
        rootFolder.addFolder("Kholil", rootFolder);
        rootFolder.addFolder("Free Code Camp", rootFolder);
        rootFolder.addFolder("Koding", rootFolder);
        rootFolder.addFolder("ozan", rootFolder);
        rootFolder.addFolder("pler", rootFolder);
        rootFolder.addFolder("sablon", rootFolder);
        
        rootFolder.childFolder.get(2).addFolder("Java", rootFolder.childFolder.get(0));
        rootFolder.childFolder.get(2).addFolder("Web", rootFolder.childFolder.get(0));
        rootFolder.childFolder.get(2).addFolder("C++", rootFolder.childFolder.get(0));
        rootFolder.childFolder.get(2).addFolder("Python", rootFolder.childFolder.get(0));
        rootFolder.childFolder.get(2).addFolder("Kotlin", rootFolder.childFolder.get(0));

        rootFolder.childFolder.get(2).childFolder.get(0).addFolder("bin", rootFolder.childFolder.get(2).childFolder.get(0));
        rootFolder.childFolder.get(2).childFolder.get(0).addFolder("lib", rootFolder.childFolder.get(2).childFolder.get(0));
        rootFolder.childFolder.get(2).childFolder.get(0).addFolder("src", rootFolder.childFolder.get(2).childFolder.get(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
