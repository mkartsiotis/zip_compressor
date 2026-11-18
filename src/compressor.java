import java.io.FileInputStream;
import java.io.IOException;

class compressor {
  BSTree huffman_tree;
  String[] huffmanCodes;

  compressor(String filename) {
    huffman_tree = new BSTree(filename);
    huffman_tree.createBstree();
    this.huffmanCodes = new String[256];
  }

  compressor() {
    this(null);
  }

  public BSTree getHuffman_tree() {
    return huffman_tree;
  }

  public void setHuffman_tree(BSTree huffman_tree) {
    this.huffman_tree = huffman_tree;
  }

  public String[] getHuffmanCodes() {
    return this.huffmanCodes;
  }

  private void buildCodeTable(Node node, String current_code) {
    if (node == null)
      return;
    else if (node.getLeft_child() == null && node.getRight_child() == null) {
      this.huffmanCodes[(int) node.getByte_code() & 0xFF] = current_code.isEmpty() ? "0" : current_code;
    }
    buildCodeTable(node.getLeft_child(), current_code + "0");
    buildCodeTable(node.getRight_child(), current_code + "1");
  }

  public static void main(String args[]) {
    String filename = args[0];
    compressor MyCompressor = new compressor(filename);
    MyCompressor.getHuffman_tree().createBstree();
    BitWritter my_bit_writter = new BitWritter();
    MyCompressor.buildCodeTable(MyCompressor.getHuffman_tree().getRoot(), "");
    int byteValue;
    try (FileInputStream input_file = new FileInputStream(filename)) {
      my_bit_writter.createHeader(MyCompressor.getHuffman_tree().getHuffmanStatistics_generator().getSum(),
          MyCompressor.getHuffman_tree().getHuffmanStatistics_generator().getNumber_of_times_found());

      while ((byteValue = input_file.read()) != -1) {
        String bitSequence = MyCompressor.getHuffmanCodes()[byteValue];
        if (bitSequence != null)
          my_bit_writter.write_bits(bitSequence);
        else
          System.err.print("NULL String in Bitstring -> Main!");
      }
    } catch (IOException e) {
      System.err.println("Error reading file" + e.getMessage());
    }
  }
}
