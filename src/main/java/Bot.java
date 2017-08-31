import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.interfaces.InputBotApiObject;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {

    private final String TranslateToMorlanCommand = "/translate_to";
    private final String TranslateFromMorlangCommand = "/translate_from";
    private final String TranslateToInlineCommand = "to";
    private final String TranslateFromInlineCommand = "from";
    
    private boolean translationToMorlangWasRequested = false;
    private boolean translationFromMorlangWasRequested = false;

    public void onUpdateReceived(Update update) {
        try {
            SendMessage message;

            if (update.hasInlineQuery() && update.getInlineQuery().hasQuery()) {
                String inputMessage = update.getInlineQuery().getQuery();
                String translatedMessage;

                AnswerInlineQuery finalAnswer;
                InlineQueryResult translatedResponse;
                ArrayList<InlineQueryResult> responseInList = new ArrayList<InlineQueryResult>();

                if (inputMessage.substring(0, 2).equals(TranslateToInlineCommand)) {
                    translatedMessage = Translator.translateToMorlang(inputMessage.substring(3));
                    translatedResponse = new InlineQueryResultArticle()
                            .setInputMessageContent(new InputTextMessageContent()
                                    .setMessageText(translatedMessage))
                            .setId("1488")
                            .setTitle(translatedMessage);
                    responseInList.add(translatedResponse);
                    finalAnswer = new AnswerInlineQuery()
                            .setResults(responseInList)
                            .setInlineQueryId(update.getInlineQuery().getId());
                }
                else if (inputMessage.substring(0, 4).equals(TranslateFromInlineCommand)) {
                    translatedMessage = Translator.translateFromMorlang(inputMessage.substring(5));
                    translatedResponse = new InlineQueryResultArticle()
                            .setInputMessageContent(new InputTextMessageContent()
                                    .setMessageText(translatedMessage))
                            .setId("1488")
                            .setTitle(translatedMessage);
                    responseInList.add(translatedResponse);
                    finalAnswer = new AnswerInlineQuery()
                            .setResults(responseInList)
                            .setInlineQueryId(update.getInlineQuery().getId());
                }
                else {
                    translatedMessage = "Please, use commands";
                    translatedResponse = new InlineQueryResultArticle()
                            .setInputMessageContent(new InputTextMessageContent()
                                    .setMessageText(translatedMessage))
                            .setId("1488")
                            .setTitle(translatedMessage);
                    responseInList.add(translatedResponse);
                    finalAnswer = new AnswerInlineQuery()
                            .setResults(responseInList)
                            .setInlineQueryId(update.getInlineQuery().getId());
                }
                answerInlineQuery(finalAnswer);
            }
            else if (update.hasMessage() && update.getMessage().hasText()) {
                if (translationFromMorlangWasRequested) {
                    message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText(Translator.translateFromMorlang(update.getMessage().getText()));
                    translationFromMorlangWasRequested = false;
                } else if (translationToMorlangWasRequested) {
                    message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText(Translator.translateToMorlang(update.getMessage().getText()));
                    translationToMorlangWasRequested = false;
                } else if (update.getMessage().getText().split(" ")[0].equals(TranslateToMorlanCommand)) {
                    translationToMorlangWasRequested = true;
                    message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText("Now, send me a phrase");
                } else if (update.getMessage().getText().split(" ")[0].equals(TranslateFromMorlangCommand)) {
                    translationFromMorlangWasRequested = true;
                    message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText("Now, send me a phrase");
                } else
                    message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText("Please, use commands");
                try {
                    sendMessage(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
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
