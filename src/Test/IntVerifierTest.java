package Test;

import Adapters.IntVerifier;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class IntVerifierTest {

    @Test
    public void verify() {
        JTextField label = new JTextField("2");
        label.setInputVerifier(new IntVerifier());
        Assert.assertEquals(true, label.getInputVerifier().verify(label));
    }
    @Test
    public void verify2() {
        JTextField label = new JTextField("a");
        label.setInputVerifier(new IntVerifier());
        Assert.assertEquals(false, label.getInputVerifier().verify(label));
    }
    @Test
    public void verify3() {
        JTextField label = new JTextField("");
        label.setInputVerifier(new IntVerifier());
        Assert.assertEquals(true, label.getInputVerifier().verify(label));
    }
}