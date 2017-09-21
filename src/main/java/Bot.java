import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        try {
            // Inline request
            if (update.hasInlineQuery() && update.getInlineQuery().hasQuery()) {
                String inputMessage = update.getInlineQuery().getQuery();
                ArrayList<String> translatedMessages = new ArrayList<String>();

                AnswerInlineQuery finalAnswer;
                InlineQueryResult translatedResponse;
                ArrayList<InlineQueryResult> responseList = new ArrayList<InlineQueryResult>();

                // Translate and wrap the messages
                translatedMessages.addAll(Translator.translate(inputMessage));
                for (int i = 0; i < translatedMessages.size(); i++) {
                    translatedResponse = new InlineQueryResultArticle()
                            .setInputMessageContent(new InputTextMessageContent()
                                    .setMessageText(translatedMessages.get(i)))
                            .setId(Integer.toString(i))
                            .setTitle(translatedMessages.get(i));
                    responseList.add(translatedResponse);
                }

                finalAnswer = new AnswerInlineQuery()
                        .setResults(responseList)
                        .setInlineQueryId(update.getInlineQuery().getId());

                answerInlineQuery(finalAnswer);
            }
            else if (update.hasMessage() && update.getMessage().hasText()) {
                // Reply in PM
                SendMessage message;

                for (String translation: Translator.translate(update.getMessage().getText())) {
                    message = new SendMessage(update.getMessage().getChatId(), translation);
                    sendMessage(message);
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "MordorLanguageBot";
    }

    public String getBotToken() {
        return "355829748:AAGr8YBz7RpKbs2Z6DCazQkyXYjWCQLOfrY";
    }

}
