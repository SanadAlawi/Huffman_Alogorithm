package project2;

public class CharacterNode implements Comparable<CharacterNode> {

	private char Character;
	private int Frequency;
	private CharacterNode LeftChild;
	private CharacterNode RightChild;
	
	public CharacterNode(char character, int frequency) { // Character Constructor
		Character = character;
		Frequency = frequency;
	}
	
	public CharacterNode(CharacterNode leftChild, CharacterNode rightChild) { // Character Constructor
		LeftChild = leftChild;
		RightChild = rightChild;
	}
	

	public char getCharacter() {
		return Character;
	}

	public void setCharacter(char character) {
		Character = character;
	}

	public int getFrequency() {
		return Frequency;
	}

	public void setFrequency(int frequency) {
		Frequency = frequency;
	}
	
	public CharacterNode getLeftChild() {
		return LeftChild;
	}

	public void setLeftChild(CharacterNode leftChild) {
		LeftChild = leftChild;
	}

	public CharacterNode getRightChild() {
		return RightChild;
	}

	public void setRightChild(CharacterNode rightChild) {
		RightChild = rightChild;
	}
	
	public boolean isLeaf() {
		return LeftChild == null && RightChild == null;
	}

	@Override
	public String toString() {
		return Character +"["+Frequency+"]" ;
	}

	@Override
	public int compareTo(CharacterNode o) {
		return Frequency - o.getFrequency();
	}
	
	
	
}
