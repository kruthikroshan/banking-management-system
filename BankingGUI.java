import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/* -------- Main GUI Application -------- */
public class BankingGUI extends JFrame {

  private static final Color PRIMARY_COLOR = new Color(25, 118, 210);
  private static final Color SUCCESS_COLOR = new Color(46, 125, 50);
  private static final Color DANGER_COLOR = new Color(211, 47, 47);
  private static final Color BG_COLOR = new Color(245, 245, 245);
  private static final Color CARD_COLOR = Color.WHITE;
  private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
  private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
  private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
  private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

  private final CardLayout cardLayout;
  private final JPanel mainPanel;
  private AccountGUI currentAccount;

  public BankingGUI() {
    setTitle("🏦 Banking Management System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1100, 750);
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(1000, 700));

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    mainPanel.setBackground(BG_COLOR);

    mainPanel.add(createWelcomePanel(), "welcome");
    mainPanel.add(createLoginPanel(), "login");
    mainPanel.add(createRegisterPanel(), "register");
    mainPanel.add(createDashboardPanel(), "dashboard");

    add(mainPanel);
    cardLayout.show(mainPanel, "welcome");
  }

  /* -------- Welcome Panel -------- */
  private JPanel createWelcomePanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BG_COLOR);

    // Header
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(PRIMARY_COLOR);
    headerPanel.setPreferredSize(new Dimension(0, 150));
    headerPanel.setLayout(new GridBagLayout());

    JLabel titleLabel = new JLabel("🏦 Banking Management System");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
    titleLabel.setForeground(Color.WHITE);
    headerPanel.add(titleLabel);

    panel.add(headerPanel, BorderLayout.NORTH);

    // Center content
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(BG_COLOR);

    JPanel cardPanel = createCard(400, 300);
    cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

    JLabel welcomeLabel = new JLabel("Welcome!");
    welcomeLabel.setFont(TITLE_FONT);
    welcomeLabel.setForeground(PRIMARY_COLOR);
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel subtitleLabel = new JLabel("Your trusted banking partner");
    subtitleLabel.setFont(REGULAR_FONT);
    subtitleLabel.setForeground(Color.GRAY);
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton existingUserBtn = createStyledButton("Existing User", PRIMARY_COLOR);
    existingUserBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    existingUserBtn.setMaximumSize(new Dimension(250, 45));
    existingUserBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));

    JButton newUserBtn = createStyledButton("New User", SUCCESS_COLOR);
    newUserBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    newUserBtn.setMaximumSize(new Dimension(250, 45));
    newUserBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));

    JButton exitBtn = createStyledButton("Exit", DANGER_COLOR);
    exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    exitBtn.setMaximumSize(new Dimension(250, 45));
    exitBtn.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(this,
          "Are you sure you want to exit?", "Confirm Exit",
          JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    });

    cardPanel.add(Box.createVerticalStrut(20));
    cardPanel.add(welcomeLabel);
    cardPanel.add(Box.createVerticalStrut(10));
    cardPanel.add(subtitleLabel);
    cardPanel.add(Box.createVerticalStrut(40));
    cardPanel.add(existingUserBtn);
    cardPanel.add(Box.createVerticalStrut(15));
    cardPanel.add(newUserBtn);
    cardPanel.add(Box.createVerticalStrut(15));
    cardPanel.add(exitBtn);
    cardPanel.add(Box.createVerticalStrut(20));

    centerPanel.add(cardPanel);
    panel.add(centerPanel, BorderLayout.CENTER);

    // Footer
    JPanel footerPanel = new JPanel();
    footerPanel.setBackground(BG_COLOR);
    JLabel footerLabel = new JLabel("© 2026 Banking Management System. All rights reserved.");
    footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    footerLabel.setForeground(Color.GRAY);
    footerPanel.add(footerLabel);
    panel.add(footerPanel, BorderLayout.SOUTH);

    return panel;
  }

  /* -------- Login Panel -------- */
  private JPanel createLoginPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BG_COLOR);

    // Header
    JPanel headerPanel = createHeader("Login to Your Account");
    panel.add(headerPanel, BorderLayout.NORTH);

    // Center
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(BG_COLOR);

    JPanel cardPanel = createCard(500, 550);
    cardPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 20, 15, 20);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel accLabel = new JLabel("Account Number:");
    accLabel.setFont(REGULAR_FONT);
    accLabel.setForeground(Color.DARK_GRAY);
    gbc.gridx = 0;
    gbc.gridy = 0;
    cardPanel.add(accLabel, gbc);

    JTextField accField = createStyledTextField();
    gbc.gridx = 0;
    gbc.gridy = 1;
    cardPanel.add(accField, gbc);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    passLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 2;
    cardPanel.add(passLabel, gbc);

    JPasswordField passField = createStyledPasswordField();
    gbc.gridx = 0;
    gbc.gridy = 3;
    cardPanel.add(passField, gbc);

    JLabel captchaLabel = new JLabel("Captcha:");
    captchaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    captchaLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 4;
    cardPanel.add(captchaLabel, gbc);

    // Captcha display with code
    JPanel captchaDisplayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    captchaDisplayPanel.setBackground(CARD_COLOR);
    int[] captcha = { BankGUI.generateCaptcha() };
    JLabel captchaDisplay = new JLabel("  " + captcha[0] + "  ");
    captchaDisplay.setFont(new Font("Courier New", Font.BOLD, 22));
    captchaDisplay.setForeground(Color.BLACK);
    captchaDisplay.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 3));
    captchaDisplay.setOpaque(true);
    captchaDisplay.setBackground(new Color(232, 245, 253));
    captchaDisplay.setPreferredSize(new Dimension(120, 45));

    JButton refreshCaptcha = new JButton("🔄 Refresh");
    refreshCaptcha.setFont(new Font("Segoe UI", Font.BOLD, 14));
    refreshCaptcha.setForeground(PRIMARY_COLOR);
    refreshCaptcha.setBorderPainted(true);
    refreshCaptcha.setContentAreaFilled(true);
    refreshCaptcha.setBackground(Color.WHITE);
    refreshCaptcha.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
    refreshCaptcha.setCursor(new Cursor(Cursor.HAND_CURSOR));
    refreshCaptcha.setPreferredSize(new Dimension(100, 45));
    refreshCaptcha.addActionListener(e -> {
      captcha[0] = BankGUI.generateCaptcha();
      captchaDisplay.setText("  " + captcha[0] + "  ");
    });

    captchaDisplayPanel.add(captchaDisplay);
    captchaDisplayPanel.add(Box.createHorizontalStrut(10));
    captchaDisplayPanel.add(refreshCaptcha);

    gbc.gridx = 0;
    gbc.gridy = 5;
    cardPanel.add(captchaDisplayPanel, gbc);

    // Captcha input field label
    JLabel captchaInputLabel = new JLabel("Enter Captcha:");
    captchaInputLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    captchaInputLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 6;
    cardPanel.add(captchaInputLabel, gbc);

    // Captcha input field
    JTextField captchaField = createStyledTextField();
    captchaField.setPreferredSize(new Dimension(250, 45));
    captchaField.setFont(new Font("Segoe UI", Font.BOLD, 18));
    gbc.gridx = 0;
    gbc.gridy = 7;
    cardPanel.add(captchaField, gbc);

    JButton loginBtn = createStyledButton("Login", PRIMARY_COLOR);
    loginBtn.setPreferredSize(new Dimension(200, 45));
    gbc.gridx = 0;
    gbc.gridy = 8;
    gbc.insets = new Insets(25, 20, 10, 20);
    cardPanel.add(loginBtn, gbc);

    loginBtn.addActionListener(e -> {
      try {
        int accNo = Integer.parseInt(accField.getText().trim());
        String password = new String(passField.getPassword());
        String captchaInput = captchaField.getText().trim();

        if (password.isEmpty()) {
          showError("Please enter your password!");
          return;
        }

        if (!captchaInput.equals(String.valueOf(captcha[0]))) {
          showError("Invalid Captcha!");
          captcha[0] = BankGUI.generateCaptcha();
          captchaDisplay.setText("  " + captcha[0] + "  ");
          captchaField.setText("");
          return;
        }

        AccountGUI acc = BankGUI.findAccount(accNo);
        if (acc == null) {
          showError("Account not found!");
          return;
        }

        if (acc.isLocked()) {
          showError("Account is locked! Contact Admin.");
          return;
        }

        if (!acc.passwordCheck(password)) {
          if (acc.isLocked()) {
            showError("Account locked due to 3 wrong attempts!");
          } else {
            showError("Incorrect Password!");
          }
          return;
        }

        currentAccount = acc;
        accField.setText("");
        passField.setText("");
        captchaField.setText("");
        captcha[0] = BankGUI.generateCaptcha();
        captchaDisplay.setText("  " + captcha[0] + "  ");

        updateDashboard();
        cardLayout.show(mainPanel, "dashboard");

      } catch (NumberFormatException ex) {
        showError("Please enter a valid account number!");
      }
    });

    JButton backBtn = createStyledButton("← Back", Color.GRAY);
    backBtn.setPreferredSize(new Dimension(200, 40));
    gbc.gridx = 0;
    gbc.gridy = 9;
    gbc.insets = new Insets(5, 20, 10, 20);
    cardPanel.add(backBtn, gbc);
    backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

    centerPanel.add(cardPanel);
    panel.add(centerPanel, BorderLayout.CENTER);

    return panel;
  }

  /* -------- Register Panel -------- */
  private JPanel createRegisterPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BG_COLOR);

    JPanel headerPanel = createHeader("Create New Account");
    panel.add(headerPanel, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(BG_COLOR);

    JPanel cardPanel = createCard(500, 500);
    cardPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(12, 20, 12, 20);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel accLabel = new JLabel("Account Number:");
    accLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    accLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 0;
    cardPanel.add(accLabel, gbc);

    JTextField accField = createStyledTextField();
    gbc.gridx = 0;
    gbc.gridy = 1;
    cardPanel.add(accField, gbc);

    JLabel passLabel = new JLabel("Create Password:");
    passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    passLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 2;
    cardPanel.add(passLabel, gbc);

    JPasswordField passField = createStyledPasswordField();
    gbc.gridx = 0;
    gbc.gridy = 3;
    cardPanel.add(passField, gbc);

    JLabel amountLabel = new JLabel("Initial Deposit Amount (₹):");
    amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    amountLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 4;
    cardPanel.add(amountLabel, gbc);

    JTextField amountField = createStyledTextField();
    gbc.gridx = 0;
    gbc.gridy = 5;
    cardPanel.add(amountField, gbc);

    JLabel typeLabel = new JLabel("Account Type:");
    typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    typeLabel.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 6;
    cardPanel.add(typeLabel, gbc);

    JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    typePanel.setBackground(CARD_COLOR);
    JRadioButton savingsRadio = new JRadioButton("Savings Account");
    JRadioButton currentRadio = new JRadioButton("Current Account");
    savingsRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    currentRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    savingsRadio.setForeground(Color.BLACK);
    currentRadio.setForeground(Color.BLACK);
    savingsRadio.setBackground(CARD_COLOR);
    currentRadio.setBackground(CARD_COLOR);
    savingsRadio.setSelected(true);
    ButtonGroup group = new ButtonGroup();
    group.add(savingsRadio);
    group.add(currentRadio);
    typePanel.add(savingsRadio);
    typePanel.add(Box.createHorizontalStrut(20));
    typePanel.add(currentRadio);

    gbc.gridx = 0;
    gbc.gridy = 7;
    cardPanel.add(typePanel, gbc);

    JButton registerBtn = createStyledButton("Create Account", SUCCESS_COLOR);
    registerBtn.setPreferredSize(new Dimension(200, 45));
    gbc.gridx = 0;
    gbc.gridy = 8;
    gbc.insets = new Insets(20, 10, 10, 10);
    cardPanel.add(registerBtn, gbc);

    registerBtn.addActionListener(e -> {
      try {
        int accNo = Integer.parseInt(accField.getText().trim());
        String password = new String(passField.getPassword());
        double amount = Double.parseDouble(amountField.getText().trim());

        if (password.isEmpty()) {
          showError("Please create a password!");
          return;
        }

        if (password.length() < 4) {
          showError("Password must be at least 4 characters!");
          return;
        }

        if (amount < 0) {
          showError("Initial amount cannot be negative!");
          return;
        }

        if (BankGUI.findAccount(accNo) != null) {
          showError("Account already exists!");
          return;
        }

        if (savingsRadio.isSelected()) {
          BankGUI.accounts.add(new SavingsAccountGUI(accNo, password, amount));
        } else {
          BankGUI.accounts.add(new CurrentAccountGUI(accNo, password, amount));
        }

        showSuccess("Account Created Successfully!\nYour Account Number: " + accNo);
        accField.setText("");
        passField.setText("");
        amountField.setText("");
        savingsRadio.setSelected(true);
        cardLayout.show(mainPanel, "welcome");

      } catch (NumberFormatException ex) {
        showError("Please enter valid numeric values!");
      }
    });

    JButton backBtn = createStyledButton("← Back", Color.GRAY);
    backBtn.setPreferredSize(new Dimension(200, 40));
    gbc.gridx = 0;
    gbc.gridy = 9;
    gbc.insets = new Insets(5, 10, 10, 10);
    cardPanel.add(backBtn, gbc);
    backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

    centerPanel.add(cardPanel);
    panel.add(centerPanel, BorderLayout.CENTER);

    return panel;
  }

  /* -------- Dashboard Panel -------- */
  private JPanel dashboardPanel;
  private JLabel balanceLabel;
  private JLabel accountInfoLabel;
  private JPanel transactionPanel;

  private JPanel createDashboardPanel() {
    dashboardPanel = new JPanel(new BorderLayout());
    dashboardPanel.setBackground(BG_COLOR);

    // Top Navigation Bar
    JPanel navBar = new JPanel(new BorderLayout());
    navBar.setBackground(PRIMARY_COLOR);
    navBar.setPreferredSize(new Dimension(0, 60));
    navBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    JLabel logoLabel = new JLabel("🏦 Banking System");
    logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
    logoLabel.setForeground(Color.WHITE);
    navBar.add(logoLabel, BorderLayout.WEST);

    JButton logoutBtn = new JButton("Logout");
    logoutBtn.setFont(BUTTON_FONT);
    logoutBtn.setForeground(Color.WHITE);
    logoutBtn.setBackground(DANGER_COLOR);
    logoutBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    logoutBtn.addActionListener(e -> {
      currentAccount = null;
      cardLayout.show(mainPanel, "welcome");
    });
    navBar.add(logoutBtn, BorderLayout.EAST);

    dashboardPanel.add(navBar, BorderLayout.NORTH);

    // Main Content
    JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
    contentPanel.setBackground(BG_COLOR);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Left Side - Account Info & Actions
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.setBackground(BG_COLOR);
    leftPanel.setPreferredSize(new Dimension(300, 0));

    // Account Card
    JPanel accountCard = createCard(280, 150);
    accountCard.setLayout(new BoxLayout(accountCard, BoxLayout.Y_AXIS));
    accountCard.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

    accountInfoLabel = new JLabel("Account: #0000");
    accountInfoLabel.setFont(HEADER_FONT);
    accountInfoLabel.setForeground(PRIMARY_COLOR);

    JLabel typeLabel = new JLabel("Account Type: ---");
    typeLabel.setFont(REGULAR_FONT);
    typeLabel.setForeground(Color.GRAY);

    balanceLabel = new JLabel("₹0.00");
    balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
    balanceLabel.setForeground(SUCCESS_COLOR);

    JLabel balanceTitle = new JLabel("Current Balance");
    balanceTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    balanceTitle.setForeground(Color.GRAY);

    accountCard.add(accountInfoLabel);
    accountCard.add(Box.createVerticalStrut(5));
    accountCard.add(typeLabel);
    accountCard.add(Box.createVerticalStrut(15));
    accountCard.add(balanceLabel);
    accountCard.add(balanceTitle);

    leftPanel.add(accountCard);
    leftPanel.add(Box.createVerticalStrut(20));

    // Quick Actions
    JPanel actionsCard = createCard(280, 320);
    actionsCard.setLayout(new BoxLayout(actionsCard, BoxLayout.Y_AXIS));
    actionsCard.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

    JLabel actionsTitle = new JLabel("Quick Actions");
    actionsTitle.setFont(HEADER_FONT);
    actionsTitle.setForeground(Color.BLACK);
    actionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    actionsCard.add(actionsTitle);
    actionsCard.add(Box.createVerticalStrut(15));

    String[][] actions = {
        { "💰 Deposit", "deposit" },
        { "💸 Withdraw", "withdraw" },
        { "🔄 Transfer", "transfer" },
        { "🔐 Change Password", "password" },
        { "📊 Apply Interest", "interest" }
    };

    for (String[] action : actions) {
      JButton btn = createActionButton(action[0]);
      btn.setAlignmentX(Component.LEFT_ALIGNMENT);
      btn.setMaximumSize(new Dimension(240, 40));

      switch (action[1]) {
        case "deposit" -> btn.addActionListener(e -> showDepositDialog());
        case "withdraw" -> btn.addActionListener(e -> showWithdrawDialog());
        case "transfer" -> btn.addActionListener(e -> showTransferDialog());
        case "password" -> btn.addActionListener(e -> showChangePasswordDialog());
        case "interest" -> btn.addActionListener(e -> applyInterest());
      }

      actionsCard.add(btn);
      actionsCard.add(Box.createVerticalStrut(10));
    }

    leftPanel.add(actionsCard);
    contentPanel.add(leftPanel, BorderLayout.WEST);

    // Right Side - Transaction History
    JPanel rightPanel = createCard(0, 0);
    rightPanel.setLayout(new BorderLayout());
    rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

    JLabel historyTitle = new JLabel("📜 Transaction History");
    historyTitle.setFont(HEADER_FONT);
    rightPanel.add(historyTitle, BorderLayout.NORTH);

    transactionPanel = new JPanel();
    transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));
    transactionPanel.setBackground(CARD_COLOR);

    JScrollPane scrollPane = new JScrollPane(transactionPanel);
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    rightPanel.add(scrollPane, BorderLayout.CENTER);

    contentPanel.add(rightPanel, BorderLayout.CENTER);
    dashboardPanel.add(contentPanel, BorderLayout.CENTER);

    return dashboardPanel;
  }

  private void updateDashboard() {
    if (currentAccount == null)
      return;

    accountInfoLabel.setText("Account: #" + currentAccount.getAccountNumber());
    balanceLabel.setText("₹" + String.format("%.2f", currentAccount.getBalance()));

    // Update transactions
    transactionPanel.removeAll();
    List<TransactionGUI> transactions = currentAccount.getTransactions();

    if (transactions.isEmpty()) {
      JLabel noTrans = new JLabel("No transactions yet");
      noTrans.setFont(REGULAR_FONT);
      noTrans.setForeground(Color.GRAY);
      noTrans.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
      transactionPanel.add(noTrans);
    } else {
      for (int i = transactions.size() - 1; i >= 0; i--) {
        TransactionGUI t = transactions.get(i);
        JPanel transCard = createTransactionCard(t);
        transactionPanel.add(transCard);
        transactionPanel.add(Box.createVerticalStrut(10));
      }
    }

    transactionPanel.revalidate();
    transactionPanel.repaint();
  }

  private JPanel createTransactionCard(TransactionGUI t) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(new Color(250, 250, 250));
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(230, 230, 230)),
        BorderFactory.createEmptyBorder(10, 15, 10, 15)));
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

    JPanel leftInfo = new JPanel();
    leftInfo.setLayout(new BoxLayout(leftInfo, BoxLayout.Y_AXIS));
    leftInfo.setBackground(new Color(250, 250, 250));

    JLabel typeLabel = new JLabel(t.type);
    typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JLabel timeLabel = new JLabel(t.time.substring(0, 16).replace("T", " "));
    timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    timeLabel.setForeground(Color.GRAY);

    leftInfo.add(typeLabel);
    leftInfo.add(timeLabel);

    JPanel rightInfo = new JPanel();
    rightInfo.setLayout(new BoxLayout(rightInfo, BoxLayout.Y_AXIS));
    rightInfo.setBackground(new Color(250, 250, 250));

    boolean isCredit = t.type.contains("Deposit") || t.type.contains("Received") || t.type.contains("Interest");
    JLabel amountLabel = new JLabel((isCredit ? "+" : "-") + "₹" + String.format("%.2f", t.amount));
    amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    amountLabel.setForeground(isCredit ? SUCCESS_COLOR : DANGER_COLOR);
    amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    JLabel balLabel = new JLabel("Bal: ₹" + String.format("%.2f", t.finalBalance));
    balLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    balLabel.setForeground(Color.GRAY);
    balLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    rightInfo.add(amountLabel);
    rightInfo.add(balLabel);

    card.add(leftInfo, BorderLayout.WEST);
    card.add(rightInfo, BorderLayout.EAST);

    return card;
  }

  /* -------- Dialog Methods -------- */
  private void showDepositDialog() {
    String input = JOptionPane.showInputDialog(this,
        "Enter amount to deposit (₹):", "Deposit",
        JOptionPane.PLAIN_MESSAGE);

    if (input != null && !input.trim().isEmpty()) {
      try {
        double amount = Double.parseDouble(input.trim());
        currentAccount.deposit(amount);
        updateDashboard();
        showSuccess("₹" + amount + " deposited successfully!");
      } catch (NumberFormatException e) {
        showError("Please enter a valid amount!");
      }
    }
  }

  private void showWithdrawDialog() {
    String input = JOptionPane.showInputDialog(this,
        "Enter amount to withdraw (₹):", "Withdraw",
        JOptionPane.PLAIN_MESSAGE);

    if (input != null && !input.trim().isEmpty()) {
      try {
        double amount = Double.parseDouble(input.trim());
        if (currentAccount.withdraw(amount)) {
          updateDashboard();
          showSuccess("₹" + amount + " withdrawn successfully!");
        }
      } catch (NumberFormatException e) {
        showError("Please enter a valid amount!");
      }
    }
  }

  private void showTransferDialog() {
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    JTextField targetField = new JTextField();
    JTextField amountField = new JTextField();

    panel.add(new JLabel("Target Account Number:"));
    panel.add(targetField);
    panel.add(new JLabel("Amount (₹):"));
    panel.add(amountField);

    int result = JOptionPane.showConfirmDialog(this, panel,
        "Transfer Money", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      try {
        int targetNo = Integer.parseInt(targetField.getText().trim());
        double amount = Double.parseDouble(amountField.getText().trim());

        if (targetNo == currentAccount.getAccountNumber()) {
          showError("Cannot transfer to same account!");
          return;
        }

        AccountGUI target = BankGUI.findAccount(targetNo);
        if (target == null) {
          showError("Target account not found!");
          return;
        }

        if (currentAccount.withdraw(amount)) {
          target.deposit(amount);
          currentAccount.addTransaction("Transfer to #" + targetNo, amount);
          target.addTransaction("Received from #" + currentAccount.getAccountNumber(), amount);
          updateDashboard();
          showSuccess("₹" + amount + " transferred successfully to Account #" + targetNo);
        }
      } catch (NumberFormatException e) {
        showError("Please enter valid values!");
      }
    }
  }

  private void showChangePasswordDialog() {
    JPasswordField newPass = new JPasswordField();
    JPasswordField confirmPass = new JPasswordField();

    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.add(new JLabel("New Password:"));
    panel.add(newPass);
    panel.add(new JLabel("Confirm Password:"));
    panel.add(confirmPass);

    int result = JOptionPane.showConfirmDialog(this, panel,
        "Change Password", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      String pass1 = new String(newPass.getPassword());
      String pass2 = new String(confirmPass.getPassword());

      if (pass1.isEmpty()) {
        showError("Password cannot be empty!");
        return;
      }

      if (!pass1.equals(pass2)) {
        showError("Passwords do not match!");
        return;
      }

      if (pass1.length() < 4) {
        showError("Password must be at least 4 characters!");
        return;
      }

      currentAccount.passwordChange(pass1);
      showSuccess("Password changed successfully!");
    }
  }

  private void applyInterest() {
    if (currentAccount instanceof SavingsAccountGUI savingsAccount) {
      savingsAccount.applyMonthlyInterest();
      updateDashboard();
      showSuccess("Monthly interest applied successfully!");
    } else {
      showError("Interest only available for Savings Accounts!");
    }
  }

  /* -------- Utility Methods -------- */
  private JPanel createCard(int width, int height) {
    JPanel card = new JPanel();
    card.setBackground(CARD_COLOR);
    card.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(new Color(220, 220, 220), 1, true),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    if (width > 0 && height > 0) {
      card.setPreferredSize(new Dimension(width, height));
    }
    return card;
  }

  private JPanel createHeader(String title) {
    JPanel header = new JPanel();
    header.setBackground(PRIMARY_COLOR);
    header.setPreferredSize(new Dimension(0, 80));
    header.setLayout(new GridBagLayout());

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(TITLE_FONT);
    titleLabel.setForeground(Color.WHITE);
    header.add(titleLabel);

    return header;
  }

  private JButton createStyledButton(String text, Color color) {
    JButton button = new JButton(text);
    button.setFont(BUTTON_FONT);
    button.setForeground(Color.WHITE);
    button.setBackground(color);
    button.setOpaque(true);
    button.setBorderPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
    button.setFocusPainted(false);
    button.setContentAreaFilled(true);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(color.darker());
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(color);
      }
    });

    return button;
  }

  private JButton createActionButton(String text) {
    JButton button = new JButton(text);
    button.setFont(REGULAR_FONT);
    button.setForeground(PRIMARY_COLOR);
    button.setBackground(new Color(232, 245, 253));
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
        BorderFactory.createEmptyBorder(8, 15, 8, 15)));
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setHorizontalAlignment(SwingConstants.LEFT);

    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(new Color(200, 230, 250));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(new Color(232, 245, 253));
      }
    });

    return button;
  }

  private JTextField createStyledTextField() {
    JTextField field = new JTextField();
    field.setFont(REGULAR_FONT);
    field.setPreferredSize(new Dimension(250, 38));
    field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    return field;
  }

  private JPasswordField createStyledPasswordField() {
    JPasswordField field = new JPasswordField();
    field.setFont(REGULAR_FONT);
    field.setPreferredSize(new Dimension(250, 38));
    field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    return field;
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
        JOptionPane.ERROR_MESSAGE);
  }

  private void showSuccess(String message) {
    JOptionPane.showMessageDialog(this, message, "Success",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /* -------- Main Method -------- */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      // Use default look and feel
    }

    SwingUtilities.invokeLater(() -> {
      BankingGUI gui = new BankingGUI();
      gui.setVisible(true);
    });
  }
}

