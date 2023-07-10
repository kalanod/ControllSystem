package Adapters;

import javax.swing.*;

public class TextVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            return text.length() > 2 || text.isBlank();
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
