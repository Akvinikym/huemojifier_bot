import re


# huify the given text
def Huify(text: str):
    # detect the language; we can only huify Russian (for now)
    if not bool(re.search('[а-яА-Я]', text)):
        return text
    return huify(text)


# we need those variables for huification logic
CONSONANTS = "бвгджзклмнпрстфхцчшщ"
EQUENC = {
  "а": "я",
  "е": "е",
  "ё": "ё",
  "и": "и",
  "о": "е",
  "у": "ю",
  "э": "е",
  "ю": "ю",
  "я": "я",
  "ы": "и"
}


# actually transform the text
def huify(text: str):
    result = ""
    for word in text.lower().split():
        # special cases
        if word == "бот":
            result += "хуебот "
            continue

        # almost impossible to handle them
        if len(word) < 3:
            result += word + " "
            continue
        word_original = word

        # iterate and cut the word till the first vowel
        word = word + "-"
        while CONSONANTS.find(word[0]) != -1:
            word = word[1:]
        if len(word) == 1:
            result += word_original + " "
            continue
        
        result += "ху" + EQUENC[word[0]] + word[1:-1] + " "

    return result
