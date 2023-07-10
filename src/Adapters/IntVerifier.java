package Adapters;

import javax.swing.*;

public class IntVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            return text.isBlank() || Integer.parseInt(text) != -1;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


