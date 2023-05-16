package prj_WindowsCalculator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
	 public static void main(String[] args) {
	        new Calculator();
	    }
    // 计算器的各个按钮
    private JButton[] buttons = {
            new JButton("7"), new JButton("8"), new JButton("9"),
            new JButton("/"), new JButton("4"), new JButton("5"),
            new JButton("6"), new JButton("*"), new JButton("1"),
            new JButton("2"), new JButton("3"), new JButton("-"),
            new JButton("0"), new JButton("."), new JButton("="),
            new JButton("+"), new JButton("C"), new JButton("CE"),
            new JButton("Backspace")
    };

    // 计算器显示屏幕和操作数
    private JTextField screen = new JTextField(10);
    private double op = 0;

    // 是否为新的操作数
    private boolean newOp = true;

    // 最后一次操作
    private String lastOp = "=";

    public Calculator() {
        // 设置窗口标题
        setTitle("Calculator");

        // 添加按钮和显示屏幕到窗口中
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(screen, BorderLayout.NORTH);

        JPanel panel2 = new JPanel(new GridLayout(5, 4, 3, 3));
        for (JButton button : buttons) {
            panel2.add(button);
            button.addActionListener(this);
        }

        panel1.add(panel2, BorderLayout.CENTER);
        add(panel1);

        // 设置窗口大小和可见性
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // 处理按钮事件
    public void actionPerformed(ActionEvent e) {
        String buttonText = ((JButton) e.getSource()).getText();
        switch (buttonText) {
            case "C": // 清空
                screen.setText("");
                op = 0;
                newOp = true;
                break;
            case "CE": // 清零
                screen.setText("0");
                newOp = true;
                break;
            case "Backspace": // 退格
                String s = screen.getText();
                if (s.length() > 0) {
                    screen.setText(s.substring(0, s.length() - 1));
                }
                break;
            case "+": // 加
            case "-": // 减
            case "*": // 乘
            case "/": // 除
                calc();
                op = Double.parseDouble(screen.getText());
                lastOp = buttonText;
                newOp = true;
                break;
            case "=": // 等于
                calc();
                lastOp = buttonText;
                newOp = true;
                break;
            default: // 数字和小数点
                if (buttonText.equals(".")) {
                    if (newOp) {
                        screen.setText("0.");
                        newOp = false;
                    } else if (screen.getText().indexOf('.') == -1) {
                        screen.setText(screen.getText() + ".");
                    }
                } else {
                    if (newOp) {
                        screen.setText(buttonText);
                        newOp = false;
                    } else {
                        screen.setText(screen.getText() + buttonText);
                    }
                }
                break;
        }
    }

    // 计算结果
    private void calc() {
        double currentValue = Double.parseDouble(screen.getText());
        switch (lastOp) {
            case "+":
                op += currentValue;
                break;
            case "-":
                op -= currentValue;
                break;
            case "*":
                op *= currentValue;
                break;
            case "/":
                op /= currentValue;
                break;
            case "=":
                op = currentValue;
                break;
        }
        screen.setText("" + op);
    }

}
