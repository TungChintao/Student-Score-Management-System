import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MessageBox {
    private final Box baseBox;
    private final int boxType;
    private Font font = new Font("楷体", Font.PLAIN, 22);
    private final ArrayList<JTextField> texts = new ArrayList<>();

    MessageBox(int type){
        baseBox=Box.createHorizontalBox();
        boxType = type;
    }

    private JTextField createTextField(){
        JTextField text = new JTextField(22);
        text.setFont(font);
        texts.add(text);
        return text;
    }

    public void addBox(String title ){
        JLabel label = new JLabel(title);
        label.setFont(font);
        Box messageBox = Box.createHorizontalBox();
        messageBox.add(label);
        messageBox.add(Box.createHorizontalStrut(12));
        messageBox.add(createTextField());
        baseBox.add(messageBox);
        baseBox.add(Box.createHorizontalStrut(28));
    }


    public void addButton(String title){
        JButton button = new JButton(title);
        button.setFont(font);
        if(boxType == 0)
            button.addActionListener(new AddItemListener());
        else if(boxType == 1){
            button.addActionListener(new SearchItemListener());
        }
        else{
            button.addActionListener(new SortItemListener());
        }
        baseBox.add(button);
        baseBox.add(Box.createHorizontalStrut(28));
    }

    public void addCenterButton(String title){

        baseBox.add(Box.createHorizontalStrut(487));
        JButton button = new JButton(title);
        button.setFont(font);
        button.addActionListener(new SortItemListener());
        baseBox.add(button);

    }

    public Box getBaseBox() {
        return baseBox;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    class AddItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String id = texts.get(0).getText();
                Integer.parseInt(id);
                double score = Double.parseDouble(texts.get(1).getText());
                if(score<0 || score > 100){
                    JOptionPane.showMessageDialog(null, "分数应在0-100间");
                    return;
                }
                if(StudentTable.getInstance().containStudentID(id)) {
                    JOptionPane.showMessageDialog(null, "学号已存在");
                    return;
                }
                Student student = new Student(id, score);
                StudentTable.getInstance().addRow(student);
                JOptionPane.showMessageDialog(null, "输入成功");
                texts.get(0).setText("");
                texts.get(1).setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "请输入正确的学号和成绩");
            }
        }
    }

    class SearchItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            String id = texts.get(0).getText();
            if(!StudentTable.getInstance().searchMessage(id))
                JOptionPane.showMessageDialog(null, "查无此学号");
            texts.get(0).setText("");

        }
    }

    static class SortItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(StudentTable.getInstance().sortByScore())
                return;
            JOptionPane.showMessageDialog(null, "表中未输入数据");
        }
    }
}
