import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class BitWritter {
  int bits_in_buffer;
  int byte_to_write;
  FileOutputStream compressed_file;
  DataOutputStream compressed_file_output_stream;

  BitWritter() {
    // Open the output file stream!
    try {
      this.compressed_file = new FileOutputStream("output.bin");
      this.compressed_file_output_stream = new DataOutputStream(compressed_file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.bits_in_buffer = 0;
    this.byte_to_write = 0;
  }

  void createHeader(int total_8bit_sequencies, int[] n_of_times) throws java.io.IOException {
    try {
      compressed_file_output_stream.writeInt(total_8bit_sequencies);
    } catch (java.io.IOException e) {
      System.out.println("An I/O error occurred: " + e.getMessage());
    }
    for (int i = 0; i < 256; i++) {
      try {
        compressed_file_output_stream.writeInt(n_of_times[i]);
      } catch (java.io.IOException e) {
        System.out.println("An I/O error occurred: " + e.getMessage());
      }
    }
  }

  boolean write_bit(int bit) throws java.io.IOException {
    bits_in_buffer++;
    byte_to_write = byte_to_write << 1;
    byte_to_write = byte_to_write + bit;
    if (bits_in_buffer == 8) {
      try {
        compressed_file.write(byte_to_write);
      } catch (Exception e) {
        System.err.println("I/O error in write bit boolean funv");
        return false;
      }
      byte_to_write = 0;
      bits_in_buffer = 0;
    }
    return true;
  }

  boolean write_bits(String bits) throws java.io.IOException {
    for (int i = 0; i < bits.length(); i++) {
      int bit = (bits.charAt(i)) - '0';
      boolean b = this.write_bit(bit);
      if (b == false)
        return false;
    }
    return true;
  }

  boolean flush() throws java.io.IOException {
    byte_to_write = byte_to_write << (8 - bits_in_buffer);
    try {
      compressed_file.write(byte_to_write);
    } catch (Exception e) {
      System.err.println("I/O error in write bit boolean funv");
      return false;
    }
    bits_in_buffer = 0;
    byte_to_write = 0;
    return true;
  }

  public int getBits_in_buffer() {
    return bits_in_buffer;
  }

  public void setBits_in_buffer(int bits_in_buffer) {
    this.bits_in_buffer = bits_in_buffer;
  }

  public int getByte_to_write() {
    return byte_to_write;
  }

  public void setByte_to_write(int byte_to_write) {
    this.byte_to_write = byte_to_write;
  }

  public FileOutputStream getCompressed_file() {
    return compressed_file;
  }

  public void setCompressed_file(FileOutputStream compressed_file) {
    this.compressed_file = compressed_file;
  }

  public DataOutputStream getCompressed_file_output_stream() {
    return compressed_file_output_stream;
  }

  public void setCompressed_file_output_stream(DataOutputStream compressed_file_output_stream) {
    this.compressed_file_output_stream = compressed_file_output_stream;
  }
}
