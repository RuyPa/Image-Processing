import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class Filter extends JFrame {
    private JLabel originalImageLabel;
    private JLabel filteredImageLabel;
    private BufferedImage originalImage;

    public Filter() {
        setTitle("Image Filter App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Create components
        originalImageLabel = new JLabel();
        filteredImageLabel = new JLabel();
        JButton loadImageButton = new JButton("Load Image");
        JButton applyAverageFilterButton = new JButton("Apply Average Filter");
        JButton applyMedianFilterButton = new JButton("Apply Median Filter");
        JButton applyBoth = new JButton("Apply both");

        // Set layout
        setLayout(new BorderLayout());

        JPanel originalPanel = new JPanel();
        originalPanel.setLayout(new BorderLayout());
        originalPanel.add(new JLabel("Original Image"), BorderLayout.NORTH);
        originalPanel.add(originalImageLabel, BorderLayout.CENTER);

        JPanel filteredPanel = new JPanel();
        filteredPanel.setLayout(new BorderLayout());
        filteredPanel.add(new JLabel("Filtered Image"), BorderLayout.NORTH);
        filteredPanel.add(filteredImageLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(loadImageButton);
        controlPanel.add(applyAverageFilterButton);
        controlPanel.add(applyMedianFilterButton);
        controlPanel.add(applyBoth);

        add(originalPanel, BorderLayout.WEST);
        add(filteredPanel, BorderLayout.EAST);
        add(controlPanel, BorderLayout.SOUTH);

        applyBoth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilter("both");
            }
        });

        // Add action listeners
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        applyAverageFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilter("average");
            }
        });

        applyMedianFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilter("median");
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

    private void applyFilter(String filterType) {
        if (originalImage != null) {
            BufferedImage filteredImage;
            if (filterType.equals("average")) {
                filteredImage = applyAverageFilter(originalImage);
            } else if (filterType.equals("median")) {
                filteredImage = applyMedianFilter(originalImage);
            } else if(filterType.equals("both")){
                filteredImage = applyAverageFilter(originalImage);
                filteredImage = applyMedianFilter(filteredImage);
            } else {
                throw new IllegalArgumentException("Invalid filter type");
            }

            displayImage(filteredImage, filteredImageLabel);
        } else {
            JOptionPane.showMessageDialog(this, "Please load an image first.");
        }
    }

    private BufferedImage applyAverageFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sumRed = 0, sumGreen = 0, sumBlue = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = image.getRGB(x + i, y + j);
                        sumRed += (rgb >> 16) & 0xFF;
                        sumGreen += (rgb >> 8) & 0xFF;
                        sumBlue += rgb & 0xFF;
                    }
                }

                int newRed = sumRed / 9;
                int newGreen = sumGreen / 9;
                int newBlue = sumBlue / 9;

                int newRGB = (newRed << 16) | (newGreen << 8) | newBlue;
                filteredImage.setRGB(x, y, newRGB);
            }
        }

        return filteredImage;
    }

    private BufferedImage applyMedianFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int[] redValues = new int[9];
                int[] greenValues = new int[9];
                int[] blueValues = new int[9];

                int index = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = image.getRGB(x + i, y + j);
                        redValues[index] = (rgb >> 16) & 0xFF;
                        greenValues[index] = (rgb >> 8) & 0xFF;
                        blueValues[index] = rgb & 0xFF;
                        index++;
                    }
                }

                int newRed = findMedian(redValues);
                int newGreen = findMedian(greenValues);
                int newBlue = findMedian(blueValues);

                int newRGB = (newRed << 16) | (newGreen << 8) | newBlue;
                filteredImage.setRGB(x, y, newRGB);
            }
        }

        return filteredImage;
    }

    private int findMedian(int[] values) {
        Arrays.sort(values);
        return values[4]; // Median of 9 values is the middle value (index 4)
    }

    private void displayImage(BufferedImage image, JLabel label) {
        ImageIcon icon = new ImageIcon(image);
        label.setIcon(icon);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Filter().setVisible(true);
//            }
//        });
//    }
}