/* -------- Transaction Class for GUI -------- */
class TransactionGUI {
  String type;
  double amount;
  double finalBalance;
  String time;

  TransactionGUI(String type, double amount, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.finalBalance = finalBalance;
    this.time = LocalDateTime.now().toString();
  }
}

/* -------- Abstract Account Class for GUI -------- */
abstract class AccountGUI {
  final int accountNumber;
  private String password;
  protected double balance;
  private int wrongAttempts = 0;
  private boolean locked = false;
  protected List<TransactionGUI> transactions = new ArrayList<>();

  AccountGUI(int accountNumber, String password, double balance) {
    this.accountNumber = accountNumber;
    this.password = password;
    this.balance = balance;
  }

  int getAccountNumber() {
    return accountNumber;
  }

  double getBalance() {
    return balance;
  }

  boolean isLocked() {
    return locked;
  }

  List<TransactionGUI> getTransactions() {
    return transactions;
  }

  boolean passwordCheck(String password) {
    if (locked)
      return false;

    if (this.password.equals(password)) {
      wrongAttempts = 0;
      return true;
    } else {
      wrongAttempts++;
      if (wrongAttempts >= 3) {
        locked = true;
      }
      return false;
    }
  }

  void passwordChange(String newPassword) {
    this.password = newPassword;
  }

