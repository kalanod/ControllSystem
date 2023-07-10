package Test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;

public class testWindow extends JFrame {
    public testWindow(String a) throws IOException {
        setBounds(40,70,200,200);
        JButton button = new JButton("Dsf");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
            }
        });
        add(button);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        testWindow t = new testWindow("a");
        t.createChart(t.createDataset());
    }
    private PieDataset createDataset()
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Оплата жилья" , new Double( 7035.8));
        dataset.setValue("Школа, фитнес", new Double( 9200.0));
        dataset.setValue("Развлечение"  , new Double(16450.0));
        dataset.setValue("Дача, стройка", new Double(40000.0));
        return dataset;
    }
    Color[] colors = {new Color(200, 200, 255), new Color(255, 200, 200),
            new Color(200, 255, 200), new Color(200, 255, 200)};

    private JFreeChart createChart(PieDataset dataset)
    {
        JFreeChart chart = ChartFactory.createPieChart(
                "Семейные расходы",  // chart title
                dataset,             // data
                false,               // no legend
                true,                // tooltips
                false                // no URL generation
        );

        // Определение фона графического изображения
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0),
                new Color(20, 20, 20),
                new Point(400, 200),
                Color.DARK_GRAY));

        // Определение заголовка
        TextTitle t = chart.getTitle();
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        // Определение подзаголовка
        TextTitle source = new TextTitle("Семейные расходы за текущий месяц",
                new Font("Courier New", Font.PLAIN, 12));
        source.setPaint(Color.WHITE);
        chart.addSubtitle(source);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        RadialGradientPaint rgpBlue  ;
        RadialGradientPaint rgpRed   ;
        RadialGradientPaint rgpGreen ;
        RadialGradientPaint rgpYellow;

        rgpBlue   = createGradientPaint(colors[0], Color.BLUE  );
        rgpRed    = createGradientPaint(colors[1], Color.RED   );
        rgpGreen  = createGradientPaint(colors[2], Color.GREEN );
        rgpYellow = createGradientPaint(colors[3], Color.YELLOW);

        // Определение секций круговой диаграммы
        plot.setSectionPaint("Оплата жилья" , rgpBlue  );
        plot.setSectionPaint("Школа, фитнес", rgpRed   );
        plot.setSectionPaint("Развлечения"  , rgpGreen );
        plot.setSectionPaint("Дача, стройка", rgpYellow);

        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        // Настройка меток названий секций
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);

        return chart;
    }
    private RadialGradientPaint createGradientPaint(Color c1, Color c2)
    {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[] {c1, c2});
    }
}
