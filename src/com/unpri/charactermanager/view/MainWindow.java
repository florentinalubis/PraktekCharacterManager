
package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

    private static final int HIGH_LEVEL_LIMIT = 5;

    private final CharacterManager manager;

    private final JPanel characterPanel;

    private final JLabel totalLabel;
    private final JLabel averageLabel;
    private final JLabel warriorLabel;
    private final JLabel mageLabel;

    public MainWindow(CharacterManager manager) {

        this.manager = manager;

        // Pengaturan jendela
        setTitle("Character Manager - Sprint #5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout utama
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(
                AppTheme.BACKGROUND
        );

        // =====================================
        // NORTH: HEADER DAN PANEL STATISTIK
        // =====================================

        JPanel northPanel = new JPanel(
                new BorderLayout(5, 8)
        );

        northPanel.setBackground(
                AppTheme.BACKGROUND
        );

        northPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 15, 5, 15
                )
        );

        JLabel titleLabel = new JLabel(
                "CHARACTER MANAGER",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                AppTheme.TITLE_FONT
        );

        titleLabel.setForeground(
                AppTheme.PRIMARY
        );

        northPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // Panel statistik menggunakan GridLayout 2x2
        JPanel statisticsPanel = new JPanel(
                new GridLayout(2, 2, 8, 6)
        );

        statisticsPanel.setBackground(
                AppTheme.BACKGROUND
        );

        statisticsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 10, 8, 10
                )
        );

        totalLabel = createStatisticLabel();
        averageLabel = createStatisticLabel();
        warriorLabel = createStatisticLabel();
        mageLabel = createStatisticLabel();

        statisticsPanel.add(totalLabel);
        statisticsPanel.add(averageLabel);
        statisticsPanel.add(warriorLabel);
        statisticsPanel.add(mageLabel);

        northPanel.add(
                statisticsPanel,
                BorderLayout.CENTER
        );

        add(northPanel, BorderLayout.NORTH);


        // =====================================
        // CENTER: DAFTAR KARTU KARAKTER
        // =====================================

        characterPanel = new JPanel();

        characterPanel.setLayout(
                new BoxLayout(
                        characterPanel,
                        BoxLayout.Y_AXIS
                )
        );

        characterPanel.setBackground(
                AppTheme.BACKGROUND
        );

        characterPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        12, 15, 12, 15
                )
        );

        // JScrollPane membungkus panel kartu
        JScrollPane scrollPane = new JScrollPane(
                characterPanel
        );

        scrollPane.getViewport().setBackground(
                AppTheme.BACKGROUND
        );

        scrollPane.setPreferredSize(
                new Dimension(620, 390)
        );

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        add(scrollPane, BorderLayout.CENTER);


        // =====================================
        // EAST: LEGENDA ROLE
        // =====================================

        add(
                createLegendPanel(),
                BorderLayout.EAST
        );


        // =====================================
        // SOUTH: STATUS BAR
        // =====================================

        add(
                createStatusBar(),
                BorderLayout.SOUTH
        );


        // Memperbarui data
        refreshData();

        // Mengatur ukuran awal jendela
        pack();

        setMinimumSize(
                new Dimension(560, 430)
        );

        setLocationRelativeTo(null);
    }


    // =====================================
    // MEMBUAT LABEL STATISTIK
    // =====================================

    private JLabel createStatisticLabel() {

        JLabel label = new JLabel(
                "",
                SwingConstants.CENTER
        );

        label.setFont(
                AppTheme.NORMAL_FONT
        );

        label.setForeground(
                AppTheme.TEXT
        );

        label.setOpaque(true);

        label.setBackground(
                AppTheme.SURFACE
        );

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 6, 8, 6
                )
        );

        return label;
    }


    // =====================================
    // MEMBUAT LEGENDA ROLE
    // =====================================

    private JPanel createLegendPanel() {

        JPanel legendPanel = new JPanel();

        legendPanel.setLayout(
                new BoxLayout(
                        legendPanel,
                        BoxLayout.Y_AXIS
                )
        );

        legendPanel.setBackground(
                AppTheme.BACKGROUND
        );

        legendPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Legenda Role"
                )
        );

        legendPanel.setPreferredSize(
                new Dimension(155, 180)
        );

        // Jarak bagian atas
        legendPanel.add(
                Box.createVerticalStrut(12)
        );

        // Mengambil seluruh jenis role dari Model
        for (CharacterType type :
                CharacterType.values()) {

            JPanel row = new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT,
                            6,
                            3
                    )
            );

            row.setBackground(
                    AppTheme.BACKGROUND
            );

            row.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            JLabel colorMark = new JLabel("   ");

            colorMark.setOpaque(true);

            colorMark.setBackground(
                    AppTheme.colorFor(type)
            );

            colorMark.setBorder(
                    BorderFactory.createLineBorder(
                            AppTheme.PRIMARY
                    )
            );

            JLabel typeLabel = new JLabel(
                    type.name()
            );

            typeLabel.setFont(
                    AppTheme.MONO_FONT
            );

            typeLabel.setForeground(
                    AppTheme.TEXT
            );

            row.add(colorMark);
            row.add(typeLabel);

            legendPanel.add(row);

            legendPanel.add(
                    Box.createVerticalStrut(5)
            );
        }

        // Mengisi ruang kosong
        legendPanel.add(
                Box.createVerticalGlue()
        );

        return legendPanel;
    }


    // =====================================
    // MEMBUAT STATUS BAR
    // =====================================

    private JPanel createStatusBar() {

        JPanel statusPanel = new JPanel(
                new BorderLayout()
        );

        statusPanel.setBackground(
                AppTheme.PRIMARY
        );

        statusPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        7, 14, 7, 14
                )
        );

        JLabel statusText = new JLabel(
                "Status: Character Manager siap digunakan"
        );

        statusText.setFont(
                AppTheme.NORMAL_FONT
        );

        statusText.setForeground(
                AppTheme.STATUS_TEXT
        );

        JLabel versionLabel = new JLabel("v0.5");

        versionLabel.setFont(
                AppTheme.NORMAL_FONT.deriveFont(
                        Font.BOLD
                )
        );

        versionLabel.setForeground(
                AppTheme.STATUS_TEXT
        );

        statusPanel.add(
                statusText,
                BorderLayout.WEST
        );

        statusPanel.add(
                versionLabel,
                BorderLayout.EAST
        );

        return statusPanel;
    }


    // =====================================
    // MEMPERBARUI DATA KARAKTER
    // =====================================

    public void refreshData() {

        characterPanel.removeAll();

        List<Character> characters =
                manager.getAllCharacters();

        for (Character character : characters) {

            characterPanel.add(
                    createCharacterCard(character)
            );

            characterPanel.add(
                    Box.createVerticalStrut(10)
            );
        }

        // Memperbarui statistik
        totalLabel.setText(
                "Total Karakter: "
                + manager.getTotalCharacters()
        );

        averageLabel.setText(
                String.format(
                        "Rata-rata Level: %.2f",
                        manager.getAverageLevel()
                )
        );

        warriorLabel.setText(
                "Warrior: "
                + manager.countByType(
                        CharacterType.WARRIOR
                )
        );

        mageLabel.setText(
                "Mage: "
                + manager.countByType(
                        CharacterType.MAGE
                )
        );

        // Mengatur ulang layout dan tampilan
        characterPanel.revalidate();

        characterPanel.repaint();
    }


    // =====================================
    // MEMBUAT KARTU KARAKTER
    // =====================================

    private JPanel createCharacterCard(
            Character character
    ) {

        JPanel card = new JPanel(
                new BorderLayout(10, 5)
        );

        card.setBackground(
                AppTheme.colorFor(
                        character.getType()
                )
        );

        card.setBorder(
                AppTheme.CARD_BORDER
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        92
                )
        );

        JLabel nameLabel = new JLabel(
                character.getName()
        );

        nameLabel.setFont(
                AppTheme.NORMAL_FONT.deriveFont(
                        Font.BOLD
                )
        );

        nameLabel.setForeground(
                AppTheme.TEXT
        );

        JLabel roleLabel = new JLabel(
                "Role: " + character.getType()
        );

        roleLabel.setFont(
                AppTheme.MONO_FONT
        );

        roleLabel.setForeground(
                AppTheme.TEXT
        );

        JLabel levelLabel = new JLabel(
                "Level: " + character.getLevel()
        );

        levelLabel.setFont(
                AppTheme.NORMAL_FONT
        );

        levelLabel.setForeground(
                AppTheme.TEXT
        );

        // Penanda karakter level tinggi
        if (character.getLevel() >= HIGH_LEVEL_LIMIT) {

            levelLabel.setFont(
                    AppTheme.NORMAL_FONT.deriveFont(
                            Font.BOLD
                    )
            );

            levelLabel.setForeground(
                    AppTheme.highLevelColor()
            );
        }

        // Panel detail kartu
        JPanel details = new JPanel();

        details.setOpaque(false);

        details.setLayout(
                new BoxLayout(
                        details,
                        BoxLayout.Y_AXIS
                )
        );

        details.add(nameLabel);
        details.add(roleLabel);
        details.add(levelLabel);

        card.add(
                details,
                BorderLayout.CENTER
        );

        card.setToolTipText(
                "Nama: " + character.getName()
                + " | Role: " + character.getType()
                + " | Level: " + character.getLevel()
        );

        // Menonaktifkan kartu level 1
        if (character.getLevel() == 1) {

            setEnabledRecursively(
                    card,
                    false
            );

            card.setToolTipText(
                    "Karakter level 1 dinonaktifkan."
            );
        }

        return card;
    }


    // =====================================
    // MENONAKTIFKAN KOMPONEN REKURSIF
    // =====================================

    private void setEnabledRecursively(
            Container container,
            boolean enabled
    ) {

        container.setEnabled(enabled);

        for (Component component :
                container.getComponents()) {

            component.setEnabled(enabled);

            if (component instanceof Container) {

                setEnabledRecursively(
                        (Container) component,
                        enabled
                );
            }
        }
    }
}
