import java.io.FileInputStream;
import java.io.IOException;

public class HuffmanStatistics {
  String filepath;
  private double[] frequencies;
  private int[] number_of_times_found;
  private int sum;

  HuffmanStatistics(String filename) {
    this.filepath = filename;
    frequencies = new double[256];
    this.number_of_times_found = new int[256];
    this.sum = 0;
  }

  HuffmanStatistics() {
    this(null);
  }

  public boolean read_and_calculate_frequencies() {
    try (FileInputStream myfile = new FileInputStream(this.filepath)) {
      int bytevalue;
      while ((bytevalue = myfile.read()) != -1)
        this.number_of_times_found[bytevalue]++;
      this.sum = 0;
      for (int i = 0; i < 256; i++)
        this.sum += this.number_of_times_found[i];
      System.out.println("File statistics created!");
      for (int i = 0; i < 256; i++)
        this.frequencies[i] = (((double) this.number_of_times_found[i] / (double) this.sum) * 100.0);
      return true;
    } catch (IOException e) {
      System.err.println("Error reading file" + e.getMessage());
      return false;
    }
  }

  public static void main(String args[]) {
  }

  public double[] getFrequencies() {
    return frequencies;
  }

  public void setFrequencies(double[] frequencies) {
    this.frequencies = frequencies;
  }

  public String getFilepath() {
    return filepath;
  }

  public int[] getNumber_of_times_found() {
    return number_of_times_found;
  }

  public int getSum() {
    return sum;
  }
}
