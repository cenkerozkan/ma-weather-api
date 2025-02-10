db = db.getSiblingDB('weather_db');

db.createCollection('air_quality');
db.createCollection('cities');

db.cities.insertMany([
    {
        "_id": {
            "$oid": "67a9bdd03d6ee1edc41b4e52"
        },
        "name": "Ankara",
        "local_names": {
            "uk": "Анкара",
            "he": "אנקרה",
            "bn": "আঙ্কারা",
            "la": "Ancyra",
            "ar": "أنقرة",
            "sv": "Ankara",
            "eu": "Ankara",
            "kk": "Анкара",
            "mi": "Anakara",
            "mr": "अंकारा",
            "fa": "آنکارا",
            "th": "อังการา",
            "bo": "ཨན་ཁ་ར།",
            "tt": "Әнкара",
            "hy": "Անկարա",
            "it": "Ankara",
            "is": "Ankara",
            "br": "Ankara",
            "ja": "アンカラ",
            "ab": "Анкара",
            "sr": "Анкара",
            "be": "Анкара",
            "pl": "Ankara",
            "ko": "앙카라",
            "en": "Ankara",
            "av": "Анкара",
            "de": "Ankara",
            "tr": "Ankara",
            "pt": "Ancara",
            "ie": "Ancara",
            "no": "Ankara",
            "el": "Άγκυρα",
            "ka": "ანკარა",
            "ml": "അങ്കാറ",
            "es": "Ankara",
            "eo": "Ankaro",
            "bg": "Анкара",
            "yi": "אנקארא",
            "gl": "Ancara",
            "am": "አንካራ",
            "hi": "अंकारा",
            "hu": "Ankara",
            "ta": "அங்காரா",
            "fr": "Ankara",
            "ky": "Анкара",
            "te": "అంకారా",
            "kn": "ಅಂಕಾರಾ",
            "mn": "Анкара",
            "ku": "Enqere",
            "ru": "Анкара",
            "os": "Анкара",
            "mk": "Анкара",
            "oc": "Ankara",
            "ug": "Enqere",
            "ps": "انقره",
            "zh": "安卡拉",
            "ur": "انقرہ",
            "tg": "Анкара",
            "nl": "Ankara"
        },
        "lat": 39.9207886,
        "lon": 32.8540482,
        "country": "TR"
    },
    {
        "_id": {
            "$oid": "67a9bec63d6ee1edc41b4e54"
        },
        "name": "London",
        "local_names": {
            "ln": "Lóndɛlɛ",
            "wa": "Londe",
            "se": "London",
            "sk": "Londýn",
            "sn": "London",
            "sm": "Lonetona",
            "qu": "London",
            "sd": "لنڊن",
            "ff": "London",
            "fa": "لندن",
            "st": "London",
            "bn": "লন্ডন",
            "ku": "London",
            "av": "Лондон",
            "vi": "Luân Đôn",
            "zh": "伦敦",
            "az": "London",
            "ka": "ლონდონი",
            "bo": "ལ���ན་ཊོན།",
            "ky": "Лондон",
            "sc": "Londra",
            "vo": "London",
            "ja": "ロンドン",
            "et": "London",
            "am": "ለንደን",
            "es": "Londres",
            "tl": "Londres",
            "tt": "Лондон",
            "ny": "London",
            "yi": "לאנדאן",
            "ee": "London",
            "zu": "ILondon",
            "fi": "Lontoo",
            "cs": "Londýn",
            "ht": "Lonn",
            "en": "London",
            "na": "London",
            "yo": "Lọndọnu",
            "gv": "Lunnin",
            "sr": "Лондон",
            "bh": "लंदन",
            "ha": "Landan",
            "lv": "Londona",
            "or": "ଲଣ୍ଡନ",
            "ga": "Londain",
            "nv": "Tooh Dineʼé Bikin Haalʼá",
            "bg": "Лондон",
            "my": "လန်ဒန်မြို့",
            "mi": "Rānana",
            "gu": "લંડન",
            "lo": "ລອນດອນ",
            "he": "לונדון",
            "ms": "London",
            "mr": "लंडन",
            "fr": "Londres",
            "bi": "London",
            "ta": "இலண்டன்",
            "ca": "Londres",
            "bm": "London",
            "km": "ឡុងដ៍",
            "ar": "لندن",
            "mt": "Londra",
            "oc": "Londres",
            "hu": "London",
            "an": "Londres",
            "th": "ลอนดอน",
            "om": "Landan",
            "nn": "London",
            "br": "Londrez",
            "sl": "London",
            "kl": "London",
            "wo": "Londar",
            "be": "Лондан",
            "mg": "Lôndôna",
            "te": "లండన్",
            "ps": "لندن",
            "ru": "Лондон",
            "hy": "Լոնդոն",
            "ur": "علاقہ لندن",
            "lb": "London",
            "pt": "Londres",
            "fy": "Londen",
            "hr": "London",
            "mk": "Лондон",
            "pa": "ਲੰਡਨ",
            "cv": "Лондон",
            "ig": "London",
            "so": "London",
            "kn": "ಲಂಡನ್",
            "ro": "Londra",
            "hi": "लंदन",
            "cu": "Лондонъ",
            "fo": "London",
            "to": "Lonitoni",
            "uk": "Лондон",
            "nl": "Londen",
            "kw": "Loundres",
            "bs": "London",
            "jv": "London",
            "uz": "London",
            "eu": "Londres",
            "pl": "Londyn",
            "ba": "Лондон",
            "eo": "Londono",
            "is": "London",
            "io": "London",
            "sv": "London",
            "cy": "Llundain",
            "si": "ලන්ඩන්",
            "rm": "Londra",
            "feature_name": "London",
            "ko": "런던",
            "os": "Лондон",
            "ce": "Лондон",
            "ab": "Лондон",
            "ascii": "London",
            "sq": "Londra",
            "ay": "London",
            "tk": "London",
            "no": "London",
            "tr": "Londra",
            "ml": "ലണ്ടൻ",
            "co": "Londra",
            "de": "London",
            "gn": "Lóndyre",
            "id": "London",
            "tg": "Лондон",
            "da": "London",
            "el": "Λονδίνο",
            "tw": "London",
            "kv": "Лондон",
            "gd": "Lunnainn",
            "sh": "London",
            "ne": "लन्डन",
            "lt": "Londonas",
            "it": "Londra",
            "ug": "لوندۇن",
            "fj": "Lodoni",
            "su": "London",
            "li": "Londe",
            "sa": "लन्डन्",
            "mn": "Лондон",
            "ia": "London",
            "ie": "London",
            "sw": "London",
            "kk": "Лондон",
            "gl": "Londres",
            "af": "Londen"
        },
        "lat": 51.5073219,
        "lon": -0.1276474,
        "country": "GB"
    },
    {
        "_id": {
            "$oid": "67a9bf7c3d6ee1edc41b4e56"
        },
        "name": "Barcelona",
        "local_names": {
            "hi": "बार्सॆलोना",
            "pl": "Barcelona",
            "cs": "Barcelona",
            "uk": "Барселона",
            "be": "Барселона",
            "he": "ברצלונה",
            "eu": "Bartzelona",
            "kn": "ಬಾರ್ಸೆಲೋನಾ",
            "br": "Barcelona",
            "es": "Barcelona",
            "mk": "Барселона",
            "sr": "Барселона",
            "el": "Βαρκελώνη",
            "oc": "Barcelona",
            "lt": "Barselona",
            "pt": "Barcelona",
            "eo": "Barcelono",
            "ja": "バルセロナ",
            "fr": "Barcelone",
            "de": "Barcelona",
            "tr": "Barselona",
            "gl": "Barcelona",
            "zh": "巴塞罗那",
            "ar": "برشلونة",
            "ru": "Барселона",
            "gd": "Barsalòna",
            "it": "Barcellona",
            "en": "Barcelona",
            "ca": "Barcelona",
            "ko": "바르셀로나"
        },
        "lat": 41.3828939,
        "lon": 2.1774322,
        "country": "ES"
    },
    {
        "_id": {
            "$oid": "67a9c0243d6ee1edc41b4e57"
        },
        "name": "Tokyo",
        "local_names": {
            "nl": "Tokio",
            "hr": "Tokio",
            "sk": "Tokio",
            "ta": "டோக்கியோ",
            "ca": "Tòquio",
            "de": "Tokio",
            "lv": "Tokija",
            "sl": "Tokio",
            "ru": "Токио",
            "ar": "طوكيو",
            "th": "โตเกียว",
            "kn": "ಟೋಕ್ಯೊ",
            "fa": "توکیو",
            "pl": "Tokio",
            "da": "Tokyo",
            "tr": "Tokyo",
            "sv": "Tokyo",
            "cy": "Tokyo",
            "lt": "Tokijas",
            "is": "Tókýó",
            "it": "Tokyo",
            "tg": "Токио",
            "et": "Tōkyō",
            "sr": "Токио",
            "eo": "Tokio",
            "lb": "Tokio",
            "la": "Tokium",
            "pt": "Tóquio",
            "es": "Tokio",
            "fr": "Tokyo",
            "ku": "Tokyo",
            "oc": "Tòquio",
            "ja": "東京都",
            "io": "Tokyo",
            "be": "Токіа",
            "zh": "东京都/東京都",
            "fi": "Tokio",
            "mr": "तोक्यो",
            "en": "Tokyo",
            "bg": "Токио",
            "ko": "도쿄도",
            "mi": "Tōkio",
            "vi": "Tokyo",
            "hu": "Tokió",
            "el": "Τόκιο",
            "ia": "Tokyo",
            "cs": "Tokio",
            "uk": "Токіо",
            "he": "טוקיו"
        },
        "lat": 35.6828387,
        "lon": 139.7594549,
        "country": "JP"
    },
    {
        "_id": {
            "$oid": "67a9c0963d6ee1edc41b4e58"
        },
        "name": "Mumbai",
        "local_names": {
            "uk": "Мумбаї",
            "ta": "மும்பை",
            "te": "ముంబై",
            "mr": "मुंबई",
            "kn": "ಮುಂಬೈ",
            "ks": "بَمبَے",
            "pa": "ਮੁੰਬਈ",
            "hi": "मुंबई",
            "ml": "മുംബൈ",
            "es": "Bombay",
            "pl": "Mumbaj",
            "ia": "Mumbai",
            "yi": "מומביי",
            "cs": "Bombaj",
            "fa": "مومبای",
            "zh": "孟买",
            "ar": "مومباي",
            "lt": "Mumbajus",
            "io": "Mumbai",
            "ur": "ممبئی",
            "ko": "뭄바이",
            "sk": "Bombaj",
            "eo": "Mumbajo",
            "ka": "მუმბაი",
            "el": "Μουμπάι",
            "ja": "ムンバイ",
            "sd": "ممبئي",
            "he": "מומבאי",
            "fr": "Bombay",
            "si": "මුම්බායි",
            "gu": "મુંબઈ",
            "ps": "ممبای",
            "sr": "Мумбај",
            "or": "ମୁମ୍ବାଇ",
            "th": "มุมไบ",
            "en": "Mumbai",
            "de": "Mumbai",
            "az": "Mumbay",
            "ru": "Мумбаи",
            "bn": "মুম্বই",
            "oc": "Mumbai"
        },
        "lat": 19.0785451,
        "lon": 72.878176,
        "country": "IN"
    }
]);