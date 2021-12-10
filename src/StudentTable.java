import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StudentTable {
    JPanel root = new JPanel(new BorderLayout());
    JTable table = new JTable();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
    private static final HashMap<String, Double> studentMessage = new HashMap<>();
    private static final StudentTable m_instance = new StudentTable();

    private StudentTable(){
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setRowHeight(50);
        table.setFont(new Font("宋体", Font.PLAIN, 18));
        table.setModel(this.tableModel);
        table.setEnabled(false);
        table.setRowSorter(sorter);

        tableModel.addColumn("学号");
        tableModel.addColumn("成绩");

        JTableHeader head = table.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.BOLD, 22));// 设置表格字体

        renderColumn();

        addRow(new Student("0001", 1));
        addRow(new Student("0002", 2));
        addRow(new Student("0003", 3));
        addRow(new Student("0004", 4));
        addRow(new Student("0005", 5));
        addRow(new Student("0006", 6));
        addRow(new Student("0009", 7));
        addRow(new Student("0007", 8));
        addRow(new Student("0008", 9));
        addRow(new Student("0010", 10));
        addRow(new Student("0012", 12));
        addRow(new Student("0011", 11));

        root.add(new JScrollPane(table));

    }

    public static StudentTable getInstance(){
        return m_instance;
    }

    public boolean searchMessage(String id){
        if(!containStudentID(id))
            return false;
        // regexFilter 正则 选择filter的列(默认为全部)
        sorter.setRowFilter(RowFilter.regexFilter(id,0));
        showTable();
        return true;
    }


    public void showAllItem(){
        sorter.setRowFilter(RowFilter.regexFilter(""));
    }

    public boolean sortByScore(){
        if(studentMessage.size() == 0)
            return false;
        Vector<String> columnNames = new Vector<>();
        columnNames.add("学号");
        columnNames.add("成绩");
        Vector<List> data =  tableModel.getDataVector();
        Collections.sort(data, new ScoreComparator());

        tableModel.setDataVector(data,columnNames);
        renderColumn();
        return true;
    }

    public void renderColumn(){
        StudentTableRender render = new StudentTableRender();
        for(int i = 0;i<table.getColumnCount();i++){
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(render);
        }
    }

    public void hideTable(){
        table.setVisible(false);
    }

    public void showTable(){
        table.setVisible(true);
    }


    public void addRow(Student item){
        Vector<Object> rowData = new Vector<>();
        rowData.add(item.getStudentID());
        rowData.add(item.getScore());
        studentMessage.put(item.getStudentID(), item.getScore());

        tableModel.addRow(rowData);
    }

    public JPanel getRoot() {
        return root;
    }

    public boolean containStudentID(String id){

        return studentMessage.containsKey(id);
    }
}

class StudentTableRender extends DefaultTableCellRenderer{
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if(row % 2 == 0) setBackground(Color.LIGHT_GRAY);
        else setBackground(Color.WHITE);
        setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}


class ScoreComparator implements Comparator<List>{

    @Override
    public int compare(List o1, List o2) {
        Double a = (Double)o1.get(1);
        Double b = (Double)o2.get(1);
        if(a<b)
            return 1;
        else if(a>b)
            return -1;
        else{
            String c = (String)o1.get(0);
            String d = (String)o2.get(0);
            if(c.compareTo(d)>0)
                return 1;
            else
                return -1;
        }
    }
}

