
package com.unpri.charactermanager;

import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.view.MainWindow;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            CharacterManager manager =
                new CharacterManager();

            MainWindow window =
                new MainWindow(manager);

            window.setVisible(true);
        });
    }
}
