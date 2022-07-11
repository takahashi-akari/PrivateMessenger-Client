// @title Private Messenger Client
// @version 0.0.12
// @author Takahashi Akari <akaritakahashioss@gmail.com>
// @date 2022-07-09
// @description This is a private messenger server. App.java contains main method.
// @license MIT License
// @copyright (c) 2022 Takahashi Akari <akaritakahashioss@gmail.com>
// @url <https://takahashi-akari.github.io/PrivateMessenger/>
// @see https://raw.githubusercontent.com/takahashi-akari/PrivateMessenger-Server/main/privatemessengerserver/src/main/java/mn/akari/maven/privatemessengerserver/App.java
// @see https://raw.githubusercontent.com/takahashi-akari/PrivateMessenger-Server/main/privatemessengerserver/src/main/java/mn/akari/maven/privatemessengerserver/Constants.java
// @see https://raw.githubusercontent.com/takahashi-akari/PrivateMessenger-Server/main/README.md
// @see https://takahashi-akari.github.io/PrivateMessenger/
// @see https://raw.githubusercontent.com/takahashi-akari/PrivateMessenger-Client/main/privatemessengerclient/src/main/java/mn/akari/maven/privatemessengerclient/Client.java
package mn.akari.maven.privatemessengerclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
// JFrame is a class that provides a window for a program.
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class App extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton btnSend;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JButton btnExit;
    private JButton btnHelp;
    private JButton btnAbout;
    private JButton btnReceive;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private String host;
    private int port;
    private String message = "";
    // String is a class for string.
    private String messageType = "";
    // String is a class for string.
    private String messageBody = "";
    // String is a class for string.
    private String messageResponse = "";
    // String is a class for string.
    private String messageError = "";
    // String is a class for string.
    private String messageNotification = "";
    // String is a class for string.
    private String messageRequest = "";
    private String response = "";
    private String receivedMessage = "";
    
    private boolean connected;
    
    private static String[] argss;

    public static void main(String[] args) {
        argss = args;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App(argss);
            }
        });
    }


    public App(String[] args) {
        setTitle("Private Messenger Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setBounds(10, 227, 414, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textArea = new JTextArea();
        textArea.setBounds(10, 11, 414, 204);
        contentPane.add(textArea);
        textArea.setLineWrap(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 11, 414, 204);
        contentPane.add(scrollPane);

        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });
        btnSend.setBounds(434, 227, 89, 23);
        contentPane.add(btnSend);

        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
        btnConnect.setBounds(10, 248, 89, 23);
        contentPane.add(btnConnect);

        btnDisconnect = new JButton("Disconnect");
        btnDisconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });
        btnDisconnect.setBounds(109, 248, 89, 23);
        contentPane.add(btnDisconnect);

        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        btnExit.setBounds(208, 248, 89, 23);
        contentPane.add(btnExit);

        btnHelp = new JButton("Help");
        btnHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                help();
            }
        });
        btnHelp.setBounds(307, 248, 89, 23);
        contentPane.add(btnHelp);

        btnAbout = new JButton("About");
        btnAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                about();
            }
        });
        btnAbout.setBounds(406, 248, 89, 23);
        contentPane.add(btnAbout);

        btnReceive = new JButton("Receive");
        btnReceive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receive();
            }
        });
        btnReceive.setBounds(10, 270, 89, 23);
        contentPane.add(btnReceive);

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        };
        addWindowListener(exitListener);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    send();
                }
            }
        });

        if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            host = "localhost";
            port = 9999;
        }
        connected = false;

        setVisible(true);

        if (args.length == 2) {
            connect();
        }

        if (args.length == 1) {
            if (args[0].equals("-h") || args[0].equals("--help")) {
                help();
            } else if (args[0].equals("-a") || args[0].equals("--about")) {
                about();
            }
        }

        if (args.length == 0) {
            help();
        }
    }


    protected void receive() {
        // receive message from server with timeout
        try {
            scanner = new Scanner(socket.getInputStream());
            if (scanner.hasNextLine()) {
                receivedMessage = scanner.nextLine();
                textArea.append(receivedMessage + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void about() {
        JOptionPane.showMessageDialog(this, "Private Messenger Client\n" +
            "Copyright (C) 2022  Takahashi Akari <akaritakahashioss@gmail.com>\n" +
            "This program is free software: you can redistribute it and/or modify\n" +
            "it under the terms of the GNU General Public License as published by\n" +
            "the Free Software Foundation, either version 3 of the License, or\n" +
            "(at your option) any later version.\n" +
            "\n" +
            "This program is distributed in the hope that it will be useful,\n" +
            "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
            "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
            "GNU General Public License for more details.\n" +
            "\n" +
            "You should have received a copy of the GNU General Public License\n" +
            "along with this program.  If not, see <http://www.gnu.org/licenses/>.",
            "About", JOptionPane.INFORMATION_MESSAGE);
    }


    protected void help() {
        JOptionPane.showMessageDialog(this, "Usage: java -jar PrivateMessenger-Client-x.x.x-jar-with-dependencies [host] [port]\n" +
            "Example: java -jar PrivateMessengerClient.jar localhost 9092",
            "Help", JOptionPane.INFORMATION_MESSAGE);
    }


    protected void exit() {
        if (connected) {
            disconnect();
        }
        System.exit(0);
    }


    protected void disconnect() {
        if (connected) {
            try {
                out.close();
                in.close();
                socket.close();
                connected = false;
                btnConnect.setEnabled(true);
                btnDisconnect.setEnabled(false);
                btnSend.setEnabled(false);
                btnExit.setEnabled(true);
                btnHelp.setEnabled(true);
                btnAbout.setEnabled(true);
                textField.setEnabled(false);
                textArea.setEnabled(false);
                textArea.setText("");
                textField.setText("");
                message = "";
                response = "";
                setTitle("Private Messenger Client");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void connect() {
        if (!connected) {
            try {
                socket = new Socket(host, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                connected = true;
                btnConnect.setEnabled(false);
                btnDisconnect.setEnabled(true);
                btnSend.setEnabled(true);
                btnExit.setEnabled(false);
                btnHelp.setEnabled(false);
                btnAbout.setEnabled(false);
                textField.setEnabled(true);
                textArea.setEnabled(true);
                textArea.setText("");
                textField.setText("");
                message = "";
                response = "";
                setTitle("Private Messenger Client - " + host + ":" + port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void send() {
        if (connected) {
            message = textField.getText();
            if (message.length() > 0) {
                out.println(message);
                textField.setText("");
            }
        }
    }
}