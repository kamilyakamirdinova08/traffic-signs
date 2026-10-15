import os
import json #JSON форматтагы маалыматтар менен иштөө үчүн json модулун импорттоо
from telegram import Update, ReplyKeyboardMarkup, KeyboardButton # Telegram модулдан керектүү класстарды жана функцияларды импорттоо
# telegram.ext модулдан керектүү класстарды жана функцияларды импорттоо
from telegram.ext import Updater, CommandHandler, MessageHandler, CallbackContext, Filters
# telegram.ext модулдан керектүү класстарды жана функцияларды импорттоо
from PIL import Image #Сүрөттөр менен иштөө үчүн  PIL китепканадан Image модулду импорттоо
import torch #Нейрон тармактары менен иштөө үчүн torch китепкананы импорттоо
import torchvision.transforms as transforms # Сүрөттү өзгөртүү үчүн touchvision.transforms модулун импорттоо
from model import GTSRB_MODEL  #  model.py файлдын GTSRB_MODEL классты импортто

# Путь к вашей модели PyTorch
MODEL_PATH = 'gtsrb_model.pth'

# Определяем input_dim и output_dim для модели GTSRB_MODEL
INPUT_DIM = 3 * 50 * 50  # Входной размер изображения (3 канала RGB и размер 50x50)
OUTPUT_DIM = 43  # Количество классов (в GTSRB датасете 43 класса)

# Загрузка модели GTSRB_MODEL
model = GTSRB_MODEL(INPUT_DIM, OUTPUT_DIM)
model.load_state_dict(torch.load(MODEL_PATH, map_location=torch.device('cpu')))
model.eval()

# Трансформация изображения перед подачей в модель
preprocess = transforms.Compose([
    transforms.Resize(50),
    transforms.CenterCrop(50),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
])

# Список поддерживаемых языков
LANGUAGES = {'English': 'en', 'Русский': 'ru', 'Кыргызча': 'ky', 'Türkçe': 'tr'}

def start(update: Update, context: CallbackContext) -> None:
    # Создаем клавиатуру с кнопками для выбора языка
    keyboard = [[KeyboardButton(lang) for lang in LANGUAGES.keys()]]

    # Отправляем клавиатуру пользователю
    update.message.reply_text(
        "Choose language / Выберите язык / Тилди тандаңыз / Dilinizi seçiniz: ",
        reply_markup=ReplyKeyboardMarkup(keyboard, one_time_keyboard=True)
    )

def select_language(update: Update, context: CallbackContext) -> None:
    selected_language = update.message.text

    # Сохраняем выбранный язык в данных пользователя
    context.user_data['language'] = LANGUAGES.get(selected_language)

    # Определяем текст сообщения в зависимости от выбранного языка
    if selected_language == 'Русский':
        confirmation_message = f'Вы выбрали {selected_language}'
    elif selected_language == 'English':
        confirmation_message = f'You have selected {selected_language}'
    elif selected_language == 'Кыргызча':
        confirmation_message = f'Сиз кыргыз тилин тандадыңыз'
    elif selected_language == 'Türkçe':
        confirmation_message = f'Siz türk dilini seçtiniz.'
    else:
        confirmation_message = f'Language {selected_language} is not supported'

    # Отправляем подтверждение выбора языка
    update.message.reply_text(confirmation_message)
def handle_image(update: Update, context: CallbackContext) -> None:
    photo_file = update.message.photo[-1].get_file()
    photo_file.download('input_image.jpg')
    img = Image.open('input_image.jpg')
    img = preprocess(img)
    img = img.unsqueeze(0)
    with torch.no_grad():
        output = model(img)
    predicted_class = torch.argmax(output).item()
    predicted_class_name = get_class_name(predicted_class, context)
    update.message.reply_text(f'{predicted_class_name}')

def help_command(update: Update, context: CallbackContext) -> None:
    update.message.reply_text(get_message('help_message', context))

def load_messages(language):
    with open(f"translations_{language}.json", "r", encoding="utf-8") as file:
        return json.load(file)

def get_message(key, context):
    language = context.user_data.get('language', 'en')  # По умолчанию используем английский
    messages = load_messages(language)
    return messages[key]

def get_class_name(index, context):
    language = context.user_data.get('language', 'en')  # По умолчанию используем английский
    messages = load_messages(language)
    class_names = messages['class']
    return class_names.get(str(index), 'Unknown class')

def main() -> None:
    # Токен бота берём из переменной окружения BOT_TOKEN, а не храним в коде
    token = os.environ.get('BOT_TOKEN')
    if not token:
        raise RuntimeError('Задайте токен бота в переменной окружения BOT_TOKEN')
    updater = Updater(token)
    dp = updater.dispatcher
    dp.add_handler(CommandHandler("start", start))
    dp.add_handler(MessageHandler(Filters.text & ~Filters.command, select_language))
    dp.add_handler(MessageHandler(Filters.photo & ~Filters.command, handle_image))
    dp.add_handler(CommandHandler("help", help_command))

    updater.start_polling()

    updater.idle()

if __name__ == '__main__':
    main()

