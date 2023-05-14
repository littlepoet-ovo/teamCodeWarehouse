import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

//备注：因为题目没有要求“格式”和“查看”菜单需要些什么功能，所以这两个菜单按钮式空有其表，没有功能的
public class work2 extends JFrame implements ActionListener{
    JMenuItem newBuilt = new JMenuItem("新建");//实例化“新建”菜单按钮
    JMenuItem open = new JMenuItem("打开");//实例化“打开”菜单按钮
    JMenuItem save = new JMenuItem("保存");//实例化“保存”菜单按钮
    JMenuItem saveAs = new JMenuItem("另存");//实例化“另存为”菜单按钮
    JMenuItem copy = new JMenuItem("复制");//实例化“复制”菜单按钮
    JMenuItem paste = new JMenuItem("粘贴");//实例化“粘贴”菜单按钮
    JMenuItem find = new JMenuItem("查找");//实例化“查找”菜单按钮
    JMenuItem replace = new JMenuItem("替换");//实例化“替换”菜单按钮
    JMenuItem about = new JMenuItem("关于");//实例化“关于”菜单按钮
    JTextArea text = new JTextArea(10,10);//实例化用来书写文本内容的文本框区域
    String filePath;//用来存储读取或者需要保存的文件来自哪个路径
    public work2() {
        initJFrame();//初始化窗口
        initJMenuBar();//初始化窗口顶部的菜单栏
        initTextArea();//初始化文本输入框
        initClose();//初始化窗口关闭监听器
        this.setVisible(true);
    }
    private void initJMenuBar() {
        //以下代码实现了对窗口顶部菜单栏的初始化，并且将菜单栏中相关的选项加入了事件监听。
        JMenuBar JMB = new JMenuBar();
        JMenu fileJM = new JMenu("文件");
        JMenu editJM = new JMenu("编辑");
        JMenu formatJM = new JMenu("格式");
        JMenu seeJM = new JMenu("查看");
        JMenu helpJM = new JMenu("帮助");
        fileJM.add(newBuilt);
        fileJM.add(open);
        fileJM.add(save);
        fileJM.add(saveAs);
        editJM.add(copy);
        editJM.add(paste);
        editJM.add(find);
        editJM.add(replace);
        helpJM.add(about);
        JMB.add(fileJM);
        JMB.add(editJM);
        JMB.add(formatJM);
        JMB.add(seeJM);
        JMB.add(helpJM);
        newBuilt.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        find.addActionListener(this);
        replace.addActionListener(this);
        about.addActionListener(this);
        this.setJMenuBar(JMB);
    }

    private void initJFrame() {
        this.setSize(600,450);
        this.setTitle("我的记事本");
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//关闭模式设置
        this.setLayout(null);//清除格式

    }
    private void initTextArea() {
        text.setBounds(0,0,600,400);//设置文本框的参数
        this.getContentPane().add(text);//将文本框添加到主窗口
    }

