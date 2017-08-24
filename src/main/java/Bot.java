import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private final String TranslateToMorlanCommand = "/translate_to";
    private final String TranslateFromMorlangCommand = "/translate_from";
    
    private boolean translationToMorlangWasRequested = false;
    private boolean translationFromMorlangWasRequested = false;

    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message;
            if (translationFromMorlangWasRequested) {
                message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(Translator.translateToRussian(update.getMessage().getText()));
                translationFromMorlangWasRequested = false;
            }
            else if (translationToMorlangWasRequested) {
                message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(Translator.translateFromRussian(update.getMessage().getText()));
                translationToMorlangWasRequested = false;
            }
            else if (update.getMessage().getText().split(" ")[0].equals(TranslateToMorlanCommand)) {
                translationToMorlangWasRequested = true;
                message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("Now, send me a phrase");
            }
            else if (update.getMessage().getText().split(" ")[0].equals(TranslateFromMorlangCommand)) {
                translationFromMorlangWasRequested = true;
                message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("Now, send me a phrase");
            }
            else
                message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("Please, use commands");
            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "MordorLanguageBot";
    }

    public String getBotToken() {
        return "355829748:AAGr8YBz7RpKbs2Z6DCazQkyXYjWCQLOfrY";
    }
}
