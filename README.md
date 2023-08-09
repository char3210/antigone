# Antigone

Fixes a rare server deadlock in Minecraft 1.16.1. 
The bug is caused by a strider spawned during generation (population) as the 2nd+ in a pack, which bypasses the intended disabling of chicken jockeys on striders.
If the strider tries to spawn with a zombified piglin as a passenger that is a baby chicken jockey, the game dismounts the zombified piglin from the chicken, looking for a solid block for the zombified piglin to land on, but this requires the chunk to be fully generated. 
This creates a dependency loop in the chunk generation, as the generation of the chunk depends on itself being fully generated. 
