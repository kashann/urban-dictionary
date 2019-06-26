package com.ebusiness.kashan.urbandictionary;

public class UrbanExpression {
    public static final String TABLE_NAME = "UrbanExpressions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_DEFINITION = "definition";
    public static final String COLUMN_EXAMPLE = "example";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String word;
    private String definition;
    private String example;
    private String timestamp;

    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_WORD + " TEXT NOT NULL,"
            + COLUMN_DEFINITION + " TEXT NOT NULL,"
            + COLUMN_EXAMPLE + " TEXT NOT NULL,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public UrbanExpression() {

    }

    public UrbanExpression(int id, String word, String definition, String example, String timestamp) {
        this.id = id;
        this.word = word;
        this.definition = definition;
        this.example = example;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) { this.word = word; }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "\n\nword: " + this.word + "\ndef: " + this.definition + "\nex: " + this.example;
    }
}