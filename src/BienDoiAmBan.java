import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BienDoiAmBan extends JFrame {
    private JLabel imageLabel;
    private BufferedImage originalImage;

    public BienDoiAmBan() {
        setTitle("Image Transformation App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Create components
        imageLabel = new JLabel();
        JButton loadImageButton = new JButton("Load Image");
        JButton applyTransformationButton = new JButton("Apply Transformation");

        // Set layout
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadImageButton);
        buttonPanel.add(applyTransformationButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        applyTransformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyTransformation();
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

    private void applyTransformation() {
        if (originalImage != null) {
            // TODO: Implement your image transformation algorithm here

            // For example, you can invert the colors as a simple transformation
            BufferedImage transformedImage = invertColors(originalImage);

            displayImage(transformedImage);
        } else {
            JOptionPane.showMessageDialog(this, "Please load an image first.");
        }
    }

    private BufferedImage invertColors(BufferedImage image) {
        // Simple example: Invert colors by subtracting each color component from 255
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = 255 - (rgb >> 16 & 0xFF);
                int green = 255 - (rgb >> 8 & 0xFF);
                int blue = 255 - (rgb & 0xFF);
                int invertedRGB = (red << 16) | (green << 8) | blue;
                invertedImage.setRGB(x, y, invertedRGB);
            }
        }

        return invertedImage;
    }

    private void displayImage(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
    }


}
