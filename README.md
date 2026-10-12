# FranekCore

![banner](assets/banner.png)

Prosty plugin komunikacyjny na BungeeCord / Waterfall. Wiadomości prywatne działają
między serwerami, jest helpop dla graczy, chat staffu i socialspy.

## Komendy

| Komenda | Co robi |
|---|---|
| `/msg <gracz> <tresc>` | priv, nawet jak adresat jest na innym serwerze |
| `/r <tresc>` | odpowiedz na ostatniego priva |
| `/helpop <tresc>` | zgloszenie do administracji (odbieraja gracze z `helpop.receive`), z cooldownem zebysmy nie spamowali |
| `/ac <tresc>` | chat widoczny tylko dla staffu (uprawnienie `mod`) |
| `/socialspy` | podgląd privów graczy, przydatne na startowym |

Kazdy modul mozna wylaczyc w configie jesli jest niepotrzebny.

## Config

`plugins/FranekCore/config.json` tworzy sie sam po pierwszym uruchomieniu proxy:

```json
{
  "options": {
    "privateMessages": true,
    "helpOp": true,
    "staffChat": true,
    "helpOpCooldownSeconds": 5,
    "privateMessageFormat": "&8[&7{SENDER_SERVER}&8] &e{SENDER} &8-> &8[&7{RECIPIENT_SERVER}&8] &e{RECIPIENT}&8: &f{MESSAGES}",
    "staffChatFormat": "&8[&6&lAdminChat&8] » &7{SENDER}&8: &f{MESSAGES}",
    "helpOpFormat": "&8[&c&lHelpop&8] &7{SENDER}&8: &f{MESSAGES}",
    "helpOpCooldownMessage": "&cPoczekaj jeszcze {TIME} sekund(y), zanim ponownie użyjesz /helpop."
  }
}
```

Formaty obsluguja kody `&`, kolory HEX (`#RRGGBB`) i placeholdery `{SENDER}`,
`{RECIPIENT}`, `{SENDER_SERVER}`, `{RECIPIENT_SERVER}`, `{MESSAGES}`, `{TIME}`.

Po zmianach trzeba zrestartowac proxy, bo komendy rejestruja sie tylko przy starcie.

## Instalacja

Jar z folderu [`releases/`](releases/) wrzucasz do `plugins/` na proxy i restart.

## Budowanie

```bash
mvn package
```

Wychodzi `target/FranekCore-1.0.jar`. Wymaga JDK 16+ i Mavena.

## Licencja

Wszelkie prawa zastrzeżone. Autor: franek_ ([GitHub](https://github.com/Blejziak)).
