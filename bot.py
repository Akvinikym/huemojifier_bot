import logging
from utils.huificator import Huify
from utils.emojificator import Emojify
from utils.tiktok import download_tiktok_video
from telegram import Update
from telegram.ext import Application, CommandHandler, ContextTypes, MessageHandler, filters


# # Enable logging
logging.basicConfig(
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s", level=logging.INFO
)
logger = logging.getLogger(__name__)


async def start(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    """Send a message when the command /start is issued."""
    user = update.effective_user
    await update.message.reply_html(
        rf"Hi {user.mention_html()}!"
    )


async def help_command(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    """Send a message when the command /help is issued."""
    await update.message.reply_text("Just send me a word or a sentence and I'll process them")


async def echo(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    """Process user message."""
    text = update.message.text
    if text.find('http') == -1:
        huification = Huify(update.message.text)
        await update.message.reply_text(huification)
        return

    try:
        file_bytes = download_tiktok_video(update.message.text)
        await update.message.reply_video(file_bytes)
    except Exception as e:
        logger.error(f'Could not download video: {e}')
        await update.message.reply_text("Could not download video")

    # in theory, that's how premium emojis are going to be sent; for now, there's no support for premium bots
    # await update.message.reply_text(text="😀", entities=[MessageEntity(type=constants.MessageEntityType.CUSTOM_EMOJI, offset=0, length=2, custom_emoji_id=5456128055414103034)])


def main() -> None:
    # read token from the file
    with open('token.txt', 'r') as file:
        token = file.read().rstrip()

    # create the bot
    application = Application.builder().token(token).build()

    # command handlers
    application.add_handler(CommandHandler("start", start))
    application.add_handler(CommandHandler("help", help_command))

    # on non command i.e message - echo the message on Telegram
    application.add_handler(MessageHandler(
        filters.TEXT & ~filters.COMMAND, echo))

    # Run the bot until the user presses Ctrl-C
    application.run_polling()


if __name__ == "__main__":
    main()
