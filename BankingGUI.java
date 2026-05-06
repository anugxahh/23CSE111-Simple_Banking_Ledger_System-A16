package banking;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;

public class BankingGUI extends Application {

    // ── Palette ────────────────────────────────────────────────────
    private static final String BG_DARK      = "#0a0a14";   // near-black with blue tint
    private static final String BG_CARD      = "#10102a";   // deep navy card
    private static final String BG_ROW       = "#13133a";   // slightly lighter row
    private static final String PURPLE_DEEP  = "#1a0533";   // darkest purple
    private static final String PURPLE_MID   = "#5b21b6";   // vivid purple
    private static final String PURPLE_LIGHT = "#7c3aed";   // bright purple
    private static final String BLUE_MID     = "#2563eb";   // vivid blue
    private static final String BLUE_LIGHT   = "#60a5fa";   // sky blue accent
    private static final String ACCENT_GLOW  = "#a78bfa";   // lavender glow
    private static final String TEXT_PRIMARY = "#e2d9f3";   // soft white-lavender
    private static final String TEXT_MUTED   = "#7c7ca0";   // muted grey-purple
    private static final String SUCCESS      = "#34d399";   // emerald green
    private static final String WARNING      = "#f59e0b";   // amber
    private static final String BORDER       = "#2d2d6b";   // subtle border

    Bank bank = new Bank("Amrita Bank", "Amritapuri");
    TextArea outputArea = new TextArea();

    public static void main(String[] args) {
        new java.io.File("data").mkdir();
        launch(args);
    }