    private void initClose() {
        this.addWindowListener(new WindowAdapter() {//添加窗口关闭监听器
            public void windowClosing(WindowEvent e) {
                if(filePath==null){//如果文件不存在保存路径也就是说文件未保存就执行下面的代码
                    int confirmed = JOptionPane.showConfirmDialog(null, "文件尚未保存，是否保存文件？", "文件未保存", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {//当用户选择了“是”就执行下面的代码，下面的代码和另存为的代码一模一样，就不赘述了。
                        JFileChooser fileChooser = new JFileChooser();//实例化文件选择器
                        fileChooser.setDialogTitle("保存");//设置文件选择器的标题
                        fileChooser.setSelectedFile(new File("filename.txt"));
                        int userSelection = fileChooser.showSaveDialog(null);
                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            filePath = fileToSave.getAbsolutePath();
                            System.out.println(filePath);
                        }
                        File file = new File(filePath);
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                            bw.write(text.getText());
                            bw.close();
                        }catch (Exception a){
                            System.out.println(a);
                        }

                    }
                }
                work2.this.dispose();//匿名内部类中调用父类方法的方式，该方法表示关闭该窗口。

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o==newBuilt){//如果是“新建”的选项被单击了，那么就清空文本框的内容，并且将保存的地址也设置为空
            text.setText("");
            filePath = null;
        }else if(o==open){//“打开”选项被单击
            JFileChooser fileChooser = new JFileChooser();//实例化一个文件选择窗口的对象
            fileChooser.setDialogTitle("选择文件");//设置文件选择窗口的标题
            int userSelection = fileChooser.showOpenDialog(null);//将文件选择器打开并等待选择文件或者目录
            if (userSelection == JFileChooser.APPROVE_OPTION) {//用户点击了是
                StringBuilder sb = new StringBuilder();//将文件中的数据存入sb中
                filePath = fileChooser.getSelectedFile().getAbsolutePath();//获取打开文件的路径
                File file = new File(filePath);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;//存储文件中每一行字符串的变量
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                    br.close();
                }catch (Exception a){
                    System.out.println(a.toString());
                }
                text.setText(sb.toString());
            }
        }
        else if(o==save){//“保存”按钮被单机
            if(filePath==null){//路径是空意味着这个文件从未被保存过，需要打开文件选择器进行保存

                JFileChooser fileChooser = new JFileChooser();//实例化文件选择器
                fileChooser.setDialogTitle("保存");//设置文件选择器的标题
                fileChooser.setSelectedFile(new File("filename.txt"));
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    filePath = fileToSave.getAbsolutePath();
                    System.out.println(filePath);
                }
                //通过以上代码获得了文件需要保存到位置的路径
            }
            //以下代码负责依照路径将文本框中的数据写到本地
            File file = new File(filePath);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(text.getText());
                bw.close();
            }catch (Exception a){
                System.out.println(a);
            }

        }
        else if(o==saveAs){//和上面代码基本一致，因为是另存为，所以不需要判断有无初始路径，直接进行保存就可以了
            JFileChooser fileChooser = new JFileChooser();//实例化文件选择器
            fileChooser.setDialogTitle("保存");//设置文件选择器的标题
            fileChooser.setSelectedFile(new File("filename.txt"));
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                filePath = fileToSave.getAbsolutePath();
                System.out.println(filePath);
            }
            File file = new File(filePath);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(text.getText());
                bw.close();
            }catch (Exception a){
                System.out.println(a);
            }
        }
        else if(o==copy){//“复制”按钮被选择
            String selectedText = text.getSelectedText();//获取文本框中被用户选中的文字段落
            StringSelection stringSelection = new StringSelection(selectedText);//创建了一个StringSelection对象，该对象包含了selectedText的内容
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//获取了系统剪辑板的实例
            clipboard.setContents(stringSelection, null);//将内容保存到系统剪辑板
        }
        else if(o==paste){//“粘贴"按钮被选中
            try {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//获取了系统剪辑板的实例
                Transferable contents = clipboard.getContents(null);//检查剪辑板中是否有字符串类型的数据
                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    String text1 = (String) contents.getTransferData(DataFlavor.stringFlavor);//将剪辑板中数据转成字符串赋值给text1
                    text.setText(text.getText()+text1);
                }
            }catch (Exception a){
                System.out.println(a);
            }

        }
        else if(o==find){//查找按钮被点击
            String textC = text.getText();//获取文本框中的内容
            String searchText = JOptionPane.showInputDialog(null, "请输入需要查找的内容：");//创建一个输入框
            int offset = textC.indexOf(searchText);//获取了需要被搜索的字符串在原字符串中的位置
            int length = searchText.length();//获取需要被搜索字符串的长度
            while (offset != -1) {//字符串被找到则进入循环结构
                try {
                    Highlighter highlighter = text.getHighlighter();//获取文本框的”高亮“对象
                    highlighter.addHighlight(offset, offset + length, DefaultHighlighter.DefaultPainter);//设置文本框高亮
                    offset = textC.indexOf(searchText, offset + 1);//寻找主文本中下一个需要被搜索的关键字
                } catch (BadLocationException a) {
                    a.printStackTrace();
                }
            }
        }
        else if(o==replace){//查找按钮被点击
            String findText =JOptionPane.showInputDialog(null, "请输入需要查找的内容：");
            String replaceText =JOptionPane.showInputDialog(null, "请输入需要替换成的内容：");
            String TextC = text.getText();
            text.setText(TextC.replace(findText,replaceText));
        }
        else if(o==about){//关于按钮被点击
            JOptionPane.showMessageDialog(null, "Powered by Ding Zijie");
        }
    }
}
