![](https://raw.githubusercontent.com/TrixzyMc/images/refs/heads/main/ocooldown.png)

# OCooldowns
OCooldowns is a lightweight Minecraft plugin for managing player-based cooldowns with API support, events, and configurable settings.

---
## Commands
- `/ocooldowns reload`  
  Reloads the plugin configuration.

- `/ocooldowns info`  
  Displays plugin information.
---

## Default Config
In-Config Placeholders available:
- `<time>` → Remaining cooldown time
- `<command>` → Command name
- `<prefix>` → Config-defined message prefix

---

### Messages

```yaml
messages:
  general:
    prefix: '&8[&c&loCooldowns&8]&r &8»&f'
    insufficient_permission: '<prefix> &cYou do not have permission to use this command.'
    reload: '<prefix> &aReloaded config in <time>ms!'

  cooldown:
    chat: '<prefix> You must wait &c<time>&f seconds to use this command again!'
    actionbar: "&c<command> &fis on cooldown for &c<time>s&f!"
    title:
      title: "&8[&c&loCooldowns&8]&r"
      subtitle: "Please wait &c<time>&f seconds to use <command> again!"

  completion:
    enabled: true
    message: "<prefix> You can now use &6<command>&f again."
```

---

### Notifications

Controls how cooldown messages are displayed.

```yaml
notifications:
  chat: true
  actionbar: true
  title: false
```

---

### Sounds

Configurable feedback sounds for different events.

```yaml
sounds:
  reload:
    enabled: true
    sound: 'block.note_block.pling'
    volume: 1
    pitch: 1

  insufficient_permission:
    enabled: true
    sound: 'block.note_block.bass'
    volume: 1
    pitch: 1

  cooldown:
    enabled: true
    sound: 'block.note_block.bass'
    volume: 1
    pitch: 1
```

---

### Extra Settings

```yaml
extra:
  # ocooldowns.bypass.[command_name|*]
  # If enabled, permission-based bypass is disabled (even OPs cannot bypass cooldowns)
  disable_bypass_permissions: false
```

### Cooldowns

Define command cooldown durations in seconds.

```yaml
cooldowns:
  # command_name: seconds
  heal: 60
  feed: 30
  spawn: 10
```
