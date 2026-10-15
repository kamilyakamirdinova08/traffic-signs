
# from telegram.ext import Updater, CommandHandler, MessageHandler, Filters
# from googletrans import Translator
#
# # Токен вашего бота
# TOKEN = "YOUR_BOT_TOKEN"
#
# # Создаем экземпляр класса Translator
# translator = Translator()
#
# # Функция-обработчик команды /start
# def start(update, context):
#     update.message.reply_text("Привет! Я мультиязычный бот. Напиши мне что-нибудь!")
#
# # Функция-обработчик текстовых сообщений
# def echo(update, context):
#     # Получаем текст сообщения
#     text = update.message.text
#     # Определяем язык текста
#     lang = translator.detect(text).lang
#     # Переводим текст на английский, если язык не английский
#     if lang != 'en':
#         translated_text = translator.translate(text, src=lang, dest='en').text
#     else:
#         translated_text = text
#     # Отправляем ответное сообщение
#     update.message.reply_text(f"You said: {translated_text}")
#
# def main():
#     # Создаем экземпляр класса Updater и передаем ему токен бота
#     updater = Updater(TOKEN, use_context=True)
#
#     # Получаем объект диспетчера из updater
#     dp = updater.dispatcher
#
#     # Добавляем обработчики команд
#     dp.add_handler(CommandHandler("start", start))
#
#     # Добавляем обработчик текстовых сообщений
#     dp.add_handler(MessageHandler(Filters.text & ~Filters.command, echo))
#
#     # Запускаем бота
#     updater.start_polling()
#
#     # Запускаем цикл обработки входящих сообщений
#     updater.idle()
#
# if __name__ == '__main__':
#     main()



#language translation

# import json
# from telegram import ReplyKeyboardMarkup, KeyboardButton
# from telegram.ext import Updater, CommandHandler, MessageHandler, Filters, ConversationHandler
# from googletrans import Translator
#
# # Токен вашего бота
# TOKEN = "YOUR_BOT_TOKEN"
#
# # Состояния для реализации диалога с пользователем
# SET_LANGUAGE = 0
#
# # Создаем экземпляр класса Translator
# translator = Translator()
#
#
# # Загружаем сообщения из json файлов
# def load_messages(language):
#     with open(f"translations_{language}.json", "r", encoding="utf-8") as file:
#         return json.load(file)
#
#
# # Функция-обработчик команды /start
# def start(update, context):
#     update.message.reply_text("Привет! Я мультиязычный бот. Чтобы выбрать язык, используй команду /setlang.")
#
#
# # Функция для начала выбора языка
# def set_language_start(update, context):
#     reply_keyboard = [["English", "Русский"]]
#     update.message.reply_text(
#         "Выберите язык:",
#         reply_markup=ReplyKeyboardMarkup(reply_keyboard, one_time_keyboard=True)
#     )
#     return SET_LANGUAGE
#
#
# # Функция для обработки выбора языка
# def set_language(update, context):
#     user = update.message.from_user
#     selected_language = update.message.text.lower()
#     context.user_data['language'] = selected_language
#     update.message.reply_text(f"Язык установлен: {selected_language.capitalize()}")
#     return ConversationHandler.END
#
#
# # Функция-обработчик текстовых сообщений
# def echo(update, context):
#     text = update.message.text
#     language = context.user_data.get('language', 'en')  # По умолчанию используем английский
#     messages = load_messages(language)
#     update.message.reply_text(f"{messages['reply_prefix']} {text}")
#
#
# # Функция для отмены операции выбора языка
# def cancel(update, context):
#     update.message.reply_text("Выбор языка отменен.")
#     return ConversationHandler.END
#
#
# def main():
#     updater = Updater(TOKEN, use_context=True)
#     dp = updater.dispatcher
#
#     dp.add_handler(CommandHandler("start", start))
#
#     conv_handler = ConversationHandler(
#         entry_points=[CommandHandler('setlang', set_language_start)],
#         states={
#             SET_LANGUAGE: [MessageHandler(Filters.text & ~Filters.command, set_language)]
#         },
#         fallbacks=[CommandHandler('cancel', cancel)]
#     )
#     dp.add_handler(conv_handler)
#
#     dp.add_handler(MessageHandler(Filters.text & ~Filters.command, echo))
#
#     updater.start_polling()
#     updater.idle()
#
#
# if __name__ == '__main__':
#     main()


















