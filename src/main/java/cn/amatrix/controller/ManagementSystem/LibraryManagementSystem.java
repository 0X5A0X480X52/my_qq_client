package cn.amatrix.controller.ManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 主类，继承 JFrame
public class LibraryManagementSystem extends JFrame {
    private JTable bookTable; // 图书表格
    private DefaultTableModel bookTableModel; // 图书表格模型
    private JTextField titleField, authorField, yearField, searchField; // 输入框
    private JPanel mainPanel; // 主面板
    private CardLayout cardLayout; // 卡片布局

    private MySQLCRUD connector; // 数据库连接器

    // 构造方法，初始化界面
    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // 居中显示

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        menu.add(aboutMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // 设置卡片布局，用于不同页面的切换
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 创建标签栏 (JTabbedPane)
        JTabbedPane tabbedPane = new JTabbedPane();

        // 添加标签并添加监听器，在选择标签时才加载内容
        tabbedPane.addTab("图书管理", null);
        tabbedPane.addTab("用户管理", null);
        mainPanel.add(createBookPanel(), "Book Management");

        // 标签选择监听器，根据选项加载内容
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 0) { // 图书管理
                mainPanel.add(createBookPanel(), "Book Management");
                cardLayout.show(mainPanel, "Book Management");
            } else if (selectedIndex == 1) { // 用户管理
                mainPanel.add(createUserPanel(), "User Management");
                cardLayout.show(mainPanel, "User Management");
            }
        });

        // 菜单项的监听器，用于显示关于页面
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Library Management System\nVersion 1.0\nDeveloped by ChatGPT",
                "关于",
                JOptionPane.INFORMATION_MESSAGE
        ));

        // 将标签栏和主面板添加到窗口中
        add(tabbedPane, BorderLayout.NORTH); // 将标签栏放置在窗口上方
        add(mainPanel, BorderLayout.CENTER);  // 将主面板放置在中央

        setVisible(true);
    }

    // 创建图书管理页面
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 创建表格模型
        bookTableModel = new DefaultTableModel(new String[]{"Title", "Author", "Year"}, 0);
        bookTable = new JTable(bookTableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 从数据库加载图书数据
        connector = new MySQLCRUD();
        String sql = "SELECT * FROM books";
        try (Statement stmt = connector.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String year = rs.getString("year");
                System.out.println("title: " + title + ", author: " + author + ", year: " + year);

                bookTableModel.addRow(new Object[]{title, author, year});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 创建表格的滚动面板
        JScrollPane tableScrollPane = new JScrollPane(bookTable);

        // 左侧侧边栏
        JPanel sidebar = new JPanel();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWeights = new double[]{2, 7};
        gridBagLayout.rowWeights = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 1};

        sidebar.setLayout(gridBagLayout);

        // sidebar.setLayout(new GridLayout(10, 1, 10, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 添加输入框
        sidebar.add(new JLabel("Title:"), new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 0; // 行
            }
        });
        titleField = new JTextField();
        sidebar.add(titleField, new GridBagConstraints() {
            {
                gridx = 1; // 列
                gridy = 0; // 行
                weighty = 0;
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        sidebar.add(new JLabel("Author:"), new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 1; // 行
            }
        });
        authorField = new JTextField();
        sidebar.add(authorField, new GridBagConstraints() {
            {
                gridx = 1; // 列
                gridy = 1; // 行
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        sidebar.add(new JLabel("Year:"), new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 2; // 行
            }
        });
        yearField = new JTextField();
        sidebar.add(yearField, new GridBagConstraints() {
            {
                gridx = 1; // 列
                gridy = 2; // 行
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        // 添加操作按钮
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new AddButtonListener());
        sidebar.add(addButton, new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 4; // 行
                gridwidth = 2;
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(new EditButtonListener());
        sidebar.add(editButton, new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 5; // 行
                gridwidth = 2;
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(new DeleteButtonListener());
        sidebar.add(deleteButton, new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 6; // 行
                gridwidth = 2;
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        // 添加搜索框和按钮
        sidebar.add(new JLabel("Search by Title:"), new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 7; // 行
            }
        });
        searchField = new JTextField();
        sidebar.add(searchField, new GridBagConstraints() {
            {
                gridx = 1; // 列
                gridy = 7; // 行
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        sidebar.add(searchButton, new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 8; // 行
                gridwidth = 2;
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        sidebar.add(new JPanel(), new GridBagConstraints() {
            {
                gridx = 0; // 列
                gridy = 10; // 行
                fill = GridBagConstraints.BOTH; // 填充模式
            }
        });

        // 使用 JSplitPane 分隔侧边栏和表格区域
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, tableScrollPane);
        splitPane.setDividerLocation(250); // 设置分隔条位置
        splitPane.setResizeWeight(0); // 固定左侧侧边栏
        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    // 创建用户管理页面 (示例)
    private JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel userLabel = new JLabel("用户管理页面", SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(userLabel, BorderLayout.CENTER);
        return panel;
    }

    // 添加图书的监听器
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String author = authorField.getText();
            String year = yearField.getText();

            if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty()) {
                bookTableModel.addRow(new Object[]{title, author, year});
                titleField.setText("");
                authorField.setText("");
                yearField.setText("");

                // 插入图书数据到数据库
                String sql = "INSERT INTO books (Title, Author, Year) VALUES ( ?, ?, ?)";
                try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
                    pstmt.setString(1, title);
                    pstmt.setString(2, author);
                    pstmt.setString(3, year);
                    pstmt.executeUpdate();
                    System.out.println("Books created: " + title);
                } catch (SQLException a) {
                    a.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            }
        }
    }

    // 编辑图书的监听器
    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = bookTable.getSelectedRow();

            if (selectedRow != -1) {
                String title = titleField.getText();
                String author = authorField.getText();
                String year = yearField.getText();

                if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty()) {

                    String org_title = bookTableModel.getValueAt(selectedRow, 0).toString();
                    String org_author = bookTableModel.getValueAt(selectedRow, 1).toString();
                    String org_year = bookTableModel.getValueAt(selectedRow, 2).toString();

                    bookTableModel.setValueAt(title, selectedRow, 0);
                    bookTableModel.setValueAt(author, selectedRow, 1);
                    bookTableModel.setValueAt(year, selectedRow, 2);

                    // 更新图书数据到数据库
                    String sql = "UPDATE books SET title = ?, author = ?, year = ? WHERE title = ? AND author = ? AND year = ?";
                    try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
                        pstmt.setString(1, title);
                        pstmt.setString(2, author);
                        pstmt.setString(3, year);
                        pstmt.setString(4, org_title);
                        pstmt.setString(5, org_author);
                        pstmt.setString(6, org_year);
                        pstmt.executeUpdate();
                        System.out.println("Books updated: " + title);
                    } catch (SQLException a) {
                        a.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a book to edit.");
            }
        }
    }

    // 删除图书的监听器
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = bookTable.getSelectedRow();

            if (selectedRow != -1) {

                String title = bookTableModel.getValueAt(selectedRow, 0).toString();
                String author = bookTableModel.getValueAt(selectedRow, 1).toString();
                String year = bookTableModel.getValueAt(selectedRow, 2).toString();

                bookTableModel.removeRow(selectedRow);

                // 从数据库删除图书数据
                String sql = "DELETE FROM books WHERE title = ? AND author = ? AND year = ?";
                try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
                    pstmt.setString(1, title);
                    pstmt.setString(2, author);
                    pstmt.setString(3, year);
                    pstmt.executeUpdate();
                    System.out.println("User deleted with Title: " + title);
                } catch (SQLException a) {
                    a.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Please select a book to delete.");
            }
        }
    }

    // 搜索图书的监听器
    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTitle = searchField.getText().toLowerCase();

            if (!searchTitle.isEmpty()) {
                for (int i = 0; i < bookTableModel.getRowCount(); i++) {
                    String title = bookTableModel.getValueAt(i, 0).toString().toLowerCase();

                    if (title.contains(searchTitle)) {
                        bookTable.setRowSelectionInterval(i, i);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Book not found.");
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a title to search.");
            }
        }
    }

    // 主方法，启动程序
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryManagementSystem::new);
    }
}
