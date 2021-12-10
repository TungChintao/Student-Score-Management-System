import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuiShow {
    JFrame frame;
    JTabbedPane tabbedPane;
    JPanel panel1, panel2, panel3;

    public static void main(String[] args) {
        GuiShow gui = new GuiShow();
        gui.run();
    }
    void run(){
        ArrayList<Student> students = new ArrayList<>();

        frame = new JFrame("学生成绩管理系统");

        panel1 = new JPanel(null);
        MessageBox msgBox = new MessageBox(0);
        msgBox.getBaseBox().setBounds(180,260, 720, 35);
        msgBox.addBox("学号");
        msgBox.addBox("成绩");
        msgBox.addButton("确认");
        panel1.add(msgBox.getBaseBox());

        panel2 = new JPanel(null);//new JPanel(new BorderLayout(60, 20));
        MessageBox msgBox2 = new MessageBox(1);
        msgBox2.addBox("学号");
        msgBox2.addButton("查询");
        msgBox2.getBaseBox().setBounds(290, 15, 500, 30);

//        panel2.add(msgBox2.getBaseBox(), BorderLayout.NORTH);
        panel2.add(msgBox2.getBaseBox());
        JPanel root = StudentTable.getInstance().getRoot();
        root.setBounds(0,60,1060,580);
        panel2.add(root);
//        panel2.add(new JScrollPane(table));
        //panel2.add(new JScrollPane(new JtableDemo().getTable()),BorderLayout.CENTER);

        panel3 = new JPanel(new BorderLayout(10,10));
        MessageBox msgBox3 = new MessageBox(2);
        msgBox3.addCenterButton("排序");
        msgBox3.getBaseBox().setBounds(100,20,10,10);
        panel3.add(msgBox3.getBaseBox(),BorderLayout.NORTH);

        JPanel[] panels = {panel1, panel2, panel3};
        createCardItem(panels);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null);
//        frame.setBackground(Color.LIGHT_GRAY);
        frame.setVisible(true);
    }

    public void createCardItem(JPanel[] panel) {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addChangeListener(new TabbedPaneListener());
        String[] title = {"输入", "查询", "排序"};

        for (int i = 0; i < 3; i++) {
            tabbedPane.add("成绩" + title[i], panel[i]);
        }
        tabbedPane.setFont(new Font("楷书", Font.PLAIN, 22));

        frame.add(tabbedPane, BorderLayout.CENTER);
    }

    class TabbedPaneListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if(tabbedPane.getSelectedIndex() == 1) {
                StudentTable.getInstance().hideTable();
                JPanel root = StudentTable.getInstance().getRoot();
                root.setBounds(0,60,1060,580);
                panel2.add(root);
            }
            else if(tabbedPane.getSelectedIndex() == 2) {
                StudentTable.getInstance().showTable();
                StudentTable.getInstance().showAllItem();
                panel3.add(StudentTable.getInstance().getRoot());
            }
        }
    }

}


