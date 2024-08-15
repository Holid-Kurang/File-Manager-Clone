
public class File {
	String nama;
	String type;
	int size;
	
	File next,prev;
	
	File(String nama, String type, int size){
		this.nama=nama;
		this.size=size;
		this.type=type;
	}
	
}
