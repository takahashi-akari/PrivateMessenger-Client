// @title Private Messenger Client (GUI)
// @version 0.0.7
// @author Takahashi Akari <akaritakahashioss@gmail.com>
// @date 2022-07-09
// @description This is a private messenger client. using Swing.
// @license MIT License
// @copyright (c) 2022 Takahashi Akari <akaritakahashioss@gmail.com>
// @url <https://takahashi-akari.github.io/PrivateMessenger/>
// @see https://github.com/takahashi-akari/PrivateMessenger-Server/blob/main/privatemessengerserver/src/main/java/mn/akari/maven/privatemessengerserver/App.java
// @see https://github.com/takahashi-akari/PrivateMessenger-Server/blob/main/privatemessengerserver/src/main/java/mn/akari/maven/privatemessengerserver/Constsans.java
// @see https://github.com/takahashi-akari/PrivateMessenger-Server/blob/main/README.md
package mn.akari.maven.privatemessengerclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter; 
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Client extends JFrame implements ActionListener, KeyListener, MouseListener, ListSelectionListener {
    
    private static final long serialVersionUID = 1L;
    
    private JPanel panel;

    // Abstract
    private JPanel panel_abstract;
    private JLabel label_abstract;
    private JTextArea textarea_abstract;

    // Title
    private JPanel panel_title;
    private JLabel label_title;

    // User
    private JPanel panel_user;
    private JLabel label_user;
    private JTextField textfield_user;

    // Password
    private JPanel panel_password;
    private JLabel label_password;
    private JTextField textfield_password;

    // Login
    private JPanel panel_login;
    private JButton button_login;

    // Logout
    private JPanel panel_logout;
    private JButton button_logout;

    // Message
    private JPanel panel_message;
    private JLabel label_message;
    private JTextField textfield_message;
    private JButton button_message;

    // List
    private JPanel panel_list;
    private JLabel label_list;
    private JList list;
    private JScrollPane scrollpane_list;

    // Chat
    private JPanel panel_chat;
    private JLabel label_chat;
    private JTextArea textarea_chat;
    private JScrollPane scrollpane_chat;

    // Connect
    private JPanel panel_connect;
    private JButton button_connect;

    // Disconnect
    private JPanel panel_disconnect;
    private JButton button_disconnect;

    // Exit
    private JPanel panel_exit;
    private JButton button_exit;

    // Socket
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    // Thread
    private Thread thread;

    // List
    private List<String> list_user;
    private Vector<String> vector_user;

    // Chat
    private String chat_user;
    private String chat_message;
    private String chat_message_all;

    // Flag
    private boolean flag_login;
    private boolean flag_connect;
    private boolean flag_disconnect;
    private boolean flag_exit;

    // Constans
    private final String CONST_SERVER_IP = "localhost";
    private final int CONST_SERVER_PORT = 9999;
    private final String CONST_SERVER_MESSAGE_LOGIN = "login";
    private final String CONST_SERVER_MESSAGE_LOGOUT = "logout";
    private final String CONST_SERVER_MESSAGE_MESSAGE = "message";
    private final String CONST_SERVER_MESSAGE_LIST = "list";
    private final String CONST_SERVER_MESSAGE_CHAT = "chat";
    private final String CONST_SERVER_MESSAGE_CONNECT = "connect";
    private final String CONST_SERVER_MESSAGE_DISCONNECT = "disconnect";
    private final String CONST_SERVER_MESSAGE_EXIT = "exit";
    private final String CONST_SERVER_MESSAGE_ERROR = "error";
    private final String CONST_SERVER_MESSAGE_SUCCESS = "success";
    private final String CONST_SERVER_MESSAGE_FAIL = "fail";

    private JTextField textarea_message;

    private JPanel panel_send;

    private JButton button_send;

    private JList list_list;

    private JPanel panel_add;

    private JButton button_add;

    private JPanel panel_remove;

    private JButton button_remove;

    private int CONST_PANEL_WIDTH = 400;

    private int CONST_PANEL_HEIGHT = 800;

    private int CONST_FRAME_WIDTH;

    private int CONST_FRAME_HEIGHT;

    private Component textfield_username;

    private JTextArea textarea_list;

    private JPanel panel_clear;

    private JButton button_clear;

    // frame layouts
    public Client() {
        super("Private Messenger");
        setSize(CONST_PANEL_WIDTH, CONST_PANEL_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        add(panel, BorderLayout.CENTER);
        panel_abstract = new JPanel();
        panel_abstract.setLayout(new BorderLayout());
        panel_abstract.setBackground(Color.WHITE);
        panel_abstract.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_abstract);
        label_abstract = new JLabel("Private Messenger");
        label_abstract.setFont(new Font("Arial", Font.BOLD, 30));
        label_abstract.setHorizontalAlignment(JLabel.CENTER);
        panel_abstract.add(label_abstract, BorderLayout.CENTER);
        panel_title = new JPanel();
        panel_title.setLayout(new BorderLayout());
        panel_title.setBackground(Color.WHITE);
        panel_title.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_title);
        label_title = new JLabel("Private Messenger");
        label_title.setFont(new Font("Arial", Font.BOLD, 30));
        label_title.setHorizontalAlignment(JLabel.CENTER);
        panel_title.add(label_title, BorderLayout.CENTER);
        panel_user = new JPanel();
        panel_user.setLayout(new BorderLayout());
        panel_user.setBackground(Color.WHITE);
        panel_user.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_user);
        label_user = new JLabel("User");
        label_user.setFont(new Font("Arial", Font.BOLD, 30));
        label_user.setHorizontalAlignment(JLabel.CENTER);
        panel_user.add(label_user, BorderLayout.CENTER);
        textfield_user = new JTextField();
        textfield_user.setFont(new Font("Arial", Font.BOLD, 30));
        textfield_user.setHorizontalAlignment(JTextField.CENTER);
        textfield_user.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(textfield_user);
        panel_password = new JPanel();
        panel_password.setLayout(new BorderLayout());
        panel_password.setBackground(Color.WHITE);
        panel_password.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_password);
        label_password = new JLabel("Password");
        label_password.setFont(new Font("Arial", Font.BOLD, 30));
        label_password.setHorizontalAlignment(JLabel.CENTER);
        panel_password.add(label_password, BorderLayout.CENTER);
        textfield_password = new JTextField();
        textfield_password.setFont(new Font("Arial", Font.BOLD, 30));
        textfield_password.setHorizontalAlignment(JTextField.CENTER);
        textfield_password.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(textfield_password);
        panel_login = new JPanel();
        panel_login.setLayout(new BorderLayout());
        panel_login.setBackground(Color.WHITE);
        panel_login.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_login);
        button_login = new JButton("Login");
        button_login.setFont(new Font("Arial", Font.BOLD, 30));
        button_login.setHorizontalAlignment(JButton.CENTER);
        button_login.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel_login.add(button_login, BorderLayout.CENTER);
        panel_message = new JPanel();
        panel_message.setLayout(new BorderLayout());
        panel_message.setBackground(Color.WHITE);
        panel_message.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        label_message = new JLabel("Message");
        label_message.setFont(new Font("Arial", Font.BOLD, 30));
        label_message.setHorizontalAlignment(JLabel.CENTER);
        panel_message.add(label_message, BorderLayout.CENTER);
        textarea_message = new JTextField();
        textarea_message.setFont(new Font("Arial", Font.BOLD, 30));
        textarea_message.setHorizontalAlignment(JTextField.CENTER);
        textarea_message.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_message);
        panel.add(textarea_message);
        panel_send = new JPanel();
        panel_send.setLayout(new BorderLayout());
        panel_send.setBackground(Color.WHITE);
        panel_send.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_send);
        button_send = new JButton("Send");
        button_send.setFont(new Font("Arial", Font.BOLD, 30));
        button_send.setHorizontalAlignment(JButton.CENTER);
        button_send.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel_send.add(button_send, BorderLayout.CENTER);
        panel_add = new JPanel();
        panel_add.setLayout(new BorderLayout());
        panel_add.setBackground(Color.WHITE);
        panel_add.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_add);
        button_add = new JButton("Add");
        button_add.setFont(new Font("Arial", Font.BOLD, 30));
        button_add.setHorizontalAlignment(JButton.CENTER);
        button_add.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel_add.add(button_add, BorderLayout.CENTER);
        panel_remove = new JPanel();
        panel_remove.setLayout(new BorderLayout());
        panel_remove.setBackground(Color.WHITE);
        panel_remove.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_remove);
        button_remove = new JButton("Remove");
        button_remove.setFont(new Font("Arial", Font.BOLD, 30));
        button_remove.setHorizontalAlignment(JButton.CENTER);
        button_remove.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel_remove.add(button_remove, BorderLayout.CENTER);
        panel_logout = new JPanel();
        panel_logout.setLayout(new BorderLayout());
        panel_logout.setBackground(Color.WHITE);
        panel_logout.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_logout);
        button_logout = new JButton("Logout");
        button_logout.setFont(new Font("Arial", Font.BOLD, 30));
        button_logout.setHorizontalAlignment(JButton.CENTER);
        button_logout.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel_logout.add(button_logout, BorderLayout.CENTER);
        panel_exit = new JPanel();
        panel_exit.setLayout(new BorderLayout());
        panel_exit.setBackground(Color.WHITE);
        panel_exit.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_exit);
        button_exit = new JButton("Exit");
        button_exit.setFont(new Font("Arial", Font.BOLD, 30));
        button_exit.setHorizontalAlignment(JButton.CENTER);
        button_exit.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel_exit.add(button_exit, BorderLayout.CENTER);
        panel_list = new JPanel();
        panel_list.setLayout(new BorderLayout());
        panel_list.setBackground(Color.WHITE);
        panel_list.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_list);
        label_list = new JLabel("List");
        label_list.setFont(new Font("Arial", Font.BOLD, 30));
        label_list.setHorizontalAlignment(JLabel.CENTER);
        panel_list.add(label_list, BorderLayout.CENTER);
        textarea_list = new JTextArea();
        textarea_list.setFont(new Font("Arial", Font.BOLD, 30));
        textarea_list.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(textarea_list);
        panel_clear = new JPanel();
        panel_clear.setLayout(new BorderLayout());
        panel_clear.setBackground(Color.WHITE);
        panel_clear.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel.add(panel_clear);
        button_clear = new JButton("Clear");
        button_clear.setFont(new Font("Arial", Font.BOLD, 30));
        button_clear.setHorizontalAlignment(JButton.CENTER);
        button_clear.setPreferredSize(new Dimension(CONST_PANEL_WIDTH, 100));
        panel_clear.add(button_clear, BorderLayout.CENTER);
        panel_exit.add(button_exit, BorderLayout.CENTER);
        panel_logout.add(button_logout, BorderLayout.CENTER);
        panel_remove.add(button_remove, BorderLayout.CENTER);
        panel_add.add(button_add, BorderLayout.CENTER);
        panel_send.add(button_send, BorderLayout.CENTER);
        panel_message.add(label_message, BorderLayout.CENTER);       
    }

    private JScrollPane scrollpane_abstract;

    private String user;

    private String password;

    private int CONST_WINDOW_WIDTH;

    private int CONST_WINDOW_HEIGHT;

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScrollpane_abstract(JScrollPane scrollpane_abstract) {
        this.scrollpane_abstract = scrollpane_abstract;
    }

    public void setLabel_abstract(JLabel label_abstract) {
        this.label_abstract = label_abstract;
    }

    public void setTextarea_message(JTextField textarea_message) {
        this.textarea_message = textarea_message;
    }

    public void setButton_send(JButton button_send) {
        this.button_send = button_send;
    }

    public void setButton_logout(JButton button_logout) {
        this.button_logout = button_logout;
    }

    public void setButton_login(JButton button_login) {
        this.button_login = button_login;
    }

    public void setTextfield_user(JTextField textfield_user) {
        this.textfield_user = textfield_user;
    }


    public Client(String user, String password) {
        this.user = user;
        this.password = password;
        this.init();
    }

    // init
    public void init() {
        this.setTitle("Client");
        this.setSize(CONST_WINDOW_WIDTH, CONST_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        String user = JOptionPane.showInputDialog("User: ");
        String password = JOptionPane.showInputDialog("Password: ");
        Client client = new Client();
/*
        client.setClient(client);
        client.setUser(user);
        client.setPassword(password);
        client.setScrollpane_abstract(client.scrollpane_abstract);
        client.setLabel_abstract(client.label_abstract);
        client.setTextarea_message(client.textarea_message);
        client.setButton_send(client.button_send);
        client.setButton_logout(client.button_logout);
        client.setButton_login(client.button_login);
        client.setTextfield_user(client.textfield_user);

        client.init();

        client.setVisible(true);

        client.setButton_send(client.button_send);
        client.setButton_logout(client.button_logout);
        client.setButton_login(client.button_login);
        client.setTextfield_user(client.textfield_user);
*/
    }
}
