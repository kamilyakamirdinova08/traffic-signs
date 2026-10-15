# Traffic Sign Recognition Telegram Bot

A Telegram bot that recognizes road signs from photos. Send it a picture of a sign, and it replies with the sign's name (in Russian) and the model's confidence.

The classifier is a convolutional neural network (Keras) trained on the **GTSRB** dataset (German Traffic Sign Recognition Benchmark): 43 sign classes, 30×30 RGB input.

## How it works

```
photo from user → resize to 30×30, scale to 0..1 → CNN (model.h5) → class 0–42 → sign name + confidence
```

## Run locally

Requires Python 3.10–3.12 (python-telegram-bot 13 does not support Python 3.13).

```bash
pip install -r requirements.txt
```

1. Create a bot with [@BotFather](https://t.me/BotFather) and copy its token.
2. Set the token as an environment variable (never put it in the code):
   - Windows (PowerShell): `$env:BOT_TOKEN = "your-token"`
   - Linux/macOS: `export BOT_TOKEN="your-token"`
3. Start the bot:

```bash
python main.py
```

## Files

| File | What it is |
|---|---|
| `main.py` | Bot: receives a photo, runs the model, replies with the sign name |
| `model.h5` | Trained Keras CNN (GTSRB, 43 classes) |
| `requirements.txt` | Dependencies |

## Stack

Python · TensorFlow / Keras · NumPy · python-telegram-bot
