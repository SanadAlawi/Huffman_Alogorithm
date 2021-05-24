# Huffman_Alogorithm
In this project, we will be using a priority queue and a binary tree of our design to
implement a file compression/uncompression algorithm called "Huffman Coding".
our program will read an image file and compress it using your implementation of the
Huffman coding algorithm. The compressed text will be written
to a file. That compressed file will be then be read back by your program and
uncompressed. The uncompressed text will then be written to a third file. The
uncompressed image file should of course match the original image file.

Summary of Processing
• Read the specified file and count the frequency of all characters in the file.
• Create the Huffman coding tree based on the frequencies.
• Create the table of encodings for each character from the Huffman coding tree.
• Encode the file and output the encoded/compressed file.
• Read the encoded/compressed file you just created, decode it and output the
decoded file
