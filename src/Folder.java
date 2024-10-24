
import java.util.ArrayList;
import java.util.Stack;

class Folder {

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

    void deleteFolder(String nama, Folder root) {
        Folder folder = searchFolder(root, nama);
		if (folder != null) {
			if(folder.parent == null){
				return;
			}
            folder.parent.childFolder.remove(folder);
        }
    }

    Folder searchFolder(Folder root, String folderName) {
        if (root == null) {
            return null;
        }

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

    void printAllNode() {
        Stack<Folder> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            Folder current = stack.pop();
            System.out.println(current.nama);
            ArrayList<Folder> temp = new ArrayList<>();
            for (Folder child : current.childFolder) {
                temp.add(child);
            }

            for (Folder currentTemp : temp) {
                stack.push(currentTemp);
            }
        }
    }

}
