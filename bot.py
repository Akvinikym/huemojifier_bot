import logging
from utils.huificator import Huify
from utils.emojificator import Emojify
from utils.tiktok import download_tiktok_video
from utils.instagram import download_instagram_media
from telegram import Update
from telegram.ext import Application, CommandHandler, ContextTypes, MessageHandler, filters


# Enable logging
logging.basicConfig(
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s", level=logging.INFO
)
logger = logging.getLogger(__name__)


# Expects a whitelist.txt to be a list of whitelisted users' IDs, space separated
whitelist_ids = []


def check_user_id(id):
    if len(whitelist_ids) == 0:
        return
    if str(id) in whitelist_ids:
        return
    raise Exception(
        "You are not in the whitelisted IDs (your ID is {}), you cannot download the video :(".format(id))


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
    try:
        if text.find('tiktok') != -1:
            check_user_id(update.message.from_user.id)

            file_bytes = download_tiktok_video(update.message.text)
            await update.message.reply_video(file_bytes)
            return
        if text.find('instagram') != -1:
            check_user_id(update.message.from_user.id)

            is_image, file_bytes = download_instagram_media(
                update.message.text)
            if is_image:
                await update.message.reply_photo(file_bytes)
            else:
                await update.message.reply_video(file_bytes)
            return
    except Exception as e:
        logger.error(f'Could not download video: {e}')
        await update.message.reply_text(f'Could not download video: {e}')
        return

    huification = Huify(update.message.text)
    await update.message.reply_text(huification)
    # in theory, that's how premium emojis are going to be sent; for now, there's no support for premium bots
    # await update.message.reply_text(text="😀", entities=[MessageEntity(type=constants.MessageEntityType.CUSTOM_EMOJI, offset=0, length=2, custom_emoji_id=5456128055414103034)])


def main() -> None:
    # read token from the file
    with open('token.txt', 'r') as file:
        token = file.read().rstrip()

    global whitelist_ids
    try:
        with open('whitelist.txt', 'r') as file:
            for line in file:
                whitelist_ids = [x.strip() for x in line.split(' ')]
    except:
        pass
    logger.info('Whitelisted IDs: {}'.format(whitelist_ids))

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
