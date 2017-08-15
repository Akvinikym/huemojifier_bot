import java.util.HashMap;

class Translator {

    private static HashMap<String, String> dict = new HashMap<String, String>() {{
        put("А", "/-\\");
        put("Б", "8");
        put("В", "|3");
        put("Г", "r");
        put("Д", "_/\\_");
        put("Е", "€");
        put("Ё", "€");
        put("Ж", ">|<");
        put("З", "3");
        put("И", "|/|");
        put("Й", "|/'|");
        put("К", "|<");
        put("Л", "/\\");
        put("М", "|\\/|");
        put("Н", "|-|");
        put("О", "0");
        put("П", "/7");
        put("Р", "|>");
        put("С", "(");
        put("Т", "'|'");
        put("У", "'/");
        put("Ф", "<|>");
        put("Х", "><");
        put("Ц", "|/|.");
        put("Ч", "4");
        put("Ш", "|_|_|");
        put("Щ", "|_|_|.");
        put("Ъ", "'b");
        put("Ы", "b|");
        put("Ь", "b");
        put("Э", "-)");
        put("Ю", "|-0");
        put("Я", "9|");
        put(" ", " ");
    }};

    static String translate(String input) {
        String[] inArr = input.split("");
        String output = "";
        for (String letter : inArr) {
            String translated = dict.containsKey(letter.toUpperCase()) ? dict.get(letter.toUpperCase()) : "";
            if (translated.equals("")) {
                output += letter;
            }
            else {
                output += dict.get(letter.toUpperCase());
            }
        }
        return output;
    }
}
