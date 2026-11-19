
package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CharacterFormPanel extends JPanel {

    private final CharacterManager manager;
    private final Runnable refreshCallback;

    private final JTextField nameField;
    private final JComboBox<CharacterType> roleCombo;
    private final JSpinner levelSpinner;
    private final JLabel messageLabel;

    private final Border originalBorder;

    public CharacterFormPanel(
            CharacterManager manager,
            Runnable refreshCallback) {

        this.manager = manager;
        this.refreshCallback = refreshCallback;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Tambah Karakter"),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(18);
        originalBorder = nameField.getBorder();

        roleCombo = new JComboBox<>(CharacterType.values());

        levelSpinner = new JSpinner(
            new SpinnerNumberModel(1, 1, 99, 1)
        );

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(AppTheme.ERROR);

        JButton addButton = new JButton("Tambah Karakter");

        addButton.setBackground(AppTheme.PRIMARY);
        addButton.setForeground(Color.WHITE);

        // Nama
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nama Karakter:"), gbc);

        gbc.gridx = 1;
        add(nameField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        add(roleCombo, gbc);

        // Level
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Level (1-99):"), gbc);

        gbc.gridx = 1;
        add(levelSpinner, gbc);

        // Tombol
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(addButton, gbc);

        // Pesan
        gbc.gridy = 4;
        add(messageLabel, gbc);

        addButton.addActionListener(e -> submitForm());

        nameField.addActionListener(e -> submitForm());
    }

    // Validasi nama
    private String validateName(String name) {

        // 1. Nama kosong
        if (name == null || name.isEmpty()) {
            return "Nama karakter wajib diisi!";
        }

        // 2. Panjang nama
        if (name.length() > 20) {
            return "Nama maksimal 20 karakter!";
        }

        // 3. Format nama
        if (!name.matches("[\\p{L}][\\p{L} '-]*")) {
            return "Format nama karakter tidak valid!";
        }

        // 4. Spasi ganda
        if (name.contains("  ")) {
            return "Nama tidak boleh menggunakan spasi ganda!";
        }

        // 5. Nama duplikat
        if (manager.isNameTaken(name)) {
            return "Nama karakter sudah digunakan!";
        }

        return null;
    }

    // Menampilkan pesan error
    private void showError(String message, JComponent field) {

        messageLabel.setForeground(AppTheme.ERROR);
        messageLabel.setText(message);

        field.setBorder(
            BorderFactory.createLineBorder(AppTheme.ERROR, 2)
        );

        field.requestFocusInWindow();
    }

    // Mengembalikan border
    private void resetFieldBorder() {

        nameField.setBorder(originalBorder);

        roleCombo.setBorder(
            UIManager.getBorder("ComboBox.border")
        );

        levelSpinner.setBorder(
            UIManager.getBorder("Spinner.border")
        );
    }

    // Proses tambah karakter
    private void submitForm() {

        resetFieldBorder();
        messageLabel.setText(" ");

        // Membersihkan spasi
        String name = nameField.getText().trim();

        nameField.setText(name);

        // Validasi nama
        String error = validateName(name);

        if (error != null) {
            showError(error, nameField);
            return;
        }

        // Commit spinner
        try {

            levelSpinner.commitEdit();

        } catch (java.text.ParseException ex) {

            showError(
                "Level harus berupa angka yang valid!",
                levelSpinner
            );

            return;
        }

        int level = (Integer) levelSpinner.getValue();

        // Mengambil role
        CharacterType role =
            (CharacterType) roleCombo.getSelectedItem();

        if (role == null) {

            showError(
                "Silakan pilih role karakter!",
                roleCombo
            );

            return;
        }

        try {

            Character character =
                new Character(name, role, level);

            manager.addCharacter(character);

            refreshCallback.run();

            messageLabel.setForeground(AppTheme.SUCCESS);

            messageLabel.setText(
                "Karakter " + name + " berhasil ditambahkan!"
            );

            // Membersihkan form
            nameField.setText("");
            roleCombo.setSelectedIndex(0);
            levelSpinner.setValue(1);

            nameField.requestFocusInWindow();

        } catch (IllegalArgumentException ex) {

            showError(ex.getMessage(), nameField);
        }
    }
}
