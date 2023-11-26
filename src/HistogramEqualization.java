import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HistogramEqualization extends JFrame {
    private JLabel imageLabel;
    private BufferedImage originalImage;

    public HistogramEqualization() {
        setTitle("Image Histogram Equalization App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Create components
        imageLabel = new JLabel();
        JButton loadImageButton = new JButton("Load Image");
        JButton applyEqualizationButton = new JButton("Apply Histogram Equalization");

        // Set layout
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(loadImageButton);
        controlPanel.add(applyEqualizationButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Add action listeners
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        applyEqualizationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyHistogramEqualization();
            }
        });
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                originalImage = ImageIO.read(fileChooser.getSelectedFile());
                displayImage(originalImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void applyHistogramEqualization() {
        if (originalImage != null) {
            BufferedImage equalizedImage = equalizeHistogram(originalImage);
            displayImage(equalizedImage);
        } else {
            JOptionPane.showMessageDialog(this, "Please load an image first.");
        }
    }

    private BufferedImage equalizeHistogram(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int totalPixels = width * height;

        int[] histogram = new int[256];
        float[] cumulativeProbability = new float[256];

        // Calculate histogram
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int grayValue = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                histogram[grayValue]++;
            }
        }

        // Calculate cumulative probability
        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += histogram[i];
            cumulativeProbability[i] = sum / totalPixels;
        }

        // Apply equalization
        BufferedImage equalizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int grayValue = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                int newGrayValue = Math.round(cumulativeProbability[grayValue] * 255);
                int newRGB = (newGrayValue << 16) | (newGrayValue << 8) | newGrayValue;
                equalizedImage.setRGB(x, y, newRGB);
            }
        }

        return equalizedImage;
    }

    private void displayImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new HistogramEqualization().setVisible(true);
//            }
//        });
//    }
}
