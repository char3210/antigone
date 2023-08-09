# Antigone

Fixes a rare server deadlock in Minecraft 1.16.1 caused by a strider spawned during generation trying to spawn with a zombified piglin as a passenger that is a baby chicken jockey,
leading to the game dismounting the zombified piglin from the chicken and creating a dependency loop in the chunk generation