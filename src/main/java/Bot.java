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

    private final String TranslateToFromRusInlineCommand = "ru_to";
    private final String TranslateToFromEnInlineCommand = "en_to";
    private final String TranslateFromToRusInlineCommand = "from_ru";
    private final String TranslateFromToEnInlineCommand = "from_en";

    public void onUpdateReceived(Update update) {
        try {
            SendMessage message;

            if (update.hasInlineQuery() && update.getInlineQuery().hasQuery()) {
                String inputMessage = update.getInlineQuery().getQuery();
                String translatedMessage;

                AnswerInlineQuery finalAnswer;
                InlineQueryResult translatedResponse;
                ArrayList<InlineQueryResult> responseInList = new ArrayList<InlineQueryResult>();

                if (inputMessage.substring(0, 5).equals(TranslateToFromRusInlineCommand)) {
                    translatedMessage = Translator.translateToMorlang(
                            inputMessage.substring(6), Translator.Language.Russian);
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
                else if (inputMessage.substring(0, 5).equals(TranslateToFromEnInlineCommand)) {
                    translatedMessage = Translator.translateToMorlang(
                            inputMessage.substring(6), Translator.Language.English);
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
                else if (inputMessage.substring(0, 7).equals(TranslateFromToRusInlineCommand)) {
                    translatedMessage = Translator.translateFromMorlang(
                            inputMessage.substring(8), Translator.Language.Russian);
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
                else if (inputMessage.substring(0, 7).equals(TranslateFromToEnInlineCommand)) {
                    translatedMessage = Translator.translateFromMorlang(
                            inputMessage.substring(8), Translator.Language.English);
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
