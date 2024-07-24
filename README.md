<a href="#">
  <p align="center">
    <img src="https://raw.githubusercontent.com/ZotyDev/ChargingWinds/main/branding/title.png" alt="ChargingWinds Title">
  </p>
</a>

A Minecraft Spigot plugin for 1.21 that lets you customize the Wind Charge projectile, more specifically:
- Projectile speed
- Explosion power.
- What particle to spawn.
- How many particles to spawn.

> [!NOTE]
> The implementation is not 100% exact, since I could not find a way to replicate certain behaviors.

> [!CAUTION]
> I do not recommend this plugin to be used on an important server, I just created it for a 5-day task, so I cannot guarantee it will be maintained.

# Config

You can set the following values in the config:

## `velocity`
Changes the velocity of the projectile, defaults to `1.0`.

## `power`
Changes the explosion power, defaults to `1.2`.

## `particle`
Changes the particle that will spawn, defaults to `GUST_EMITTER_SMALL`.

## `particle-amount`
Changes the amount of particles that will spawn, defaults to `1`.