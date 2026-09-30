# Screenshots — Lab Activity 11: LiceoFieldKit

The seven required PNGs still need to be captured by hand on an emulator.
This folder does not yet contain them. See "How to capture" below.

| File | What it must show |
|---|---|
| `01-camera-dialog.png` | The system camera permission dialog over your app |
| `02-rationale.png` | Your reason text and the "Try again" button |
| `03-open-settings.png` | The blocked message and the "Open Settings" button |
| `04-camera-photo.png` | Live preview, thumbnail and the "Saved:" line |
| `05-level.png` | Level card with numbers, and the Virtual sensors window beside the emulator |
| `06-location.png` | Coordinates, the ± accuracy and "Location access: Precise" |
| `07-branch.png` | Terminal showing `git branch` with `* lab-activity-11` and your last commit |

`08-shake-capture.png` is the bonus shot (Logcat line `Shake capture` plus the
new thumbnail). `09-torch.png` is optional and needs a real phone.

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

## How to capture

Reset between the permission-state walkthroughs:

    adb shell pm clear com.example.capinpuyan

Then walk the camera states in order (1 fresh install -> 2 Don't allow ->
3 Try again + Don't allow -> Settings -> 4 preview and photo).

Capture on Windows. Prefer `screencap` + `pull` over `exec-out` redirection,
because PowerShell 5.1 corrupts binary output on `>`:

    adb shell screencap -p /sdcard/s.png
    adb pull /sdcard/s.png screenshots/01-camera-dialog.png

For `05-level.png`, open the emulator's
`⋮ Extended controls -> Virtual sensors` panel next to the emulator window and
drag the phone until the numbers move and `LEVEL ✓` appears; the panel must be
visible in the shot.

For `06-location.png`, in
`⋮ Extended controls -> Location` search "Cagayan de Oro", click
`Set Location`, then tap `Tag my location` and choose `Precise`.

Then re-run `adb shell pm clear com.example.capinpuyan`, tap again and choose
`Approximate` to confirm the label changes to `Approximate`.

For `08-shake-capture.png`, watch Logcat (`adb logcat -s FieldKit`) and shake
with a quick drag in `Virtual sensors`; you want the `Shake capture` line
alongside the refreshed thumbnail.

## Bonus checklist

The bonus (TODO 13, 14) only counts if TODOs 1-12 all run. `Torch` stays hidden
on the emulator because it has no flash unit -- that is the correct behaviour,
and `09-torch.png` needs a real phone.