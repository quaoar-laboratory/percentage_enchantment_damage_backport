Mod Info:
=======
Backport of DMK Multiplicative Enchantment Damage mod to 1.20.1, adjusted specifically for Apocalyptic Trilogy (APT)
an upcoming modpack I'm working on.

Changes Sharpness, Smite, Bane of Arthropods, and Impaling damage calculations to be percentage based. This mod aims to
lessen the gap between enchanted heavy vs light weapons.

Mods often add "fast and weak" vs "slow and powerful" and even 2-handed weapons. When the additional damage from
enchantments is all linear, the balance falls apart and fast weapons almost always win out in DPS. Because of the flat
additions, the % DPS increase for 1 handed is much greater than 2 handed due to faster attack rate.

This mod changes the following enchantment damage modifiers (Based on Vanilla scaling at 6 damage from a lighter weapon,
Knives):

1. Sharpness: From +1 damage at level 1, 0.5 per additional level -> +16.8% level 1, + 8.3% per additional. (x1.5 at
   level 5)
2. Bane of Arthropods, Smite: +41.6% level 1, +41.6% per additional level. (x2.66 base damage at level 5 vs specific
   mobs)

Fully configurable, so you can fine tune the balance:

1. Whether to run the multiplier logic at all for this enchant.
2. Whether to override the default enchantment behavior (undo flat additions)
3. What the first level multiplier is.
4. What each additional level adds to that multiplier.

Keep in mind this stacks on top of crits (e.g. 1.5x from crit times 2.65x)

Note: For modded enchantments from my experience (Illager's Bane and Sculk's Smite, will vary per mod), they don't have
hardcoded damage scaling logic and will work through a Data Pack.
Replace their damage minecraft:add effect(s) with the following:
"type": "minecraft:multiply",
"factor": {
"type": "minecraft:linear",
"base": 1.416,
"per_level_above_first": 0.416
}
I recommend unzipping the mod(s) jar to find the data folder path.
