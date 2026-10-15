import os
from io import BytesIO

import numpy as np
from keras.models import load_model
from keras.utils import load_img, img_to_array
from telegram import Update
from telegram.ext import Updater, CommandHandler, MessageHandler, CallbackContext, Filters


# Путь к модели Keras
MODEL_PATH = 'model.h5'

# Загрузка модели
model = load_model(MODEL_PATH)

# Классы GTSRB (German Traffic Sign Recognition Benchmark), индекс -> название знака
classes = {
    0: 'Ограничение максимальной скорости (20 км/ч)',
    1: 'Ограничение максимальной скорости (30 км/ч)',
    2: 'Ограничение максимальной скорости (50 км/ч)',
    3: 'Ограничение максимальной скорости (60 км/ч)',
    4: 'Ограничение максимальной скорости (70 км/ч)',
    5: 'Ограничение максимальной скорости (80 км/ч)',
    6: 'Конец зоны ограничения максимальной скорости (80 км/ч)',
    7: 'Ограничение максимальной скорости (100 км/ч)',
    8: 'Ограничение максимальной скорости (120 км/ч)',
    9: 'Обгон запрещён',
    10: 'Обгон грузовым автомобилям запрещён',
    11: 'Пересечение со второстепенной дорогой',
    12: 'Главная дорога',
    13: 'Уступи дорогу',
    14: 'Движение без остановки запрещено',
    15: 'Движение запрещено',
    16: 'Движение грузовых автомобилей запрещено',
    17: 'Въезд запрещён',
    18: 'Прочие опасности',
    19: 'Опасный поворот (левый)',
    20: 'Опасный поворот (правый)',
    21: 'Опасные повороты',
    22: 'Неровная дорога',
    23: 'Скользкая дорога',
    24: 'Сужение дороги',
    25: 'Дорожные работы',
    26: 'Светофорное регулирование',
    27: 'Пешеходный переход',
    28: 'Дети',
    29: 'Пересечение с велосипедной дорожкой',
    30: 'Гололедица',
    31: 'Дикие животные',
    32: 'Конец зоны всех ограничений',
    33: 'Движение направо',
    34: 'Движение налево',
    35: 'Движение прямо',
    36: 'Движение прямо или направо',
    37: 'Движение прямо или налево',
    38: 'Объезд препятствия справа',
    39: 'Объезд препятствия слева',
    40: 'Круговое движение',
    41: 'Конец зоны запрещения обгона',
    42: 'Конец зоны запрещения обгона грузовым автомобилям',
}


# Функция для обработки команды /start
def start(update: Update, context: CallbackContext) -> None:
    update.message.reply_text('Привет! Отправь мне фото дорожного знака, и я скажу, что это за знак.')


# Функция для обработки входящего изображения
def handle_image(update: Update, context: CallbackContext) -> None:
    # Фото читается в память, без сохранения на диск: несколько пользователей не мешают друг другу
    buffer = BytesIO()
    update.message.photo[-1].get_file().download(out=buffer)
    buffer.seek(0)

    # Подготовка изображения так же, как при обучении: 30x30, значения 0..1
    img = load_img(buffer, target_size=(30, 30))
    x = np.expand_dims(img_to_array(img), axis=0) / 255.0

    # Предсказание класса
    predictions = model.predict(x, verbose=0)[0]
    class_index = int(np.argmax(predictions))
    confidence = float(predictions[class_index])

    predicted_class = classes.get(class_index, 'Неизвестный класс')
    update.message.reply_text(f'Знак: {predicted_class}\nУверенность: {confidence:.0%}')


# Функция для обработки команды /help
def help_command(update: Update, context: CallbackContext) -> None:
    update.message.reply_text('Отправьте мне фото дорожного знака для распознавания.')


def main() -> None:
    token = os.environ.get('BOT_TOKEN')
    if not token:
        raise SystemExit('Укажите токен бота в переменной окружения BOT_TOKEN (см. README).')
    updater = Updater(token)

    dp = updater.dispatcher

    dp.add_handler(CommandHandler("start", start))
    dp.add_handler(MessageHandler(Filters.photo & ~Filters.command, handle_image))
    dp.add_handler(CommandHandler("help", help_command))

    updater.start_polling()

    updater.idle()


if __name__ == '__main__':
    main()
