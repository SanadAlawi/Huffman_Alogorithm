package project2;

public class Huffman implements Comparable<Huffman>{
	private char Character;
	private String HuffmanCode;
	private int Lenght;
	private int Frequency;
	
	public Huffman(char character, String huffmanCode, int lenght, int frequency) {
		Character = character;
		HuffmanCode = huffmanCode;
		Lenght = lenght;
		Frequency = frequency;
	}


	public String getHuffmanCode() {
		return HuffmanCode;
	}



	public void setHuffmanCode(String huffmanCode) {
		HuffmanCode = huffmanCode;
	}



	public char getCharacter() {
		return Character;
	}



	public void setCharacter(char character) {
		Character = character;
	}



	public int getLenght() {
		return Lenght;
	}



	public void setLenght(int lenght) {
		Lenght = lenght;
	}



	public int getFrequency() {
		return Frequency;
	}



	public void setFrequency(int frequency) {
		Frequency = frequency;
	}

	@Override
	public int hashCode() {
		return Character;
	}

	@Override
	public String toString() {
		return " {Character=" + Character + ", HuffmanCode=" + HuffmanCode + ", Lenght=" + Lenght + ", Frequency="
				+ Frequency+"} ";
	}


	@Override
	public int compareTo(Huffman o) {
		return Character - o.getCharacter();
	}
	
	
	
}
