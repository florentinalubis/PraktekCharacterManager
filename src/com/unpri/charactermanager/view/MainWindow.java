
package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

    private final CharacterManager manager;

    private JPanel characterListPanel;
    private JLabel totalLabel;
    private JLabel averageLabel;

    public MainWindow(CharacterManager manager) {

        this.manager = manager;

        setTitle("Character Manager - Sprint #6");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(AppTheme.BACKGROUND);
        setLayout(new BorderLayout(12, 12));

        createHeader();
        createCharacterList();
        createForm();

        refreshList();
    }

    private void createHeader() {

        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setBackground(AppTheme.BACKGROUND);
        header.setBorder(BorderFactory.createEmptyBorder(
            15, 20, 5, 20
        ));

        JLabel title = new JLabel(
            "CHARACTER MANAGER SPRINT #6"
        );

        title.setFont(AppTheme.TITLE_FONT);
        title.setForeground(AppTheme.TITLE_COLOR);

        JPanel statistics = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );

        statistics.setBackground(AppTheme.BACKGROUND);

        totalLabel = new JLabel("Total Karakter: 0");
        averageLabel = new JLabel("Rata-rata Level: 0");

        statistics.add(totalLabel);
        statistics.add(averageLabel);

        header.add(title);
        header.add(statistics);

        add(header, BorderLayout.NORTH);
    }

    private void createCharacterList() {

        characterListPanel = new JPanel();
        characterListPanel.setLayout(
            new BoxLayout(characterListPanel, BoxLayout.Y_AXIS)
        );

        characterListPanel.setBackground(Color.WHITE);
        characterListPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JScrollPane scrollPane =
            new JScrollPane(characterListPanel);

        scrollPane.setBorder(
            BorderFactory.createTitledBorder("Daftar Karakter")
        );

        add(scrollPane, BorderLayout.CENTER);
    }

    private void createForm() {

        CharacterFormPanel formPanel =
            new CharacterFormPanel(manager, this::refreshList);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(AppTheme.BACKGROUND);
        wrapper.setBorder(
            BorderFactory.createEmptyBorder(5, 15, 15, 15)
        );

        wrapper.add(formPanel, BorderLayout.CENTER);

        add(wrapper, BorderLayout.SOUTH);
    }

    public void refreshList() {

        characterListPanel.removeAll();

        List<Character> characters =
            manager.getAllCharacters();

        if (characters.isEmpty()) {

            JLabel emptyLabel =
                new JLabel("Belum ada karakter.");

            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            characterListPanel.add(emptyLabel);

        } else {

            for (Character character : characters) {

                JPanel card = new JPanel(
                    new BorderLayout(10, 5)
                );

                card.setBackground(new Color(245, 245, 255));

                card.setBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                            AppTheme.PRIMARY
                        ),
                        BorderFactory.createEmptyBorder(
                            10, 12, 10, 12
                        )
                    )
                );

                JLabel name = new JLabel(
                    character.getName()
                );

                name.setFont(
                    new Font("Segoe UI", Font.BOLD, 15)
                );

                JLabel details = new JLabel(
                    character.getType()
                    + " | Level "
                    + character.getLevel()
                );

                card.add(name, BorderLayout.NORTH);
                card.add(details, BorderLayout.CENTER);

                card.setMaximumSize(
                    new Dimension(Integer.MAX_VALUE, 75)
                );

                card.setAlignmentX(Component.LEFT_ALIGNMENT);

                characterListPanel.add(card);
                characterListPanel.add(Box.createVerticalStrut(8));
            }
        }

        totalLabel.setText(
            "Total Karakter: " + manager.getTotalCharacters()
        );

        averageLabel.setText(
            String.format(
                "Rata-rata Level: %.2f",
                manager.getAverageLevel()
            )
        );

        characterListPanel.revalidate();
        characterListPanel.repaint();
    }
}
