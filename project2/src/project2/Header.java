package project2;

import java.io.Serializable;
import java.util.Arrays;

public class Header implements Serializable{
	private String OriginalFileName;
	private int OriginalFileSize;
	private int [] FrequencyCharacter; // frequency
	private int [] Bytes; // frequency
	
	public Header(String originalFileName, int originalFileSize) {
		OriginalFileName = originalFileName;
		OriginalFileSize = originalFileSize;
	}

	public String getOriginalFileName() {
		return OriginalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		OriginalFileName = originalFileName;
	}

	public int getOriginalFileSize() {
		return OriginalFileSize;
	}

	public void setOriginalFileSize(int originalFileSize) {
		OriginalFileSize = originalFileSize;
	}

	public int[] getFrequencyCharacter() {
		return FrequencyCharacter;
	}

	public void setFrequencyCharacter(int[] frequencyCharacter) {
		FrequencyCharacter = frequencyCharacter;
	}

	@Override
	public String toString() {
		String header = new String();
		header += this.OriginalFileName +"\nsize: "+ this.OriginalFileSize+"\n";
		for (int i = 0; i < FrequencyCharacter.length; i++) {
			header += FrequencyCharacter[i]+" ";
			header += FrequencyCharacter[i]+"\n";
		}
		return header;
	}
	
	
	
	
	
}
