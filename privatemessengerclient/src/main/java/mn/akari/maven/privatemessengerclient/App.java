// @title Private Messenger Client
// @version 0.0.8
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
    private JButton btnClear;
    private JButton btnHelp;
    private JButton btnAbout;
    private JButton btnSendFile;
    private JButton btnReceiveFile;
    private JButton btnSendMessage;
    private JButton btnReceiveMessage;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private String host;
    private int port;
    private String username;
    private String password;
    private String message;
    private String file;
    private String filePath;
    private String fileName;
    private String fileSize;
    private String fileType;
    private String fileReceived;
    private String fileReceivedPath;
    private String fileReceivedName;
    private String fileReceivedSize;
    private String fileReceivedType;
    
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
        setBounds(100, 100, 550, 380);
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

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        btnClear.setBounds(307, 248, 89, 23);
        contentPane.add(btnClear);

        btnHelp = new JButton("Help");
        btnHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                help();
            }
        });
        btnHelp.setBounds(406, 248, 89, 23);
        contentPane.add(btnHelp);

        btnAbout = new JButton("About");
        btnAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                about();
            }
        });

        btnAbout.setBounds(405, 11, 89, 23);
        contentPane.add(btnAbout);

        btnSendFile = new JButton("Send File");
        btnSendFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFile();
            }
        });

        btnSendFile.setBounds(10, 268, 89, 23);
        contentPane.add(btnSendFile);

        btnReceiveFile = new JButton("Receive File");
        btnReceiveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiveFile();
            }
        });

        btnReceiveFile.setBounds(109, 268, 89, 23);

        contentPane.add(btnReceiveFile);

        btnSendMessage = new JButton("Send Message");
        btnSendMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        btnSendMessage.setBounds(208, 268, 89, 23);
        contentPane.add(btnSendMessage);

        btnReceiveMessage = new JButton("Receive Message");
        btnReceiveMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiveMessage();
            }
        });

        btnReceiveMessage.setBounds(307, 268, 89, 23);
        contentPane.add(btnReceiveMessage);

        if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            host = "localhost";
            port = 9999;
        }

        connected = false;
        username = "";
        password = "";
        message = "";
        file = "";
        filePath = "";
        fileName = "";
        fileSize = "";
        fileType = "";
        fileReceived = "";
        fileReceivedPath = "";
        fileReceivedName = "";
        fileReceivedSize = "";
        fileReceivedType = "";

        setVisible(true);

        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        String line = in.readLine();
                        if (line != null) {
                            if (line.startsWith("#")) {
                                if (line.startsWith("#CONNECTED")) {
                                    connected = true;
                                    username = line.substring(10);
                                    textArea.append("Connected to server as " + username + "\n");
                                } else if (line.startsWith("#DISCONNECTED")) {
                                    connected = false;
                                    username = "";
                                    password = "";
                                    message = "";
                                    file = "";
                                    filePath = "";
                                    fileName = "";
                                    fileSize = "";
                                    fileType = "";
                                    fileReceived = "";
                                    fileReceivedPath = "";
                                    fileReceivedName = "";
                                    fileReceivedSize = "";
                                    fileReceivedType = "";
                                    textArea.append("Disconnected from server\n");
                                } else if (line.startsWith("#MESSAGE")) {
                                    textArea.append(line.substring(9) + "\n");
                                } else if (line.startsWith("#FILE")) {
                                    textArea.append(line.substring(7) + "\n");
                                } else if (line.startsWith("#FILE_RECEIVED")) {
                                    textArea.append(line.substring(14) + "\n");
                                } else if (line.startsWith("#FILE_RECEIVED_ERROR")) {
                                    textArea.append(line.substring(19) + "\n");
                                } else if (line.startsWith("#FILE_RECEIVED_SUCCESS")) {
                                    textArea.append(line.substring(20) + "\n");
                                } else if (line.startsWith("#FILE_SENT")) {
                                    textArea.append(line.substring(10) + "\n");
                                } else if (line.startsWith("#FILE_SENT_ERROR")) {
                                    textArea.append(line.substring(15) + "\n");
                                } else if (line.startsWith("#FILE_SENT_SUCCESS")) {
                                    textArea.append(line.substring(16) + "\n");
                                } else if (line.startsWith("#FILE_SENT_PROGRESS")) {
                                    textArea.append(line.substring(18) + "\n");
                                } else if (line.startsWith("#FILE_RECEIVED_PROGRESS")) {
                                    textArea.append(line.substring(21) + "\n");
                                }
                            } else {
                                textArea.append(line + "\n");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        setResizable(false);

        setTitle("PrivateMessenger Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void receiveMessage() {
        if (connected) {
            try {
                String message = scanner.nextLine();
                textArea.append(message + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    protected void sendMessage() {
        if (connected) {
            message = textField.getText();
            if (message.length() > 0) {
                out.println(message);
                textField.setText("");
            }
        }
    }


    protected void receiveFile() {
        if (connected) {
            try {
                fileReceived = scanner.nextLine();
                textArea.append(fileReceived + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    protected void sendFile() {
        if (connected) {
            file = textField.getText();
            if (file.length() > 0) {
                out.println(file);
                textField.setText("");
            }
        }
    }


    protected void about() {
        JOptionPane.showMessageDialog(this, "Private Messenger\nVersion 0.0.8\nCopyright (c) 2022 Takahashi Akari <https://takahashi-akari.github.io/PrivateMessenger/>\n\n");
    }


    protected void help() {
        JOptionPane.showMessageDialog(this, "Private Messenger\nVersion 0.0.8\nCopyright (c) 2022 Takahashi Akari <https://takahashi-akari.github.io/PrivateMessenger/>\n\n");
    }


    protected void clear() {
        textArea.setText("");
    }


    protected void exit() {
        if (connected) {
            disconnect();
        }
        System.exit(0);
    }


    protected void disconnect() {
        if (connected) {
            out.println("DISCONNECT");
            connected = false;
            btnConnect.setText("Connect");
            btnDisconnect.setEnabled(false);
            btnSendFile.setEnabled(false);
            btnReceiveFile.setEnabled(false);
            btnSendMessage.setEnabled(false);
            btnReceiveMessage.setEnabled(false);
            btnSendFile.setEnabled(false);
            btnReceiveFile.setEnabled(false);
            btnSendMessage.setEnabled(false);
            btnReceiveMessage.setEnabled(false);
            btnClear.setEnabled(false);
            btnExit.setEnabled(false);
            btnAbout.setEnabled(false);
            btnHelp.setEnabled(false);
            textField.setEnabled(false);
            textArea.setEnabled(false);
            textField.setText("");
            textArea.setText("");
            username = "";
            password = "";
            message = "";
            file = "";
            filePath = "";
            fileName = "";
            fileSize = "";
            fileType = "";
            fileReceived = "";
            fileReceivedPath = "";
            fileReceivedName = "";
            fileReceivedSize = "";
            fileReceivedType = "";
        }
    }


    protected void connect() {
        if (!connected) {
            username = textField.getText();
            if (username.length() > 0) {
                out.println(username);
                textField.setText("");
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


    protected void receive() {
        if (connected) {
            try {
                String message = scanner.nextLine();
                textArea.append(message + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

