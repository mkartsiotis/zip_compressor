/**
 * BSTree
 */
class BSTree {
  String filename;
  Node root;
  HuffmanStatistics huffmanStatistics_generator;

  BSTree(String filename) {
    this.filename = filename;
    this.root = null;
    this.huffmanStatistics_generator = new HuffmanStatistics(filename);
  }

  BSTree() {
    this(null);
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Node getRoot() {
    return root;
  }

  public void setRoot(Node root) {
    this.root = root;
  }

  public HuffmanStatistics getHuffmanStatistics_generator() {
    return huffmanStatistics_generator;
  }

  public void setHuffmanStatistics_generator(HuffmanStatistics huffmanStatistics_generator) {
    this.huffmanStatistics_generator = huffmanStatistics_generator;
  }

  /*
   * public static void insert_to_node_array(Node[] nodearray, Node N, int start,
   * int finish) {
   * // nlogn ofr now but will be improved in the future
   * if (start == finish && N.getFrequency() > nodearray[start].getFrequency()) {
   * for (int i = start; nodearray[i] != null; i++)
   * nodearray[i + 1] = nodearray[i];
   * nodearray[start] = N;
   * } else if (start == finish && N.getFrequency() <
   * nodearray[start].getFrequency()) {
   * for (int i = start + 1; nodearray[i] != null; i++)
   * nodearray[i + 1] = nodearray[i];
   * nodearray[start + 1] = N;
   * } else {
   * int mid = ((finish + start) / 2);
   * if (nodearray[mid].getFrequency() > N.getFrequency())
   * insert_to_node_array(nodearray, N, start, mid);
   * else
   * insert_to_node_array(nodearray, N, mid, finish);
   * }
   * }
   */
  public static void insert_to_node_array(Node[] nodearray, Node N, int start, int finish) {
    if (nodearray[start] == null) {
      nodearray[start] = N;
      return;
    }
    int low = start;
    int high = finish;
    while (low <= high) {
      int mid = low + (high - low) / 2;
      if (nodearray[mid] == null || N.getFrequency() > nodearray[mid].getFrequency()) {
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }
    int insertPos = low;
    int end = insertPos;
    while (end < nodearray.length && nodearray[end] != null) {
      end++;
    }
    for (int i = end; i > insertPos; i--) {
      nodearray[i] = nodearray[i - 1];
    }
    nodearray[insertPos] = N;
  }

  public static void quick_sort_array(Node[] array_to_quicksort) {
    quick_sort_array_from_to(array_to_quicksort, 0, array_to_quicksort.length - 1);
  }

  private static void quick_sort_array_from_to(Node[] array_to_quicksort, int start, int finish) {
    int basepoint = index_of_basepoint_in_position(array_to_quicksort, start, finish);
    if (basepoint != -1) {
      quick_sort_array_from_to(array_to_quicksort, start, basepoint - 1);
      quick_sort_array_from_to(array_to_quicksort, basepoint + 1, finish);
    }
  }

  private static int index_of_basepoint_in_position(Node[] array_to_qsort, int starting_index, int final_index) {
    if (starting_index >= final_index)
      return -1;
    int basepoint = starting_index;
    Node basepoint_node = array_to_qsort[basepoint];
    Node[] left_Arr = new Node[(final_index - starting_index + 1)];
    Node[] right_Arr = new Node[(final_index - starting_index + 1)];
    int left_Arr_index = 0, right_Arr_index = 0;
    for (int i = starting_index + 1; i <= final_index; i++) {
      if (array_to_qsort[i].getFrequency() > array_to_qsort[basepoint].getFrequency()) {
        left_Arr[left_Arr_index] = array_to_qsort[i];
        left_Arr_index++;
      } else if (array_to_qsort[i].getFrequency() < array_to_qsort[basepoint].getFrequency()) {
        right_Arr[right_Arr_index] = array_to_qsort[i];
        right_Arr_index++;
      }
    }
    for (int i = 0; i < left_Arr_index; i++) {
      array_to_qsort[starting_index + i] = left_Arr[i];
    }
    array_to_qsort[starting_index + left_Arr_index] = basepoint_node;
    for (int i = 0; i < right_Arr_index; i++) {
      array_to_qsort[starting_index + left_Arr_index + 1 + i] = right_Arr[i];
    }
    return starting_index + left_Arr_index;
  }

  void createBstree() {
    huffmanStatistics_generator.read_and_calculate_frequencies();
    Node[] node_array = new Node[256];
    for (int i = 0; i < 256; i++) {
      node_array[i] = new Node(huffmanStatistics_generator.getFrequencies()[i], i);
    }
    quick_sort_array(node_array);
    for (int i = node_array.length - 1; i > 0; i--) {
      double new_freq = node_array[i].getFrequency() + node_array[i - 1].getFrequency();
      Node tempNode = new Node(new_freq, 0, node_array[i], node_array[i - 1]);
      node_array[i] = null;
      node_array[i - 1] = null;
      insert_to_node_array(node_array, tempNode, 0, i);
    }
    this.root = node_array[0];
  }

}
