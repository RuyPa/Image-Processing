import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeManageView extends JFrame {
    public HomeManageView() {
        setTitle("Main Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton transformButton = new JButton("Bien doi am ban");

        JButton thresholdingButton = new JButton("Phan nguong");

        JButton histogramButton = new JButton("Can bang luoc do xam");

        JButton filterButton = new JButton("Bo loc");

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
        BoLoc filter = new BoLoc();
        filter.setVisible(true);
    }

    private void openHistogramEqualization() {
        CanBangLuocDoXam histogramEqualization = new CanBangLuocDoXam();
        histogramEqualization.setVisible(true);
    }

    private void openThresholdingView() {
        PhanNguong thresholding = new PhanNguong();
        thresholding.setVisible(true);
    }

    private void openTransformationView() {
        BienDoiAmBan transformationView = new BienDoiAmBan();
        transformationView.setVisible(true);
    }

}
