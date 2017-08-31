import java.util.HashMap;
import java.util.Map;

class Translator {

    private static final int MaximumMordorLetterLength = 6;

    private static HashMap<String, String> dict = new HashMap<String, String>() {{
        put("А", "/-\\");
        put("Б", "6");
        put("В", "|3");
        put("Г", "r");
        put("Д", "_/\\_");
        put("Е", "€");
        put("Ё", "€'");
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

    static String translateToMorlang(String input) {
        String[] inArr = input.split("");
        StringBuilder output = new StringBuilder();
        for (String letter : inArr) {
            String translated = dict.containsKey(letter.toUpperCase()) ? dict.get(letter.toUpperCase()) : "";
            if (translated.equals("")) {
                output.append(letter);
            }
            else {
                output.append(dict.get(letter.toUpperCase()));
            }
        }
        return output.toString();
    }

    static String translateFromMorlang(String input) {
        String[] inArr = input.split("");
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < inArr.length; i++) {
            String candidateLetter = "";
            int inArrCurrentPos = i;
            boolean letterIsFound = false;

            // Loop until we get a letter or reach the longest possible size
            for (int j = 0; j < MaximumMordorLetterLength; j++) {
                // Append another symbol from the input to candidate letter
                // If we get a proper letter, take it, otherwise continue
                candidateLetter += inArr[inArrCurrentPos];
                if (dict.containsValue(candidateLetter)) {
                    letterIsFound = true;
                    for (Map.Entry<String, String> entry : dict.entrySet()) {
                        if (entry.getValue().equals(candidateLetter)) {
                            out.append(entry.getKey());
                            break;
                        }
                    }
                    break;
                }
                if (++inArrCurrentPos == inArr.length)
                    break;
            }
            if (letterIsFound)
                i = inArrCurrentPos;
            else {
                out.append(inArr[i]);
            }
        }
        return out.toString();
    }
}