  void addTransaction(String type, double amount) {
    transactions.add(new TransactionGUI(type, amount, balance));
  }

  public abstract void deposit(double amount);

  public abstract boolean withdraw(double amount);
}

/* -------- Savings Account for GUI -------- */
class SavingsAccountGUI extends AccountGUI {
  private static final double WITHDRAW_LIMIT = 10000;
  private static final double MIN_BALANCE = 500;
  private static final double YEARLY_INTEREST = 4.0;

  SavingsAccountGUI(int accountNumber, String password, double balance) {
    super(accountNumber, password, balance);
  }

  @Override
  public void deposit(double amount) {
    if (amount <= 0) {
      javax.swing.JOptionPane.showMessageDialog(null, "Invalid deposit amount!");
      return;
    }
    balance += amount;
    addTransaction("Deposit", amount);
  }

  @Override
  public boolean withdraw(double amount) {
    if (amount <= 0) {
      javax.swing.JOptionPane.showMessageDialog(null, "Invalid withdrawal amount!");
      return false;
    }
    if (amount > WITHDRAW_LIMIT) {
      javax.swing.JOptionPane.showMessageDialog(null,
          "Withdrawal limit exceeded (Max ₹" + WITHDRAW_LIMIT + ")");
      return false;
    }
    if (balance - amount < MIN_BALANCE) {
      javax.swing.JOptionPane.showMessageDialog(null,
          "Cannot withdraw! Minimum balance ₹" + MIN_BALANCE + " must be maintained.");
      return false;
    }
    if (amount <= balance) {
      balance -= amount;
      addTransaction("Withdraw", amount);
      return true;
    } else {
      javax.swing.JOptionPane.showMessageDialog(null, "Insufficient Balance!");
      return false;
    }
  }

