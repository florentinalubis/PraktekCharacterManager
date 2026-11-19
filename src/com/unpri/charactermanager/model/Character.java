
package com.unpri.charactermanager.model;

public class Character {

    private String name;
    private CharacterType type;
    private int level;

    public Character(String name, CharacterType type, int level) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nama karakter tidak boleh kosong."
            );
        }

        if (type == null) {
            throw new IllegalArgumentException(
                "Role karakter harus dipilih."
            );
        }

        if (level < 1 || level > 99) {
            throw new IllegalArgumentException(
                "Level harus berada di antara 1 sampai 99."
            );
        }

        if (type == CharacterType.MAGE && level > 60) {
            throw new IllegalArgumentException(
                "Karakter MAGE tidak boleh memiliki level di atas 60."
            );
        }

        this.name = name.trim();
        this.type = type;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public CharacterType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {

        if (level < 1 || level > 99) {
            throw new IllegalArgumentException(
                "Level harus berada di antara 1 sampai 99."
            );
        }

        if (type == CharacterType.MAGE && level > 60) {
            throw new IllegalArgumentException(
                "Karakter MAGE tidak boleh memiliki level di atas 60."
            );
        }

        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Character)) {
            return false;
        }

        Character other = (Character) obj;

        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return name + " - " + type + " - Level " + level;
    }
}
