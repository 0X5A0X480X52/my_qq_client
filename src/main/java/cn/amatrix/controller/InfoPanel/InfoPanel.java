package cn.amatrix.controller.InfoPanel;

import javax.swing.*;

public abstract class InfoPanel extends JPanel {
    public abstract void setAdditionalInfo(String additionalInfo);
    public abstract void setButton(JComponent button);
    public abstract JComponent getButton();
}