  void applyMonthlyInterest() {
    double monthlyRate = YEARLY_INTEREST / 12;
    double interest = (balance * monthlyRate) / 100;
    balance += interest;
    addTransaction("Interest Added", interest);
  }
}

/* -------- Current Account for GUI -------- */
class CurrentAccountGUI extends AccountGUI {

  CurrentAccountGUI(int accountNumber, String password, double balance) {
    super(accountNumber, password, balance);
  }

  @Override
  public void deposit(double amount) {
    if (amount <= 0) {
      javax.swing.JOptionPane.showMessageDialog(null, "Invalid deposit amount!");
      return;
    }
    balance += amount;
    addTransaction("Deposit", amount);
  }

  @Override
  public boolean withdraw(double amount) {
    if (amount <= 0) {
      javax.swing.JOptionPane.showMessageDialog(null, "Invalid withdrawal amount!");
      return false;
    }
    if (amount <= balance) {
      balance -= amount;
      addTransaction("Withdraw", amount);
      return true;
    } else {
      javax.swing.JOptionPane.showMessageDialog(null, "Insufficient Balance!");
      return false;
    }
  }
}

/* -------- Bank Utility Class for GUI -------- */
class BankGUI {
  static List<AccountGUI> accounts = new ArrayList<>();

  static AccountGUI findAccount(int accountNumber) {
    for (AccountGUI acc : accounts) {
      if (acc.getAccountNumber() == accountNumber)
        return acc;
    }
    return null;
  }

  static int generateCaptcha() {
    return 1000 + new java.util.Random().nextInt(9000);
  }
}
