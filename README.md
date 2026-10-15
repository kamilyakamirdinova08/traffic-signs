# Traffic Sign Recognition Telegram Bot

A Telegram bot that recognizes road signs from photos. Send it a picture of a sign, and it replies with the sign's name in the language you chose: English, Russian, Kyrgyz or Turkish.

The classifier is a neural network (PyTorch) trained on the **GTSRB** dataset (German Traffic Sign Recognition Benchmark): 43 sign classes, 50×50 RGB input.

## Results

| | Accuracy |
|---|---|
| Validation (20% of the training set) | 99.85% |
| **Official GTSRB test set (12,630 images)** | **98.97%** |

Model: 6 convolutional layers with batch normalization and 3 fully connected layers, trained for 20 epochs with Adam and a linear learning-rate decay (see `Model.ipynb`).

## How it works

```
/start → choose language → photo from user → resize to 50×50, scale to 0..1 → model (gtsrb_model.pth) → class 0–42 → sign name in chosen language
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
| `main.py` | Bot: language choice, receives a photo, runs the model, replies with the sign name |
| `model.py` | `GTSRB_MODEL` network definition (PyTorch) |
| `gtsrb_model.pth` | Trained PyTorch weights (GTSRB, 43 classes) |
| `translations_*.json` | Bot messages and sign names: `en`, `ru`, `ky`, `tr` |
| `Model.ipynb` | Training notebook |
| `model.h5` | Earlier Keras version of the model |
| `requirements.txt` | Dependencies |

## Stack

Python · PyTorch / torchvision · Pillow · python-telegram-bot