    @Override
    public void start(Stage window) {
        loadCustomersFromFile();
        window.setTitle("✦ Amrita Bank — Desktop Ledger");

        // ── Root layout: sidebar + content ──────────────────────────
        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(140);
        // -fx-background-color alone doesn't cover the tab header strip.
        // We must also override the inner sub-regions JavaFX uses.
        tabPane.setStyle(
            "-fx-background-color: " + BG_DARK + ";" +
            "-fx-tab-min-height: 44px;" +
            "-fx-tab-max-height: 44px;"
        );
        // Force tab header strip dark + add purple bottom border line
        tabPane.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                javafx.scene.Node headerArea = tabPane.lookup(".tab-header-area");
                if (headerArea != null) headerArea.setStyle(
                    "-fx-background-color: " + BG_CARD + ";" +
                    "-fx-border-color: transparent transparent " + PURPLE_MID + " transparent;" +
                    "-fx-border-width: 0 0 2 0;"
                );
                javafx.scene.Node headerBg = tabPane.lookup(".tab-header-background");
                if (headerBg != null) headerBg.setStyle(
                    "-fx-background-color: " + BG_CARD + ";"
                );
            }
        });

        Tab tDash = styledTab("⬡  Dashboard",  createDashboardTab());
        Tab tAdd  = styledTab("＋  New Customer", createAddCustomerTab());
        Tab tTxn  = styledTab("⇄  Transactions", createTransactionTab());
        Tab tLog  = styledTab("≡  Logs",         createLogsTab());

        tabPane.getTabs().addAll(tDash, tAdd, tTxn, tLog);
        tabPane.getTabs().forEach(t -> t.setClosable(false));

        // ── Header bar ───────────────────────────────────────────────
        HBox header = new HBox();
        header.setPrefHeight(52);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 24, 0, 24));
        header.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(PURPLE_DEEP)),
                new Stop(1, Color.web(BG_DARK))),
            CornerRadii.EMPTY, Insets.EMPTY)));

        Label bankTitle = new Label("AMRITA BANK");
        bankTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        bankTitle.setTextFill(Color.web(ACCENT_GLOW));
        bankTitle.setStyle("-fx-letter-spacing: 4px;");

        Label tagline = new Label("  ·  Desktop Ledger v2");
        tagline.setFont(Font.font("Courier New", 12));
        tagline.setTextFill(Color.web(TEXT_MUTED));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label dot1 = ledDot(SUCCESS);
        Label dot2 = ledDot(WARNING);
        Label dot3 = ledDot(BLUE_LIGHT);

        header.getChildren().addAll(bankTitle, tagline, spacer, dot3, dot2, dot1);

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(tabPane);
        root.setBackground(new Background(new BackgroundFill(
            Color.web(BG_DARK), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, 960, 580);
        window.setScene(scene);
        window.show();
        refreshDashboard();
    }

    // ── Helper: styled tab ──────────────────────────────────────────
    private Tab styledTab(String title, javafx.scene.Node content) {
        Tab t = new Tab(title, content);
        t.setStyle(
            "-fx-background-color: " + BG_CARD + ";" +
            "-fx-text-base-color: " + TEXT_MUTED + ";"
        );
        return t;
    }

    // ── Helper: LED indicator dot ───────────────────────────────────
    private Label ledDot(String color) {
        Label l = new Label("●");
        l.setTextFill(Color.web(color));
        l.setFont(Font.font(10));
        l.setPadding(new Insets(0, 4, 0, 4));
        return l;
    }

    // ── Helper: section card wrapper ───────────────────────────────
    private VBox card(javafx.scene.Node... children) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(20));
        box.setBackground(new Background(new BackgroundFill(
            Color.web(BG_CARD), new CornerRadii(10), Insets.EMPTY)));
        box.setStyle(
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 10;" +
            "-fx-border-width: 1;"
        );
        box.getChildren().addAll(children);
        return box;
    }

    // ── Helper: accent button ───────────────────────────────────────
    private Button accentBtn(String label, String bg) {
        Button b = new Button(label);
        b.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        b.setTextFill(Color.WHITE);
        b.setPadding(new Insets(9, 22, 9, 22));
        b.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        b.setOnMouseEntered(e -> b.setStyle(
            "-fx-background-color: " + ACCENT_GLOW + ";" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;" +
            "-fx-text-fill: #0a0a14;"
        ));
        b.setOnMouseExited(e -> b.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        ));
        return b;
    }

    // ── Helper: styled TextField ────────────────────────────────────
    private TextField styledField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setFont(Font.font("Courier New", 13));
        tf.setStyle(
            "-fx-background-color: " + BG_ROW + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-prompt-text-fill: " + TEXT_MUTED + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 7 12 7 12;"
        );
        tf.setPrefWidth(220);
        return tf;
    }

    // ── Helper: styled Label ─────────────────────────────────────────
    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Courier New", 12));
        l.setTextFill(Color.web(TEXT_MUTED));
        l.setMinWidth(140);
        return l;
    }

    // ── Helper: status label ─────────────────────────────────────────
    private Label statusLabel() {
        Label l = new Label();
        l.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        return l;
    }

    private void setStatus(Label l, boolean ok, String msg) {
        l.setText(msg);
        l.setTextFill(ok ? Color.web(SUCCESS) : Color.web(WARNING));
    }

    // ═══════════════════════════════════════════════════════════════
    // DASHBOARD TAB
    // ═══════════════════════════════════════════════════════════════
    private VBox createDashboardTab() {
        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setBackground(new Background(new BackgroundFill(
            Color.web(BG_DARK), CornerRadii.EMPTY, Insets.EMPTY)));

        Label heading = new Label("Customer Overview");
        heading.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        heading.setTextFill(Color.web(ACCENT_GLOW));

        outputArea.setEditable(false);
        outputArea.setPrefHeight(340);
        outputArea.setFont(Font.font("Courier New", 13));
        // Suppress ALL borders — white border comes from inner ScrollPane skin.
        // Let the surrounding card() VBox provide the visible styled border.
        outputArea.setStyle(
            "-fx-control-inner-background: " + BG_CARD + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-border-color: transparent;" +
            "-fx-border-width: 0;" +
            "-fx-background-color: transparent;" +
            "-fx-padding: 8;"
        );
        outputArea.skinProperty().addListener((obs, o, n) -> {
            if (n != null) {
                javafx.scene.Node scroll = outputArea.lookup(".scroll-pane");
                if (scroll != null) scroll.setStyle(
                    "-fx-background-color: " + BG_CARD + ";" +
                    "-fx-border-color: transparent;" +
                    "-fx-border-width: 0;"
                );
                javafx.scene.Node content = outputArea.lookup(".content");
                if (content != null) content.setStyle(
                    "-fx-background-color: " + BG_CARD + ";"
                );
            }
        });

        Button btnRefresh = accentBtn("⟳  Refresh", PURPLE_MID);
        btnRefresh.setOnAction(e -> refreshDashboard());

        VBox cardBox = card(heading, outputArea, btnRefresh);
        VBox.setVgrow(cardBox, Priority.ALWAYS);
        root.getChildren().add(cardBox);
        return root;
    }

    private void refreshDashboard() {
        outputArea.clear();
        if (bank.getCustomers().isEmpty()) {
            outputArea.setText("  No customers loaded yet. Add one from 'New Customer'.");
        } else {
            outputArea.appendText(String.format("  %-6s  %-22s  %-14s  %s%n",
                "ID", "NAME", "PHONE", "BALANCE"));
            outputArea.appendText("  " + "─".repeat(62) + "\n");
            for (Customer c : bank.getCustomers()) {
                String bal = String.format("Rs.%,.2f", c.getAccount().getBalance());
                outputArea.appendText(String.format("  %-6d  %-22s  %-14s  %s%n",
                    c.getCustomerId(), c.getName(), c.getPhone(), bal));
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // ADD CUSTOMER TAB
    // ═══════════════════════════════════════════════════════════════
    private VBox createAddCustomerTab() {
        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setBackground(new Background(new BackgroundFill(
            Color.web(BG_DARK), CornerRadii.EMPTY, Insets.EMPTY)));

        Label heading = new Label("Register New Customer");
        heading.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        heading.setTextFill(Color.web(ACCENT_GLOW));

        TextField txtId    = styledField("e.g. 1001");
        TextField txtName  = styledField("Full name");
        TextField txtPhone = styledField("+91 XXXXX XXXXX");
        TextField txtAcc   = styledField("Account number");
        TextField txtBal   = styledField("Opening deposit");

        GridPane form = new GridPane();
        form.setVgap(14); form.setHgap(16);
        form.addRow(0, fieldLabel("Customer ID"), txtId);
        form.addRow(1, fieldLabel("Full Name"), txtName);
        form.addRow(2, fieldLabel("Phone"), txtPhone);
        form.addRow(3, fieldLabel("Account No"), txtAcc);
        form.addRow(4, fieldLabel("Initial Balance (Rs.)"), txtBal);

        Label lblStatus = statusLabel();
        Button btnAdd = accentBtn("＋  Add Customer", BLUE_MID);

        btnAdd.setOnAction(e -> {
            try {
                int id    = Integer.parseInt(txtId.getText().trim());
                String nm = txtName.getText().trim();
                String ph = txtPhone.getText().trim();
                int acc   = Integer.parseInt(txtAcc.getText().trim());
                double bal = Double.parseDouble(txtBal.getText().trim());

                if (nm.isEmpty() || ph.isEmpty()) {
                    setStatus(lblStatus, false, "⚠  Fill all fields!"); return;
                }
                if (bank.getCustomer(id) != null) {
                    setStatus(lblStatus, false, "⚠  ID " + id + " already exists!"); return;
                }
                for (Customer x : bank.getCustomers()) {
                    if (x.getAccount().getAccountNumber() == acc) {
                        setStatus(lblStatus, false, "⚠  Acc# " + acc + " is taken!"); return;
                    }
                }
                Customer c = new Customer(id, nm, ph);
                c.createAccount(acc, bal);
                bank.addCustomer(c);
                saveAllCustomers();
                refreshDashboard();
                setStatus(lblStatus, true, "✔  Customer saved!");
                txtId.clear(); txtName.clear(); txtPhone.clear(); txtAcc.clear(); txtBal.clear();
            } catch (Exception ex) {
                setStatus(lblStatus, false, "⚠  Invalid input — check all fields.");
            }
        });

        HBox actions = new HBox(16, btnAdd, lblStatus);
        actions.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().add(card(heading, form, actions));
        return root;
    }

    // ═══════════════════════════════════════════════════════════════
    // TRANSACTIONS TAB
    // ═══════════════════════════════════════════════════════════════
    private VBox createTransactionTab() {
        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setBackground(new Background(new BackgroundFill(
            Color.web(BG_DARK), CornerRadii.EMPTY, Insets.EMPTY)));

        Label heading = new Label("Perform Transaction");
        heading.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        heading.setTextFill(Color.web(ACCENT_GLOW));

        TextField txtId  = styledField("Customer ID");
        TextField txtAmt = styledField("Amount in Rs.");

        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("DEPOSIT", "WITHDRAWAL");
        typeBox.setValue("DEPOSIT");
        typeBox.setStyle(
            "-fx-background-color: " + BG_ROW + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 4 8 4 8;"
        );
        typeBox.setPrefWidth(220);

        GridPane form = new GridPane();
        form.setVgap(14); form.setHgap(16);
        form.addRow(0, fieldLabel("Customer ID"), txtId);
        form.addRow(1, fieldLabel("Amount (Rs.)"), txtAmt);
        form.addRow(2, fieldLabel("Transaction Type"), typeBox);

        Label lblStatus = statusLabel();
        Button btnSubmit = accentBtn("⇄  Submit", PURPLE_LIGHT);

        btnSubmit.setOnAction(e -> {
            try {
                Customer c = bank.getCustomer(Integer.parseInt(txtId.getText().trim()));
                if (c != null) {
                    double amt = Double.parseDouble(txtAmt.getText().trim());
                    if (typeBox.getValue().equals("DEPOSIT")) c.getAccount().deposit(amt);
                    else c.getAccount().withdraw(amt);
                    saveAllCustomers();
                    setStatus(lblStatus, true, "✔  Transaction successful!");
                    txtId.clear(); txtAmt.clear();
                } else {
                    setStatus(lblStatus, false, "⚠  Customer not found.");
                }
            } catch (Exception ex) {
                setStatus(lblStatus, false, "⚠  Error — check inputs.");
            }
        });

        HBox actions = new HBox(16, btnSubmit, lblStatus);
        actions.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().add(card(heading, form, actions));
        return root;
    }

    // ═══════════════════════════════════════════════════════════════
    // LOGS TAB
    // ═══════════════════════════════════════════════════════════════
    private VBox createLogsTab() {
        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setBackground(new Background(new BackgroundFill(
            Color.web(BG_DARK), CornerRadii.EMPTY, Insets.EMPTY)));

        Label heading = new Label("Transaction Logs");
        heading.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        heading.setTextFill(Color.web(ACCENT_GLOW));

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(330);
        logArea.setFont(Font.font("Courier New", 12));
        logArea.setStyle(
            "-fx-control-inner-background: #080815;" +
            "-fx-text-fill: #00e5ff;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 12;"
        );

        Button btnLoad = accentBtn("📂  Load Transactions", "#581c87");
        btnLoad.setOnAction(e -> {
            logArea.clear();
            try {
                File file = new File("data/transactions.txt");
                if (file.exists()) {
                    logArea.appendText(String.format("  %-20s | %-14s | %-16s | %-16s | %s%n",
                        "NAME", "ACCOUNT", "AMOUNT", "BALANCE", "TYPE"));
                    logArea.appendText("  " + "─".repeat(78) + "\n");
                    Scanner reader = new Scanner(file);
                    while (reader.hasNextLine()) {
                        logArea.appendText("  " + reader.nextLine() + "\n");
                    }
                    reader.close();
                } else {
                    logArea.setText("  No transaction records found yet.");
                }
            } catch (Exception ex) { /* silent */ }
        });

        root.getChildren().add(card(heading, logArea, btnLoad));
        return root;
    }

    // ═══════════════════════════════════════════════════════════════
    // FILE I/O (unchanged logic)
    // ═══════════════════════════════════════════════════════════════
    private void saveAllCustomers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/customers.txt"))) {
            for (Customer c : bank.getCustomers()) {
                writer.println(c.getCustomerId() + "|" +
                               c.getName() + "|" +
                               c.getPhone() + "|" +
                               c.getAccount().getAccountNumber() + "|" +
                               c.getAccount().getBalance());
            }
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    private void loadCustomersFromFile() {
        List<String> lines = FileManager.readAllLines("data/customers.txt");
        for (String line : lines) {
            try {
                String sep = line.contains("|") ? "\\|" : ",";
                String[] d = line.split(sep);
                if (d.length == 5) {
                    int id = Integer.parseInt(d[0].trim());
                    if (bank.getCustomer(id) != null) continue;
                    Customer c = new Customer(id, d[1].trim(), d[2].trim());
                    c.createAccount(Integer.parseInt(d[3].trim()), Double.parseDouble(d[4].trim()));
                    bank.addCustomer(c);
                }
            } catch (Exception e) { /* skip bad lines */ }
        }
    }
}