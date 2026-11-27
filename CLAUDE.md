# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

FTC (FIRST Tech Challenge) robot controller app for team ByteBusters, built on the official FTC SDK 12.0 for the 2026–2027 **BIOBUZZ** season. It is an Android app that runs on the Control Hub; there is no desktop runtime and no unit tests. Code is verified by building and deploying to the robot.

## Build

```powershell
.\gradlew.bat :TeamCode:assembleDebug     # compile/build the APK (main check that code is valid)
.\gradlew.bat :TeamCode:installDebug      # deploy to a connected Control Hub (adb over USB or Wi-Fi)
```

- Java source/target is **1.8** (`build.common.gradle`). Lambdas work, but `var`, records, switch expressions and newer APIs do not.
- `build.gradle`, `build.common.gradle` and `FtcRobotController/` are SDK-owned. Leave them alone unless upgrading the SDK. Team dependencies go in `build.dependencies.gradle`, which also pulls Pedro Pathing (`com.pedropathing.*`) from `repo.dairy.foundation`.
- All team code lives under `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`.

## Architecture

- **OpModes** extend `OpMode` (iterative: `init`/`start`/`loop`). They show up on the Driver Station through `@TeleOp` / `@Autonomous` annotations. `BioBuzzTeleOp/` holds driver-controlled modes and drive/intake test modes; `BioBuzzAuto/` holds autonomous modes.
- **`Mechanisms/`** holds hardware wrapper classes (`MecanumDrive`, `Intake`, the older `ByteBustersBee`). Each one has an `init(HardwareMap)` that looks up devices by config name, plus setter-style control methods. OpModes create these as fields and call `init(hardwareMap)` in `init()`.
- **Hardware config names must match the Robot Configuration on the Control Hub exactly.** Drive: `motor_fl`, `motor_fr`, `motor_bl`, `motor_br`, `imu`, `pinpoint`. Intake: `motor_intake_front`, `motor_intake_back`, `motor_shooter_pollen`, `motor_shooter_nectar`, `servo_lift_pollen`, `servo_lift_nectar` (CRServos). Sensors: `sensor_distance` (`Mechanisms/TestDistance`).
- An OpMode's folder doesn't decide whether it is TeleOp or Auto; the annotation does. For example, `BioBuzzAuto/HelloWorldBioBuzz` is a `@TeleOp`.
- **Two separate drive paths:**
  - Most TeleOps (`BioBuzzTeleOp`, `MecanumFieldOrientedOpMode`) drive the wheels directly through `Mechanisms/MecanumDrive`, with field-relative control from the hub IMU (`driveFieldRelative`). `ExampleTeleOp` instead drives through the Pedro `Follower` (`ManualDrive.fieldCentric`), using the Pinpoint heading seeded from `OpModeStorage`.
  - Autonomous uses **Pedro Pathing** through `pedro/Constants.create(hardwareMap)`, which builds a `Follower` from a GoBilda Pinpoint localizer, a Mecanum drivetrain config and a Foresight controller. Autos build `Path`s with `PoseFactory`/`Paths.line` and run them as Ivy commands (`Scheduler`, `sequential`, `follow`). Call `Scheduler.reset()` in `init()` and `schedule(...)` in `start()`; see `BioBuzzAuto/ExampleAuto.java`.
  - Motor directions are set in both places (`MecanumDrive.init` and `Constants.drivetrainConfig`). Keep them consistent when changing either one. As of this writing they **disagree on front-left** (`MecanumDrive`: REVERSE, `Constants`: FORWARD). Check which one is correct on the robot before "fixing" either.
- **`pedro/Constants.java`** holds the tuned values: Pinpoint pod offsets/directions and Foresight gains. These values come from running the tuners, so don't hand-edit them casually. The `CoaxialPodConfig` swerve blocks are unused leftovers.
- **`pedro/Tuning.java`** registers tuning procedures with `@Tuner` static methods, which the `com.pedropathing:tuning` library exposes on the Driver Station. The procedure implementations are in `pedro/procedures/`.
- **`Mechanisms/OpModeStorage`** is a static holder that carries the robot's end pose from auto into TeleOp.
