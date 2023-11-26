import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeManageView extends JFrame {
    public HomeManageView() {
        setTitle("Main Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton transformButton = new JButton("Negative Transformation");

        JButton thresholdingButton = new JButton("Thresholding");

        JButton histogramButton = new JButton("Histogram Equalization");

        JButton filterButton = new JButton("Filter");

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opentFilterView();
            }
        });
        transformButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTransformationView();
            }
        });

        thresholdingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openThresholdingView();
            }
        });

        histogramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHistogramEqualization();
            }
        });

        JPanel panel = new JPanel();
        panel.add(transformButton);
        panel.add(histogramButton);
        panel.add(thresholdingButton);
        panel.add(filterButton);
        add(panel);

        setLocationRelativeTo(null);
    }

    private void opentFilterView() {
        Filter filter = new Filter();
        filter.setVisible(true);
    }

    private void openHistogramEqualization() {
        HistogramEqualization histogramEqualization = new HistogramEqualization();
        histogramEqualization.setVisible(true);
    }

    private void openThresholdingView() {
        Thresholding thresholding = new Thresholding();
        thresholding.setVisible(true);
    }

    private void openTransformationView() {
        NegativeTransformation transformationView = new NegativeTransformation();
        transformationView.setVisible(true);
    }

}
