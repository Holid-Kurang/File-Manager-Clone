import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.*;


class TreePanel extends JScrollPane {

	JTree tree;

	TreePanel(Folder rootFolder) {

		DefaultMutableTreeNode root = makeTree(rootFolder);
		
		tree = new JTree(root);
		tree.setScrollsOnExpand(true);
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
				Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				
				ImageIcon folderIcon = new ImageIcon("assets/folder.png");
				Image scaledFolder = folderIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon scaledIconFolder = new ImageIcon(scaledFolder);
				
				ImageIcon openFolderIcon = new ImageIcon("assets/open-folder.png");
				Image scaledOpenFolder = openFolderIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon scaledIconOpenFolder = new ImageIcon(scaledOpenFolder);

				if (leaf) {
					setIcon(scaledIconFolder);
				} else if (expanded) {
					setIcon(scaledIconOpenFolder);
				} else {
					setIcon(scaledIconFolder);
				}

				return c;
			}
		});
		
		tree.setBorder(new MatteBorder(10, 5, 0, 0, tree.getBackground()));
		this.setViewportView(tree);
		this.setBorder(null);
	}

	DefaultMutableTreeNode makeTree(Folder root) {
		if (root == null)
			return null;

		DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(root.nama);
		Queue<Folder> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			Folder parent = queue.poll();
			DefaultMutableTreeNode parentNode = findNode(treeRoot, parent);

			for (Folder child : parent.childFolder) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child.nama);
				parentNode.add(childNode);
				queue.add(child);
			}
		}

		return treeRoot;
	}

	DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, Folder folder) {
		if (root == null || folder == null)
			return null;

		if (root.getUserObject().equals(folder.nama))
			return root;

		Enumeration<?> enumeration = root.depthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
			System.out.println("Enumerating node: " + node.getUserObject()); // Debug statement
			if (node.getUserObject().equals(folder.nama))
				return node;
		}

		return null;
	}
}
