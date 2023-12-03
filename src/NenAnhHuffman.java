//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.PriorityQueue;
//import java.util.Map;
//
//public class NenAnhHuffman extends JFrame {
//    private JLabel originalImageLabel;
//    private JLabel compressedImageLabel;
//    private BufferedImage originalImage;
//
//    public NenAnhHuffman() {
//        setTitle("Huffman Image Compression App");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        initComponents();
//        setLocationRelativeTo(null);
//    }
//
//    private void initComponents() {
//        // Create components
//        originalImageLabel = new JLabel();
//        compressedImageLabel = new JLabel();
//        JButton loadImageButton = new JButton("Load Image");
//        JButton compressImageButton = new JButton("Compress Image");
//
//        // Set layout
//        setLayout(new BorderLayout());
//
//        JPanel originalPanel = new JPanel();
//        originalPanel.setLayout(new BorderLayout());
//        originalPanel.add(new JLabel("Original Image"), BorderLayout.NORTH);
//        originalPanel.add(originalImageLabel, BorderLayout.CENTER);
//
//        JPanel compressedPanel = new JPanel();
//        compressedPanel.setLayout(new BorderLayout());
//        compressedPanel.add(new JLabel("Compressed Image"), BorderLayout.NORTH);
//        compressedPanel.add(compressedImageLabel, BorderLayout.CENTER);
//
//        JPanel controlPanel = new JPanel();
//        controlPanel.setLayout(new FlowLayout());
//        controlPanel.add(loadImageButton);
//        controlPanel.add(compressImageButton);
//
//        add(originalPanel, BorderLayout.WEST);
//        add(compressedPanel, BorderLayout.EAST);
//        add(controlPanel, BorderLayout.SOUTH);
//
//        // Add action listeners
//        loadImageButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadImage();
//            }
//        });
//
//        compressImageButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                compressImage();
//            }
//        });
//    }
//
//    private void loadImage() {
//        JFileChooser fileChooser = new JFileChooser();
//        int result = fileChooser.showOpenDialog(this);
//
//        if (result == JFileChooser.APPROVE_OPTION) {
//            try {
//                originalImage = ImageIO.read(fileChooser.getSelectedFile());
//                displayImage(originalImage, originalImageLabel);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void compressImage() {
//        if (originalImage != null) {
//            BufferedImage compressedImage = compressHuffman(originalImage);
//            displayImage(compressedImage, compressedImageLabel);
//        } else {
//            JOptionPane.showMessageDialog(this, "Please load an image first.");
//        }
//    }
//
//    private BufferedImage compressHuffman(BufferedImage image) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        BufferedImage compressedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//        int[] pixelValues = new int[width * height];
//        int index = 0;
//
//        // Extract pixel values
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int rgb = image.getRGB(x, y);
//                int grayValue = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
//                pixelValues[index++] = grayValue;
//            }
//        }
//
//        // Build Huffman tree
//        HuffmanTree huffmanTree = buildHuffmanTree(pixelValues);
//
//        // Generate Huffman codes
//        Map<Integer, String> huffmanCodes = huffmanTree.generateCodes();
//
//        // Compress image using Huffman codes
//        int compressedIndex = 0;
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int pixelValue = pixelValues[compressedIndex++];
//                String huffmanCode = huffmanCodes.get(pixelValue);
//
//                // Store Huffman code length as RGB value
//                int newRGB = (huffmanCode.length() << 16) | (huffmanCode.length() << 8) | huffmanCode.length();
//                compressedImage.setRGB(x, y, newRGB);
//            }
//        }
//
//        return compressedImage;
//    }
//
//    private HuffmanTree buildHuffmanTree(int[] values) {
//        // Count frequency of each value
//        Map<Integer, Integer> frequencyMap = new HashMap<>();
//        for (int value : values) {
//            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
//        }
//
//        // Build priority queue of Huffman nodes
//        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
//        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
//            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
//        }
//
//        // Build Huffman tree
//        while (priorityQueue.size() > 1) {
//            HuffmanNode leftChild = priorityQueue.poll();
//            HuffmanNode rightChild = priorityQueue.poll();
//            HuffmanNode parent = new HuffmanNode(leftChild, rightChild);
//            priorityQueue.add(parent);
//        }
//
//        // Return the root of the Huffman tree
//        return new HuffmanTree(priorityQueue.poll());
//    }
//
//    private void displayImage(BufferedImage image, JLabel label) {
//        ImageIcon icon = new ImageIcon(image);
//        label.setIcon(icon);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new NenAnhHuffman().setVisible(true);
//            }
//        });
//    }
//}
//
//class HuffmanTree {
//    private HuffmanNode root;
//
//    public HuffmanTree(HuffmanNode root) {
//        this.root = root;
//    }
//
//    public Map<Integer, String> generateCodes() {
//        Map<Integer, String> huffmanCodes = new HashMap<>();
//        generateCodesRecursive(root, "", huffmanCodes);
//        return huffmanCodes;
//    }
//
//    private void generateCodesRecursive(HuffmanNode node, String code, Map<Integer, String> huffmanCodes) {
//        if (node.isLeaf()) {
//            huffmanCodes.put(node.getValue(), code);
//        } else {
//            generateCodesRecursive(node.getLeftChild(), code + "0", huffmanCodes);
//            generateCodesRecursive(node.getRightChild(), code + "1", huffmanCodes);
//        }
//    }
//}
//
//class HuffmanNode implements Comparable<HuffmanNode> {
//    private int value;
//    private int frequency;
//    private HuffmanNode leftChild;
//    private HuffmanNode rightChild;
//
//    public HuffmanNode(int value, int frequency) {
//        this.value = value;
//        this.frequency = frequency;
//    }
//
//    public HuffmanNode(HuffmanNode leftChild, HuffmanNode rightChild) {
//        this.leftChild = leftChild;
//        this.rightChild = rightChild;
//        this.frequency = leftChild.getFrequency() + rightChild.getFrequency();
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public int getFrequency() {
//        return frequency;
//    }
//
//    public HuffmanNode getLeftChild() {
//        return leftChild;
//    }
//
//    public HuffmanNode getRightChild() {
//        return rightChild;
//    }
//
//    public boolean isLeaf() {
//        return leftChild == null && rightChild == null;
//    }
//
//    @Override
//    public int compareTo(HuffmanNode o) {
//        return this.frequency - o.frequency;
//    }
//}