# models keras and pythorch


# from keras.models import load_model
# from keras.preprocessing import image
# import numpy as np
# from telegram import Update
# # from telegram.ext import Updater, CommandHandler, MessageHandler, Filters, CallbackContext
# # from telegram.ext import Updater, CommandHandler, MessageHandler, CallbackContext
# from telegram.ext import Updater, CommandHandler, MessageHandler, CallbackContext, filters
# from telegram.ext.filters import Filters
#
#
# # Путь к вашей модели Keras
# MODEL_PATH = 'model.h5'
#
# # Загрузка модели
# model = load_model(MODEL_PATH)
#
#
# # Функция для обработки команды /start
# def start(update: Update, context: CallbackContext) -> None:
#     update.message.reply_text('Привет! Отправь мне изображение для классификации.')
#
#
# # Функция для обработки входящего изображения
# def handle_image(update: Update, context: CallbackContext) -> None:
#     # Сохранение изображения, полученного от пользователя
#     photo_file = update.message.photo[-1].get_file()
#     photo_file.download('input_image.jpg')
#
#     # Загрузка изображения и подготовка для классификации
#     img_path = 'input_image.jpg'
#     img = image.load_img(img_path, target_size=(30, 30))
#     x = image.img_to_array(img)
#     x = np.expand_dims(x, axis=0)
#     x /= 255.0  # Нормализация
#
#     # Предсказание класса
#     predictions = model.predict(x)
#     class_index = np.argmax(predictions)
#
#     # Определение класса по индексу
#     # classes = {
#     #     0: 'Speed limit (20km/h)',
#     #     1: 'Speed limit (30km/h)',
#     #     2: 'Speed limit (50km/h)',
#     #     3: 'Speed limit (60km/h)',
#     #     4: 'Speed limit (70km/h)',
#     #     5: 'Speed limit (80km/h)',
#     #     6: 'End of speed limit (80km/h)',
#     #     7: 'Speed limit (100km/h)',
#     #     8: 'Speed limit (120km/h)',
#     #     9: 'No passing',
#     #     10: 'No passing veh over 3.5 tons',
#     #     11: 'Right-of-way at intersection',
#     #     12: 'Priority road',
#     #     13: 'Yield',
#     #     14: 'Stop',
#     #     15: 'No vehicles',
#     #     16: 'Veh > 3.5 tons prohibited',
#     #     17: 'No entry',
#     #     18: 'General caution',
#     #     19: 'Dangerous curve left',
#     #     20: 'Dangerous curve right',
#     #     21: 'Double curve',
#     #     22: 'Bumpy road',
#     #     23: 'Slippery road',
#     #     24: 'Road narrows on the right',
#     #     25: 'Road work',
#     #     26: 'Traffic signals',
#     #     27: 'Pedestrians',
#     #     28: 'Children crossing',
#     #     29: 'Bicycles crossing',
#     #     30: 'Beware of ice/snow',
#     #     31: 'Wild animals crossing',
#     #     32: 'End speed + passing limits',
#     #     33: 'Turn right ahead',
#     #     34: 'Turn left ahead',
#     #     35: 'Ahead only',
#     #     36: 'Go straight or right',
#     #     37: 'Go straight or left',
#     #     38: 'Keep right',
#     #     39: 'Keep left',
#     #     40: 'Roundabout mandatory',
#     #     41: 'End of no passing',
#     #     42: 'End no passing veh > 3.5 tons'
#     # }
#     classes = {
#         0: 'Ограничение максимальной скорости (20km/h)',
#         1: 'Ограничение максимальной скорости (30km/h)',
#         2: 'Ограничение максимальной скорости (50km/h)',
#         3: 'Ограничение максимальной скорости (60km/h)',
#         4: 'Ограничение максимальной скорости (70km/h)',
#         5: 'Ограничение максимальной скорости (80km/h)',
#         6: 'Конец зоны ограничение максимальной скорости (80km/h)',
#         7: 'Ограничение максимальной скорости (100km/h)',
#         8: 'Ограничение максимальной скорости (120km/h)',
#         9: 'Обгон запрещен',
#         10: 'Обгон грузовым автомобилям запрещен',
#         11: 'Пересечение со второстепенной дорогой',
#         12: 'Круговое движение',
#         13: 'Уступи дорогу',
#         14: 'Движение без остановки запрещено',
#         15: 'Движение запрещено',
#         16: 'Движение грузовых автомобилей запрещено',
#         17: 'Движение направо',
#         18: 'Прочие опасности',
#         19: 'Опасный поворот (левый)',
#         20: 'Опасный поворот (правый)',
#         21: 'Опасные повороты',
#         22: 'Неровная дорога',
#         23: 'Скользкая дорога',
#         24: 'Сужение дороги',
#         25: 'Дорожные работы',
#         26: 'Светофорное регулирование',
#         27: 'Пешеходный переход',
#         28: 'Дети',
#         29: 'Пересечение с велосипедной дорожкой',
#         30: 'Гололедица',
#         31: 'Дикие животные',
#         32: 'Конец зоны всех ограничений',
#         33: 'Въезд запрещен',
#         34: 'Движение налево',
#         35: 'Движение прямо',
#         36: 'Движение прямо или направо',
#         37: 'Движение прямо или налево',
#         38: 'Объезд препятствия справа',
#         39: 'Объезд препятствия слева',
#         40: 'Главная дорога',
#         41: 'Конец зоны запрещения обгона',
#         42: 'Конец зоны запрещения обгона грузовым автомобилям'
#     }
#     predicted_class = classes.get(class_index, 'Неизвестный класс')
#
#     # Вывод результата
#     update.message.reply_text(f'Класс изображения: {predicted_class}')
#
#
# # Функция для обработки команды /help
# def help_command(update: Update, context: CallbackContext) -> None:
#     update.message.reply_text('Отправьте мне изображение для классификации.')
#
#
# def main() -> None:
#     updater = Updater("YOUR_BOT_TOKEN")
#
#     dp = updater.dispatcher
#
#     dp.add_handler(CommandHandler("start", start))
#     dp.add_handler(MessageHandler(Filters.photo & ~Filters.command, handle_image))
#     dp.add_handler(CommandHandler("help", help_command))
#
#     updater.start_polling()
#
#     updater.idle()
#
#
# if __name__ == '__main__':
#     main()
#
#     import torch
#     import torchvision.transforms as transforms
#     from torch.utils.data import DataLoader
#     from telegram import Update
#     from telegram.ext import Updater, CommandHandler, MessageHandler, CallbackContext, Filters
#     from PIL import Image
#     from model import GTSRB_MODEL  # Импортируем класс GTSRB_MODEL из файла model.py
#
#     # Путь к вашей модели PyTorch
#     MODEL_PATH = 'gtsrb_model.pth'
#
#     # Определяем input_dim и output_dim для модели GTSRB_MODEL
#     INPUT_DIM = 3 * 50 * 50  # Входной размер изображения (3 канала RGB и размер 50x50)
#     OUTPUT_DIM = 43  # Количество классов (в GTSRB датасете 43 класса)
#
#     # Загрузка модели GTSRB_MODEL
#     model = GTSRB_MODEL(INPUT_DIM, OUTPUT_DIM)
#     model.load_state_dict(torch.load(MODEL_PATH, map_location=torch.device('cpu')))
#
#     model.eval()
#
#     # Трансформация изображения перед подачей в модель
#     preprocess = transforms.Compose([
#         transforms.Resize(50),
#         transforms.CenterCrop(50),
#         transforms.ToTensor(),
#         transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
#     ])
#
#
#     # Функция для обработки команды /start
#     def start(update: Update, context: CallbackContext) -> None:
#         update.message.reply_text('Привет! Отправь мне изображение для классификации.')
#
#
#     # Функция для обработки входящего изображения
#     def handle_image(update: Update, context: CallbackContext) -> None:
#         # Сохранение изображения, полученного от пользователя
#         photo_file = update.message.photo[-1].get_file()
#         photo_file.download('input_image.jpg')
#
#         # Загрузка изображения и подготовка для классификации
#         img = Image.open('input_image.jpg')
#         img = preprocess(img)
#         img = img.unsqueeze(0)  # добавляем размерность пакета
#
#         # Предсказание класса
#         with torch.no_grad():
#             output = model(img)
#         predicted_class = torch.argmax(output).item()
#
#         # Определение класса по индексу
#         classes = {
#             0: 'Ограничение максимальной скорости (20km/h)',
#             1: 'lllllОграничение максимальной скорости (30km/h)',
#             2: 'Ограничение максимальной скорости (50km/h)',
#             3: 'Ограничение максимальной скорости (60km/h)',
#             4: 'Ограничение максимальной скорости (70km/h)',
#             5: 'Ограничение максимальной скорости (80km/h)',
#             6: 'Конец зоны ограничение максимальной скорости (80km/h)',
#             7: 'Ограничение максимальной скорости (100km/h)',
#             8: 'Ограничение максимальной скорости (120km/h)',
#             9: 'Обгон запрещен',
#             10: 'Обгон грузовым автомобилям запрещен',
#             11: 'Пересечение со второстепенной дорогой',
#             12: 'Круговое движение',
#             13: 'Уступи дорогу',
#             14: 'Движение без остановки запрещено',
#             15: 'Движение запрещено',
#             16: 'Движение грузовых автомобилей запрещено',
#             17: 'Движение направо',
#             18: 'Прочие опасности',
#             19: 'Опасный поворот (левый)',
#             20: 'Опасный поворот (правый)',
#             21: 'Опасные повороты',
#             22: 'Неровная дорога',
#             23: 'Скользкая дорога',
#             24: 'Сужение дороги',
#             25: 'Дорожные работы',
#             26: 'Светофорное регулирование',
#             27: 'Пешеходный переход',
#             28: 'Дети',
#             29: 'Пересечение с велосипедной дорожкой',
#             30: 'Гололедица',
#             31: 'Дикие животные',
#             32: 'Конец зоны всех ограничений',
#             33: 'Въезд запрещен',
#             34: 'Движение налево',
#             35: 'Движение прямо',
#             36: 'Движение прямо или направо',
#             37: 'Движение прямо или налево',
#             38: 'Объезд препятствия справа',
#             39: 'Объезд препятствия слева',
#             40: 'Главная дорога',
#             41: 'Конец зоны запрещения обгона',
#             42: 'Конец зоны запрещения обгона грузовым автомобилям'
#         }
#         predicted_class_name = classes.get(predicted_class, 'Неизвестный класс')
#
#         # Вывод результата
#         update.message.reply_text(f'Класс изображения: {predicted_class_name}')
#
#
#     # Функция для обработки команды /help
#     def help_command(update: Update, context: CallbackContext) -> None:
#         update.message.reply_text('Отправьте мне изображение для классификации.')
#
#
#     def main() -> None:
#         updater = Updater("YOUR_BOT_TOKEN")
#
#         dp = updater.dispatcher
#
#         dp.add_handler(CommandHandler("start", start))
#         dp.add_handler(MessageHandler(Filters.photo & ~Filters.command, handle_image))
#         dp.add_handler(CommandHandler("help", help_command))
#
#         updater.start_polling()
#
#         updater.idle()
#
#
#     if __name__ == '__main__':
#         main()







