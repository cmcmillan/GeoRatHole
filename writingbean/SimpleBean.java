import java.awt.Color;
import java.beans.XMLDecoder;
import javax.swing.JLabel;
import java.io.Serializable;

public class SimpleBean extends JLabel implements Serializable
{
    public SimpleBean()
    {
        setText("Hello World!");
        setOpaque(true);
        setBackground(Color.RED);
        setForeground(Color.YELLOW);
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
    }
}