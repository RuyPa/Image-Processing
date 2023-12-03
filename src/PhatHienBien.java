import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PhatHienBien extends JFrame {
    private JLabel originalImageLabel;
    private JLabel edgeDetectedImageLabel;
    private BufferedImage originalImage;

    public PhatHienBien() {
        setTitle("Roberts Edge Detector App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Create components
        originalImageLabel = new JLabel();
        edgeDetectedImageLabel = new JLabel();
        JButton loadImageButton = new JButton("Load Image");
        JButton applyRobertsDetectorButton = new JButton("Apply Roberts Edge Detector");

        // Set layout
        setLayout(new BorderLayout());

        JPanel originalPanel = new JPanel();
        originalPanel.setLayout(new BorderLayout());
        originalPanel.add(new JLabel("Original Image"), BorderLayout.NORTH);
        originalPanel.add(originalImageLabel, BorderLayout.CENTER);

        JPanel edgeDetectedPanel = new JPanel();
        edgeDetectedPanel.setLayout(new BorderLayout());
        edgeDetectedPanel.add(new JLabel("Edge Detected Image"), BorderLayout.NORTH);
        edgeDetectedPanel.add(edgeDetectedImageLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(loadImageButton);
        controlPanel.add(applyRobertsDetectorButton);

        add(originalPanel, BorderLayout.WEST);
        add(edgeDetectedPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        // Add action listeners
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        applyRobertsDetectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyRobertsDetector();
            }
        });
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                originalImage = ImageIO.read(fileChooser.getSelectedFile());
                displayImage(originalImage, originalImageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void applyRobertsDetector() {
        if (originalImage != null) {
            BufferedImage edgeDetectedImage = robertsEdgeDetection(originalImage);
            displayImage(edgeDetectedImage, edgeDetectedImageLabel);
        } else {
            JOptionPane.showMessageDialog(this, "Please load an image first.");
        }
    }

    private BufferedImage robertsEdgeDetection(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage edgeDetectedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width - 1; x++) {
                int rgb1 = image.getRGB(x, y);
                int rgb2 = image.getRGB(x + 1, y + 1);

                int grayValue1 = (int) (0.299 * ((rgb1 >> 16) & 0xFF) + 0.587 * ((rgb1 >> 8) & 0xFF) + 0.114 * (rgb1 & 0xFF));
                int grayValue2 = (int) (0.299 * ((rgb2 >> 16) & 0xFF) + 0.587 * ((rgb2 >> 8) & 0xFF) + 0.114 * (rgb2 & 0xFF));

                int edgeValue = Math.abs(grayValue1 - grayValue2);
                int newRGB = (edgeValue << 16) | (edgeValue << 8) | edgeValue;
                edgeDetectedImage.setRGB(x, y, newRGB);
            }
        }

        return edgeDetectedImage;
    }

    private void displayImage(BufferedImage image, JLabel label) {
        ImageIcon icon = new ImageIcon(image);
        label.setIcon(icon);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PhatHienBien().setVisible(true);
            }
        });
    }
}
