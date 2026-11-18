public class Node {
  double frequency;
  byte byte_code;
  Node left_child, right_child;

  Node(double frequency, int byte_code, Node left_child, Node right_child) {
    this.frequency = frequency;
    this.left_child = left_child;
    this.right_child = right_child;
    this.byte_code = (byte) byte_code;
  }

  Node(double frequency, int byte_code) {
    this(frequency, byte_code, null, null);
  }

  Node(int frequency) {
    this(frequency, 0, null, null);
  }

  boolean is_leaf() {
    return (right_child == null && left_child == null);
  }

  public double getFrequency() {
    return frequency;
  }

  public void setFrequency(double frequency) {
    this.frequency = frequency;
  }

  public byte getByte_code() {
    return byte_code;
  }

  public void setByte_code(byte byte_code) {
    this.byte_code = byte_code;
  }

  public Node getLeft_child() {
    return left_child;
  }

  public void setLeft_child(Node left_child) {
    this.left_child = left_child;
  }

  public Node getRight_child() {
    return right_child;
  }

  public void setRight_child(Node right_child) {
    this.right_child = right_child;
  }

}
