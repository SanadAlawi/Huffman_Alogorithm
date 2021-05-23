package project2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Main {
	
	
	private  int Alph_Length = 256; // length 
	private  Hashtable<Character, Huffman> HuffmanTable;
	private  CharacterNode Root;
	private  int NumberOfByte;
	private Header header;
	private String Table = "";
	private int HeadLength = 0;
	
	private  String FileName;
	public Main(String fileName) {
		this.FileName = fileName;
	}
	
	public  void Compress() throws Exception{
		int [] CharacterFrequency = new int [Alph_Length];
		ReadFile(CharacterFrequency); // Fill the Character Frequency to array
		
		CharacterNode Root = BuildHuffmanTree(CharacterFrequency); // Build Huffman tree 
		
		HuffmanTable = new Hashtable(); 
				
		BuildHuffmanTable(Root, "", HuffmanTable); // Build Huffman Table
		
		WriteHuffmanToFile(CharacterFrequency);
		
		System.out.println(HuffmanTable.toString()+"\n"+HuffmanTable.size()+"\n else Number Of Byte= "+NumberOfByte);
		PreOrder(Root);
		
	}
	
	public void Decompress() throws Exception{
		Decompress(Root);
	}
	
	private void Decompress(CharacterNode Node) throws Exception{
		File DecompressFile = new File(FileName+".huf");
		FileInputStream FileStream = new FileInputStream(new File(FileName+".huf"));
		BufferedInputStream InputStream = new BufferedInputStream(FileStream);
//		ObjectInputStream ObjectStream = new ObjectInputStream(InputStream);
//		Header header = (Header)ObjectStream.readObject();
		
		
		FileOutputStream OutFile = new FileOutputStream(DecompressFile);
		
		int bytesRead = 0;
		String s = new String();
		int bytesInFile = 0;
		int value = 0;
		try {
			int count = InputStream.available();
			while (bytesInFile < count) {
				bytesRead = InputStream.read();
				
				s += getBinaryValue(bytesRead);
				while (s.length() > 32) {
					for (int i = 0; i < 32; i++) {
						value = getTreeHuffmanCode(Node,
								s.substring(0, i + 1));
						if (value != -1) {
							OutFile.write(value);
							s = s.substring(i + 1);
							break;
						}
					}
				}
				// }
				bytesInFile++;
			}
			// //////////// When reading, i have to read just 8 bits
			s = s.substring(0, s.length() - 8);
			s = s.substring(0, s.length() - 8 + bytesRead);
			int num = 0;
			while (s.length() > 0) {
				if (s.length() > 16) {
					num = 16;
				} else {
					num = s.length();
				}
				for (int i = 0; i < num; i++) {
					value = getTreeHuffmanCode(Node,
							s.substring(0, i + 1));
					if (value != -1) {
						OutFile.write(value);
						s = s.substring(i + 1);
						break;
					}
				}
			}
			OutFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int getTreeHuffmanCode(CharacterNode node, String s) {
		if(node != null) {
			while (true) {
				if (s.charAt(0) == '0') {
					node = node.getLeftChild();
				} else {
					node = node.getRightChild();
				}
				if (node.isLeaf()) {
					return node.getCharacter();
				}
				if (s.length() == 1) {
					break;
				}
				s = s.substring(1);
			}
		}
		return -1;
	}
	private String getBinaryValue(int num) {
		String s = new String();
		int[] reminder = new int[8];
		int i = 0;
		for (i = 0; i < reminder.length; i++) {
			reminder[i] = num % 2;
			num = num / 2;
		}
		for (i = 7; i >= 0; i--) {
			s += reminder[i];
		}
		return s;
	}

	private  void PreOrder(CharacterNode node) {
		if(node != null) {
			if(node.isLeaf())
				System.out.println(node.toString());
			else {
				PreOrder(node.getLeftChild());
				PreOrder(node.getRightChild());
			}
		}
		
	}

	private void WriteHuffmanToFile(int [] CharacterFrequency) throws Exception{
		File InputFile = new File(FileName);
		header = new Header(FileName, NumberOfByte);
		header.setFrequencyCharacter(CharacterFrequency);
//		System.arraycopy(CharacterFrequency, 0, header.getFrequencyCharacter(), 0, CharacterFrequency.length);
		
		int point = FileName.indexOf('.');
		String filename = FileName.substring(0, point);
		OutputStream OutputFile = new FileOutputStream(FileName+".huf");
		FileOutputStream FileStream = new FileOutputStream(filename+".huf", true);
		BufferedOutputStream outputStream = new BufferedOutputStream(FileStream);
		ObjectOutputStream ObjectStream = new ObjectOutputStream(FileStream);
		ObjectStream.writeObject(header);
		BufferedInputStream InputStream = new BufferedInputStream(new FileInputStream(FileName));
		int count = 0;
		if(InputFile.exists()) {
			Scanner in = new Scanner(InputFile);
			StringBuffer sb = new StringBuffer();
			byte [] Bytes = new byte[8];
			int byteCounter = 0;
			String HuffmanCode = "";
			while(in.hasNext()) {
				sb.append(in.nextLine());
				for(char ch : sb.toString().toCharArray()) {
					HuffmanCode += HuffmanTable.get(ch).getHuffmanCode();
					if(HuffmanCode.length() < 8)
						continue;
					byte StringToByte = toByte(HuffmanCode.substring(0, 8));
					Bytes[byteCounter++] = StringToByte;
					if(HuffmanCode.length() > 8) HuffmanCode = HuffmanCode.substring(8);
					else
						HuffmanCode = "";
					if(byteCounter >= 8) {
						outputStream.write(Bytes);
						Bytes = new byte[8];
						byteCounter = 0;
					}
				}
				sb.delete(0, sb.length());
			}
			if(HuffmanCode.length() != 0) {
				for(int i = 0 ; i < 8 ; i++)
					HuffmanCode += "0";
				Bytes[byteCounter++] = toByte(HuffmanCode.substring(0, 8));
				outputStream.write(Bytes);
			}
			outputStream.close();
		}
		else
			System.out.println("File Not Found!!!");
	}
	
	
	/*
	 * bit wise OR, if the 8 bits are tested return result byte 
	 */
	private byte toByte (String huffmanCode){
		int bitCount = 0 ; 
	    byte temp = 0 ,iIndex=0 ;
	    for (iIndex=0 ; iIndex < huffmanCode.length()  ; bitCount++ , iIndex++){
	    	if ( (huffmanCode.charAt(iIndex)+"").equals("1") )
	    		// bit wise or 
	    	//	temp=(byte) (temp| (1<<(7-bitCount%8)));
		    		temp |= (1 << (7- bitCount % 8));
		    	if (bitCount == 7 )
		    		return temp ; 
		    }
			return temp ; // return revived value 
		}

	/*
	 * Build Huffman Table
	 */
	private void BuildHuffmanTable(CharacterNode CharacterNode, String s, Hashtable<Character, Huffman> table) {
		if(!CharacterNode.isLeaf()) {
			BuildHuffmanTable(CharacterNode.getLeftChild(), s+"0", table);
			BuildHuffmanTable(CharacterNode.getRightChild(), s+"1", table);
		}
		else {
			table.put(CharacterNode.getCharacter(), new Huffman(CharacterNode.getCharacter(), s, s.length(), CharacterNode.getFrequency()));
			Table += table.get(CharacterNode.getCharacter()).toString()+" \n";
		}
	} // end method

	/*
	 *  Read File + 
	 *  increase Character Frequency if exists more than one + 
	 *  compute number of leaves
	 */
//	private static void ReadFile(int [] CharacterFrequency) throws Exception{ //
//		File file = new File(FileName);
//		DataInputStream stream = new DataInputStream(new FileInputStream(FileName));
//		int readStutas = 0;
//		byte [] ByteImage = new byte[1024];
//		if(file.exists()) {
////			Scanner in = new Scanner(file);
//			StringBuffer sb = new StringBuffer();
//			while( (readStutas = stream.read(ByteImage, 0, 1024)) != -1) {
////				String line = in.nextLine();
//				for (int i = 0; i < readStutas; i++) 
//					sb.append((char)ByteImage[i]);				
//			}
//			for(int i = 0 ; i < sb.length() ; i++) { 
//				char ch = sb.charAt(i);
//				CharacterFrequency[ch]++;
//			}
//		}
//		else
//			System.out.println("File Not Exists!!!");
//	} // end method
	
	private void ReadFile(int [] CharacterFrequency) throws Exception{ //
		BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(FileName));
		int Byte = 0;
		while( (Byte = buffer.read()) != -1 ) {
			CharacterFrequency[Byte]++;
			NumberOfByte++;
		}
		buffer.close();
	} // end method
	
	
	/*
	 * Build Huffman Tree
	 * using PriorityQueue
	 * and return it
	 */
	private CharacterNode BuildHuffmanTree(int [] CharacterFrequency) {
		PriorityQueue<CharacterNode> queue = new PriorityQueue<>();
		for (int i = 0; i < CharacterFrequency.length; i++) {
			if(CharacterFrequency[i] > 0) {
				queue.add(new CharacterNode((char)i, CharacterFrequency[i]));
			}
		}
		while(queue.size() > 1) {
			CharacterNode Parent = new CharacterNode(' ', 0);
			CharacterNode Left = queue.remove();
			CharacterNode Right = queue.remove();
			Parent.setLeftChild(Left);
			Parent.setRightChild(Right);
			Parent.setFrequency( Left.getFrequency() + Right.getFrequency() );
			queue.add(Parent);
		}
		return queue.remove();
	}

	public int getAlph_Length() {
		return Alph_Length;
	}

	public void setAlph_Length(int alph_Length) {
		Alph_Length = alph_Length;
	}

	public Hashtable<Character, Huffman> getHuffmanTable() {
		return HuffmanTable;
	}

	public void setHuffmanTable(Hashtable<Character, Huffman> huffmanTable) {
		HuffmanTable = huffmanTable;
	}

	public CharacterNode getRoot() {
		return Root;
	}

	public void setRoot(CharacterNode root) {
		Root = root;
	}

	public int getNumberOfByte() {
		return NumberOfByte;
	}

	public void setNumberOfByte(int numberOfByte) {
		NumberOfByte = numberOfByte;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getHeader() {
		String h = header.getOriginalFileName()+"\n size= "+header.getOriginalFileSize()+"\n";
		for (int i = 0; i < 256; i++) {
			if(HuffmanTable.get((char)i) != null) {
				h += (char)i +"-->"+HuffmanTable.get((char)i).getHuffmanCode()+"\n";
			}
		}
		return h;
	}

	public void setHeader(Header header) {
		this.header = header;
	}
	
	public String getHuffmanTale() {
		return Table;
	}
	
	
	
}
