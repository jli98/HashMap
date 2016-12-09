import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HashMapLinearChaining{
	private static class ChainingHashMapNode{
		private int key;
		private String value;
		private ChainingHashMapNode next;


		public ChainingHashMapNode(int key, String value){
			this.key = key;
			this.value = value;
			this.next = null;
		}
		public String getValue(){
			return value;
		}
		public ChainingHashMapNode getNext(){
			return next;
		}

		public void setValue(String newValue){
			this.value = newValue;
		}
		public void setNext(ChainingHashMapNode next){
			this.next = next;
		}
	}

	private ChainingHashMapNode[] table;
	private int size;
	private int used;
	private int[] times = new int[12];

	public HashMapLinearChaining(int n){
		this.size = n * 2;
		this.used = 0;
		table = new ChainingHashMapNode[(int) size];
	}

	public void load(String filename) throws Exception{
		File dictionary = new File(filename);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(dictionary),"gbk");
		BufferedReader br = new BufferedReader(reader);
		String value = "";
		value = br.readLine();
		while(value != null){
			int count = 1;
			int key = hash(value);
			if(table[key] == null){
				ChainingHashMapNode node = new ChainingHashMapNode(key, value);
				table[key] = node;
				used++;
			}else{
				count++;
				ChainingHashMapNode node = table[key];
				while(node.getNext() != null && !node.getValue().equals(value)){
					count++;
					node = node.getNext();
				}
				if(node.getValue().equals(value)){
					node.setValue(value);
				}else{
					ChainingHashMapNode newNode = new ChainingHashMapNode(key, value);
					node.setNext(newNode);
					used++;
				}
			}
			if(count > 11){
				count = 11;
			}
			times[count]++;
			value = br.readLine();
		}
	}

	public boolean contains(String value) {
		int key = hash(value);
		ChainingHashMapNode node = table[key];
 		while (node != null) {
			if (node.getValue().equals(value)){
				return true;
			}
			node = node.getNext();
		}
		return false;
	}

	public int hash(String value){
		return Math.abs(value.hashCode())  % size;
	}

	public void displayHistogram() {
		for (int i = 0; i < times.length; i++) {
			if (table[i]==null) {
				times[0]++;
			}
		}

		System.out.println("insert   count");
		for (int i = 1; i < times.length; i++) {
			System.out.println(i + "        " + times[i]);
		}
		System.out.println(">11" + "        " + times[11]);
		System.out.println();
	}


	public static void main(String[] args) throws Exception {
		int n = 1000000;
		String filename = "dict.txt";
		HashMapLinearChaining h = new HashMapLinearChaining(n);
		h.load(filename);
		String pathname = "hw8.dat";
		File testfile = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(testfile),"gbk");
		BufferedReader br = new BufferedReader(reader);
		String value = br.readLine();
		while(value != null){
			System.out.println(value+"    "+ h.contains(value));
			value = br.readLine();
		}
		System.out.println();
		h.displayHistogram();
	}
}
