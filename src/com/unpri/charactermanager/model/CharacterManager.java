
package com.unpri.charactermanager.model;

import java.util.ArrayList;
import java.util.List;

public class CharacterManager {

    private final List<Character> characters = new ArrayList<>();

    private static final int MAX_CHARACTERS = 20;

    // Menambahkan karakter
    public void addCharacter(Character character) {

        if (character == null) {
            throw new IllegalArgumentException(
                "Karakter tidak boleh kosong!"
            );
        }

        // Validasi nama duplikat
        if (isNameTaken(character.getName())) {
            throw new IllegalArgumentException(
                "Nama karakter sudah digunakan!"
            );
        }

        // Validasi maksimal karakter
        if (characters.size() >= MAX_CHARACTERS) {
            throw new IllegalArgumentException(
                "Jumlah karakter maksimal adalah 20!"
            );
        }

        characters.add(character);
    }

    // Memeriksa nama duplikat
    public boolean isNameTaken(String name) {

        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        for (Character character : characters) {

            if (character.getName()
                    .equalsIgnoreCase(name.trim())) {

                return true;
            }
        }

        return false;
    }

    // Mencari karakter
    public Character findCharacter(String name) {

        for (Character character : characters) {

            if (character.getName()
                    .equalsIgnoreCase(name.trim())) {

                return character;
            }
        }

        return null;
    }

    // Menghapus karakter
    public boolean removeCharacter(String name) {

        Character character = findCharacter(name);

        if (character != null) {
            return characters.remove(character);
        }

        return false;
    }

    // Mengambil seluruh karakter
    public List<Character> getAllCharacters() {
        return new ArrayList<>(characters);
    }

    // Menghitung total karakter
    public int getTotalCharacters() {
        return characters.size();
    }

    // Menghitung rata-rata level
    public double getAverageLevel() {

        if (characters.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (Character character : characters) {
            total += character.getLevel();
        }

        return (double) total / characters.size();
    }

    // Menghitung karakter berdasarkan role
    public int countByType(CharacterType type) {

        int count = 0;

        for (Character character : characters) {

            if (character.getType() == type) {
                count++;
            }
        }

        return count;
    }
}
