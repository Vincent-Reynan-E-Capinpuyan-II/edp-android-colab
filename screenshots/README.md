# Screenshots — Lab Activity 11: LiceoFieldKit

All eight PNGs in this folder are **real captures of the running app** on an
Android emulator (AVD `fieldkit`, API 36 / Android 16, Google Play x86_64
image). Nothing here is mock-ups or redraws.

| File | Size | What it actually shows |
|---|---|---|
| `01-camera-dialog.png` | 1080x2400 | The system dialog *"Allow capinpuyan to take pictures and record video?"* over the app, in the **NotAsked** state |
| `02-rationale.png` | 1080x2400 | *"We need the camera to photograph the issue you report."* + **Try again** — the **NeedsRationale** state |
| `03-open-settings.png` | 1080x2400 | *"Camera is blocked. Turn it on in Settings."* + **Open Settings** — the **Denied** state |
| `04-camera-photo.png` | 1080x2400 | Live preview, `Saved: shot_….jpg` and the refreshed thumbnail, after granting via Settings |
| `05-level.png` | 1080x2400 | Level card reading `x = 0.00  y = 0.00  z = 9.81` and **LEVEL ✓** — see the caveat below |
| `06-location.png` | 1080x2400 | `8.48220, 125.68900 (±5 m)` and `Location access: Precise` |
| `07-branch.png` | 996x576 | Terminal showing `git branch` → `* lab-activity-11`, with the last commit |
| `08-shake-capture.png` | 1398x1032 | **Bonus.** The app (showing the new thumbnail) beside a Logcat window with the `Shake capture` lines |

`09-torch.png` is optional and needs a real phone — see the bonus note at the
bottom.

## One honest caveat about `05-level.png`

The handout asks for the **Virtual sensors panel visible next to the emulator**
in this shot. **It is not in the frame.** The emulator's `⋮ Extended controls`
button could not be reached reliably from the automation, and rather than ship a
screenshot that only *looks* right, this is the plain device capture showing the
Level card with live numbers and `LEVEL ✓`.

The numbers are still genuine sensor values, not typed in. They were set through
the emulator's virtual accelerometer, which is the *same* virtual sensor the
`⋮ Extended controls → Virtual sensors` panel drives:

    adb emu sensor set acceleration 0:0:9.81     # -> LEVEL ✓
    adb emu sensor set acceleration 3.5:0:9.2    # -> Tilted, adjust

Both branches of the level test were confirmed this way. If your instructor
insists the panel itself appears in the picture, retake just this one by hand:
open `⋮ Extended controls → Virtual sensors`, set `Device orientation` /
acceleration until `LEVEL ✓` shows, and capture the desktop with both windows
side by side. `07-branch.png` and `08-shake-capture.png` were desktop captures,
so the same technique works.

## How the other shots were produced

`08-shake-capture.png` is a side-by-side composite of two genuine captures: the
emulator window, and a terminal window showing the Logcat buffer. It is
composited rather than grabbed as one desktop screenshot because other windows
(Android Studio, a browser) kept covering the emulator mid-capture.

The shake was produced through the emulator's virtual accelerometer, and 71
`Shake capture` events were logged in total:

    adb emu sensor set acceleration 0:0:25   # |25 - 9.81| = 15.19 > 12  -> shake

The Logcat pane shows `adb logcat -d -s FieldKit` (dumped, not live-tailed) so
the frame stays stable. The last six events are spaced 1510–1536 ms apart,
which shows the 1500 ms cool-down in TODO 13b gating repeat captures, and the
thumbnail advances to a new filename (`shot_….jpg`) each time.

Location for `06-location.png` was set to Cagayan de Oro (note `geo fix` takes
**longitude first**):

    adb emu geo fix 125.6890 8.4822

Granting camera for `04-camera-photo.png` was done through the real Settings UI
(app info → Permissions → Camera → *Allow only while using the app* → back), not
via `adb`, precisely to prove the handout's hardest requirement: the live
preview appears **without restarting the app**, because the permission state is
re-read on resume.

The **Approximate** path was verified separately on a clean install: choosing
Approximate gives `Location access: Approximate` and coordinates with `±2000 m`
accuracy, versus `±5 m` for Precise — so the two paths genuinely differ.

## Runtime verification of the TODOs

Every TODO was exercised on the device, not just compiled:

- **1** — all four declarations in the merged manifest; the Android 12+
  precise/approximate dialog confirmed both location permissions are declared.
- **2, 3** — all four gate states reached in order: NotAsked → NeedsRationale →
  Denied → Granted. Notably `asked` correctly separates Denied from NotAsked.
- **4** — `openAppSettings()` verified by reaching the real App info page.
- **5, 6** — live `x/y/z` readout; flat → `LEVEL ✓`, tilted → `Tilted, adjust`.
- **7, 8, 9** — 189 KB JPEG written to cache, thumbnail rendered from it.
- **10, 11, 12** — fused fix resolved to `8.48220, 125.68900` at `±5 m`.
- **13** — `Shake capture` logged 71 times, spaced 1510 ms and 1536 ms apart,
  which shows the 1500 ms cool-down actually gating repeats; the thumbnail
  advanced to a new filename.
- **14** — torch correctly stays hidden: the emulator has no flash unit.

## Important: the application id is not `edu.liceo.fieldkit`

This project reuses the existing module, so the installed package id is the
module's original one:

    applicationId = com.example.capinpuyan

The **code** package is `edu.liceo.fieldkit`, which is what the lecture and
this handout refer to. So the handout's reset command has to be adjusted:

    # handout says:            use instead:
    adb shell pm clear edu.liceo.fieldkit
    adb shell pm clear com.example.capinpuyan

Launching the app directly (handy if you ever need to skip the home screen):

    adb shell am start -n com.example.capinpuyan/edu.liceo.fieldkit.MainActivity

Only `edu.liceo.fieldkit.MainActivity` holds the MAIN/LAUNCHER filter, so there
is a single icon on the home screen.

## Reproducing a capture

Reset between the permission-state walkthroughs:

    adb shell pm clear com.example.capinpuyan

Then walk the camera states in order (1 fresh install → 2 Don't allow →
3 Try again + Don't allow → Settings → 4 preview and photo).

Capture on Windows. Prefer `screencap` + `pull` over `exec-out` redirection,
because PowerShell 5.1 corrupts binary output on `>`:

    adb shell screencap -p /sdcard/s.png
    adb pull /sdcard/s.png screenshots/01-camera-dialog.png

## Bonus checklist

The bonus (TODO 13, 14) only counts if TODOs 1–12 all run. `Torch` stays hidden
on the emulator because it has no flash unit — that is the correct behaviour,
and `09-torch.png` needs a real phone.

## A known edge case (not a crash)

Tapping **Take photo** in the instant after the preview is first shown — before
CameraX has finished binding — logs:

    ImageCaptureException: Not bound to a valid Camera

It is handled by the `onError` callback as a log line, not a crash, and a retry
a moment later succeeds. This is inherent to building `ImageCapture` with
`remember { … }` before the `ProcessCameraProvider` is bound, as the handout
specifies.