#
# import torch
# import torchvision.transforms as transforms
# from torch.utils.data import DataLoader
# from telegram import Update
# from telegram.ext import Updater, CommandHandler, MessageHandler, CallbackContext, Filters
# from PIL import Image
# from model import GTSRB_MODEL  # Импортируем класс GTSRB_MODEL из файла model.py
#
# # Путь к вашей модели PyTorch
# MODEL_PATH = 'gtsrb_model.pth'
#
# # Определяем input_dim и output_dim для модели GTSRB_MODEL
# INPUT_DIM = 3 * 50 * 50  # Входной размер изображения (3 канала RGB и размер 50x50)
# OUTPUT_DIM = 43  # Количество классов (в GTSRB датасете 43 класса)
#
# # Загрузка модели GTSRB_MODEL
# model = GTSRB_MODEL(INPUT_DIM, OUTPUT_DIM)
# model.load_state_dict(torch.load(MODEL_PATH, map_location=torch.device('cpu')))
#
# model.eval()
#
# # Трансформация изображения перед подачей в модель
# preprocess = transforms.Compose([
#     transforms.Resize(50),
#     transforms.CenterCrop(50),
#     transforms.ToTensor(),
#     transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
# ])
#
# # Функция для обработки команды /start
# def start(update: Update, context: CallbackContext) -> None:
#     update.message.reply_text('Привет! Отправь мне изображение для классификации.')
#
# # Функция для обработки входящего изображения
# def handle_image(update: Update, context: CallbackContext) -> None:
#     # Сохранение изображения, полученного от пользователя
#     photo_file = update.message.photo[-1].get_file()
#     photo_file.download('input_image.jpg')
#
#     # Загрузка изображения и подготовка для классификации
#     img = Image.open('input_image.jpg')
#     img = preprocess(img)
#     img = img.unsqueeze(0)  # добавляем размерность пакета
#
#     # Предсказание класса
#     with torch.no_grad():
#         output = model(img)
#     predicted_class = torch.argmax(output).item()
#
#     # Определение класса по индексу
#     classes = {
#         0: 'Максималдуу ылдамдыкка чек коюу (20км/ч)\n\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         1: 'Максималдуу ылдамдыкка чек коюу (30км/ч)\n\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         2: 'Максималдуу ылдамдыкка чек коюу (50км/ч)\n\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         3: 'Максималдуу ылдамдыкка чек коюу (60км/ч)\n\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         4: 'Максималдуу ылдамдыкка чек коюу (70км/ч)\n\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         5: 'Максималдуу ылдамдыкка чек коюу (80км/ч)\n\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         6: 'Максималдуу ылдамдыкка чек коюунун бүтүшү (80km/h)',
#         7: 'Максималдуу ылдамдыкка чек коюу (100км/ч)\n\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         8: 'Максималдуу ылдамдыкка чек коюу (120км/ч)\nБелгиде көрсөтүлгөн ылдамдыктан ашкан ылдамдыкта айдоого тыюу салынат. 3.24 белгилеринин камтуу аймагы белги орнотулган жерден анын артындагы жакынкы кесилишке чейин, ал эми элдуу пункттарда, кесилиш жок болгон учурда калктуу пункттун аягына чейин созулат.',
#         9: 'Озуп өтүүгө тыюу салынат',
#         10: 'Жүк ташуучу автомобиль менен озуп өтүүгө тыюу салынат',
#         11: 'Экинчи даражадагы жол менен кесилишүү',
#         12: 'Негизги жол',
#         13: 'Жол бер',
#         14: 'Токтомоюнча өтүүгө тыюу салынат',
#         15: 'Кыймылга тыюу салынат',
#         16: 'Жүк ташуучу автомобилдердин кыймылына тыюу салынат',
#         17: 'Кирүүгө тыюу салынат',
#         18: 'Башка коркунучтар',
#         19: 'Кооптуу бурулуштар (сол)',
#         20: 'Кооптуу бурулуштар (оң)',
#         21: 'Кооптуу бурулуштар',
#         22: 'Тегиз эмес жол',
#         23: 'Тайгак жол',
#         24: 'Жолдордун тарышы',
#         25: 'Жол иштери',
#         26: 'Светофорду жөнгө салуу',
#         27: 'Жөө жүргүнчү өткөөлү',
#         28: 'Балдар',
#         29: 'Велосипед жолу менен кесилишүү',
#         30: 'Кара тоңголок',
#         31: 'Жапайы жаныбарлар',
#         32: 'Бардык чектөөлөрдүн бүтүшү',
#         33: 'Оңго кетүү',
#         34: 'Солго кетүү',
#         35: 'Түз кутүү',
#         36: 'Түз же оңго кетүү',
#         37: 'Түз же солго кетүү',
#         38: 'Тоскоолдукту оң жагынан айланып өтүү',
#         39: 'Тоскоолдукту сол жагынан айланып өтүү',
#         40: 'Алампа кыймыл',
#         41: 'Озуп өтүүгө тыюу салуунун бүтүшү',
#         42: 'Жүк ташуучу автомобиль менен озуп өтүүгө тыюу салуунун бүтүшү'
#     }
#     predicted_class_name = classes.get(predicted_class, 'Неизвестный класс')
#
#     # Вывод результата
#     update.message.reply_text(f'{predicted_class_name}')
#
# # Функция для обработки команды /help
# def help_command(update: Update, context: CallbackContext) -> None:
#     update.message.reply_text('Отправьте мне изображение для классификации.')
#
# def main() -> None:
#     updater = Updater("YOUR_BOT_TOKEN")
#
#     dp = updater.dispatcher
#
#     dp.add_handler(CommandHandler("start", start))
#     dp.add_handler(MessageHandler(Filters.photo & ~Filters.command, handle_image))
#     dp.add_handler(CommandHandler("help", help_command))
#
#     updater.start_polling()
#
#     updater.idle()
#
# if __name__ == '__main__':
#     main()



