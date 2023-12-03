import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PhanNguong extends JFrame {
    private JLabel imageLabel;
    private BufferedImage originalImage;
    private int thresholdValue = 128; // Giá trị ngưỡng mặc định

    public PhanNguong() {
        setTitle("Image Thresholding App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Create components
        imageLabel = new JLabel();
        JButton loadImageButton = new JButton("Load Image");
        JSlider thresholdSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, thresholdValue);
        JButton applyThresholdButton = new JButton("Apply Threshold");

        // Set layout
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(loadImageButton);
        controlPanel.add(new JLabel("Threshold:"));
        controlPanel.add(thresholdSlider);
        controlPanel.add(applyThresholdButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Add action listeners
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        thresholdSlider.addChangeListener(e -> {
            thresholdValue = thresholdSlider.getValue();
        });

        applyThresholdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyThreshold();
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

    private void applyThreshold() {
        if (originalImage != null) {
            BufferedImage thresholdedImage = thresholdImage(originalImage, thresholdValue);
            displayImage(thresholdedImage);
        } else {
            JOptionPane.showMessageDialog(this, "Please load an image first.");
        }
    }

    private BufferedImage thresholdImage(BufferedImage image, int threshold) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int grayValue = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                int newRGB = (grayValue < threshold) ? 0 : 0xFFFFFF; // Black or white based on threshold
                thresholdedImage.setRGB(x, y, newRGB);
            }
        }

        return thresholdedImage;
    }

    private void displayImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Thresholding().setVisible(true);
//            }
//        });
//    }
}
