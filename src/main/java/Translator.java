import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Translator {

    public enum Language {
        Russian,
        English,
    }
    private static final int MaximumMordorLetterLength = 6;

    private static HashMap<String, String> rusDict = new HashMap<String, String>() {{
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
    }};
    private static HashMap<String, String> enDict = new HashMap<String, String>() {{
        put("А", "@");
        put("B", "8");
        put("C", "C");
        put("D", "|)");
        put("E", "3");
        put("F", "|=");
        put("G", "G");
        put("H", "|-|");
        put("I", "i");
        put("J", "_)");
        put("K", "|(");
        put("L", "|_");
        put("M", "/\\/\\");
        put("N", "|\\|");
        put("O", "0");
        put("P", "P");
        put("Q", "0,");
        put("R", "Я");
        put("S", "$");
        put("T", "7");
        put("U", "U");
        put("V", "\\/");
        put("W", "\\/\\/");
        put("X", "><");
        put("Y", "`/");
        put("Z", "7_");
    }};

    static List<String> translate(String s) {
        ArrayList<String> translations = new ArrayList<String>();

        // Not a cycle, because we have two functions. Could be, if we had more
        translations.add(translateToMorlang(s, Language.Russian));
        translations.add(translateToMorlang(s, Language.English));
        translations.add(translateFromMorlang(s, Language.Russian));
        translations.add(translateFromMorlang(s, Language.English));
        return translations;
    }

    private static String translateToMorlang(String input, Language inLanguage) {
        String[] inArr = input.split("");
        StringBuilder output = new StringBuilder();
        HashMap<String, String> dict = inLanguage == Language.Russian ? rusDict : enDict;
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

    private static String translateFromMorlang(String input, Language outLanguage) {
        String[] inArr = input.split("");
        StringBuilder out = new StringBuilder();
        HashMap<String, String> dict = outLanguage == Language.Russian ? rusDict : enDict;
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
