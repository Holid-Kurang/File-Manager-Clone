import java.util.ArrayList;
import java.util.Stack;

public class Folder {
	String nama;
	Folder parent;
	ArrayList<Folder> childFolder = new ArrayList<>();
	ArrayList<File> childFile = new ArrayList<>();

	Folder(String nama, Folder parent) {
		this.nama = nama;
		this.parent = parent;
	}

	void addFolder(String nama, Folder parent) {
		Folder newFolder = new Folder(nama, parent);
		childFolder.add(newFolder);
	}

	Folder[] searchAll(Folder root, String folderName) {
		if (root == null)
			return null;

		Stack<Folder> stack = new Stack<>();
		stack.push(root);

		ArrayList<Folder> hasilSearch = new ArrayList<>();
		while (!stack.isEmpty()) {
			Folder current = stack.pop();
			if (current.nama.contains(folderName)) {
				hasilSearch.add(current);
				// hasilSearch.tambahFolder(current.nama, current.parent);
			}

			// ListFolder childFolders = current.childFolder;
			// Folder child = current.childFolder.head;

			// ListFolderNode temp = new ListFolderNode();
			// while (child != null) {
			// temp.add(child);
			// child = child.next;
			// }

			ArrayList<Folder> temp = new ArrayList<>();
			for (Folder child : current.childFolder) {
				temp.add(child);
			}

			// FolderNode currentTemp = temp.head;
			// while (currentTemp != null) {
			// stack.push(currentTemp.folder);
			// currentTemp = currentTemp.next;
			// }
			for (Folder currentTemp : temp) {
				stack.push(currentTemp);
			}
		}
		return hasilSearch.toArray(new Folder[0]);
	}

	Folder searchFolder(Folder root, String folderName) {
		if (root == null)
			return null;

		Stack<Folder> stack = new Stack<>();
		stack.push(root);

		while (!stack.isEmpty()) {
			Folder current = stack.pop();
			if (current.nama.equals(folderName)) {
				return current;
			}

			ArrayList<Folder> temp = new ArrayList<>();
			for (Folder child : current.childFolder) {
				temp.add(child);
			}

			for (Folder currentTemp : temp) {
				stack.push(currentTemp);
			}
		}
		return null;
	}
}
